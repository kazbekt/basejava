package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.model.ResumeTestData.*;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    public static final File STORAGE_DIR = Config.get().getStorageDir();

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
        Resume newResume = new Resume(UUID_1, "New Name");
        newResume.addContact(Resume.ContactType.PHONE, "newPHONE");
        newResume.addContact(Resume.ContactType.SKYPE, "newSKYPE");
        newResume.addContact(Resume.ContactType.EMAIL, "newEMAIL");
        newResume.addContact(Resume.ContactType.LINKEDIN, "newLINKEDIN");
        newResume.addContact(Resume.ContactType.GITHUB, "newGITHUB");
        newResume.addContact(Resume.ContactType.STACKOVERFLOW, "newSTACKOVERFLOW");
        newResume.addContact(Resume.ContactType.HOMEPAGE, "newHOMEPAGE");
        newResume.addSection(SectionType.OBJECTIVE, new TextSection("New Section"));
        newResume.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievement1", "Achievement2", "Achievement3", "Achievement4"));
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
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
        assertGet(ResumeTestData.filledResume(UUID_0, RESUME_0));

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
        Resume T1 = ResumeTestData.filledResume(UUID_1, RESUME_1);
        Resume T2 = ResumeTestData.filledResume(UUID_2, RESUME_2);
        Resume T3 = ResumeTestData.filledResume(UUID_3, RESUME_3);

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