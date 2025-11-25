package com.astrawriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.astrawriter.AstraUIKit.AstraColors.GOLD;

public class AstraWriter extends JFrame {
    private static final Color DARK_BG = new Color(20, 20, 20);
    private static final Color DARK_PANEL = new Color(30, 30, 30);
    private static final Color TEXT_PRIMARY = new Color(240, 240, 240);
    private static final Color TEXT_SECONDARY = new Color(180, 180, 180);
    private static final Color BORDER_COLOR = new Color(80, 80, 80);

    private ExpandablePanel advancedSection;

    public AstraWriter() {
        setTitle("AstraWriter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(DARK_BG);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(DARK_BG);
        mainPanel.setLayout(null);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        // Header panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Content panel (left and right sections)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
        splitPane.setEnabled(false);
        splitPane.setOpaque(false);
        splitPane.setLeftComponent(createLeftPanel());
        splitPane.setRightComponent(createRightPanel());
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Footer panel
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BG);
        panel.setLayout(null);

        // Left side: Logo and title
        JPanel leftHeader = new JPanel();
        leftHeader.setBackground(DARK_BG);
        leftHeader.setLayout(null);

        // Lock icon
        AstraUIKit.AstraLabel lockLabel = new AstraUIKit.AstraLabel("");
        lockLabel.setIcon(Icons.createLockIcon(32));

        leftHeader.add(lockLabel);

        // Title
        AstraUIKit.AstraLabel titleLabel = new AstraUIKit.AstraLabel("Astra Writer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setBounds(0,0,400,60);
        leftHeader.add(titleLabel);
        panel.add(leftHeader);

        // Right side: Icons (moon and settings)
        JPanel rightHeader = new JPanel();
        rightHeader.setBackground(DARK_BG);
        rightHeader.setLayout(null);

        AstraUIKit.AstraLabel moonLabel = new AstraUIKit.AstraLabel("");
        moonLabel.setIcon(Icons.createMoonIcon(32));
        rightHeader.add(moonLabel);

        AstraUIKit.AstraLabel settingsLabel = new AstraUIKit.AstraLabel("");
        settingsLabel.setIcon(Icons.createSettingsIcon(32));
        rightHeader.add(settingsLabel);

        panel.add(rightHeader, BorderLayout.EAST);

        return panel;
    }

    private JPanel createSubtitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BG);
        panel.setLayout(null);

        AstraUIKit.AstraLabel subtitle = new AstraUIKit.AstraLabel("Create bootable USB drives with style.");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setForeground(TEXT_PRIMARY);
        panel.add(subtitle);

