package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void clear() {
        /* Игорь, вот такие методы пустышки, нужно оставлять или удалять?
         * Как я понимаю, для удобства это нужно, а практического смысла нет.
         * Из таких в этом классе еще getAll и size. А в AbstractArrayStorage
         * соответственно вспомогательные, которые объявлены теперь тут - getIndex */
    }

    @Override
    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (!isExisting(index)) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            doUpdate(index, r);
        }
    }

    @Override
    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (isExisting(index)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(r, index);
        }
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (!isExisting(index)) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(index);
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (!isExisting(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(index);
        }
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    protected boolean isExisting(int index) {
        return index >= 0;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void doSave(Resume r, int index);

    protected abstract Resume doGet(int index);

    protected abstract void doUpdate(int index, Resume r);

    protected abstract void doDelete(int index);


}


