package com.urise.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.urise.webapp.model.Resume;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private static final String  UUID_0 = "uuid0";
    private static final String  UUID_1 = "uuid1";
    private static final String  UUID_2 = "uuid2";
    private static final String  UUID_3 = "uuid3";
    public static final String DUMMY = "dummy";

    private static final Resume r0 = new Resume(UUID_0);
    private static final Resume r1 = new Resume(UUID_1);
    private static final Resume r2 = new Resume(UUID_2);
    private static final Resume r3 = new Resume(UUID_3);


    protected final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Storage[0], storage.getAll());
    }

    @Test
    public void update() {
        assertSame(r1, storage.get(UUID_1));
    }

    @Test
    public void getAll() {
        Resume[] expected = new Resume[]{r1, r2, r3};
        assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void save() {
        storage.save(r0);
        assertGet(new Resume(UUID_0));
        assertSize(4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        assertGet(r2);
    }

    @Test
    public void get() {
        assertGet(new Resume(UUID_1));
        assertGet(new Resume(UUID_2));
        assertGet(new Resume(UUID_3));
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                Resume resume = new Resume();
                storage.save(resume);
            }
        } catch (Exception e) {
            Assert.fail("Переполнение произошло рано: " + e.getMessage());
        }
        storage.save(new Resume(String.valueOf(AbstractArrayStorage.STORAGE_LIMIT + 1)));
    }
}