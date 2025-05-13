package com.urise.webapp.storage;

import com.urise.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }

    @Override
    public void get() {
        super.get();
    }

    @Override
    public void delete() {
        super.delete();
    }

    @Override
    public void save() {
        super.save();
    }

    @Override
    public void getAllSorted() {
        super.getAllSorted();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void size() {
        super.size();
    }

}