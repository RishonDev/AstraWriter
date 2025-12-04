package com.astrawriter;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class AstraWriter extends AstraUIKit.AstraWindow {

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
        AstraUIKit.AstraTheme.setMode(AstraUIKit.AstraTheme.Mode.DARK);
        buildUI();
    }

    private void buildUI(){
        AstraUIKit.AstraIconLabel lockIcon = new AstraUIKit.AstraIconLabel(new ImageIcon(Objects.requireNonNull(AstraWriter.class.getResource("image-3.png"))));
        lockIcon.setBounds(10,0,64,64);
        add(lockIcon);

        AstraUIKit.AstraTitleLabel title = new AstraUIKit.AstraTitleLabel("Astra Writer");
        title.setBounds(80,10,400,40);
        add(title);

        AstraUIKit.AstraIconLabel moonIcon = new AstraUIKit.AstraIconLabel(new ImageIcon(Objects.requireNonNull(AstraWriter.class.getResource("moon.png"))));
        moonIcon.setBounds(560,0,80,80);
        add(moonIcon);

        AstraUIKit.AstraRoundButton moonButton = new AstraUIKit.AstraRoundButton("");
        moonButton.setBounds(590,24,35,35);
        add(moonButton);

        AstraUIKit.AstraIconLabel settingsIcon = new AstraUIKit.AstraIconLabel(new ImageIcon(Objects.requireNonNull(AstraWriter.class.getResource("gear.png"))));
        settingsIcon.setBounds(627,27,80,30);
        add(settingsIcon);

        AstraUIKit.AstraRoundButton settingsButton = new AstraUIKit.AstraRoundButton("");
        settingsButton.setBounds(640,24,35,35);
        add(settingsButton);

        AstraUIKit.AstraSubtitleLabel subtitle = new AstraUIKit.AstraSubtitleLabel("Create bootable USB drives with ease.");
        subtitle.setBounds(20,55,450,25);
        add(subtitle);

        // left column
        int leftX = 20;
        int y = 95;

        AstraUIKit.AstraGroupBox gbDevice = new AstraUIKit.AstraGroupBox("TARGET DEVICE");
        gbDevice.setBounds(leftX,y,200,20);
        add(gbDevice);
        y += 30;

        AstraUIKit.AstraRoundedPanel devicePanel = new AstraUIKit.AstraRoundedPanel(14);
        devicePanel.setLayout(null);
        devicePanel.setBounds(leftX,y,300,50);
        add(devicePanel);

        AstraUIKit.AstraTextField deviceTextField = new AstraUIKit.AstraTextField(new ImageIcon(Objects.requireNonNull(AstraWriter.class.getResource("ub.png"))));
        deviceTextField.addActionListener(e -> {
            deviceTextField.setText(fileChooser.getDisk());
        });
        deviceTextField.setBounds(0,0,300,50);
        devicePanel.add(deviceTextField);
        y += 70;

        AstraUIKit.AstraGroupBox gbImage = new AstraUIKit.AstraGroupBox("BOOT IMAGE");
        gbImage.setBounds(leftX,y,200,20);
        add(gbImage);
        y += 30;

        AstraUIKit.AstraRoundedPanel imagePanel = new AstraUIKit.AstraRoundedPanel(14);
        imagePanel.setLayout(null);
        imagePanel.setBounds(leftX,y,300,50);
        add(imagePanel);

        AstraUIKit.AstraTextField imageCombo = new AstraUIKit.AstraTextField(new ImageIcon(AstraWriter.class.getResource("")));
        imageCombo.setBounds(0,0,300,50);
        imagePanel.add(imageCombo);
        y += 70;

        AstraUIKit.AstraGroupBox gbFormat = new AstraUIKit.AstraGroupBox("FORMAT & SCHEME");
        gbFormat.setBounds(leftX,y,200,20);
        add(gbFormat);
        y += 30;

        AstraUIKit.AstraRoundedPanel formatPanel = new AstraUIKit.AstraRoundedPanel(14);
        formatPanel.setLayout(null);
        formatPanel.setBounds(leftX,y,300,50);
        add(formatPanel);

        AstraUIKit.AstraToggleButton btnGPT = new AstraUIKit.AstraToggleButton("GPT");
        btnGPT.setBounds(0,0,150,50);
        btnGPT.setSelected(true);
        formatPanel.add(btnGPT);

        AstraUIKit.AstraToggleButton btnMBR = new AstraUIKit.AstraToggleButton("MBR");
        btnMBR.setBounds(150,0,150,50);
        formatPanel.add(btnMBR);

        // toggle linking
        btnGPT.addPropertyChangeListener("selected", e -> { if((boolean)e.getNewValue()) btnMBR.setSelected(false); });
        btnMBR.addPropertyChangeListener("selected", e -> { if((boolean)e.getNewValue()) btnGPT.setSelected(false); });

        // Right card
        AstraUIKit.AstraCardPanel card = new AstraUIKit.AstraCardPanel();
        card.setBounds(350,95,330,300);
        add(card);

        AstraUIKit.AstraIconLabel osLogo = new AstraUIKit.AstraIconLabel("[ubuntu-logo]");
        osLogo.setBounds(100,20,140,40);
        card.add(osLogo);

        AstraUIKit.AstraLabel osTitle = new AstraUIKit.AstraLabel("Detected OS:");
        osTitle.setFont(AstraUIKit.AstraStyles.medium(14f));
        osTitle.setBounds(100,70,200,22);
        card.add(osTitle);

        AstraUIKit.AstraLabel osName = new AstraUIKit.AstraLabel("Ubuntu 24.04 - 64-bit");
        osName.setFont(AstraUIKit.AstraStyles.mediumBold(16f));
        osName.setBounds(60,95,220,24);
        card.add(osName);

        AstraUIKit.AstraProgressPanel progress = new AstraUIKit.AstraProgressPanel();
        progress.setBarSize(300,20);
        progress.setTitle("Writing Image…");
        progress.setValueAnimated(65);
        progress.setEstimatedTime("Estimated write time: 4 min");
        progress.setBounds(15,200,300,80);
        card.add(progress);

        // Footer
        AstraUIKit.AstraIconLabel okIcon = new AstraUIKit.AstraIconLabel(new ImageIcon(AstraWriter.class.getResource("check3.png")));
        okIcon.setBounds(20,410,64,64);
        add(okIcon);

        AstraUIKit.AstraLabel readyLabel = new AstraUIKit.AstraLabel("Ready to Write");
        readyLabel.setFont(AstraUIKit.AstraStyles.medium(16f));
        readyLabel.setBounds(65,430,200,30);
        add(readyLabel);

        AstraUIKit.AstraButton createBtn = new AstraUIKit.AstraButton("Create Bootable Drive →");
        createBtn.setBounds(430,425,230,40);
        add(createBtn);

        // Expand / Contract button
        AstraUIKit.AstraButton expandBtn = new AstraUIKit.AstraButton("Expand");
        expandBtn.setBounds(320,425,90,34);
        add(expandBtn);

        // Expand action: animate between collapsedSize and expandedSize
        expandBtn.addActionListener(e -> {
            if (resizeTimer != null && resizeTimer.isRunning()) return; // ignore while animating
            Dimension from = getSize();
            Dimension to = expanded ? collapsedSize : expandedSize;
            int frames = Math.max(1, animDurationMs / animIntervalMs);
            int startW = from.width, startH = from.height;
            int targetW = to.width, targetH = to.height;
            final int[] step = {0};
            resizeTimer = new Timer(animIntervalMs, null);
            long startTime = System.currentTimeMillis();
            resizeTimer.addActionListener(evt -> {
                float t = (System.currentTimeMillis() - startTime) / (float) animDurationMs;
                if (t >= 1f) t = 1f;
                // ease in-out cubic
                float tt = (t < 0.5f) ? (4*t*t*t) : (1 - (float)Math.pow(-2*t + 2, 3)/2f);
                int nw = Math.round(startW + (targetW - startW) * tt);
                int nh = Math.round(startH + (targetH - startH) * tt);
                setSize(nw, nh);
                revalidate(); repaint();
                // keep centered during animation
                setLocationRelativeTo(null);
                if (t >= 1f) {
                    resizeTimer.stop();
                    expanded = !expanded;
                    expandBtn.setText(expanded ? "Contract" : "Expand");
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
    JFileChooser chooser = new JFileChooser();
    FileSystemView fsv = FileSystemView.getFileSystemView();
    public String getDisk(){
        String os =  System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            fsv = createDriveOnlyFSV(getDisksWindows());
            chooser = new JFileChooser(fsv);
            chooser.setAcceptAllFileFilterUsed(false);
        }
        else if (os.contains("mac")) chooser.setCurrentDirectory(new File("/Volumes/"));
        else if (os.contains("linux"))chooser.setCurrentDirectory(new File("/media/"));

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Select a Volume");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        if (chooser.showOpenDialog(null) == 0)
            return chooser.getSelectedFile().getAbsolutePath();
        return null;
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