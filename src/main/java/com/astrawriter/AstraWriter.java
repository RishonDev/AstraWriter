package com.astrawriter;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import com.formdev.flatlaf.util.SystemFileChooser;


public class AstraWriter extends AstraUI.AstraWindow {

    // adjustable sizes — change later as needed
    private FileChooser fileChooser = new FileChooser();
    private Dimension collapsedSize = new Dimension(700, 500);
    private Dimension expandedSize  = new Dimension(1000, 700);

    // animation
    private Timer resizeTimer;
    private boolean expanded = false;
    private int animDurationMs = 450; // total animation duration
    private int animIntervalMs = 16;  // ~60fps

    public AstraWriter(){
        super("AstraWriter");
        setSize(collapsedSize);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        // initial theme: DARK
        AstraUI.AstraTheme.setMode(AstraUI.AstraTheme.Mode.DARK);
        buildUI();
    }

    private void buildUI(){
        AstraUI.AstraIconLabel lockIcon = new AstraUI.AstraIconLabel(new ImageIcon(Objects.requireNonNull(AstraWriter.class.getResource("image-3.png"))));
        lockIcon.setBounds(10,0,64,64);
        add(lockIcon);

        AstraUI.AstraTitleLabel title = new AstraUI.AstraTitleLabel("Astra Writer");
        title.setBounds(80,10,400,40);
        add(title);

        AstraUI.AstraIconLabel moonIcon = new AstraUI.AstraIconLabel(new ImageIcon(Objects.requireNonNull(AstraWriter.class.getResource("moon.png"))));
        moonIcon.setBounds(560,0,80,80);
        add(moonIcon);

        AstraUI.AstraRoundButton moonButton = new AstraUI.AstraRoundButton("");
        moonButton.setBounds(590,24,35,35);
        add(moonButton);

        AstraUI.AstraIconLabel settingsIcon = new AstraUI.AstraIconLabel(new ImageIcon(Objects.requireNonNull(AstraWriter.class.getResource("gear.png"))));
        settingsIcon.setBounds(627,27,80,30);
        add(settingsIcon);

        AstraUI.AstraRoundButton settingsButton = new AstraUI.AstraRoundButton("");
        settingsButton.setBounds(640,24,35,35);
        add(settingsButton);

        AstraUI.AstraSubtitleLabel subtitle = new AstraUI.AstraSubtitleLabel("Create bootable USB drives with ease.");
        subtitle.setBounds(20,55,450,25);
        add(subtitle);

        // left column
        int leftX = 20;
        int y = 95;

        AstraUI.AstraGroupBox gbDevice = new AstraUI.AstraGroupBox("TARGET DEVICE");
        gbDevice.setBounds(leftX,y,200,20);
        add(gbDevice);
        y += 30;

        AstraUI.AstraRoundedPanel devicePanel = new AstraUI.AstraRoundedPanel(14);
        devicePanel.setLayout(null);
        devicePanel.setBounds(leftX,y,300,50);
        add(devicePanel);

        AstraUI.AstraTextField deviceTextField = new AstraUI.AstraTextField(new ImageIcon(Objects.requireNonNull(AstraWriter.class.getResource("ub.png"))));
        deviceTextField.addActionListener(e -> {
            deviceTextField.setText(fileChooser.getDisk());
        });
        deviceTextField.setBounds(0,0,300,50);
        devicePanel.add(deviceTextField);
        y += 70;

        AstraUI.AstraGroupBox gbImage = new AstraUI.AstraGroupBox("BOOT IMAGE");
        gbImage.setBounds(leftX,y,200,20);
        add(gbImage);
        y += 30;

        AstraUI.AstraRoundedPanel imagePanel = new AstraUI.AstraRoundedPanel(14);
        imagePanel.setLayout(null);
        imagePanel.setBounds(leftX,y,300,50);
        add(imagePanel);

        AstraUI.AstraTextField imageTextField = new AstraUI.AstraTextField(new ImageIcon(AstraWriter.class.getResource("")));
        imageTextField.setBounds(0,0,300,50);
        imagePanel.add(imageTextField);
        imageTextField.addActionListener(e ->{
            imageTextField.setText(fileChooser.getImage());
        });
        y += 70;

        AstraUI.AstraGroupBox gbFormat = new AstraUI.AstraGroupBox("FORMAT & SCHEME");
        gbFormat.setBounds(leftX,y,200,20);
        add(gbFormat);
        y += 30;

        AstraUI.AstraRoundedPanel formatPanel = new AstraUI.AstraRoundedPanel(14);
        formatPanel.setLayout(null);
        formatPanel.setBounds(leftX,y,300,50);
        add(formatPanel);

        AstraUI.AstraToggleButton btnGPT = new AstraUI.AstraToggleButton("GPT");
        btnGPT.setBounds(0,0,150,50);
        btnGPT.setSelected(true);
        formatPanel.add(btnGPT);

        AstraUI.AstraToggleButton btnMBR = new AstraUI.AstraToggleButton("MBR");
        btnMBR.setBounds(150,0,150,50);
        formatPanel.add(btnMBR);

        // toggle linking
        btnGPT.addPropertyChangeListener("selected", e -> { if((boolean)e.getNewValue()) btnMBR.setSelected(false); });
        btnMBR.addPropertyChangeListener("selected", e -> { if((boolean)e.getNewValue()) btnGPT.setSelected(false); });

        // Right card
        AstraUI.AstraCardPanel card = new AstraUI.AstraCardPanel();
        card.setBounds(350,95,330,300);
        add(card);

        AstraUI.AstraIconLabel osLogo = new AstraUI.AstraIconLabel("[ubuntu-logo]");
        osLogo.setBounds(100,20,140,40);
        card.add(osLogo);

        AstraUI.AstraLabel osTitle = new AstraUI.AstraLabel("Detected OS:");
        osTitle.setFont(AstraUI.AstraStyles.medium(14f));
        osTitle.setBounds(100,70,200,22);
        card.add(osTitle);

        AstraUI.AstraLabel osName = new AstraUI.AstraLabel("Ubuntu 24.04 - 64-bit");
        osName.setFont(AstraUI.AstraStyles.mediumBold(16f));
        osName.setBounds(60,95,220,24);
        card.add(osName);

        AstraUI.AstraProgressPanel progress = new AstraUI.AstraProgressPanel();
        progress.setBarSize(300,20);
        progress.setTitle("Writing Image…");
        progress.setValueAnimated(0);
        progress.setEstimatedTime("Estimated write time: 4 min");
        progress.setBounds(15,200,300,80);
        card.add(progress);

        // Footer
        AstraUI.AstraIconLabel okIcon = new AstraUI.AstraIconLabel(new ImageIcon(AstraWriter.class.getResource("check3.png")));
        okIcon.setBounds(20,410,64,64);
        add(okIcon);

        AstraUI.AstraLabel readyLabel = new AstraUI.AstraLabel("Ready to Write");
        readyLabel.setFont(AstraUI.AstraStyles.medium(16f));
        readyLabel.setBounds(65,430,200,30);
        add(readyLabel);

        AstraUI.AstraButton createBtn = new AstraUI.AstraButton("Create Bootable Drive →");
        createBtn.setBounds(430,425,230,40);
        add(createBtn);

        // Expand / Contract button
        AstraUI.ExpandButton expandBtn = new AstraUI.ExpandButton();
        expandBtn.setBounds(20,390,160,34);
        add(expandBtn);

        // Expand action
        expandBtn.addActionListener(e -> {
            if (resizeTimer != null && resizeTimer.isRunning()) return;

            Dimension from = getSize();
            Dimension to   = expanded ? collapsedSize : expandedSize;

            int startW = from.width, startH = from.height;
            int targetW = to.width,   targetH = to.height;

            long startTime = System.currentTimeMillis();
            resizeTimer = new Timer(animIntervalMs, null);

            resizeTimer.addActionListener(evt -> {
                float t = (System.currentTimeMillis() - startTime) / (float) animDurationMs;
                if (t > 1f) t = 1f;

                // Ease-in-out cubic
                float tt = (t < 0.5f)
                        ? (4 * t * t * t)
                        : (1 - (float)Math.pow(-2 * t + 2, 3) / 2f);

                int nw = Math.round(startW + (targetW - startW) * tt);
                int nh = Math.round(startH + (targetH - startH) * tt);

                setSize(nw, nh);
                revalidate();
                repaint();
                setLocationRelativeTo(null);

                if (t >= 1f) {
                    resizeTimer.stop();
                    expanded = !expanded;

                    // NEW — your ExpandButton updates itself
                    expandBtn.setExpanded(expanded);
                }
            });

            resizeTimer.start();
        });

        // Example theme toggle (you can wire this to a settings toggle)
        // For demonstration add a small theme-toggle button
//        AstraUIKit.AstraButton themeBtn = new AstraUIKit.AstraButton("Light");
//        themeBtn.setBounds(540, 15, 80, 30);
//        add(themeBtn);
//        themeBtn.addActionListener(ae -> {
//            if (AstraUIKit.AstraTheme.getMode() == AstraUIKit.AstraTheme.Mode.DARK) {
//                AstraUIKit.AstraTheme.setMode(AstraUIKit.AstraTheme.Mode.LIGHT);
//                themeBtn.setText("Dark");
//            } else {
//                AstraUIKit.AstraTheme.setMode(AstraUIKit.AstraTheme.Mode.DARK);
//                themeBtn.setText("Light");
//            }
//            // repaint everything
//            repaintAll(this);
//        });

    }

