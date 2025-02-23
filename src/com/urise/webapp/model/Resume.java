package com.urise.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;

    private final String fullName;

    private final Map<ContactType, String> contacts = new HashMap<>(Map.of(
            ContactType.PHONE, "",
            ContactType.SKYPE, "",
            ContactType.EMAIL, "",
            ContactType.LINKEDIN, "",
            ContactType.GITHUB, "",
            ContactType.STACKOVERFLOW, "",
            ContactType.HOMEPAGE, ""
    ));

    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

//    private final Map<SectionType, Section> sections = new HashMap<>(Map.of(
//            SectionType.OBJECTIVE, null,
//            SectionType.PERSONAL, null,
//            SectionType.ACHIEVEMENT, null,
//            SectionType.QUALIFICATIONS, null,
//            SectionType.EXPERIENCE, null,
//            SectionType.EDUCATION, null
//    ));

    public Resume() {
        this(UUID.randomUUID().toString(), "");
        for (SectionType type : SectionType.values()) {
            sections.put(type, null);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + contacts +
                ", sections=" + sections +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        int compare = this.getFullName().compareTo(o.getFullName());
        return (compare != 0) ? compare : uuid.compareTo(o.getUuid());
    }

    public void setContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public void getContact(ContactType type) {
        contacts.get(type);
    }

    public void setSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    public void getSection(SectionType type) {
        sections.get(type);
    }

    enum ContactType {
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
