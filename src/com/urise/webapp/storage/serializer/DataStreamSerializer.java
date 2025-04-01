package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @FunctionalInterface
    private interface ConsumerEx<T> {
        void accept(T t) throws IOException;
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> c, ConsumerEx<T> consumer) throws IOException {
        dos.writeInt(c.size());
        for (T t : c) {
            consumer.accept(t);
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeWithException(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(dos, r.getSections().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) entry.getValue()).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) entry.getValue()).getItems();
                        writeWithException(dos, list, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> list = ((OrganizationSection) entry.getValue()).getOrganizations();
                        writeWithException(dos, list, org -> {
                            dos.writeUTF(org.getHomePage().getName());
                            dos.writeUTF(org.getHomePage().getUrl());
                            List<Organization.Period> periods = org.getPeriods();
                            writeWithException(dos, periods, per -> {
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

    @FunctionalInterface
    private interface CustomInterface {
        void read() throws IOException;
    }

    private void readWithException(DataInputStream dis, CustomInterface reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readWithException(dis,
                    () -> resume.addContact(Resume.ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithException(dis,
                    () -> {
                        SectionType type = SectionType.valueOf(dis.readUTF());

                        switch (type) {
                            case OBJECTIVE, PERSONAL -> resume.addSection(type, new TextSection(dis.readUTF()));
                            case ACHIEVEMENT, QUALIFICATIONS -> {
                                List<String> items = new ArrayList<>();
                                readWithException(dis, () -> items.add(dis.readUTF()));
                                resume.addSection(type, new ListSection(items));
                            }
                            case EXPERIENCE, EDUCATION -> {
                                List<Organization> organizations = new ArrayList<>();
                                readWithException(dis, () -> {
                                    Link link = new Link(dis.readUTF(), dis.readUTF());
                                    List<Organization.Period> periods = new ArrayList<>();
                                    readWithException(dis, () -> periods.add(new Organization.Period(LocalDate.parse(dis.readUTF()),
                                            LocalDate.parse(dis.readUTF()),
                                            dis.readUTF(), dis.readUTF())));
                                    organizations.add(new Organization(link, periods));
                                });
                                resume.addSection(type, new OrganizationSection(organizations));
                            }
                        }
                    });
            return resume;
        }
    }
}