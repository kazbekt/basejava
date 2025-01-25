package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void update(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    @Override
    public final void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object getSearchKey(Object searchKey);

    private Object getExistingSearchKey(Object searchKey) {
        Object key = getSearchKey(searchKey);
        if (!isExist(key)) {
            throw new NotExistStorageException(searchKey.toString());
        }
        return key;
    }

    private Object getNotExistingSearchKey(Object searchKey) {
        Object key = getSearchKey(searchKey);
        if (isExist(key)) {
            throw new ExistStorageException(searchKey.toString());
        }
        return key;
    }

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract void doDelete(Object searchKey);

}


