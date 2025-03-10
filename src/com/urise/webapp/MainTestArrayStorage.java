package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.*;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume ss = new Resume("uuid1");

        System.out.println(ss.getFullName());

        Resume r1 = new Resume("uuid1","uuid1_name");
        Resume r2 = new Resume("uuid2","uuid2_name");
        Resume r3 = new Resume("uuid3", "uuid3_name");
        Resume r4 = new Resume("uuid4", "uuid4_name");

        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r4);


        printAll();
        System.out.println("save finished");
        ARRAY_STORAGE.delete("uuid4");
//
//
//        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
//        System.out.println("Size: " + ARRAY_STORAGE.size());
//
////        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
////
////        printAll();
//        ARRAY_STORAGE.delete(r1.getUuid());
//        System.out.println("Size: " + ARRAY_STORAGE.size());
//        printAll();
//        ARRAY_STORAGE.clear();
//        printAll();
//
////        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All-----------");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
        System.out.println();
    }
}
