package com.urise.webapp.model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName = "NoName";

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Resume(){
        this(UUID.randomUUID().toString());
    }



    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid + " : " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        int compare = this.getFullName().compareTo(o.getFullName());
        return (compare !=0) ? compare:uuid.compareTo(o.getUuid());
    }

}
