package com.astrawriter.core;

@SuppressWarnings("ALL")
public interface SoftwareInfo {
    static final String buildMode = "Staging";
    static final String About = "GUI Edition of WaveUSB, A complete USB writer designed in Java And Shell(Batch scripts if on  Windows). ";
    static final String versionNumber = "0.8.1";

    public static String getVersionNumber() {
        return versionNumber;
    }

    public static String getVersion() {
        return versionNumber + "-" + buildMode;
    }

    public static String getAbout() {
        return About;
    }

    public static String getBuildMode() {
        return buildMode;
    }

    public static String getOSVersion() {
        return System.getProperty("os.version");
    }

    public static String getISODirectory() {
        switch (getPlatform()) {
            case "Mac OS X", "Linux" -> {
                return getAppDataDirectory() + "/ISO";
            }
            case "Windows" -> {
                return getAppDataDirectory() + "\\ISO";
            }
        }
        return null;
    }

    public static String getPlatform() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows"))
            return "Windows";
        else if (osName.equals("Mac OS X"))
            return System.getProperty("os.name");
        else return "Linux";
    }

    public static String getAppDataDirectory() {
        switch (getPlatform()) {
            case "Mac OS X", "Linux" -> {
                return getHomeDirectory() + "/.WaveUSB";
            }
            case "Windows" -> {
                return getHomeDirectory() + "\\.WaveUSB";
            }
        }
        return null;
    }

    public static String getHomeDirectory() {
        return System.getProperty("user.home");
    }

}
