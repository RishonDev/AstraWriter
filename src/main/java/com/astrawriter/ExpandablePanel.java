package com.astrawriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExpandablePanel extends JPanel {
    private final JPanel contentPanel;
    private final AstraUIKit.AstraLabel headerLabel;
    private boolean isExpanded = false;
    private int collapsedHeight = 30;
    private int expandedHeight = 200;
    private final int ANIMATION_DURATION = 300;
    private int currentHeight;
    private final JFrame parentFrame;

    private static final Color DARK_BG = new Color(20, 20, 20);
    private static final Color GOLD = new Color(220, 170, 70);

    public ExpandablePanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.currentHeight = collapsedHeight;
        setBackground(DARK_BG);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, collapsedHeight));
        setPreferredSize(new Dimension(Integer.MAX_VALUE, collapsedHeight));

        // Header
        headerLabel = new AstraUIKit.AstraLabel("⬇ Advanced");
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        headerLabel.setForeground(GOLD);
        headerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(headerLabel);

        // Content panel (hidden initially)
        contentPanel = new JPanel();
        contentPanel.setBackground(DARK_BG);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setVisible(false);
        contentPanel.setOpaque(false);
        add(contentPanel);

        // Click listener
        headerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleExpanded();
            }
        });
    }

    private void toggleExpanded() {
        isExpanded = !isExpanded;
        headerLabel.setText(isExpanded ? "⬆ Advanced" : "⬇ Advanced");
        contentPanel.setVisible(isExpanded);
        animateExpansion();
    }

    private void animateExpansion() {
        int targetHeight = isExpanded ? expandedHeight : collapsedHeight;
        int frameCount = 10;
        int stepHeight = (targetHeight - currentHeight) / frameCount;

        Timer timer = new Timer(ANIMATION_DURATION / frameCount, null);
        timer.addActionListener(e -> {
            if ((stepHeight > 0 && currentHeight < targetHeight) || (stepHeight < 0 && currentHeight > targetHeight)) {
                currentHeight += stepHeight;
                setMaximumSize(new Dimension(Integer.MAX_VALUE, currentHeight));
                setPreferredSize(new Dimension(Integer.MAX_VALUE, currentHeight));
                if (parentFrame != null) {
                    parentFrame.pack();
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
            } else {
                currentHeight = targetHeight;
                setMaximumSize(new Dimension(Integer.MAX_VALUE, targetHeight));
                setPreferredSize(new Dimension(Integer.MAX_VALUE, targetHeight));
                ((Timer) e.getSource()).stop();
                if (parentFrame != null) {
                    parentFrame.pack();
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
            }
        });
        timer.start();
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setCollapsedHeight(int height) {
        this.collapsedHeight = height;
    }

    public void setExpandedHeight(int height) {
        this.expandedHeight = height;
    }
}
