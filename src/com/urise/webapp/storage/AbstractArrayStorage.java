package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    public static final int STORAGE_LIMIT = 100;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполнено", r.getUuid());
        }
        insertElement(r, (Integer) searchKey);
        size++;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage[(Integer) searchKey] = r;
    }

    @Override
    protected void doDelete(Object searchKey) {
        fillDeletedElement((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

}
