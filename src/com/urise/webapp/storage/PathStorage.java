package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final StreamSerializer serializer;
    private final Path storage;

    protected PathStorage(String dir, StreamSerializer serializer) {
        Objects.requireNonNull(dir, "directory must not be null");
        storage = Paths.get(dir);
        if (!Files.isDirectory(storage)) {
            throw new IllegalArgumentException(storage + " is not a directory");
        }
        if (!Files.isReadable(storage) || !Files.isWritable(storage)) {
            throw new IllegalArgumentException("No read/write access to directory: " + storage);
        }
        this.serializer = serializer;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return storage.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path searchKey) {
        try {
            serializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Failed to write resume", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.isRegularFile(searchKey);
    }

    @Override
    protected void doSave(Resume r, Path searchKey) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + searchKey, getFileName(searchKey), e);
        }
        doUpdate(r, searchKey);
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Failed to read resume", getFileName(searchKey), e);
        }
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Failed to delete resume", getFileName(searchKey), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getStorageList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getStorageList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getStorageList().count();
    }

    private Stream<Path> getStorageList() {
        try {
            return Files.list(storage);
        } catch (IOException e) {
            throw new StorageException("Storage read error", getFileName(storage), e);
        }
    }

    private static String getFileName(Path searchKey) {
        return searchKey.getFileName().toString();
    }
}
