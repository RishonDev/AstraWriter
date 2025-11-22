import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AstraWriter extends JFrame {
    private static final Color DARK_BG = new Color(20, 20, 20);
    private static final Color DARK_PANEL = new Color(30, 30, 30);
    private static final Color GOLD = new Color(220, 170, 70);
    private static final Color TEXT_PRIMARY = new Color(240, 240, 240);
    private static final Color TEXT_SECONDARY = new Color(180, 180, 180);
    private static final Color BORDER_COLOR = new Color(80, 80, 80);

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
        mainPanel.setLayout(new BorderLayout(20, 20));
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
        panel.setLayout(new BorderLayout());
        
        // Left side: Logo and title
        JPanel leftHeader = new JPanel();
        leftHeader.setBackground(DARK_BG);
        leftHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
        
        // Lock icon
        JLabel lockLabel = new JLabel("🔒");
        lockLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        lockLabel.setForeground(GOLD);
        leftHeader.add(lockLabel);
        
        // Title
        JLabel titleLabel = new JLabel("AstraWriter");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(GOLD);
        leftHeader.add(titleLabel);
        
        panel.add(leftHeader, BorderLayout.WEST);
        
        // Right side: Icons (moon and settings)
        JPanel rightHeader = new JPanel();
        rightHeader.setBackground(DARK_BG);
        rightHeader.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        
        JLabel moonLabel = new JLabel("🌙");
        moonLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        moonLabel.setForeground(GOLD);
        rightHeader.add(moonLabel);
        
        JLabel settingsLabel = new JLabel("⚙️");
        settingsLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        settingsLabel.setForeground(GOLD);
        rightHeader.add(settingsLabel);
        
        panel.add(rightHeader, BorderLayout.EAST);
        
        return panel;
    }

    private JPanel createSubtitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BG);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        JLabel subtitle = new JLabel("Create bootable USB drives with style.");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setForeground(TEXT_PRIMARY);
        panel.add(subtitle);
        
        return panel;
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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
        
        // Advanced Section
        panel.add(createAdvancedSection());
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(GOLD);
        return label;
    }

    private JPanel createDeviceSelector() {
        JPanel panel = new RoundedPanel(15);
        panel.setBackground(DARK_PANEL);
        panel.setLayout(new BorderLayout(15, 0));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel lockLabel = new JLabel("🔒");
        lockLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        lockLabel.setForeground(GOLD);
        panel.add(lockLabel, BorderLayout.WEST);
        
        JLabel deviceLabel = new JLabel("E: (32 GB)");
        deviceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        deviceLabel.setForeground(TEXT_PRIMARY);
        panel.add(deviceLabel, BorderLayout.CENTER);
        
        JLabel arrowLabel = new JLabel("›");
        arrowLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        arrowLabel.setForeground(TEXT_PRIMARY);
        panel.add(arrowLabel, BorderLayout.EAST);
        
        return panel;
    }

    private JPanel createBootImagePanel() {
        JPanel panel = new RoundedPanel(15);
        panel.setBackground(DARK_PANEL);
        panel.setLayout(new BorderLayout(15, 0));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel imageLabel = new JLabel("C:\\ubuntu-24.04-desktop-amd64.");
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imageLabel.setForeground(TEXT_PRIMARY);
        panel.add(imageLabel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createFormatSchemePanel() {
        JPanel panel = new RoundedPanel(15);
        panel.setBackground(DARK_PANEL);
        panel.setLayout(new BorderLayout(15, 0));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel gptLabel = new JLabel("GPT");
        gptLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gptLabel.setForeground(TEXT_PRIMARY);
        panel.add(gptLabel, BorderLayout.WEST);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(DARK_PANEL);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel fat32Label = new JLabel("FAT32 ›");
        fat32Label.setFont(new Font("Arial", Font.PLAIN, 18));
        fat32Label.setForeground(TEXT_PRIMARY);
        centerPanel.add(fat32Label);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createAdvancedSection() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BG);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        JLabel advancedLabel = new JLabel("⬆ Advanced");
        advancedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        advancedLabel.setForeground(GOLD);
        panel.add(advancedLabel);
        
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DARK_BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        // Info panel
        JPanel infoPanel = new RoundedPanel(20);
        infoPanel.setBackground(DARK_PANEL);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        
        // Ubuntu logo
        JLabel logoLabel = new JLabel("🔵");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        logoLabel.setForeground(GOLD);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(logoLabel);
        infoPanel.add(Box.createVerticalStrut(20));
        
        // Detected OS text
        JLabel osLabel1 = new JLabel("Detected OS:");
        osLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
        osLabel1.setForeground(TEXT_PRIMARY);
        osLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(osLabel1);
        
        JLabel osLabel2 = new JLabel("Ubuntu 24.04 - 64-bit");
        osLabel2.setFont(new Font("Arial", Font.BOLD, 18));
        osLabel2.setForeground(TEXT_PRIMARY);
        osLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(osLabel2);
        infoPanel.add(Box.createVerticalStrut(20));
        
        // Progress bar
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(65);
        progressBar.setForeground(GOLD);
        progressBar.setBackground(DARK_BG);
        progressBar.setPreferredSize(new Dimension(300, 8));
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
        infoPanel.add(progressBar);
        infoPanel.add(Box.createVerticalStrut(15));
        
        // Estimated time
        JLabel timeLabel = new JLabel("Estimated write time: 4 min");
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
        panel.setLayout(new BorderLayout(20, 0));
        
        // Ready to Write
        JPanel leftFooter = new JPanel();
        leftFooter.setBackground(DARK_BG);
        leftFooter.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
        
        JLabel checkLabel = new JLabel("✓");
        checkLabel.setFont(new Font("Arial", Font.BOLD, 24));
        checkLabel.setForeground(GOLD);
        leftFooter.add(checkLabel);
        
        JLabel readyLabel = new JLabel("Ready to Write");
        readyLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        readyLabel.setForeground(TEXT_PRIMARY);
        leftFooter.add(readyLabel);
        
        panel.add(leftFooter, BorderLayout.WEST);
        
        // Create Bootable Drive Button
        JButton createButton = new JButton("Create Bootable Drive  →");
        createButton.setFont(new Font("Arial", Font.BOLD, 16));
        createButton.setForeground(GOLD);
        createButton.setBackground(DARK_BG);
        createButton.setBorder(new RoundedBorder(GOLD, 2, 15));
        createButton.setFocusPainted(false);
        createButton.setContentAreaFilled(false);
        createButton.setBorderPainted(true);
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel rightFooter = new JPanel();
        rightFooter.setBackground(DARK_BG);
        rightFooter.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
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
