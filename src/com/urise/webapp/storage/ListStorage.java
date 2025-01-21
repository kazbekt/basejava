package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private static ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        Resume r = new Resume(uuid);
        if (storage.contains(r)) {
            return storage.indexOf(r);
        }
        return -1;
    }

    @Override
    protected boolean isExisting(int index) {
        return false;
    }

    @Override
    protected Resume doGet(int index) {
        return storage.get(index);
    }

    @Override
    protected void doSave(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected void doUpdate(int index, Resume r) {
        storage.add(index, r);
    }

    @Override
    protected void doDelete(int index) {
        storage.remove(index);
    }
}
