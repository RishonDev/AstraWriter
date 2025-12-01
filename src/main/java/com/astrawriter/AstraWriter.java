package com.astrawriter;

import javax.swing.*;
import java.awt.*;

/**
 * AstraWriter.java
 *
 * Uses only AstraUIKit components.
 * Null layout, medium layout 700x500 (collapsed), expandable to configurable size.
 *
 * Expand/Contract animation implemented via Timer interpolation.
 */
public class AstraWriter extends AstraUIKit.AstraWindow {

    // adjustable sizes — change later as needed
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
        // header icons and title
        AstraUIKit.AstraIconLabel lockIcon = new AstraUIKit.AstraIconLabel("[lock]");
        lockIcon.setBounds(20,15,60,30);
        add(lockIcon);

        AstraUIKit.AstraTitleLabel title = new AstraUIKit.AstraTitleLabel("Astra Writer");
        title.setBounds(80,10,400,40);
        add(title);

        AstraUIKit.AstraIconLabel moonIcon = new AstraUIKit.AstraIconLabel("[moon]");
        moonIcon.setBounds(600,15,50,30);
        add(moonIcon);

        AstraUIKit.AstraIconLabel settingsIcon = new AstraUIKit.AstraIconLabel("[settings]");
        settingsIcon.setBounds(640,15,60,30);
        add(settingsIcon);

        AstraUIKit.AstraSubtitleLabel subtitle = new AstraUIKit.AstraSubtitleLabel("Create bootable USB drives with style.");
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

        AstraUIKit.AstraIconLabel devLock = new AstraUIKit.AstraIconLabel("[lock]");
        devLock.setBounds(12,10,40,30);
        devicePanel.add(devLock);

        AstraUIKit.AstraComboBox deviceCombo = new AstraUIKit.AstraComboBox();
        deviceCombo.setBounds(60,10,220,30);
        devicePanel.add(deviceCombo);
        y += 70;

        AstraUIKit.AstraGroupBox gbImage = new AstraUIKit.AstraGroupBox("BOOT IMAGE");
        gbImage.setBounds(leftX,y,200,20);
        add(gbImage);
        y += 30;

        AstraUIKit.AstraRoundedPanel imagePanel = new AstraUIKit.AstraRoundedPanel(14);
        imagePanel.setLayout(null);
        imagePanel.setBounds(leftX,y,300,50);
        add(imagePanel);

        AstraUIKit.AstraComboBox imageCombo = new AstraUIKit.AstraComboBox();
        imageCombo.setBounds(12,10,276,30);
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
        btnGPT.setBounds(12,10,90,30);
        btnGPT.setSelected(true);
        formatPanel.add(btnGPT);

        AstraUIKit.AstraToggleButton btnMBR = new AstraUIKit.AstraToggleButton("MBR");
        btnMBR.setBounds(108,10,90,30);
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
        progress.setTitle("Writing Image…");
        progress.setValueAnimated(65);
        progress.setEstimatedTime("Estimated write time: 4 min");
        progress.setBounds(15,140,300,80);
        card.add(progress);

        // Footer
        AstraUIKit.AstraIconLabel okIcon = new AstraUIKit.AstraIconLabel("[check]");
        okIcon.setBounds(20,430,50,30);
        add(okIcon);

        AstraUIKit.AstraLabel readyLabel = new AstraUIKit.AstraLabel("Ready to Write");
        readyLabel.setFont(AstraUIKit.AstraStyles.medium(16f));
        readyLabel.setBounds(60,430,200,30);
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
        AstraUIKit.AstraButton themeBtn = new AstraUIKit.AstraButton("Light");
        themeBtn.setBounds(540, 15, 80, 30);
        add(themeBtn);
        themeBtn.addActionListener(ae -> {
            if (AstraUIKit.AstraTheme.getMode() == AstraUIKit.AstraTheme.Mode.DARK) {
                AstraUIKit.AstraTheme.setMode(AstraUIKit.AstraTheme.Mode.LIGHT);
                themeBtn.setText("Dark");
            } else {
                AstraUIKit.AstraTheme.setMode(AstraUIKit.AstraTheme.Mode.DARK);
                themeBtn.setText("Light");
            }
            // repaint everything
            repaintAll(this);
        });

        // Note: label glow is disabled by default. enable manually:
        // someLabel.enableGlow(true);
    }

    // helper to repaint whole window after theme switch
    private void repaintAll(Component root) {
        root.repaint();
        if (root instanceof Container) {
            for (Component c : ((Container) root).getComponents()) repaintAll(c);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            AstraWriter w = new AstraWriter();
            w.setVisible(true);
        });
    }
}