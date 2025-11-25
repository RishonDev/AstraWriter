package com.astrawriter;

import java.net.MalformedURLException;
import java.net.URL;

public class URLs {
    public static final URL macOS11 = getURL("https://archive.org/download/macos-collection/Releases/Big%20Sur%2011.6.5%20v16.8.00.iso");
    public static final URL macOS12 = getURL("https://archive.org/download/macos-collection/Releases/Monterey%2012.6.1%20v17.6.11.iso ");
    public static final URL macOS13 = getURL("https://archive.org/download/macos-collection/Releases/Ventura%2013.0%20v18.0.02.iso");
    public static final URL ubuntu = getURL("https://releases.ubuntu.com/22.04/ubuntu-22.04.1-desktop-amd64.iso");
    public static final URL debian64 = getURL("https://cdimage.debian.org/debian-cd/current/amd64/bt-dvd/debian-11.4.0-amd64-DVD-1.iso.torrent");
    public static final URL debian = getURL("https://cdimage.debian.org/debian-cd/current/i386/bt-dvd/debian-11.4.0-i386-DVD-1.iso.torrent");
    public static final URL debianNet64 = getURL("https://cdimage.debian.org/debian-cd/current/amd64/iso-cd/debian-11.6.0-amd64-netinst.iso");
    public static final URL debianNet = getURL("https://cdimage.debian.org/debian-cd/current/i386/iso-cd/debian-11.6.0-i386-netinst.iso");
    public static final URL ubuntuServer = getURL("https://releases.ubuntu.com/22.04/ubuntu-22.04.1-live-server-amd64.iso");
    public static final URL arch = getURL("https://mirrors.edge.kernel.org/archlinux/iso/2023.03.01/archlinux-2023.03.01-x86_64.iso");
    public static final URL fedoraWorkspace64 = getURL("https://download.fedoraproject.org/pub/fedora/linux/releases/37/Workstation/x86_64/iso/Fedora-Workstation-Live-x86_64-37-1.7.iso");
    public static final URL fedoraWorkspace = getURL("https://download.fedoraproject.org/pub/fedora/linux/releases/37/Workstation/aarch64/iso/Fedora-Workstation-Live-aarch64-37-1.7.iso");
    public static final URL fedoraServer = getURL("https://download.fedoraproject.org/pub/fedora/linux/releases/37/Server/x86_64/iso/Fedora-Server-netinst-x86_64-37-1.7.iso");
    public static final URL fedoraServer_ARM = getURL("https://download.fedoraproject.org/pub/fedora/linux/releases/36/Server/aarch64/iso/Fedora-Server-dvd-aarch64-36-1.5.iso");
    public static final URL deepin = getURL("https://cdimage.deepin.com/releases/20.8/deepin-desktop-community-20.8-amd64.iso");
    public static final URL manjaroGnome = getURL("https://download.manjaro.org/gnome/22.0/manjaro-gnome-22.0-221224-linux61.iso");
    public static final URL manjaroGnomeARM = getURL("");
    public static final URL manjaroXFCE = getURL("https://download.manjaro.org/xfce/22.0/manjaro-xfce-22.0-221224-linux61.iso");
    public static final URL manjaroKDE = getURL("https://download.manjaro.org/kde/22.0/manjaro-kde-22.0-221224-linux61.iso");
    public static final URL linuxMintXFCE = getURL("https://mirrors.kernel.org/linuxmint/stable/21.1/linuxmint-21.1-xfce-64bit.iso");
    public static final URL linuxMintCinnamon = getURL("https://mirrors.kernel.org/linuxmint/stable/21.1/linuxmint-21.1-cinnamon-64bit.iso");
    public static final URL linuxMintMate = getURL("https://mirrors.kernel.org/linuxmint/stable/21.1/linuxmint-21.1-mate-64bit.iso");
    public static final URL elementaryOS = getURL("https://sgp1.dl.elementary.io/download/MTY1ODIyMTYzMg==/elementaryos-6.1-stable.20211218-rc.iso");
    public static final URL solusGnome = getURL("https://mirrors.rit.edu/solus/images/4.3/Solus-4.3-GNOME.iso");
    public static final URL solusBudgie = getURL("https://mirrors.rit.edu/solus/images/4.3/Solus-4.3-Budgie.iso");
    public static final URL solusMATE = getURL("https://mirrors.rit.edu/solus/images/4.3/Solus-4.3-MATE.iso");
    public static final URL solusKDE = getURL("https://mirrors.rit.edu/solus/images/4.3/Solus-4.3-Plasma.iso");
    public static final URL blackArch = getURL("https://ftp.halifax.rwth-aachen.de/blackarch/iso/blackarch-linux-full-2021.09.01-x86_64.iso");
    public static final URL blackArchMinimum = getURL("https://ftp.halifax.rwth-aachen.de/blackarch/iso/blackarch-linux-slim-2021.09.01-x86_64.iso");
    public static final URL blackArchNet = getURL("https://ftp.halifax.rwth-aachen.de/blackarch/iso/blackarch-linux-netinst-2021.09.01-x86_64.iso");
    public static final URL fedoraServerNet_ARM = getURL("https://mirrors.tuna.tsinghua.edu.cn/fedora/releases/36/Server/aarch64/iso/Fedora-Server-netinst-aarch64-36-1.5.iso");
    public static final URL fedoraServerNet = getURL("https://download.fedoraproject.org/pub/fedora/linux/releases/36/Server/x86_64/iso/Fedora-Server-netinst-x86_64-36-1.5.iso");
    public static final URL windowsXP64 = getURL("https://github.com/JavaOpenSoft/WaveUSB.old/releases/download/WindowsXP64/Windows.XP.Professional.64-bit.Corporate.Edition.CD.Key.VCFQD-V9FX9-46WVH-K3CD4-4J3JM.iso");
    public static final URL WindowsXP = getURL("https://github.com/JavaOpenSoft/WaveUSB/releases/download/WindowsXP/WindowsXP-32bit-Professional-ServicePack3.iso");
    public static final URL Windows8 = getURL("https://www.microsoft.com/en-in/software-download/windows8ISO");
    public static final URL Windows10 = getURL("https://www.microsoft.com/en-in/software-download/windows10ISO");
    public static final URL Windows11 = getURL("https://www.microsoft.com/en-in/software-download/windows11");
    public static final URL ventoy = getURL("https://github.com/ventoy/Ventoy/releases/download/v1.0.87/ventoy-1.0.87-livecd.iso");
    public static URL ubuntu2210 = getURL("https://releases.ubuntu.com/22.10/ubuntu-22.10-desktop-amd64.iso");
    public static URL ubuntu2210Server = getURL("https://releases.ubuntu.com/22.10/ubuntu-22.10-live-server-amd64.iso.zsync");
    public static URL ubuntu2210T2 = getURL("https://github.com/AdityaGarg8/T2-Ubuntu/releases/download/v6.0.12-1/ubuntu-22.10-6.0.12-t2-kinetic.iso");
    public static URL ubuntu2204T2 = getURL("https://github.com/AdityaGarg8/T2-Ubuntu/releases/download/v6.0.12-1/ubuntu-22.04-6.0.12-t2-jammy.iso");
    public static URL ubuntu2210T2SafeGraphics = getURL("https://github.com/AdityaGarg8/T2-Ubuntu/releases/download/v6.0.12-1/ubuntu-22.10-6.0.12-t2-kinetic-safe-graphics.iso");
    public static URL ubuntu2204T2SafeGraphics = getURL("https://github.com/AdityaGarg8/T2-Ubuntu/releases/download/v6.0.12-1/ubuntu-22.04-6.0.12-t2-jammy-safe-graphics.iso");
    public static URL T2Arch = getURL("https://github.com/t2linux/archiso-t2/releases/download/2022.10.28/archlinux-t2-2022.10.28-t2-x86_64.iso");
    public static URL T2Fedora = getURL("https://github.com/mikeeq/mbp-fedora/releases/download/v6.0.9-f37/livecd-mbp-f37-gnome-20221122.zip");
    public static URL T2FedoraKDE = getURL("https://github.com/mikeeq/mbp-fedora/releases/download/v6.0.9-f37/livecd-mbp-f37-kde-20221122.zip");
    public static URL T2EndevourOS = getURL("https://github.com/t2linux/EndeavourOS-ISO-t2/releases/download/2022.12.24/endeavouros-t2-Cassini-T2-x86_64.iso");
    public static URL archlinux32 = getURL("https://mirror.bradiceanu.net/archlinux32/archisos/");
    public static URL archlinuxARM = getURL("http://os.archlinuxarm.org/os/ArchLinuxARM-aarch64-latest.tar.gz");

    //No Archives images are available for Windows 12 so commented out to avoid taking up unnecessary memory
    //String Windows12 = null;
    public static URL getURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + url + "\n" + e.getMessage());
        }
        return null;
    }
}
