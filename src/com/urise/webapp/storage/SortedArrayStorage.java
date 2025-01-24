package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected void fillDeletedElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        int insertionIndex = -(index + 1);
        if (insertionIndex != size) {
            System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        }
        storage[insertionIndex] = r;
    }

    @Override
    protected Integer getSearchKey(Object searchKey) {
        searchKey = new Resume((String) searchKey);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
