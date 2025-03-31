package com.urise.webapp.storage.serializer;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

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
    private interface SupplierEx<T> {
        T read(DataInputStream dis) throws IOException;
    }

    private <T> void readCollection(
            DataInputStream dis,
            SupplierEx<T> reader,
            Consumer<T> adder
    ) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            adder.accept(reader.read(dis));
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readCollection(dis, entry -> new AbstractMap.SimpleEntry<>(
                    Resume.ContactType.valueOf(dis.readUTF()),
                    dis.readUTF()), entry -> resume.addContact(entry.getKey(), entry.getValue()));

            readCollection(dis,
                    s -> {
                        SectionType type = SectionType.valueOf(s.readUTF());
                        Section section = switch (type) {

                            case OBJECTIVE, PERSONAL -> new TextSection(dis.readUTF());

                            case ACHIEVEMENT, QUALIFICATIONS -> {
                                List<String> items = new ArrayList<>();
                                readCollection(dis, item -> dis.readUTF(),
                                        items::add);
                                yield new ListSection(items);
                            }

                            case EXPERIENCE, EDUCATION -> {
                                List<Organization> organizations = new ArrayList<>();
                                readCollection(dis, organization -> {
                                    Link link = new Link(dis.readUTF(), dis.readUTF());
                                    List<Organization.Period> periods = new ArrayList<>();
                                    readCollection(dis,
                                            period -> new Organization.Period(LocalDate.parse(dis.readUTF()),
                                                    LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()),
                                            periods::add);

                                    return new Organization(link, periods);
                                }, organizations::add);

                                yield new OrganizationSection(organizations);
                            }
                        };
                        return new AbstractMap.SimpleEntry<>(type, section);
                    },
                    section -> resume.addSection(section.getKey(), section.getValue())
            );

            return resume;
        }
    }
}