package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;


public class HashMapStorage extends AbstractStorage {

    protected static final Map<String, Resume> storage = new HashMap<>();

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Object getSearchKey(Object searchKey) {
        for (String key : storage.keySet()) {
            if (key.equals(searchKey)) {
                return key;
            }
        }
        return null;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey.toString());
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.replace(searchKey.toString(), r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(searchKey.toString());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
