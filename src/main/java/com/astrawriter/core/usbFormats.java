package com.astrawriter.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class usbFormats {
    private String os = SoftwareInfo.getPlatform();

    public static String HFSPlus() {
        return "hfs+";
    }

    public static String FAT32() {
        return "fat32";
    }

    public static String EXT4() {
        return "ext4";
    }

    public static String Btrfs() {
        return "btrfs";
    }

    public static String exFAT() {
        return "exfat";
    }

    @Contract(pure = true)
    public static @NotNull String NTFS() {
        return "";
    }
}
