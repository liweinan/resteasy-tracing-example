package io.weli;

import java.io.File;

public class ListPath {
    public static void main(String[] args) {
        String path = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
        System.out.println(path);

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            System.out.println(listOfFiles[i].getPath());
        }
    }
}
