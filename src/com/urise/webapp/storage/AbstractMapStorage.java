package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage extends AbstractStorage {

    protected static final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        ArrayList<Resume> list = new ArrayList<>(storage.values());
        list.sort(null);
        return list;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.put((String) searchKey, r);
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doDelete(Object searchKey);

}
