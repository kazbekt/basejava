package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    private final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

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

    private Object getExistingSearchKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object key = getSearchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getAll();
        sortedList.sort(RESUME_COMPARATOR);
        return sortedList;
    }

    protected abstract List<Resume> getAll();

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract void doDelete(Object searchKey);

}


