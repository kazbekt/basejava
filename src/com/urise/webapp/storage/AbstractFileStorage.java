package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
        if (!isExist(searchKey)) {
            throw new StorageException(r.getUuid(), "Resume does not exist");
        }
        try {
            doWrite(r, searchKey);
        } catch (IOException e) {
            throw new StorageException(r.getUuid(), "Failed to update resume: ");
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
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException(r.getUuid(), "Filed to save resume");
        }
    }

    @Override
    protected Resume doGet(File searchKey) {
        try {
            return doRead(searchKey);
        } catch (IOException e) {
            throw new StorageException(searchKey.getName(), "Failed to read resume");
        }
    }

    @Override
    protected void doDelete(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException(searchKey.getName(), "Failed to delete resume");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = storage.listFiles();
        if (files != null) {
            List<Resume> storageCopy = new ArrayList<>();
            for (File file : files) {
                storageCopy.add(doGet(file));
            }
            return storageCopy;
        } else {
            throw new StorageException(storage.getName(), "Failed to copy storage");
        }
    }


    @Override
    public void clear() {
        File[] files = storage.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }else {
            throw new StorageException(storage.getName(), "Failed to clear storage");
        }
    }

    @Override
    public int size() {
        String[] files = storage.list();
        if (files!= null) {
            return files.length;
        } else {
            throw new StorageException(storage.getName(), "Failed to return storage size");
        }
    }
}
