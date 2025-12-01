package com.astrawriter;

import com.astrawriter.core.SoftwareInfo;
import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("all")
public class Definitions implements SoftwareInfo {
    static final Runtime console = Runtime.getRuntime();
    public static JProgressBar progressBar = new JProgressBar(0, 100);
    static String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
    static String jar = System.getProperty("java.class.path");
    static String osType;
    static File currentJAR = null;
    static File jarFile = null;
    //static USB usb = null;
    static String filename;
    static File bootloaderDirectory = new File(SoftwareInfo.getAppDataDirectory() + "/Bootloaders");
    static File isoDirectory = new File(SoftwareInfo.getAppDataDirectory() + "/ISO");
    static URL isoURL;
    //Layout Declarations
    static CardLayout layout = new CardLayout();
    //Application Window And Pane Declarations
    static JPanel applicationPanel = new JPanel();
    static JPanel linuxPane = new JPanel();
    static JFrame frame = new JFrame("Wave USB Image Writer");
    static JPanel macOSPane = new JPanel();
    static JPanel macOSEULA = new JPanel();
    static JPanel othersPane = new JPanel();
    static JPanel homePane = new JPanel();
    static JPanel windowsPane = new JPanel();
    static JPanel usbOptions = new JPanel();
    static JPanel downloadScreen = new JPanel();
    //Linux Distros.
    static JPanel debianPane = new JPanel();
    static JPanel archPane = new JPanel();
    static JPanel miscLinuxPane = new JPanel();
    //Labels Used in the application
    static JLabel supportedFileTypes = new JLabel("Supported file types: (*.iso)(*.img)(*tar.gz)(*.zip)");
    static JLabel statusLabel = new JLabel("Status");
    static JLabel chooseOS = new JLabel("To start, choose your operating system installer you want to write to your USB:");
    static JLabel chooseMacOSLabel = new JLabel("Choose your macOS Version:");
    static JLabel linuxChoose = new JLabel("Please choose your linux Installer:");
    static JLabel linuxChoose2 = new JLabel("Please choose your linux Installer:");
    static JLabel linuxChoose3 = new JLabel("Please choose your linux Installer:");
    static JLabel linuxChoose4 = new JLabel("Please choose your linux Installer:");
    static JLabel othersChoose = new JLabel("Please choose an image file:");
    static JLabel progressLabel = new JLabel();
    static JLabel wallpaper = new JLabel();
    static JLabel welcomeLabel = new JLabel("Welcome!");
    static JLabel windowsChose = new JLabel("Choose your Windows Version");
    //Text fields
    static JTextField imageFileDirectory = new JTextField("Enter the file path or select a file..");
    static JTextArea jTextArea = new JTextArea();
    static JTextField usbDirectory = new JTextField("Enter or Choose the USB path..");
    static JTextField usbName = new JTextField("New USB name here..");
    //internal frame and it's components for End User License Agreement text for macOS
    static JInternalFrame jInternalFrame = new JInternalFrame();
    static JScrollPane jScrollPane = new JScrollPane();
    static JScrollBar jScrollBar = new JScrollBar();
    //the menu bar and menus
    static JMenuBar menuBar = new JMenuBar();
    static JMenu fileMenu = new JMenu("File");
    static JMenu settingsMenu = new JMenu("Settings");
    static JMenu helpMenu = new JMenu("Help");
    static JComboBox<String> architectureMenu = new JComboBox<>();
//    //macOS Buttons
//    static JButton macOS_13 = new JButton("macOS 13.0 (Ventura)", new ImageIcon(WaveUSB.class.getResource("images/Ventura_Icons/Ventura_32x32x32.png")));
//    static JButton macOS_12 = new JButton("macOS 12.6 (Monterey)", new ImageIcon(WaveUSB.class.getResource("images/Monterey_Icon/Monterey_32x32x32.png")));
//    static JButton macOS_11 = new JButton("macOS 11.6.8(Big Sur)", new ImageIcon(WaveUSB.class.getResource("images/BigSur_Icons/BigSur_32x32x32.png")));
//    static JButton quitButton = new JButton("Quit");
//    //Misc buttons
//    static JButton selectFile = new JButton("Select a Image File");
//    static JButton macOSButton = new JButton("macOS", new ImageIcon(WaveUSB.class.getResource("images/apple_icons/apple_round.png")));
//    static JButton windowsButton = new JButton("Windows", new ImageIcon(WaveUSB.class.getResource("images/windows-8-icon.png")));
//    static JButton linuxButton = new JButton("Linux", new ImageIcon(WaveUSB.class.getResource("images/OS-Linux-icon.png")));
//    static JButton otherButton = new JButton("Other..");
//    //Linux buttons
//    static JButton archBasedDistros = new JButton("Arch Based Distributions", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/arch/arch_round.png")));
//    static JButton debianBasedDistros = new JButton("Debian Based Distributions", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/debian/debian_round.png")));
//    static JButton miscDistributions = new JButton("Miscellaneous Distributions", new ImageIcon(WaveUSB.class.getResource("images/OS-Linux-icon.png")));
//    static JButton arch = new JButton("Arch 2022.11.01 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/arch/arch_round.png")));
//    static JButton blackArch = new JButton("Black Arch Version 2021.09.1 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/arch/blackarch/black_arch_round.png")));
//    static JButton blackArchMinimum = new JButton("Black Arch Minimum v2021.09.1 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/arch/blackarch/black_arch_round.png")));
//    static JButton blackArchNet = new JButton("Black Arch Network Installer v2021.09.1 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/arch/blackarch/black_arch_round.png")));
//    static JButton debian = new JButton("Debian 11-32 bit(Bullseye)", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/debian/debian_round.png")));
//    static JButton debian64 = new JButton("Debian 11 64-bit (Bullseye)", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/debian/debian_round.png")));
//    static JButton debianNet64 = new JButton("Debian 11 64-bit Network Installer(Bullseye)", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/debian/debian_round.png")));
//    static JButton debianNet = new JButton("Debian 11 32-bit Network Installer(Bullseye)", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/debian/debian_round.png")));
//    static JButton deepin = new JButton("Deepin OS 20.6", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/deepin/deepin.png")));
//    static JButton elementaryOS = new JButton("Elementary OS Version 6.1", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/elementaryOS/elementaryOS_round.png")));
//    static JButton fedoraWorkspace = new JButton("Fedora Workstation 36 32-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/fedora/fedora_round.png")));
//    static JButton fedoraWorkspace64 = new JButton("Fedora Workstation 36 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/fedora/fedora_round.png")));
//    static JButton fedoraServer = new JButton("Fedora Server 36 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/fedora/fedora_round.png")));
//    static JButton fedoraServer_ARM = new JButton("Fedora Server ARM 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/fedora/fedora_round.png")));
//    static JButton fedoraServerNet64 = new JButton("Fedora Server Net 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/fedora/fedora_round.png")));
//    static JButton fedoraServerNet_ARM = new JButton("Fedora Server Net ARM 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/fedora/fedora_round.png")));
//    static JButton linuxMintCinnamon = new JButton("Linux Mint Cinnamon Version 21", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/linux-mint/linux-mint_round.png")));
//    static JButton linuxMintMate = new JButton("Linux Mint MATE Edition Version 21", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/linux-mint/linux-mint_round.png")));
//    static JButton linuxMintXfce = new JButton("Linux Mint Xfce Edition Version 21", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/linux-mint/linux-mint_round.png")));
//    static JButton manjaroGnome = new JButton("Manjaro Gnome Edition", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/manjaro/manjaro_round.png")));
//    static JButton manjaroXFCE = new JButton("Manjaro XFCE Edition", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/manjaro/manjaro_round.png")));
//    static JButton manjaroKDE = new JButton("Manjaro KDE Edition", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/manjaro/manjaro_round.png")));
//    static JButton solusMATE = new JButton("Solus MATE Edition Version 4.3", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/solus/solus_round.png")));
//    static JButton solusBudgie = new JButton("Solus Budgie Edition Version 4.3", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/solus/solus_round.png")));
//    static JButton solusGnome = new JButton("Solus Gnome Edition Version 4.3", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/solus/solus_round.png")));
//    static JButton solusKDE = new JButton("Solus KDE Edition Version 4.3", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/solus/solus_round.png")));
//    static JButton ubuntuServerLTS = new JButton("Ubuntu Server 22.04(Jammy Jellyfish)", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/ubuntu/original/ubuntu_round.png")));
//    static JButton ubuntuLTS = new JButton("Ubuntu 22.04(Jammy Jellyfish)", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/ubuntu/original/ubuntu_round.png")));
//    static JButton ubuntuServer = new JButton("Ubuntu Server 22.04 LTS(Jammy Jellyfish)", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/ubuntu/original/ubuntu_round.png")));
//    static JButton ubuntu = new JButton("Ubuntu 22.10 (Kinetic Kudu)", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/ubuntu/original/ubuntu_round.png")));
//    static JButton ubuntuUnity = new JButton(" Ubuntu Unity 22.10(Kinetic Kudu)", new ImageIcon(WaveUSB.class.getResource("images/Linux_Icons/ubuntu/original/ubuntu_round.png")));
//    //Windows Buttons
//    static JButton windows8 = new JButton("Windows 8.1 32-bit", new ImageIcon(WaveUSB.class.getResource("images/Windows_Icons/Win8And10/windows_round.png")));
//    static JButton windows8_64 = new JButton("Windows 8.1 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Windows_Icons/Win8And10/windows_round.png")));
//    static JButton windows10 = new JButton("Windows 10 32-bit", new ImageIcon(WaveUSB.class.getResource("images/Windows_Icons/Win8And10/windows_round.png")));
//    static JButton windows10_64 = new JButton("Windows 10 64-bit", new ImageIcon(WaveUSB.class.getResource("images/Windows_Icons/Win8And10/windows_round.png")));
//    static JButton windows11 = new JButton("Windows 11", new ImageIcon(WaveUSB.class.getResource("images/Windows_Icons/Win11/win11_round.png")));
//    static JButton windowsXP = new JButton("Windows XP 32-bit", new ImageIcon(WaveUSB.class.getResource("images/Windows_Icons/Win7AndXP/winxp_round.png")));
//    static JButton windowsXP_64 = new JButton("Windows XP 64-bit ", new ImageIcon(WaveUSB.class.getResource("images/Windows_Icons/Win7AndXP/winxp_round.png")));
    //(SubComment) Back and next buttons for the whole application
    static JButton back = new JButton("Back");
    static JButton back2 = new JButton("Back");
    static JButton back3 = new JButton("Back");
    static JButton back4 = new JButton("Back");
    static JButton back5 = new JButton("Back");
    static JButton back6 = new JButton("Back");
    static JButton back7 = new JButton("Back");
    static JButton back8 = new JButton("Back");
    static JButton back9 = new JButton("Back");
    static JButton back10 = new JButton("Back");
    static JButton next1 = new JButton("Next");
    static JButton next2 = new JButton("Next");
    static JButton selectUSB = new JButton("Select USB...");
    //Other buttons
    static JButton bootloaderNext = new JButton("Next");
    //Radio buttons
    static JRadioButton sysLinuxOption = new JRadioButton("Syslinux Bootloader");
    static JRadioButton grubOption = new JRadioButton("Grub");
    static JRadioButton cloverButton = new JRadioButton("Clover bootloader");
    static JRadioButton grub = new JRadioButton("Grub bootloader");
    static JRadioButton grub_Windows = new JRadioButton("");
    static JRadioButton noneBootloaderOption = new JRadioButton("No bootloader");
    static JComboBox<String> fsFormatSelectionMenu = new JComboBox<String>();
    static JRadioButton APFS = new JRadioButton("APFS");
    static JRadioButton HFS_PLUS = new JRadioButton("HFS+");
    static JRadioButton EXFAT = new JRadioButton("EXFAT");
    static JRadioButton EXT4 = new JRadioButton("EXT4");
    static JRadioButton FAT32 = new JRadioButton("FAT32");
    static JRadioButton NTFS = new JRadioButton("NTFS");
    static JRadioButton BTRFS = new JRadioButton("BTRFS");
    static JTextField mainPartitionName = new JTextField();
    static JTextField EFI_PartitionName = new JTextField();
    static JLabel efiNotice = new JLabel("EFI partition name:");
    static JLabel mainPartitionNotice = new JLabel("Main partition name:");
    static JLabel filesystem = new JLabel("Filesystem format:");
    static JLabel bootloaderType = new JLabel("Bootloader type:");
    static JLabel freeSpaceLabel = new JLabel("Writeable free space:");
    static JLabel mainDiskLabel = new JLabel("Main bootable disk space:");
    static JTextField freeDiskSpace = new JTextField();
    static JTextField mainDiskSpace = new JTextField();
    static JCheckBox canZeroOutDisk = new JCheckBox("Zero out disk?");
    static JLabel passesLabel = new JLabel("Number of passes:");
    static JSpinner passes = new JSpinner();
    //File Chooser Windows for Choosing Images and USB Directories
    static JFileChooser chooseUSB = new JFileChooser();
    static JFileChooser isoImageChooser = new JFileChooser();
    //Menu Items
    static JMenuItem closeApplication = new JMenuItem("Close Application");
    static JMenuItem updateApplication = new JMenuItem("Check for updates");
    static JMenuItem settings = new JMenuItem("Settings");
    static JCheckBoxMenuItem flashAnywhere = new JCheckBoxMenuItem("Flash The Installer files anywhere");
    //Labels
    static JLabel helpLabel = new JLabel("Stuck? More Info at ");
    static JLabel downloadIcon = new JLabel(new ImageIcon(Definitions.class.getResource("images/app_icons/iso.png")));
    static JLabel downloadLabel = new JLabel("Downloaded 0 out 0 MB");
    static JLabel waitForDownload = new JLabel("Please wait while we download the ISO file to " + SoftwareInfo.getISODirectory().replaceAll(SoftwareInfo.getHomeDirectory(), ""));
    //Unspecific Declarations
    static File image = null;
    static String os = SoftwareInfo.getPlatform();
    static USBDeviceDetectorManager usbDetectorManager = new USBDeviceDetectorManager();
    static Desktop desktopActions = Desktop.getDesktop();
    static ArrayList<Image> icons = new ArrayList<Image>();
    static Toolkit tk = Toolkit.getDefaultToolkit();
    static SwingWorker<Void, Void> updateProgressBar = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            final int maxProgress = isoURL.openConnection().getContentLength();
            int progress = 0;
            while (progress < maxProgress) {
                progressBar.setValue(progress);
                progress = (int) (image.length() / 1000000);
            }
            return null;
        }
    };
    private static byte osToWrite = 0;
    private static byte bootloaderToWrite = 0;

    public static JLabel getStatusLabel() {
        return statusLabel;
    }

