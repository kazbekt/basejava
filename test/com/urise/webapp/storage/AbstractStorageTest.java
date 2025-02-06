package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    private static final String UUID_0 = "uuid0";
    private static final String UUID_0_name = "Ivan Petrov";
    private static final String UUID_1 = "uuid1";

    private static final String UUID_1_name = "Petr Ivanov";
    private static final String UUID_2 = "uuid2";

    private static final String UUID_2_name = "John Smith";
    private static final String UUID_3 = "uuid3";

    private static final String UUID_3_name = "Benny Dict";
    public static final String DUMMY = "dummy";



    private static final Resume r0 = new Resume(UUID_0);
    private static final Resume r1 = new Resume(UUID_1);
    private static final Resume r2 = new Resume(UUID_2);
    private static final Resume r3 = new Resume(UUID_3);


    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(r1);
        r1.setFullName(UUID_1_name);
        storage.save(r2);
        r2.setFullName(UUID_2_name);
        storage.save(r3);
        r3.setFullName(UUID_3_name);
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
        assertArrayEquals(new Storage[0], storage.getAllSorted().toArray(new Resume[storage.size()]));
    }

    @Test
    public void update() {
        assertSame(r1, storage.get(UUID_1));
    }

    @Test
    public void getAllSorted() {
        Resume[] expected = new Resume[]{r1, r2, r3};
        Resume[] actual = storage.getAllSorted().toArray(new Resume[storage.size()]);
        Arrays.sort(actual);
        assertArrayEquals(expected, actual);
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

}
