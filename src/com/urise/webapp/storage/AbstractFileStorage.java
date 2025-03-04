package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class AbstractFileStorage extends AbstractStorage<File>{
    private final File storage;

    private AbstractFileStorage(File storage){
        Objects.requireNonNull(storage, "directory must not be null");
        this.storage = storage;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(storage,uuid);
    }

    @Override
    protected void doUpdate(Resume r, File searchKey) {

    }

    @Override
    protected boolean isExist(File searchKey) {
        String[] files = storage.list();
        if (files != null) {
            for (String file : files) {
                if (file.equals(searchKey.getName())) {
                    return true;
                }
            }}
        return false;
    }

    @Override
    protected void doSave(Resume r, File searchKey) {

    }

    @Override
    protected Resume doGet(File searchKey) {
        return null;
    }

    @Override
    protected void doDelete(File searchKey) {

    }

    @Override
    protected List<Resume> doCopyAll() {
        return null;
    }

    @Override
    public List<Resume> getAllSorted() {
        return super.getAllSorted();
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }
}
