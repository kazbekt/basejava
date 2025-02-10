package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;


public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey instanceof String uuid && storage.containsKey(uuid);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }


    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

}
