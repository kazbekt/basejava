package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (!isExisting(index)) {
            System.out.println("Резюме " + r.getUuid() + " в хранилище не содержится");
        } else {
            storage[index] = r;
            System.out.println("Резюме " + r.getUuid() + " заменено на " + r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (!isExisting(index)) {
            System.out.println("Резюме " + uuid + " в хранилище не содержится");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected boolean isExisting(int index) {
        return index >= 0;
    }

    protected int getIndex(String uuid) {
        return 0;
    }

}
