package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void addResume(Resume r, int index) {
        int insertionIndex = -(index + 1);
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size);
        storage[insertionIndex] = r;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return  Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
