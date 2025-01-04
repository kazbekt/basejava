package com.urise.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.urise.webapp.model.Resume;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public abstract class AbstractArrayStorageTest {
    protected Storage storage;

    abstract public void createStorage();

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        createStorage();
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        //метод update еще не функционален
    }

    @Test
    public void getAll() throws Exception {
        assertEquals(new Resume[]{new Resume("uuid1"),
                new Resume("uuid2"),
                new Resume("uuid3")}, storage.getAll());
    }

    @Test
    public void save() throws Exception {
        Resume test = new Resume("uuid0");
        storage.save(test);
        assertEquals(test, storage.get("uuid0"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete("uuid2");
        storage.get("uuid2");
    }

    @Test
    public void get() throws Exception {
        Resume test = new Resume("uuid3");
        assertEquals(test, storage.get("uuid3"));

    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void StorageException() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                Resume resume = new Resume();
                storage.save(resume);
            }
        } catch (Exception e) {
            Assert.fail("Переполнение произошло рано: " + e.getMessage());
        }
        try {
            storage.save(new Resume(String.valueOf(AbstractArrayStorage.STORAGE_LIMIT + 1)));
        } catch (StorageException e) {
        } catch (Exception e) {
            Assert.fail("При переполнении выбрасывается неправильное исключение " + e.getMessage());
        }
    }
}