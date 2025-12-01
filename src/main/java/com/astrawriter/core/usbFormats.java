package com.astrawriter.core;

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

}
