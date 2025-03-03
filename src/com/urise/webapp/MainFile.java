package com.urise.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File("..\\.gitignore");
        System.out.println(file.getCanonicalFile());
        File dir = new File(".\\src\\com\\urise\\webapp");
        System.out.println(dir.getCanonicalFile());
        System.out.println(dir.isDirectory());

        String[] list = dir.list();
        if (list != null) {
            for (String s : list) {
                System.out.println(s);
            }
            System.out.println("------------------");
        }

        File dir1 = new File("C:\\Users\\LOVE\\IdeaProjects\\basejava");
        recList(dir1);
    }


    public static void recList(File file) {
        String[] list = file.list();
        if (list != null) {
            for (String s : list) {
                File file1 = new File(file, s);
                if (file1.isFile()) {
                    System.out.println(s);
                } else {
                    recList(file1);
                }
            }
        }
    }
}
