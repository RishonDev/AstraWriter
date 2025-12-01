package com.astrawriter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {
    public static void downloadFile(URL url, String outputFileName) throws IOException {
        try (InputStream in = url.openStream(); ReadableByteChannel rbc = Channels.newChannel(in); FileOutputStream fos = new FileOutputStream(outputFileName)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    public static void downloadFileWithProgress(JLabel label, URL url, String DirectoryOutputFileName) throws MalformedURLException {
        try (InputStream in = url.openStream(); ReadableByteChannel rbc = Channels.newChannel(in); FileOutputStream fos = new FileOutputStream(DirectoryOutputFileName)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            float size = FileIO.convertToMegabytes(FileIO.  getFileSize(url), "Bytes"), downloadedData = 0.0F;
            while (downloadedData <= size) {
                label.setText("Downloaded " + FileIO.getFileSizeInMegabytes(DirectoryOutputFileName) + "MB out of " + size + "MB");
                downloadedData = FileIO.getFileSizeInMegabytes(Definitions.image.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
