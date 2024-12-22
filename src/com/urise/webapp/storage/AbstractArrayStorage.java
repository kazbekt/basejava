package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 3;
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

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище заполнено. Добавление резюме невозможно");
            return;
        }
        int index = getIndex(r.getUuid());
        if (isExisting(index)) {
            System.out.println("Резюме " + r.getUuid() + "ранее добавлено в хранилище");
        } else {
            addResume(r, index);
            size++;
        }
    }

    protected void addResume(Resume r, int index) {
        storage[size] = r;
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
        int index = getIndex(uuid);
        if (isExisting(index)) {
            deleteResume(index);
            storage[size - 1] = null;
            System.out.println("Резюме " + uuid + " удалено из хранилища");
            size--;
        } else {
            System.out.println("Резюме " + uuid + " в хранилище не содержится");
        }
    }

    protected void deleteResume(int index) {
        storage[index] = storage[size - 1];
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
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
