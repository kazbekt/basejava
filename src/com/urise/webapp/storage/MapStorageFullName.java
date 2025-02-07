package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorageFullName extends AbstractStorage {

    protected static final Map<String, Resume> storage = new HashMap<>();

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> list = new ArrayList<>(storage.values());
        list.sort(null);
        return list;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey instanceof Resume && storage.containsValue(searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume r = (Resume) searchKey;
        storage.remove(r.getUuid());
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