//    public static void initializeDefinitions() {
//        finishedScreen.setRestartHandler(e -> {
//            layout.show(applicationPanel, "1");
//        });
//        if (System.getProperty("os.version").charAt(1) != '0') {
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_16x16.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_16x16@2x.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_32x32.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_32x32@2x.png")));
////            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_64x64.png")));
////            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_64x64@2x.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_128x128.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_128x128@2x.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_256x256.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS11/icon_256x256@2x.png")));
//        }
//        if (System.getProperty("os.version").charAt(1) == '0') {
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_16x16.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_16x16@2x.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_32x32.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_32x32@2x.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_64x64.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_64x64@2x.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_128x64.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_128x128@2x.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_256x256.png")));
//            icons.add(tk.getImage(WaveUSB.class.getResource("images/app_icons/ico_macOS1015/icon_256x256@2x.png")));
//        }
//        if (flashAnywhere.getState()) chooseUSB.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//        else chooseUSB.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        isoImageChooser.setFileHidingEnabled(true);
//        isoImageChooser.setApproveButtonMnemonic(KeyEvent.VK_ENTER);
//        isoImageChooser.setAutoscrolls(true);
//        isoImageChooser.setDialogTitle("Choose an image file (*.iso *.dmg *.img *.zip)");
//        isoImageChooser.setFileFilter(new FileNameExtensionFilter("ISO image files (*.iso)", "iso"));
//        isoImageChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tape Archives with gz(*.tar.gz)", "gz"));
//        isoImageChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tape Archives with xz(*.tar.xz)", "xz"));
//        isoImageChooser.addChoosableFileFilter(new FileNameExtensionFilter("ZIP files (*.zip)", "zip"));
//        isoImageChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tape Atchives(*.tar)", "tar"));
//        isoImageChooser.addChoosableFileFilter(new FileNameExtensionFilter("DMG image files (*.dmg)", "dmg"));
//        isoImageChooser.addChoosableFileFilter(new FileNameExtensionFilter("IMG Floppy Disk image files (*.img)", "img"));
//        frame.setIconImages(icons);
//        usbDirectory.setForeground(Color.gray);
//        chooseUSB.setForeground(Color.gray);
//    }

    public static File getBootloaderDirectory() {
        return bootloaderDirectory;
    }

    public static File getIsoDirectory() {
        return isoDirectory;
    }

    public static JPanel getDownloadScreen() {
        return downloadScreen;
    }

    public static JLabel getLinuxChoose2() {
        return linuxChoose2;
    }

    public static JLabel getLinuxChoose3() {
        return linuxChoose3;
    }

    public static JLabel getLinuxChoose4() {
        return linuxChoose4;
    }

    public static JTextField getUsbName() {
        return usbName;
    }

    public static JProgressBar getProgressBar() {
        return progressBar;
    }

    public static JButton getBack9() {
        return back9;
    }

    public static JButton getNext1() {
        return next1;
    }

    public static JButton getNext2() {
        return next2;
    }

    public static JLabel getHelpLabel() {
        return helpLabel;
    }

    public static JMenuItem getSettings() {
        return settings;
    }

    public static JCheckBoxMenuItem getFlashAnywhere() {
        return flashAnywhere;
    }

    public static ArrayList<Image> getIcons() {
        return icons;
    }

    public CardLayout getLayout() {
        return layout;
    }

    public JPanel getApplicationPanel() {
        return applicationPanel;
    }

    public JPanel getLinuxPane() {
        return linuxPane;
    }

    public JFrame getMainFrame() {
        return frame;
    }

    public JPanel getMacOSPane() {
        return macOSPane;
    }

    public JPanel getMacOSEULA() {
        return macOSEULA;
    }

    public JPanel getOthersPane() {
        return othersPane;
    }

    public JPanel getWelcomePane() {
        return homePane;
    }

    public JPanel getWindowsPane() {
        return windowsPane;
    }

    public JPanel getChooseBootloaderPane() {
        return usbOptions;
    }

    public JPanel getWriteImageToUSB() {
        return downloadScreen;
    }

    public JPanel getDebianPane() {
        return debianPane;
    }

    public JPanel getArchPane() {
        return archPane;
    }

    public JPanel getMiscLinuxPane() {
        return miscLinuxPane;
    }

    public JLabel getSupportedFileTypes() {
        return supportedFileTypes;
    }

    public JLabel getChooseOS() {
        return chooseOS;
    }

    public JLabel getChooseMacOSLabel() {
        return chooseMacOSLabel;
    }

    public JLabel getLinuxChoose() {
        return linuxChoose;
    }

    public JLabel getOthersChoose() {
        return othersChoose;
    }

    public JLabel getProgressLabel() {
        return progressLabel;
    }

    public JLabel getWallpaper() {
        return wallpaper;
    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

    public JLabel getWindowsChose() {
        return windowsChose;
    }

    public JTextField getImageFileDirectory() {
        return imageFileDirectory;
    }

    public JTextArea getjTextArea() {
        return jTextArea;
    }

    public JTextField getUsbDirectory() {
        return usbDirectory;
    }

    public JInternalFrame getjInternalFrame() {
        return jInternalFrame;
    }

    public JProgressBar getjProgressBar() {
        return progressBar;
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

    public JScrollBar getjScrollBar() {
        return jScrollBar;
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public JMenu getSettingsMenu() {
        return settingsMenu;
    }

    public JMenu getHelpMenu() {
        return helpMenu;
    }

    public JButton getBack() {
        return back;
    }

    public JButton getBack2() {
        return back2;
    }

    public JButton getBack3() {
        return back3;
    }

    public JButton getBack4() {
        return back4;
    }

    public JButton getBack5() {
        return back5;
    }

    public JButton getBack6() {
        return back6;
    }

    public JButton getBack7() {
        return back7;
    }

    public JButton getBack8() {
        return back8;
    }

    public JButton getNext() {
        return next1;
    }

    public JButton getProceed() {
        return next2;
    }

    public JButton getSelectUSB() {
        return selectUSB;
    }

    public JButton getBootloaderNext() {
        return bootloaderNext;
    }

    public JRadioButton getSysLinuxButton() {
        return sysLinuxOption;
    }

    public JRadioButton getCloverButton() {
        return cloverButton;
    }

    public JRadioButton getNoneBootloaderOption() {
        return noneBootloaderOption;
    }

    public JFileChooser getChooseUSB() {
        return chooseUSB;
    }

    public JFileChooser getIsoImageChooser() {
        return isoImageChooser;
    }

    public JMenuItem getCloseApplication() {
        return closeApplication;
    }


    public JMenuItem getUpdateApplication() {
        return updateApplication;
    }

    public String getOs() {
        return os;
    }

    public USBDeviceDetectorManager getUsbDetectorManager() {
        return usbDetectorManager;
    }

    public Desktop getDesktopActions() {
        return desktopActions;
    }
}

