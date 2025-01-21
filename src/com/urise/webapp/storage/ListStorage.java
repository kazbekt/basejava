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
        for (int i = 0; i < storage.size(); i++) {
            String search = storage.get(i).getUuid();
            if (uuid.equals(search))
                return i;
        }
        return -1;
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
