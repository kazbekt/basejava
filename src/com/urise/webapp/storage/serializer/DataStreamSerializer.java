package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.model.Resume.ContactType;


import java.io.*;
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
                    for(String s : list) {
                        dos.writeUTF(s);
                    }
                } else {

                }

               // dos.writeUTF(section.);
            }





            // TODO implements sections
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(Resume.ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            return resume;
        }
    }

   //public void readMap(Map<K,V> map) {



}
