package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 100000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (!isExisting(index)) {
            System.out.println("Резюме " + uuid + " в хранилище не содержится");
            return null;
        }
        return storage[index];
    }

    protected abstract boolean isExisting(int index);

    protected abstract int getIndex(String uuid);

}
