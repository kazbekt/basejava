package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage {
    private StreamSerializer serializer;

    protected FileStorage(File storage) {
        super(storage);
        serializer = new ObjectStreamSerializer();
    }

    @Override
    protected void doWrite(Resume resume, OutputStream file) throws IOException {
        serializer.doWrite(resume, file);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}
