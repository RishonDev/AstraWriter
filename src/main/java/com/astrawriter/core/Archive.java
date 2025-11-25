package com.astrawriter.core;

import com.github.stephenc.javaisotools.loopfs.iso9660.Iso9660FileEntry;
import com.github.stephenc.javaisotools.loopfs.iso9660.Iso9660FileSystem;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.tukaani.xz.XZInputStream;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Archive {
    // Declaring a variable for the Archives Input

    static Iso9660FileSystem discFs;
    static Runtime console = Runtime.getRuntime();

    public static void extractISO(String isoImage, String outputDirectory) throws IOException {
        File isoToWrite = new File(isoImage);
        File saveLocation = new File(outputDirectory);
        try {
            //Give the file and mention if this is treated as a read only file.
            discFs = new Iso9660FileSystem(isoToWrite, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Make our saving folder if it does not exist
        if (!saveLocation.exists()) {
            boolean temp = saveLocation.mkdirs();
        }

        //Go through each file on the disc and save it.
        for (Iso9660FileEntry singleFile : discFs) {
            if (singleFile.isDirectory()) {
                boolean t = new File(saveLocation, singleFile.getPath()).mkdirs();
            } else {
                File tempFile = new File(saveLocation, singleFile.getPath());

                try {
                    Files.copy(discFs.getInputStream(singleFile), tempFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeISO_DD(String input, String output) throws IOException {
        console.exec("dd if=" + input + " of=" + output + "status=progress");
    }

    public static void untar(String iF, String oD) throws IOException, ArchiveException {
        File inputFile = new File(iF);
        File outputDir = new File(oD);
        final InputStream is = new FileInputStream(inputFile);
        final TarArchiveInputStream tarInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", is);
        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry) tarInputStream.getNextEntry()) != null) {
            final File outputFile = new File(outputDir, entry.getName());
            if (entry.isDirectory()) {
                if (!outputFile.exists()) {
                    if (!outputFile.mkdirs()) {
                        throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
                    }
                }
            } else {
                final OutputStream outputFileStream = new FileOutputStream(outputFile);
                IOUtils.copy(tarInputStream, outputFileStream);
                outputFileStream.close();
            }
        }
        tarInputStream.close();
    }

    public static void extractGzip(final File inputFile, final File outputDir) throws FileNotFoundException, IOException {
        final File outputFile = new File(outputDir, inputFile.getName().substring(0, inputFile.getName().length() - 3));
        final GZIPInputStream in = new GZIPInputStream(new FileInputStream(inputFile));
        final FileOutputStream out = new FileOutputStream(outputFile);
        IOUtils.copy(in, out);
        in.close();
        out.close();
    }

    public static void extractXZ(File inputFile, File outputDir) throws FileNotFoundException, IOException {

        final File outputFile = new File(outputDir + inputFile.getName().substring(0, inputFile.getName().length() - 3));
        final XZInputStream in = new XZInputStream(new FileInputStream(inputFile));
        final FileOutputStream out = new FileOutputStream(outputFile);
        IOUtils.copy(in, out);
        in.close();
        out.close();
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

    public int getEstimatedSize(String saveLocation) {
        int estimatedSize = 0;
        for (Iso9660FileEntry singleFile : discFs) {
            if (!singleFile.isDirectory()) {
                estimatedSize += new File(saveLocation, singleFile.getPath()).length();

            }
        }
        return estimatedSize;
    }

}