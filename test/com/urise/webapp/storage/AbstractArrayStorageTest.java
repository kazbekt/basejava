package com.urise.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import com.urise.webapp.model.Resume;
import com.urise.webapp.exception.StorageException;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assert.fail("Переполнение произошло рано: " + e.getMessage());
        }
        //storage.save(new Resume(String.valueOf(AbstractArrayStorage.STORAGE_LIMIT + 1)));
        storage.save(new Resume());


    }
}