    // helper to repaint whole window after theme switch
    private void repaintAll(Component root) {
        root.repaint();
        if (root instanceof Container) {
            for (Component c : ((Container) root).getComponents()) repaintAll(c);
        }
    }

    static void main(){
        SwingUtilities.invokeLater(() -> {
            AstraWriter w = new AstraWriter();
            w.setVisible(true);
        });
    }
}

class FileChooser{
    public FileChooser(){
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("ISO File(.iso)", "iso"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("DMG File(.dmg)", "dmg"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("XZ Archive(.xz)", "xz"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("TAR Archive(.iso)", "tar"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("macOS Installer(.pkg)", "pkg"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("RAW Image(.img)", "img"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("RAW Image(.cdr)", "cdr"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("Binary Image(.bin)", "bin"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("Compressed Image(.tar.xz)", "tar", "xz"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("Compressed Image(.tar.gz)", "tar", "gz"));
        imgChooser.addChoosableFileFilter(new SystemFileChooser.FileNameExtensionFilter("Compressed Image(.zip)", "zip"));
        imgChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        imgChooser.setMultiSelectionEnabled(false);
        imgChooser.setDialogTitle("Select a Image");
        imgChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Select a Volume");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
    }
    JFileChooser chooser = new JFileChooser();
    FileSystemView fsv = FileSystemView.getFileSystemView();
    SystemFileChooser imgChooser = new SystemFileChooser();
    public String getDisk(){
        String os =  System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            fsv = createDriveOnlyFSV(getDisksWindows());
            chooser = new JFileChooser(fsv);
            chooser.setAcceptAllFileFilterUsed(false);
        }
        else if (os.contains("mac"))
            chooser.setCurrentDirectory(new File("/Volumes/"));
        else if (os.contains("linux"))
            chooser.setCurrentDirectory(new File("/media/"));

        if (chooser.showOpenDialog(null) == 0)
            return chooser.getSelectedFile().getAbsolutePath();
        else return "";
    }
    private File[] getDisksWindows() {
        ArrayList<File> drives = new ArrayList<>(26);

        for (char c = 'A'; c <= 'Z'; c++) {
            File drive = new File(c + ":\\");
            if (drive.exists()) {
                drives.add(drive);
            }
        }

        return drives.toArray(new File[0]);
    }
    public String getImage(){
        if (imgChooser.showOpenDialog(null) == 0)return imgChooser.getSelectedFile().getAbsolutePath();
        else return "";

    }
    private FileSystemView createDriveOnlyFSV(File[] drives) {
        return new FileSystemView() {

            @Override
            public File[] getRoots() {
                return drives; // Show ONLY these drives
            }

            @Override
            public boolean isRoot(File f) {
                for (File d : drives) {
                    if (d.equals(f)) return true;
                }
                return false;
            }

            @Override
            public File createNewFolder(File containingDir) throws IOException {
                return null;
            }

            @Override
            public File getHomeDirectory() {
                return drives.length > 0 ? drives[0] : new File("C:\\");
            }

            @Override
            public File getDefaultDirectory() {
                return getHomeDirectory();
            }
        };
    }


}

class InstallerImage{
    public final byte VENTOY = 0;
    public final byte GRUB = 1;
    public final byte SYSLINUX = 2;
    public final byte CLOVER = 3;
    public final byte reFINd = 4;
    public final byte LIMINE = 4;
    //Detecting the ISO
    public void detectOSType(String iso){}

    //Ubuntu: detect from casper folder
    public void detectDistro(String iso){}

    //Ubuntu: Detect from boot.efi
    public void detectArch(String iso){}

    //Installing the OS
    public void installBootloader(String path){}
    public void installmacOS(String app, String path){}
    public void installXZArchive(String path){}
    public void installTarArchive(String path){}
    public void installPkg(String path){}

}