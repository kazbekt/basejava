package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path storage;

    protected AbstractPathStorage(String dir) {
        Objects.requireNonNull(dir, "directory must not be null");
        storage = Paths.get(dir);
        if (!Files.isDirectory(storage)) {
            throw new IllegalArgumentException(storage + " is not a directory");
        }
        if (!Files.isReadable(storage) || !Files.isWritable(storage)) {
            throw new IllegalArgumentException("No read/write access to directory: " + storage);
        }
    }

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected Path getSearchKey(String uuid) {
        return storage.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path searchKey) {
        try {
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Failed to write resume", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected void doSave(Resume r, Path searchKey) {
        doUpdate(r, searchKey);
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            if (Files.size(searchKey) == 0) {
                throw new StorageException("File is empty", searchKey.getFileName().toString());
            }
            return doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Failed to read resume", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Failed to delete resume", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();

        try (DirectoryStream<Path> files = Files.newDirectoryStream(storage)) {
            for (Path path : files) {
                list.add(doGet(path));
            }
        } catch (IOException e) {
            throw new StorageException("Failed to copy storage", storage.getFileName().toString());
        }
        return list;
    }

    @Override
    public void clear() {
        try {
            Files.list(storage).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Failed to clear storage", storage.getFileName().toString());
        }
    }


    @Override
    public int size() {
        try {
            return (int) Files.list(storage).count();
        } catch (IOException e) {
            throw new StorageException("Failed to return storage size", storage.getFileName().toString());
        }
    }
}
