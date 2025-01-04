package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.*;

public class ArrayStorageTest extends AbstractArrayStorageTest{
    @Override
    public void createStorage() {
        storage = new ArrayStorage();
    }
}