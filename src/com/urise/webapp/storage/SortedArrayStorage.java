package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище заполнено. Добавление резюме невозможно");
        } else if (size == 0) {
            storage[0] = r;
            size++;
        } else {
            int index = getIndex(r.getUuid());
            if (index == 0) {
                System.out.println("Резюме " + r.getUuid() + "ранее добавлено в хранилище");
            } else {
                int insertionIndex = -(index + 1);
                for (int i = size; i > insertionIndex; i--) {
                    storage[i] = storage[i - 1];
                }
                storage[insertionIndex] = r;
                size++;
            }
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (isExisting(index)) {
            for (int i = index; i < size; i++) {
                storage[i] = storage[i + 1];
            }
            System.out.println("Резюме " + uuid + " удалено из хранилища");
            size--;
        } else {
            System.out.println("Резюме " + uuid + " в хранилище не содержится");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        int index = Arrays.binarySearch(storage, 0, size, searchKey);
        return index;
    }
}
