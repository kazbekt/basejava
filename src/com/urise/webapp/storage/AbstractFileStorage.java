package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File storage;

    private AbstractFileStorage(File storage) {
        Objects.requireNonNull(storage, "directory must not be null");

        if (!storage.isDirectory()) {
            throw new IllegalArgumentException(storage + " is not a directory");
        }

        if (!storage.canRead() || !storage.canWrite()) {
            throw new IllegalArgumentException("No read/write access to directory: " + storage);
        }
        this.storage = storage;
    }

    protected abstract void doWrite(Resume resume, File storage) throws IOException;

    protected abstract Resume doRead(File searchKey) throws IOException;

    @Override
    protected File getSearchKey(String uuid) {
        return new File(storage, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File searchKey) {
        try {
            doWrite(r, searchKey);
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
            return doRead(searchKey);
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
        File[] files = storage.listFiles();
        if (files == null) {
            throw new StorageException("Failed to copy storage", storage.getName());
        } else {
            List<Resume> storageCopy = new ArrayList<>();
            for (File file : files) {
                storageCopy.add(doGet(file));
            }
            return storageCopy;
        }
    }


    @Override
    public void clear() {
        File[] files = storage.listFiles();
        if (files == null) {
            throw new StorageException("Failed to clear storage", storage.getName());
        } else {
            for (File file : files) {
                doDelete(file);
            }
        }
    }


    @Override
    public int size() {
        String[] files = storage.list();
        if (files == null) {
            throw new StorageException("Failed to return storage size", storage.getName());
        } else {
            return files.length;
        }
    }
}
