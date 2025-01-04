package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import static org.junit.Assert.*;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    @Override
    public void createStorage() {
        storage = new SortedArrayStorage();
    }
}