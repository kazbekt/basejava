package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest{
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }

    @Override
    public void size() {
        super.size();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void getAllSorted() {
        super.getAllSorted();
    }

    @Override
    public void save() {
        super.save();
    }

    @Override
    public void delete() {
        super.delete();
    }

    @Override
    public void get() {
        super.get();
    }

    @Override
    public void getNotExist() {
        super.getNotExist();
    }
}
