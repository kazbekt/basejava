package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.model.Resume.ContactType;


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                String sectionType = entry.getKey().name();
                dos.writeUTF(sectionType);
                Section section = entry.getValue();
                if (section instanceof TextSection) {
                    dos.writeUTF(((TextSection) section).getContent());
                } else if (section instanceof ListSection) {
                    List<String> list = ((ListSection) section).getItems();
                    dos.writeInt(list.size());
                    for (String s : list) {
                        dos.writeUTF(s);
                    }
                } else if (section instanceof OrganizationSection) {
                    List<Organization> list = ((OrganizationSection) section).getOrganizations();
                    dos.writeInt(list.size());
                    for (int i = 0; i < list.size(); i++) {
                        dos.writeUTF(list.get(i).getHomePage().getName());
                        dos.writeUTF(list.get(i).getHomePage().getUrl());
                        List<Organization.Period> periods = list.get(i).getPeriods();
                        dos.writeInt(periods.size());
                        for (int j = 0; j < periods.size(); j++) {
                            dos.writeUTF(periods.get(j).getStartDate().toString());
                            dos.writeUTF(periods.get(j).getEndDate().toString());
                            dos.writeUTF(periods.get(j).getTitle());
                            dos.writeUTF(periods.get(j).getDescription());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        System.out.println("НАЧАЛО ЧТЕНИЯ!!!");
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(Resume.ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            int sections = dis.readInt();
            for (int i = 0; i < sections; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                if (type == SectionType.OBJECTIVE || type == SectionType.PERSONAL) {
                    resume.addSection(type, new TextSection(dis.readUTF()));
                } else if (type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS) {
                    int itemsSize = dis.readInt();
                    List<String> items = new ArrayList<>(itemsSize);
                    for (int j = 0; j < itemsSize; j++) {
                        items.add(dis.readUTF());
                    }
                    resume.addSection(type, new ListSection(items));
                } else if (type == SectionType.EXPERIENCE || type == SectionType.EDUCATION) {
                    int organisationsSize = dis.readInt();
                    List<Organization> organizations = new ArrayList<>();
                    for (int j = 0; j < organisationsSize; j++) {
                        Organization organization = new Organization(dis.readUTF(), dis.readUTF());
                        int periods = dis.readInt();
                        for (int k = 0; k < periods; k++) {
                            organization.addPeriod(new Organization.Period(LocalDate.parse(dis.readUTF())
                                    , LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                        }
                        organizations.add(organization);
                    }
                    resume.addSection(type, new OrganizationSection(organizations));
                }
            }
            return resume;
        }
    }
}



