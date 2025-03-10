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

        File dir1 = new File("..\\basejava\\src\\com");
        printDirectoryDeeply(dir1);
    }
    public static StringBuilder lines = new StringBuilder();

    public static StringBuilder lines2 = new StringBuilder();

    public static String status;
    public static String gap = "   ";
    public static String line = "│  ";

    public static void addGap() {
        lines.append(gap);
    }
    public static void removeGap() {
        lines.replace(lines.indexOf(gap),lines.indexOf(gap)+ gap.length(), "");
    }

    public static void addLine(){
        lines2.append(line);
    }

    public static void removeLine() {
        lines2.replace(lines2.indexOf(line),lines2.indexOf(line) + line.length(), "");
    }

    public static void printDirectoryDeeply(File file) {
        String[] list = file.list();
        if (list != null) {
            for (int i=0; i < list.length; i++) {
                File file1 = new File(file, list[i]);

                if(i!= list.length-1){
                    status = "├─";
                } else {
                    status = "└─";
                }
                if (file1.isFile()) {
                    System.out.println( lines + lines2.toString() + status + "File: " + list[i]);
                } else {
                    System.out.println(lines + lines2.toString() + status + "Folder: " + list[i]);
                    addGap();
                    if (list.length>1 && i<list.length-1){
                        removeGap();
                        addLine();
                    }
                    printDirectoryDeeply(file1);
                }
                if (list.length >1 && i==list.length-1 && lines2.indexOf(line) !=-1){
                    removeLine();
                }
            }
        }
    }
}
