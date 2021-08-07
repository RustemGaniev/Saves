package com.company;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        GameProgress save1 = new GameProgress(100, 4, 1, 200);
        GameProgress save2 = new GameProgress(80, 10, 3, 400);
        GameProgress save3 = new GameProgress(65, 3, 17, 151);

        saveGame("/Users/natalaganieva/Documents/Games/savegames/save1.dat", save1);
        saveGame("/Users/natalaganieva/Documents/Games/savegames/save2.dat", save2);
        saveGame("/Users/natalaganieva/Documents/Games/savegames/save3.dat", save3);

        String zipFileName = "/Users/natalaganieva/Documents/Games/savegames/saves.zip";
        String[] pathFiles = new String[3];
        pathFiles[0] = "/Users/natalaganieva/Documents/Games/savegames/save1.dat";
        pathFiles[1] = "/Users/natalaganieva/Documents/Games/savegames/save2.dat";
        pathFiles[2] = "/Users/natalaganieva/Documents/Games/savegames/save3.dat";

        zipFiles(zipFileName, pathFiles);

        File save1File = new File("/Users/natalaganieva/Documents/Games/savegames/save1.dat");
        save1File.delete();
        File save2File = new File("/Users/natalaganieva/Documents/Games/savegames/save2.dat");
        save2File.delete();
        File save3File = new File("/Users/natalaganieva/Documents/Games/savegames/save3.dat");
        save3File.delete();

    }

    public static void saveGame(String path, GameProgress save) {

        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(save);
            System.out.println("Game has saved in " + path);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String zipFileName, String[] pathFiles) {

        ZipOutputStream zout = null;
        try {
            zout = new ZipOutputStream(new FileOutputStream(zipFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (String s : pathFiles) {
            try (FileInputStream fis = new FileInputStream(s)) {
                ZipEntry entry = new ZipEntry(s);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                System.out.println("File " + s + " added to zip file");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}