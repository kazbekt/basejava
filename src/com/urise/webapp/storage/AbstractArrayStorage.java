package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 3;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (!isExisting(index)) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage[index] = r;
            System.out.println("Резюме " + r.getUuid() + " заменено на " + r.getUuid());
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public final void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище переполнено", r.getUuid());
        }
        int index = getIndex(r.getUuid());
        if (isExisting(index)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertElement(r, index);
            size++;
        }
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (isExisting(index)) {
            fillDeletedElement(index);
            storage[size - 1] = null;
            System.out.println("Резюме " + uuid + " удалено из хранилища");
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (!isExisting(index)) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    protected boolean isExisting(int index) {
        return index >= 0;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);

    protected abstract int getIndex(String uuid);

}
