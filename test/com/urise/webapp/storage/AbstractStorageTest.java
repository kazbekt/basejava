package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.ResumeTestData;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    private static final String UUID_0 = "uuid0";
    private static final String RESUME_0 = "Name0";

    private static final String UUID_1 = "uuid1";
    private static final String RESUME_1 = "Name1";

    private static final String UUID_2 = "uuid2";
    private static final String RESUME_2 = "Name2";

    private static final String UUID_3 = "uuid3";
    private static final String RESUME_3 = "Name3";

    public static final String DUMMY = "dummy";




    private static final Resume r0 = ResumeTestData.filledResume(UUID_0, RESUME_0);
    private static final Resume r1 = ResumeTestData.filledResume(UUID_1, RESUME_1);
    private static final Resume r2 = ResumeTestData.filledResume(UUID_2, RESUME_2);
    private static final Resume r3 = ResumeTestData.filledResume(UUID_3, RESUME_3);


    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
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
        assertArrayEquals(new Storage[0], storage.getAllSorted().toArray(new Resume[storage.size()]));
    }

    @Test
    public void update() {
        assertSame(r1, storage.get(UUID_1));
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = new ArrayList<>(Arrays.asList(r1, r2, r3));
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }

    @Test
    public void save() {
        storage.save(r0);
        assertGet(new Resume(UUID_0, RESUME_0));
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
        Resume T1 = new Resume(UUID_1, RESUME_1);
        Resume T2 = new Resume(UUID_2, RESUME_2);
        Resume T3 = new Resume(UUID_3, RESUME_3);


        assertGet(T1);
        assertGet(T2);
        assertGet(T3);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

}
