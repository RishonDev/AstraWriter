package com.astrawriter.core;


import com.astrawriter.Downloader;
import com.astrawriter.URLs;

import java.io.File;
import java.io.IOException;

public class Bootloader {
    public static final int SYSLINUX = 0;
    public static final int GRUB_WINDOWS = 1;
    public static final int GRUB = 2;
    public static final int CLOVER = 3;
    public static final int VENTOY = 4;
    USB usb;

    public Bootloader(USB usb) {
        this.usb = usb;
    }

    public void install(byte bootloader) {
        usb.addDiskWithEFI();
        if (bootloader == SYSLINUX) {
        } else if (bootloader == GRUB) {

        } else if (bootloader == GRUB_WINDOWS) {

        } else if (bootloader == CLOVER) {
            ///
            System.out.println("Clover");
        } else if (bootloader == VENTOY) {
            if (!new File(SoftwareInfo.getISODirectory() + "Ventoy.iso").exists()) {
                try {
                    Downloader.downloadFile(URLs.ventoy, SoftwareInfo.getISODirectory() + "Ventoy.iso");
                } catch (IOException ignored) {
                }
            }
            try {
                Archive.extractISO(SoftwareInfo.getISODirectory() + "Ventoy.iso", usb.getDiskPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void uninstall() {
        for (File f : usb.getUSB().listFiles()) {
            if (!f.delete()) {
                System.err.println("FIle is not deleted...");
            }
        }
    }

}