        return panel;
    }

    private JPanel createLeftPanel() {
        AstraUIKit.AstraPanel panel = new AstraUIKit.AstraPanel();
        panel.setLayout(null);
        panel.setBorder(new EmptyBorder(0, 0, 0, 20));

        // Subtitle
        panel.add(createSubtitlePanel());
        panel.add(Box.createVerticalStrut(20));

        // Separator
        JSeparator sep1 = new JSeparator();
        sep1.setForeground(BORDER_COLOR);
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        panel.add(sep1);
        panel.add(Box.createVerticalStrut(20));

        // Target Device Section
        panel.add(createSectionLabel("TARGET DEVICE"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createDeviceSelector());
        panel.add(Box.createVerticalStrut(20));

        // Boot Image Section
        panel.add(createSectionLabel("BOOT IMAGE"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createBootImagePanel());
        panel.add(Box.createVerticalStrut(20));

        // Format & Scheme Section
        panel.add(createSectionLabel("FORMAT & SCHEME"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFormatSchemePanel());
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }

    private AstraUIKit.AstraLabel createSectionLabel(String text) {
        AstraUIKit.AstraLabel label = new AstraUIKit.AstraLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }

    private JPanel createDeviceSelector() {
        JPanel panel = new RoundedPanel(15);
        panel.setBackground(DARK_PANEL);
        panel.setLayout(null);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        AstraUIKit.AstraLabel lockLabel = new AstraUIKit.AstraLabel("");
        lockLabel.setIcon(Icons.createLockIcon(32));
        panel.add(lockLabel, BorderLayout.WEST);

        JComboBox<String> deviceCombo = new JComboBox<>();
        deviceCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        deviceCombo.setForeground(TEXT_PRIMARY);
        deviceCombo.setBackground(DARK_PANEL);
        deviceCombo.setEditable(false);
        deviceCombo.setFocusable(true);
        styleComboBox(deviceCombo);
        panel.add(deviceCombo, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBootImagePanel() {
        JPanel panel = new RoundedPanel(15);
        panel.setBackground(DARK_PANEL);
        panel.setLayout(null);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JComboBox<String> imageCombo = new JComboBox<>();
        imageCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        imageCombo.setForeground(TEXT_PRIMARY);
        imageCombo.setBackground(DARK_PANEL);
        imageCombo.setEditable(false);
        styleComboBox(imageCombo);
        panel.add(imageCombo, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFormatSchemePanel() {
        JPanel panel = new RoundedPanel(15);
        panel.setBackground(AstraUIKit.AstraColors.WINDOW_BG);
        panel.setLayout(null);
        panel.setBorder(new EmptyBorder(0, 15, 0, 0));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        AstraUIKit.AstraButton gptButton = new AstraUIKit.AstraButton("GPT");
        AstraUIKit.AstraButton mbrButton = new AstraUIKit.AstraButton("MBR");
        mbrButton.setBounds(0,0,100,30);
        gptButton.setBounds(0,200,100,30);
        panel.setBounds(0,0,200,30);
        panel.add(gptButton);
        panel.add(mbrButton);
        return panel;
    }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("›");
                button.setFont(new Font("Arial", Font.PLAIN, 18));
                button.setForeground(TEXT_PRIMARY);
                button.setBackground(DARK_PANEL);
                button.setBorder(null);
                button.setContentAreaFilled(false);
                return button;
            }
        });
    }


    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BG);
        panel.setLayout(null);
        panel.setBorder(new EmptyBorder(0, 20, 0, 0));

        // Info panel
        JPanel infoPanel = new RoundedPanel(20);
        infoPanel.setBackground(DARK_PANEL);
        infoPanel.setLayout(null);
        infoPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        // Ubuntu logo
        AstraUIKit.AstraLabel logoLabel = new AstraUIKit.AstraLabel("");
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(logoLabel);
        infoPanel.add(Box.createVerticalStrut(20));

        // Detected OS text
        AstraUIKit.AstraLabel osLabel1 = new AstraUIKit.AstraLabel("Detected OS:");
        osLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
        osLabel1.setForeground(TEXT_PRIMARY);
        osLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(osLabel1);

        AstraUIKit.AstraLabel osLabel2 = new AstraUIKit.AstraLabel("Ubuntu 24.04 - 64-bit");
        osLabel2.setFont(new Font("Arial", Font.BOLD, 18));
        osLabel2.setForeground(TEXT_PRIMARY);
        osLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(osLabel2);
        infoPanel.add(Box.createVerticalStrut(20));

        // Progress bar
        AstraUIKit.AstraProgressBar progressBar = new AstraUIKit.AstraProgressBar(0, 100);
        progressBar.setValue(65);
        progressBar.setPreferredSize(new Dimension(300, 8));
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
        infoPanel.add(progressBar);
        infoPanel.add(Box.createVerticalStrut(15));

        // Estimated time
        AstraUIKit.AstraLabel timeLabel = new AstraUIKit.AstraLabel("Estimated write time: 4 min");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setForeground(TEXT_PRIMARY);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(timeLabel);

        panel.add(infoPanel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BG);
        panel.setLayout(null);

        // Ready to Write
        JPanel leftFooter = new JPanel();
        leftFooter.setBackground(DARK_BG);
        leftFooter.setLayout(null);

        AstraUIKit.AstraLabel checkLabel = new AstraUIKit.AstraLabel("");
        checkLabel.setIcon(Icons.createCheckIcon(24));
        leftFooter.add(checkLabel);

        AstraUIKit.AstraLabel readyLabel = new AstraUIKit.AstraLabel("Ready to Write");
        readyLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        readyLabel.setForeground(TEXT_PRIMARY);
        leftFooter.add(readyLabel);

        panel.add(leftFooter, BorderLayout.WEST);

        // Create Bootable Drive Button
        JButton createButton = new JButton("Create Bootable Drive  →");
        createButton.setFont(new Font("Arial", Font.BOLD, 16));
        createButton.setBackground(DARK_BG);
        createButton.setBorder(new RoundedBorder(GOLD, 2, 15));
        createButton.setFocusPainted(false);
        createButton.setContentAreaFilled(false);
        createButton.setBorderPainted(true);
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel rightFooter = new JPanel();
        rightFooter.setBackground(DARK_BG);
        rightFooter.setLayout(null);
        rightFooter.add(createButton);
        panel.add(rightFooter, BorderLayout.EAST);

        // Separator
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(BORDER_COLOR);
        panel.add(sep, BorderLayout.NORTH);

        return panel;
    }

    // Custom rounded panel class
    static class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(BORDER_COLOR);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

            super.paintComponent(g);
        }
    }

    // Custom rounded border class
    static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private Color color;
        private int thickness;
        private int radius;

        public RoundedBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AstraWriter frame = new AstraWriter();
            frame.setVisible(true);
        });
    }
}