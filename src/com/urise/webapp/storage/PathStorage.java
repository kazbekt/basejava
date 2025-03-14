package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PathStorage extends AbstractPathStorage {
    private StreamSerializer serializer;

    protected PathStorage(String dir) {
        super(dir);
        serializer = new ObjectStreamSerializer();
    }

    @Override
    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        serializer.doWrite(resume, os);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}
