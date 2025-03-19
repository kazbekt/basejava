package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.ObjectStreamSerializer;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final StreamSerializer serializer;
    private final File storage;

    protected FileStorage(File storage) {
        Objects.requireNonNull(storage, "directory must not be null");

        if (!storage.isDirectory()) {
            throw new IllegalArgumentException(storage + " is not a directory");
        }

        if (!storage.canRead() || !storage.canWrite()) {
            throw new IllegalArgumentException("No read/write access to directory: " + storage);
        }
        this.storage = storage;
        serializer = new ObjectStreamSerializer();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(storage, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File searchKey) {
        try {
            serializer.doWrite(r, new BufferedOutputStream(new FileOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Failed to write resume", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Filed to create file" + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected Resume doGet(File searchKey) {
        try {
            return serializer.doRead(new BufferedInputStream(new FileInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Failed to read resume", searchKey.getName(), e);
        }
    }

    @Override
    protected void doDelete(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("Failed to delete resume", searchKey.getName());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> storageCopy = new ArrayList<>();
        for (File file : getStorageFilesArray()) {
            storageCopy.add(doGet(file));
        }
        return storageCopy;
    }

    @Override
    public void clear() {
        for (File file : getStorageFilesArray()) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return getStorageFilesArray().length;
    }

    private File[] getStorageFilesArray() {
        File[] files = storage.listFiles();
        if (files == null) {
            throw new StorageException("Failed to read storage", storage.getName());
        } else {
            return files;
        }
    }
}
