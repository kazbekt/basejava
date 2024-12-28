package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String uuid) {
        super("Резюме " + uuid + " в хранилище не содержится", uuid);
    }
}
