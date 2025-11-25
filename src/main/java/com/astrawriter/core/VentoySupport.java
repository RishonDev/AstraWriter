package com.astrawriter.core;

import com.astrawriter.Downloader;
import com.astrawriter.URLs;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class VentoySupport {
    private USB usb;

    public VentoySupport(USB usb) {
        this.usb = usb;
    }
    public VentoySupport(String usbPath) {
        this.usb = new USB(usbPath);
    }
    public VentoySupport(File iso) {
        this.usb = new USB(iso);
    }
    public void setUSB(USB usb) {
        this.usb = usb;
    }
    public void setUSB(String usbPath) {
        this.usb = new USB(usbPath);
    }
    public void setUSB(File usb) {
        this.usb = new USB(usb);
    }
    public void downloadISO() {
        try {
            assert URLs.ventoy != null;
            Downloader.downloadFile(URLs.ventoy, SoftwareInfo.getISODirectory() + "Ventoy.iso");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to download the file" + e.getMessage(), "Error Downloading the File", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void makeVentoyUSB() throws IOException {
        usb.addPartition("WEFI", usbFormats.FAT32(), 200000000);
        usb.addPartition("BootUSB", usbFormats.exFAT(), usb.getRemainingFreeSpace());
        Archive.extractISO(SoftwareInfo.getISODirectory() + "Ventoy.iso", usb.getDiskPathInDevFolder() + "s1");
    }

    public void copyISO(File image) {
        try {
            Files.copy(image.toPath(), new File(usb.getDiskPathInDevFolder() + "s2").toPath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error copying ISO image", "Error copying files", JOptionPane.ERROR_MESSAGE);
        }
    }


}
