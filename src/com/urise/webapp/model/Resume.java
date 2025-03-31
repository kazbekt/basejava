package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;

    private String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public void addContact(ContactType type, String value) {
        Objects.requireNonNull(type, "Contact type must not be null");
        Objects.requireNonNull(value, "Contact value must not be null");
        contacts.put(type, value);
    }

    public void addSection(SectionType type, Section section) {
        Objects.requireNonNull(type, "Section type must not be null");
        Objects.requireNonNull(section, "Section value must not be null");
        sections.put(type, section);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return "Resume{\n" +
                "  uuid='" + uuid + "',\n" +
                "  fullName='" + fullName + "',\n" +
                "  contacts=" + contacts + ",\n" +
                "  sections=" + sections +
                "}";
    }

    @Override
    public int compareTo(Resume o) {
        int compare = fullName.compareTo(o.fullName);
        return (compare != 0) ? compare : uuid.compareTo(o.uuid);
    }

    public enum ContactType {
        PHONE("Тел: "),
        SKYPE("Skype: "),
        EMAIL("Почта: "),
        LINKEDIN("Профиль LinkedIn: "),
        GITHUB("Профиль GitHub: "),
        STACKOVERFLOW("Профиль Stackoverflow: "),
        HOMEPAGE("Домашняя страница: ");

        private final String contactType;

        ContactType(String contactType) {
            this.contactType = contactType;
        }

        public String getContactType() {
            return contactType;
        }
    }
}