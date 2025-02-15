package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;


public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected boolean isExist(String searchKey) {
        return searchKey instanceof String uuid && storage.containsKey(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

}
