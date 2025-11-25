package com.astrawriter.core;

import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.classification.InterfaceStability;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class USB {

    private static final Runtime runtime = Runtime.getRuntime();
    private final USBDeviceDetectorManager deviceDetector = new USBDeviceDetectorManager();
    private File usb;
    private String platform = SoftwareInfo.getPlatform();

    public USB(String usb) {
        this.usb = new File(usb);
    }

    public USB(File usb) {
        this.usb = usb;
    }

    public USB(USB usb) {
        this.usb = usb.getUSB();
    }

    public File getUSB() {
        return usb;
    }

    public void setUSB(USB usb) {
        this.usb = usb.getUSB();
    }

    public void setUSB(File file) {
        this.usb = file;
    }

    public void setUSB(String file) {
        this.usb = new File(file);
    }

    public static void format(String format, String newName, int size) {
        try {
            runtime.exec("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePlatform() {
        platform = SoftwareInfo.getPlatform();
    }

    public USBDeviceDetectorManager getDeviceDetector() {
        return deviceDetector;
    }

    public boolean doesExist() {
        return this.usb.exists();
    }

    public String getName() {
        return this.usb.getName();
    }

    public boolean verify(boolean writeMode, boolean isEmpty) {
        boolean isSuccessful = true;
        String args = "-v";
        if (writeMode) args += "-w";
        if (isEmpty) args += "-n";
        switch (SoftwareInfo.getPlatform()) {
            case "macOS" -> {
                try {
                    runtime.exec(getShellScriptsPath() + "verify/verifymacos.sh -v " + usb.getAbsolutePath());
                } catch (IOException e) {
                    isSuccessful = false;
                }
            }
            case "Windows" -> {
                try {
                    runtime.exec(getShellScriptsPath() + "verify\\verifywin.cmd " + usb.getAbsolutePath());
                } catch (IOException e) {
                    isSuccessful = false;
                }
            }
            case "Linux" -> {
                try {
                    runtime.exec(getShellScriptsPath() + "verify/verifylinux.sh -u " + usb.getAbsolutePath());
                } catch (IOException e) {
                    isSuccessful = false;
                }
            }
        }
        return isSuccessful;
    }

    private String getShellScriptsPath() {
        switch (SoftwareInfo.getPlatform()) {
            case "Windows" -> {
                return "src\\main\\shell\\usb\\";
            }
            case "macOS", "Linux" -> {
                return "src/main/shell/usb/";
            }
        }
        return null;
    }

    public void unmount() throws IOException {
        switch (SoftwareInfo.getPlatform()) {
            case "macOS" -> runtime.exec(getShellScriptsPath() + "unmount/unmountmacos.sh -u " + usb.getAbsolutePath());
            case "Windows" -> runtime.exec(getShellScriptsPath() + "unmount\\unmountwin.cmd " + usb.getAbsolutePath());
            case "Linux" -> runtime.exec(getShellScriptsPath() + "unmount/unmountlinux.sh -u " + usb.getAbsolutePath());
        }
    }

    public boolean eject() {
        boolean isSuccessful = true;

        switch (SoftwareInfo.getPlatform()) {
            case "macOS" -> {
                try {
                    runtime.exec(getShellScriptsPath() + "eject/ejectmacos.sh -u " + usb.getAbsolutePath());
                } catch (IOException e) {
                    isSuccessful = false;
                }
            }
            case "Windows" -> {
                try {
                    runtime.exec(getShellScriptsPath() + "eject\\ejectwin.cmd " + usb.getAbsolutePath());
                } catch (IOException e) {
                    isSuccessful = false;
                }
            }
            case "Linux" -> {
                try {
                    runtime.exec(getShellScriptsPath() + "eject/ejectlinux.sh -u " + usb.getAbsolutePath());
                } catch (IOException e) {
                    isSuccessful = false;
                }
            }
            default -> System.err.println("Error:Unsupported OS");
        }
        return isSuccessful;
    }

    public boolean delete(String fileOrFolder) throws IOException {
        boolean bool;
        if (fileOrFolder.equals("*")) FileUtils.cleanDirectory(usb);
        return new File(usb.getAbsolutePath() + fileOrFolder).delete();
    }

    public boolean isExternalDevice() {
        return deviceDetector.getRemovableDevices().toString().contains(usb.getName());
    }

    @InterfaceStability.Unstable
    public void clearPartitions() throws IOException {
        runtime.exec("./src/main/shell/");
    }

    /*
     **newName - The Name of the partition after fWormatting
     **  format - What file system it should format
     **  size - The size of the partition
     ** isLFS - Files greater than 4GB, this should be enabled.
     **/
    public void addPartition(String newName, String format, long size, boolean isLFS) throws IOException {
        switch (SoftwareInfo.getPlatform()) {
            case "macOS" -> runtime.exec(getShellScriptsPath() + "format/formatmacos.sh -l " + usb.getAbsolutePath());
            case "Windows" -> runtime.exec(getShellScriptsPath() + "unmount\\unmountwin.cmd " + usb.getAbsolutePath());
            case "Linux" -> runtime.exec(getShellScriptsPath() + "unmount/unmountlinux.sh -u " + usb.getAbsolutePath());
            default -> System.err.println("Error: Unsupported OS.");
        }
    }

    /*
     **newName - The Name of the partition after formatting
     **  format - What file system it shpuld format
     **  size - The size of the partition
     ** args - the arguments if any
     ** isLFS
     **/
    public void addPartition(String newName, String format, long size) throws IOException {
        switch (SoftwareInfo.getPlatform()) {
            case "macOS" -> runtime.exec(getShellScriptsPath() + "format/formatmacos.sh -l " + usb.getAbsolutePath());
            case "Windows" -> runtime.exec(getShellScriptsPath() + "unmount\\unmountwin.cmd " + usb.getAbsolutePath());
            case "Linux" -> runtime.exec(getShellScriptsPath() + "unmount/unmountlinux.sh -u " + usb.getAbsolutePath());
            default -> System.err.println("Error: Unsupported OS.");
        }
    }

    private void printErrorMessage(String operation) {
        System.err.println("Error: Unable to " + operation + " the USB. Could be\n1)The USB is corrupt\n2) USB is unmounted\n3)");
    }

    public void makeEFI(int size, String bootloaderEFI_Path) throws IOException {
        addPartition("WEFI", usbFormats.FAT32(), size, "", false);
        Files.copy(Path.of(bootloaderEFI_Path), Path.of(usb.getAbsolutePath()));

    }

    /*
     **newName - The Name of the partition after formatting
     **  format - What file system it should format
     **  size - The size of the partition
     ** args - the arguments if any
     ** isLFS - files greater than 4GB, this should be enabled.
     **/
    public void addPartition(String newName, String format, long size, String args, boolean isLFS) throws IOException {
        String arg = args;
        if (isLFS) arg += " -l";
        switch (SoftwareInfo.getPlatform()) {
            case "macOS" -> runtime.exec(getShellScriptsPath() + "format/formatmacos.sh -l " + usb.getAbsolutePath());
            case "Windows" -> runtime.exec(getShellScriptsPath() + "format\\formatwin.cmd " + usb.getAbsolutePath());
            case "Linux" -> runtime.exec(getShellScriptsPath() + "format/formatlinux.sh -u " + usb.getAbsolutePath());
            default -> System.err.println("Error: Unsupported OS.");
        }
    }

    public String getID() {
        return null;
    }

    public String getDiskPath() {
        return getUSB().getAbsolutePath();
    }

    public String getDiskPathInDevFolder() {
        return null;
    }

    public void getDiskPartitionID() {

    }

    public void addDiskWithEFI() {

    }

    /**
     * WARNING:ANY DATA  WILL BE DESTROYED ON DISK. PLEASE BE CAREFUL WHEN USING IT!
     * Continuously writes 0s to the disk until
     */

    public void zeroOutDisk(String diskPath) {
        long usbSize = new File(diskPath).length() * 8;

        try (RandomAccessFile file = new RandomAccessFile(diskPath, "rw")) {
            byte[] buffer = new byte[1024];
            while (usbSize >= 0) {
                file.write(buffer);
                usbSize--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The remaining free space of the selected disk.
     */
    public long getRemainingFreeSpace() {
        return usb.getFreeSpace();
    }
}