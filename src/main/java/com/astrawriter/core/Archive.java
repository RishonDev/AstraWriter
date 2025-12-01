package com.astrawriter.core;



import java.io.*;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Archive {
    // Declaring a variable for the Archives Input

    static Runtime console = Runtime.getRuntime();

    public static void extractISO(String isoImage, String outputDirectory) throws IOException {

    }

    public static void writeISO_DD(String input, String output) throws IOException {
        console.exec("dd if=" + input + " of=" + output + "status=progress");
    }

    public static void extractPKG(String inputPKG, String outputDirectory) throws IOException {
        File outputFile = new File(outputDirectory);
        if (!outputFile.exists()) {
            boolean temp = outputFile.mkdirs();
        }
        unzip(inputPKG, outputFile.getAbsolutePath());

    }

    public static void unzip(String zipFileName, String destDirectory) throws IOException {
        File destDirectoryFolder = new File(destDirectory);
        if (!destDirectoryFolder.exists()) {
            destDirectoryFolder.mkdir();
        }
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            String entryName = zipEntry.getName();
            if (!zipEntry.isDirectory() && !entryName.startsWith("__MACOSX/")) {
                String filePath = destDirectory + File.separator + entryName;
                System.out.println("Unzipping " + filePath);
                FileOutputStream fos = new FileOutputStream(filePath);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            } else {
                File dir = new File(destDirectory + File.separator + entryName);
                dir.mkdir();
            }
            zis.closeEntry();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        System.out.println("Unzipping complete");
    }

}