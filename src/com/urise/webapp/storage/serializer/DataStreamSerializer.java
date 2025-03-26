package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.model.Resume.ContactType;


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @FunctionalInterface
    private interface ConsumerExMap<K, V> {
        void accept(K key, V val) throws IOException;
    }

    @FunctionalInterface
    private interface ConsumerExList<T> {
        void accept(T t) throws IOException;
    }

    private <K, V> void writeMapWithException(Map<K, V> map, ConsumerExMap<K, V> consumer) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            consumer.accept(entry.getKey(), entry.getValue());
        }
    }

    private <T> void writeListWithException(List<T> list, ConsumerExList<T> consumer) throws IOException {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            dos.writeInt(r.getContacts().size());
            writeMapWithException(r.getContacts(), (ContactType key, String val) -> {
                dos.writeUTF(key.name());
                dos.writeUTF(val);
            });

            dos.writeInt(r.getSections().size());
            writeMapWithException(r.getSections(), (SectionType type, Section section) -> {
                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) section).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) section).getItems();
                        dos.writeInt(list.size());
                        writeListWithException(list, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> list = ((OrganizationSection) section).getOrganizations();
                        dos.writeInt(list.size());
                        writeListWithException(list, org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                            List<Organization.Period> periods = org.getPeriods();
                            dos.writeInt(periods.size());
                            writeListWithException(periods, per -> {
                                        dos.writeUTF(per.getStartDate().toString());
                                        dos.writeUTF(per.getEndDate().toString());
                                        dos.writeUTF(per.getTitle());
                                        dos.writeUTF(per.getDescription());
                                    }
                            );
                        });
                    }
                }
            });
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