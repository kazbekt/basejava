package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
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