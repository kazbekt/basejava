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

            dos.writeInt(r.getContacts().size());
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            dos.writeInt(r.getSections().size());
            for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                SectionType type = entry.getKey();
                dos.writeUTF(type.name());
                Section section = entry.getValue();

                switch (type) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) section).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) section).getItems();
                        dos.writeInt(list.size());
                        for (String s : list) {
                            dos.writeUTF(s);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> list = ((OrganizationSection) section).getOrganizations();
                        dos.writeInt(list.size());
                        for (Organization org : list) {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                            List<Organization.Period> periods = org.getPeriods();
                            dos.writeInt(periods.size());
                            for (Organization.Period per : periods) {
                                dos.writeUTF(per.getStartDate().toString());
                                dos.writeUTF(per.getEndDate().toString());
                                dos.writeUTF(per.getTitle());
                                dos.writeUTF(per.getDescription());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(Resume.ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sections = dis.readInt();
            for (int i = 0; i < sections; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());

                switch (type) {

                    case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int itemsSize = dis.readInt();
                        List<String> items = new ArrayList<>(itemsSize);
                        for (int j = 0; j < itemsSize; j++) {
                            items.add(dis.readUTF());
                        }
                        resume.addSection(type, new ListSection(items));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int orgsSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>();
                        for (int j = 0; j < orgsSize; j++) {
                            Link link = new Link(dis.readUTF(), dis.readUTF());
                            List<Organization.Period> periods = new ArrayList<>();
                            int periodsSize = dis.readInt();
                            for (int k = 0; k < periodsSize; k++) {
                                periods.add(new Organization.Period(LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()),
                                        dis.readUTF(), dis.readUTF()));
                            }
                            organizations.add(new Organization(link, periods));
                        }
                        resume.addSection(type, new OrganizationSection(organizations));
                    }
                }
            }
            return resume;
        }
    }
}