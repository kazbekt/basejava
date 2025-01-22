package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void update(Resume r) {
        Object searchKey = r.getUuid();
        doUpdate(getNotExistingSearchKey(searchKey), r);
    }

    @Override
    public final void save(Resume r) {
        Object searchKey = r.getUuid();
        doSave(r, getNotExistingSearchKey(searchKey));
    }

    @Override
    public final Resume get(String uuid) {
        return doGet(getExistingSearchKey(uuid));
    }

    @Override
    public final void delete(String uuid) {
        doDelete(getExistingSearchKey(uuid));
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    protected abstract int isExist(Object searchKey);

    private int getExistingSearchKey(Object searchKey) {
        int index = isExist(searchKey);
        if (index < 0) {
            throw new NotExistStorageException(searchKey.toString());
        }
        return index;
    }

    private int getNotExistingSearchKey(Object searchKey) {
        int index = isExist(searchKey);
        if (index >= 0) {
            throw new ExistStorageException(searchKey.toString());
        }
        return index;
    }

    protected abstract void doSave(Resume r, int index);

    protected abstract Resume doGet(int index);

    protected abstract void doUpdate(int index, Resume r);

    protected abstract void doDelete(int index);

}


