package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    private int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void save(Resume r) {
        if (storageSize == storage.length) {
            System.out.println("Хранилище заполнено. Добавление резюме невозможно");
            return;
        }
        if (checkResumePresence(r)) {
            System.out.println("Резюме " + r.getUuid() + "ранее добавлено в хранилище");
            return;
        }
        storage[storageSize++] = r;
    }

    public void update(Resume r, String uuidUpdate) {
        if (!checkResumePresence(r)) {
            System.out.println("Резюме " + r.getUuid() + " в хранилище не содержится");
            return;
        }
        if (checkUuidPresence(uuidUpdate) == null) {
            r.setUuid(uuidUpdate);
        } else {
            System.out.println("Обновление резюме " + r.getUuid() + " неуникальным идентификатором невозможно");
        }
    }

    public Resume get(String uuid) {
        Resume r = checkUuidPresence(uuid);
        if (r == null)
            System.out.println("Резюме " + uuid + " в хранилище не содержится");
        return r;
    }

    public void delete(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[storageSize - 1];
                storage[storageSize - 1] = null;
                System.out.println("Резюме " + uuid + " удалено из хранилища");
                storageSize--;
                return;
            }
        }
        System.out.println("Резюме " + uuid + " в хранилище не содержится");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, storageSize);
    }

    public int size() {
        return storageSize;
    }

    private boolean checkResumePresence(Resume r) {
        if (storageSize != 0) {
            for (int i = 0; i < storageSize; i++) {
                if (storage[i].getUuid().equals(r.getUuid())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Resume checkUuidPresence(String uuid) {
        if (storageSize != 0) {
            for (int i = 0; i < storageSize; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return storage[i];
                }
            }
        }

        return null;
    }
}
