package com.astrawriter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicMenuItemUI;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class AstraUIKit {

    public static class AstraColors {
        public static final Color GOLD = new Color(0xD4AF37);
        public static final Color GOLD_DARK = new Color(0xA67C1A);
        public static final Color TRACK_TOP = new Color(0x0E0E0E);
        public static final Color TRACK_BOTTOM = new Color(0x151515);
        public static final Color WINDOW_BG = new Color(0x070707);
        public static final Color SOFT_TEXT = new Color(0xE6E0C8);
    }

    public static class AstraStyles {
        public static Font mediumBold(float size) { return new Font("Dialog", Font.BOLD, (int)size); }
        public static Font medium(float size) { return new Font("Dialog", Font.PLAIN, (int)size); }
    }

    // -----------------------------
    // Gold rounded button (compact)
    // -----------------------------
    public static class AstraButton extends JButton {
        public AstraButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(AstraColors.GOLD);
            setFont(AstraStyles.mediumBold(13f));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setMargin(new Insets(8, 14, 8, 14));
            initHover();
        }
        private boolean hover = false;
        private boolean pressed = false;
        private void initHover() {
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e){ hover = true; repaint(); }
                public void mouseExited(MouseEvent e){ hover = false; pressed = false; repaint(); }
                public void mousePressed(MouseEvent e){ pressed = true; repaint(); }
                public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
            });
        }
        @Override
        protected void paintComponent(Graphics g) {
            int w = getWidth(); int h = getHeight();
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int arc = Math.min(h, 28);
                RoundRectangle2D bg = new RoundRectangle2D.Float(0,0,w-1,h-1,arc,arc);
                // background
                Color top = AstraColors.TRACK_TOP;
                Color bottom = AstraColors.TRACK_BOTTOM;
                if (hover) { top = top.brighter(); bottom = bottom.brighter(); }
                if (pressed) { top = top.darker(); bottom = bottom.darker(); }
                g2.setPaint(new GradientPaint(0,0,top,0,h,bottom));
                g2.fill(bg);
                // border
                g2.setStroke(new BasicStroke(1.8f));
                g2.setPaint(new GradientPaint(0,0,AstraColors.GOLD,0,h,AstraColors.GOLD_DARK));
                g2.draw(bg);
                // text
                FontMetrics fm = g2.getFontMetrics(getFont());
                int tw = fm.stringWidth(getText());
                int tx = (w - tw)/2;
                int ty = (h + fm.getAscent())/2 - fm.getDescent();
                if (pressed) ty += 1;
                // shadow
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.45f));
                g2.setPaint(AstraColors.GOLD_DARK.darker());
                g2.drawString(getText(), tx, ty+1);
                g2.setComposite(AlphaComposite.SrcOver);
                g2.setPaint(AstraColors.GOLD);
                g2.drawString(getText(), tx, ty);
            } finally { g2.dispose(); }
        }
    }

    // -----------------------------
    // Gold Label (compact)
    // -----------------------------
    public static class AstraLabel extends JLabel {
        private boolean hoverGlow = false;
        private boolean gradientBackground = false;
        private boolean roundedBackground = false;
        public AstraLabel(String text) {
            super(text);
            setOpaque(false);
            setForeground(AstraColors.GOLD);
            setFont(AstraStyles.mediumBold(13f));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent e){ hoverGlow = true; repaint(); }
                public void mouseExited(MouseEvent e){ hoverGlow = false; repaint(); }
            });
        }
        public void setRoundedBackground(boolean b){ this.roundedBackground = b; repaint(); }
        public void setGradientBackground(boolean b){ this.gradientBackground = b; repaint(); }
        @Override
        protected void paintComponent(Graphics g) {
            int w = getWidth(); int h = getHeight();
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (gradientBackground || roundedBackground) {
                    GradientPaint gp = new GradientPaint(0,0,AstraColors.TRACK_TOP,0,h,AstraColors.TRACK_BOTTOM);
                    Shape bg = new RoundRectangle2D.Float(0,0,w-1,h-1, roundedBackground?14:0, roundedBackground?14:0);
                    g2.setPaint(gp);
                    g2.fill(bg);
                    g2.setPaint(AstraColors.GOLD_DARK);
                    g2.setStroke(new BasicStroke(1.2f));
                    g2.draw(bg);
                }
                if (hoverGlow) {
                    g2.setPaint(new Color(212,175,55,70));
                    g2.setStroke(new BasicStroke(10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.draw(new RoundRectangle2D.Float(4,4,w-8,h-8,14,14));
                }
                // icon support
                int x = getInsets().left;
                Icon ic = getIcon();
                if (ic != null) { ic.paintIcon(this, g2, x, (h - ic.getIconHeight())/2); x += ic.getIconWidth() + getIconTextGap(); }
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics(getFont());
                int ty = (h + fm.getAscent())/2 - fm.getDescent();
                // shadow
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
                g2.setPaint(AstraColors.GOLD_DARK.darker());
                g2.drawString(getText(), x, ty+1);
                g2.setComposite(AlphaComposite.SrcOver);
                g2.setPaint(AstraColors.GOLD);
                g2.drawString(getText(), x, ty);
            } finally { g2.dispose(); }
        }
        @Override public Dimension getPreferredSize(){ Dimension d = super.getPreferredSize(); d.width += 10; d.height += 6; return d; }
    }

    // -----------------------------
    // Gold progress panel (reused from earlier)
    // -----------------------------
    public static class AstraProgressPanel extends JPanel {
        private static final int HEIGHT = 18;
        private final JLabel titleLabel = new JLabel();
        private final JLabel captionLabel = new JLabel();
        private final GoldBar bar = new GoldBar();
        private volatile double displayedValue = 0; private volatile double targetValue = 0;
        private boolean indeterminate = false;
        private Timer animateTimer;
        private Timer shimmerTimer;
        private float shimmerOffset = -0.4f; private final float shimmerSpeed = 0.02f; private final double easing = 0.18;
        public AstraProgressPanel(){
            setOpaque(false);
            setLayout(new GridBagLayout());
            titleLabel.setForeground(AstraColors.SOFT_TEXT); titleLabel.setFont(AstraStyles.mediumBold(13f));
            captionLabel.setForeground(new Color(0xA7A7A7)); captionLabel.setFont(AstraStyles.medium(12f));
            bar.setPreferredSize(new Dimension(380, HEIGHT));
            GridBagConstraints c = new GridBagConstraints(); c.gridx=0;c.gridy=0;c.anchor=GridBagConstraints.WEST; c.insets=new Insets(0,0,6,0); add(titleLabel,c);
            c.gridy=1;c.fill=GridBagConstraints.HORIZONTAL;c.weightx=1; add(bar,c);
            c.gridy=2;c.fill=GridBagConstraints.NONE;c.weightx=0;c.insets=new Insets(6,0,0,0); add(captionLabel,c);
            animateTimer = new Timer(16, e->{ double diff = targetValue - displayedValue; if (Math.abs(diff) < 0.05) { displayedValue = targetValue; } else { displayedValue += diff * easing; } bar.setDisplayPercent(displayedValue/100.0); if (!indeterminate && displayedValue==targetValue) animateTimer.stop(); });
            shimmerTimer = new Timer(16, e->{ shimmerOffset += shimmerSpeed; if (shimmerOffset > 1.4f) shimmerOffset = -0.6f; bar.setShimmerOffset(shimmerOffset); bar.repaint(); });
        }
        public void setTitle(String t){ titleLabel.setText(t); }
        public void setEstimatedTime(String t){ captionLabel.setText(t); }
        public void setValue(int v){ if (v<0) v=0; if(v>100) v=100; targetValue=v; displayedValue=v; bar.setDisplayPercent(displayedValue/100.0); bar.repaint(); }
        public void setValueAnimated(int v){ if (v<0) v=0; if(v>100) v=100; targetValue=v; if(!animateTimer.isRunning()) animateTimer.start(); }
        public void setIndeterminate(boolean b){ indeterminate=b; bar.setIndeterminate(b); if(b){ if(animateTimer.isRunning()) animateTimer.stop(); shimmerOffset = -0.6f; shimmerTimer.start(); } else { shimmerTimer.stop(); bar.setShimmerOffset(-1f); bar.repaint(); } }
        public boolean isIndeterminate(){ return indeterminate; }
        private class GoldBar extends JComponent {
            private double displayPercent = 0.0; private boolean indet = false; private float shimmer = -1f;
            GoldBar(){ setOpaque(false); setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); }
            void setDisplayPercent(double p){ displayPercent = Math.max(0, Math.min(1, p)); repaint(); }
            void setIndeterminate(boolean b){ indet = b; repaint(); }
            void setShimmerOffset(float o){ shimmer = o; }
            @Override protected void paintComponent(Graphics g){ int w = getWidth(); int h = Math.max(getHeight(), HEIGHT); Graphics2D g2 = (Graphics2D) g.create(); try { g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); RoundRectangle2D track = new RoundRectangle2D.Float(0,0,w-1,h-1,18,18); g2.setPaint(new GradientPaint(0,0,AstraColors.TRACK_TOP,0,h,AstraColors.TRACK_BOTTOM)); g2.fill(track); g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.12f)); g2.setPaint(new GradientPaint(0,0,new Color(0,0,0,120),0,h,new Color(0,0,0,20))); g2.fill(new RoundRectangle2D.Float(1,1,w-3,h-3,12,12)); g2.setComposite(AlphaComposite.SrcOver);
                int fillW;
                if (indet) fillW = w; else fillW = (int)Math.round(displayPercent * (w - 4));
                if (fillW > 0) {
                    GradientPaint goldPaint = new GradientPaint(0,0,AstraColors.GOLD,0,h,AstraColors.GOLD_DARK);
                    if (indet) {
                        int bandW = Math.max(60, w/4);
                        float pos = shimmer; int bandX = (int)((pos + 0.5f) * w) - bandW/2;
                        GradientPaint base = new GradientPaint(0,0,AstraColors.GOLD_DARK.darker(),0,h,AstraColors.GOLD_DARK.brighter()); g2.setPaint(base); g2.fill(new RoundRectangle2D.Float(2,2,w-4,h-4,14,14));
                        Rectangle clip = new Rectangle(Math.max(0, bandX), 0, Math.min(bandW, w - bandX), h);
                        g2.setClip(clip);
                        GradientPaint streak = new GradientPaint(bandX,0,new Color(255,255,255,180), bandX + bandW/2f,0,AstraColors.GOLD.brighter()); g2.setPaint(streak); g2.fill(new RoundRectangle2D.Float(2,2,w-4,h-4,14,14)); g2.setClip(null);
                    } else {
                        Area clipArea = new Area(new Rectangle(0,0, Math.max(1, fillW), h)); g2.setClip(clipArea); g2.setPaint(goldPaint); g2.fill(new RoundRectangle2D.Float(2,2,w-4,h-4,14,14)); g2.setClip(null);
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.14f)); g2.setPaint(new GradientPaint(0,0,new Color(255,255,255,140),0,h,new Color(255,255,255,0))); g2.fill(new RoundRectangle2D.Float(2,2,Math.max(1,fillW), (h-4)/2f,14,14)); g2.setComposite(AlphaComposite.SrcOver);
                    }
                }
                g2.setStroke(new BasicStroke(2f)); g2.setPaint(new GradientPaint(0,0,AstraColors.GOLD,0,h,AstraColors.GOLD_DARK)); g2.draw(track);
                g2.setStroke(new BasicStroke(1f)); g2.setPaint(new Color(0,0,0,80)); g2.draw(new RoundRectangle2D.Float(2,2,w-5,h-5,12,12));
                if (!indet) {
                    String pct = String.valueOf((int)Math.round(displayPercent*100)) + "%";
                    FontMetrics fm = g2.getFontMetrics(getFont().deriveFont(Font.BOLD,11f)); int tw = fm.stringWidth(pct); int tx = w - tw - 8; int ty = (h + fm.getAscent())/2 - 2; g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.45f)); g2.setPaint(AstraColors.GOLD_DARK.darker()); g2.setFont(fm.getFont()); g2.drawString(pct, tx, ty+1); g2.setComposite(AlphaComposite.SrcOver); g2.setPaint(AstraColors.GOLD); g2.drawString(pct, tx, ty);
                }
            } finally { g2.dispose(); } }
            @Override public Dimension getPreferredSize(){ return new Dimension(300, HEIGHT + 6); }
        }
        // demo helper
        public static void demoInFrame(){ JFrame f = new JFrame("Astra Progress Demo"); f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); f.getContentPane().setBackground(AstraColors.WINDOW_BG); AstraProgressPanel p = new AstraProgressPanel(); p.setTitle("Detected OS: Ubuntu 24.04 - 64-bit"); p.setEstimatedTime("Estimated write time: 4 min"); p.setValueAnimated(52); f.add(p); f.pack(); f.setLocationRelativeTo(null); f.setVisible(true); }
    }
    public static class AstraPanel extends JPanel {
        public AstraPanel() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth(), h = getHeight();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0,0,new Color(0x0D0D0D),0,h,new Color(0x0A0A0A));
            Shape r = new RoundRectangle2D.Float(0,0,w-1,h-1,18,18);
            g2.setPaint(gp); g2.fill(r);
            g2.setColor(new Color(255,255,255,8)); g2.setStroke(new BasicStroke(1.2f)); g2.draw(r);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // -----------------------------
    // AstraTextField (exact replica)
    // -----------------------------
    public static class AstraTextField extends JTextField {
        public AstraTextField(String txt){ super(txt); init(); }
        public AstraTextField(){ super(); init(); }
        private void init(){
            setOpaque(false);
            setForeground(AstraColors.SOFT_TEXT);
            setCaretColor(AstraColors.GOLD);
            setBorder(BorderFactory.createEmptyBorder(10,14,10,14));
            setFont(AstraStyles.medium(13f));
        }
        @Override protected void paintComponent(Graphics g){
            int w = getWidth(), h = getHeight();
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0,0,new Color(0x111111),0,h,new Color(0x0C0C0C));
            Shape r = new RoundRectangle2D.Float(0,0,w-1,h-1,14,14);
            g2.setPaint(gp); g2.fill(r);
            g2.setPaint(new GradientPaint(0,0,AstraColors.GOLD,0,h,AstraColors.GOLD_DARK));
            g2.setStroke(new BasicStroke(1.4f)); g2.draw(r);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    // -----------------------------
    // AstraComboBox (exact replica)
    // -----------------------------
    public static class AstraComboBox extends JComboBox {
        public AstraComboBox(Object[] items){ super(items); init(); }
        public AstraComboBox(){ super(); init(); }
        private void init(){
            setOpaque(false);
            setForeground(AstraColors.SOFT_TEXT);
            setBackground(new Color(0,0,0,0));
            setBorder(BorderFactory.createEmptyBorder(6,10,6,10));
            setUI(new AstraComboUI());
            setFont(AstraStyles.medium(13f));
        }
    }

    // Custom UI for exact Astra style
    public static class AstraComboUI extends javax.swing.plaf.basic.BasicComboBoxUI {
        @Override protected JButton createArrowButton(){
            JButton btn = new JButton(){
                @Override protected void paintComponent(Graphics g){
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int w = getWidth(), h = getHeight();
                    g2.setColor(new Color(0,0,0,0)); g2.fillRect(0,0,w,h);
                    g2.setStroke(new BasicStroke(2f));
                    g2.setColor(AstraColors.GOLD);
                    int x = w/2 - 4, y = h/2 - 1;
                    g2.drawLine(x, y, x+4, y+4);
                    g2.drawLine(x+4, y+4, x+8, y);
                    g2.dispose();
                }
            };
            btn.setBorder(null);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            return btn;
        }
        @Override public void paint(Graphics g, JComponent c){ super.paint(g, c); }
        @Override public void paintCurrentValueBackground(Graphics g, Rectangle r, boolean f){ }
        @Override public void paintCurrentValue(Graphics g, Rectangle r, boolean f){
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = r.width, h = r.height;
            GradientPaint gp = new GradientPaint(0,0,new Color(0x111111),0,h,new Color(0x0C0C0C));
            Shape rr = new RoundRectangle2D.Float(0,0,w-1,h-1,14,14);
            g2.setPaint(gp); g2.fill(rr);
            g2.setPaint(new GradientPaint(0,0,AstraColors.GOLD,0,h,AstraColors.GOLD_DARK));
            g2.setStroke(new BasicStroke(1.4f)); g2.draw(rr);
            super.paintCurrentValue(g2, r, f);
            g2.dispose();
        }
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {
                @Override
                protected void configurePopup() {
                    super.configurePopup();
                    setOpaque(false);
                }

                @Override
                public void paint(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    int w = getWidth(), h = getHeight();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    Shape r = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 10, 10);
                    g2.setPaint(new Color(20, 20, 20, 160));    // translucent background
                    g2.fill(r);

                    g2.setPaint(new Color(255, 255, 255, 12));  // subtle border
                    g2.draw(r);

                    g2.dispose();
                    super.paint(g);
                }
            };

            return popup;
        }

    }

    // -----------------------------
    // Transparent floating menu components (Exact Replica)
    // -----------------------------
    public static class AstraProgressBar extends JComponent {

        private int minimum = 0;
        private int maximum = 100;
        private int targetValue = 0;
        private double displayedValue = 0;

        private boolean indeterminate = false;

        private final double easing = 0.18;
        private float shimmerOffset = -0.4f;
        private final float shimmerSpeed = 0.02f;

        private final Timer animateTimer;
        private final Timer shimmerTimer;

        public AstraProgressBar(int min, int max) {
            setOpaque(false);
            setPreferredSize(new Dimension(340, 22));

            this.minimum = min;
            this.maximum = max;
            this.targetValue = min;
            this.displayedValue = min;

            // Smooth animated timer
            animateTimer = new Timer(16, e -> {
                double diff = targetValue - displayedValue;
                if (Math.abs(diff) < 0.05) {
                    displayedValue = targetValue;
                } else {
                    displayedValue += diff * easing;
                }
                repaint();

                if (!indeterminate && displayedValue == targetValue) {
                    ((Timer)((ActionEvent)e).getSource()).stop();
                }
            });

            // Indeterminate shimmer
            shimmerTimer = new Timer(16, e -> {
                shimmerOffset += shimmerSpeed;
                if (shimmerOffset > 1.4f) shimmerOffset = -0.6f;
                repaint();
            });
        }

        // Default constructor (0–100)
        public AstraProgressBar() {
            this(0, 100);
        }

        // ---------------------------
        // Range Handling
        // ---------------------------
        public void setMinimum(int min) {
            this.minimum = min;
            if (targetValue < min) setValue(min);
            repaint();
        }

        public void setMaximum(int max) {
            this.maximum = max;
            if (targetValue > max) setValue(max);
            repaint();
        }

        public int getMinimum() { return minimum; }
        public int getMaximum() { return maximum; }

        // ---------------------------
        // Value Setting
        // ---------------------------
        public void setValue(int v) {
            v = clamp(v);
            targetValue = v;
            displayedValue = v;
            repaint();
        }

        public void setValueAnimated(int v) {
            v = clamp(v);
            targetValue = v;

            if (!animateTimer.isRunning()) {
                animateTimer.start();
            }
        }

        private int clamp(int v) {
            return Math.max(minimum, Math.min(maximum, v));
        }

        private double getNormalizedProgress() {
            if (maximum == minimum) return 0;
            return (displayedValue - minimum) / (double)(maximum - minimum);
        }

        // ---------------------------
        // Indeterminate Mode
        // ---------------------------
        public void setIndeterminate(boolean b) {
            indeterminate = b;
            if (b) {
                animateTimer.stop();
                shimmerOffset = -0.6f;
                shimmerTimer.start();
            } else {
                shimmerTimer.stop();
            }
            repaint();
        }

        public boolean isIndeterminate() { return indeterminate; }

        // ---------------------------
        // Painting
        // ---------------------------
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw track background
            RoundRectangle2D track = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 18, 18);
            g2.setPaint(new GradientPaint(0, 0, new Color(0x0E0E0E), 0, h, new Color(0x151515)));
            g2.fill(track);

            // Fill amount
            double pct = getNormalizedProgress();
            int fillW = indeterminate ? w : (int) (pct * (w - 4));

            if (fillW > 0) {
                if (indeterminate) {
                    // Indeterminate streak base
                    g2.setPaint(new GradientPaint(
                            0, 0, new Color(0x8A6A1A),
                            0, h, new Color(0xC8A23D)
                    ));
                    g2.fill(new RoundRectangle2D.Float(2, 2, w - 4, h - 4, 14, 14));

                    // Shimmer pass
                    int bandW = Math.max(60, w / 4);
                    int bandX = (int)((shimmerOffset + 0.5f) * w) - bandW / 2;

                    Rectangle clip = new Rectangle(
                            Math.max(0, bandX),
                            0,
                            Math.min(bandW, w - bandX),
                            h
                    );

                    g2.setClip(clip);
                    g2.setPaint(new GradientPaint(bandX, 0,
                            new Color(255, 255, 255, 160),
                            bandX + bandW / 2f, 0,
                            new Color(0xFFD668)
                    ));
                    g2.fill(new RoundRectangle2D.Float(2, 2, w - 4, h - 4, 14, 14));
                    g2.setClip(null);

                } else {
                    // Determinate gold fill
                    g2.setClip(new Rectangle(0, 0, fillW, h));
                    g2.setPaint(new GradientPaint(
                            0, 0, new Color(0xD4AF37),
                            0, h, new Color(0xA67C1A)
                    ));
                    g2.fill(new RoundRectangle2D.Float(2, 2, w - 4, h - 4, 14, 14));
                    g2.setClip(null);
                }
            }

            // Border
            g2.setStroke(new BasicStroke(2f));
            g2.setPaint(new GradientPaint(0, 0, new Color(0xD4AF37), 0, h, new Color(0xA67C1A)));
            g2.draw(track);

            g2.dispose();
        }
    }
    // -----------------------------
    // Transparent floating menu components (Option B)
    // -----------------------------
    public static class AstraMenuBar extends JMenuBar {
        public AstraMenuBar() {
            setOpaque(false);
            setBorder(null);
            setBackground(new Color(0,0,0,0));
        }
        @Override protected void paintComponent(Graphics g){ // no background - keep transparent
            super.paintComponent(g);
        }
    }

    public static class AstraMenu extends JMenu {
        public AstraMenu(String text){ super(text); setOpaque(false); setForeground(AstraColors.SOFT_TEXT); setFont(AstraStyles.mediumBold(13f)); setBorder(BorderFactory.createEmptyBorder(4,12,4,12)); setPopupMenuVisible(false); }
        @Override public void addNotify(){ super.addNotify(); setupPopup(); }
        private void setupPopup(){ JPopupMenu pm = getPopupMenu(); pm.setOpaque(false); pm.setBorder(BorderFactory.createEmptyBorder(6,6,6,6)); pm.setBackground(new Color(0,0,0,0)); pm.setFocusable(false);
            pm.setUI(new TransparentPopupUI());
        }
    }

    public static class AstraMenuItem extends JMenuItem {
        public AstraMenuItem(String text){ super(text); setOpaque(false); setFont(AstraStyles.medium(13f)); setForeground(AstraColors.SOFT_TEXT); setUI(new AstraMenuItemUI()); setBorder(BorderFactory.createEmptyBorder(6,12,6,12)); }
    }

    // Custom UI for transparent popup menu
    private static class TransparentPopupUI extends javax.swing.plaf.basic.BasicPopupMenuUI {
        @Override public void paint(Graphics g, JComponent c){ Graphics2D g2 = (Graphics2D) g.create(); try { int w = c.getWidth(), h = c.getHeight(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // translucent backdrop with slight blur-like feel (simulated)
            RoundRectangle2D bg = new RoundRectangle2D.Float(0,0,w-1,h-1,10,10);
            g2.setPaint(new Color(20,20,20,160)); g2.fill(bg);
            // inner faint border
            g2.setPaint(new Color(255,255,255,6)); g2.setStroke(new BasicStroke(1f)); g2.draw(bg);
        } finally { g2.dispose(); } }
    }

    // MenuItem UI for hover highlight & gold accent
    private static class AstraMenuItemUI extends BasicMenuItemUI {
        @Override protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
            Graphics2D g2 = (Graphics2D) g.create(); try {
                int w = menuItem.getWidth(), h = menuItem.getHeight();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (menuItem.getModel().isArmed() || menuItem.getModel().isSelected()) {
                    // gold accent line on the left + subtle translucent hover fill
                    g2.setPaint(new Color(212,175,55,30));
                    g2.fillRect(0,0,w,h);
                    g2.setPaint(AstraColors.GOLD);
                    g2.fillRect(6, 6, 4, h-12);
                }
            } finally { g2.dispose(); }
        }
        @Override public void paint(Graphics g, JComponent c) {
            JMenuItem mi = (JMenuItem)c;
            paintBackground(g, mi, mi.getBackground());
            super.paint(g, c);
        }
        @Override protected void installDefaults() {
            super.installDefaults();
            menuItem.setOpaque(false);
            menuItem.setForeground(AstraColors.SOFT_TEXT);
            menuItem.setFont(AstraStyles.medium(13f));
        }
    }

    // -----------------------------
    // AstraWindow - base dark frame
    // -----------------------------
    public static class AstraWindow extends JFrame {
        public AstraWindow(String title) {
            super(title);
            getContentPane().setBackground(AstraColors.WINDOW_BG);
            setUndecorated(false);
        }
        public void applyAstraMenuBar(AstraMenuBar mb){ setJMenuBar(mb); }
    }

    // -----------------------------
    // Demo: show everything together
    // -----------------------------
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception ignored){}
        SwingUtilities.invokeLater(() -> {
            AstraWindow win = new AstraWindow("Astra UI Kit Demo");
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            win.setLayout(new BorderLayout());

            // Top panel with menu bar
            JPanel top = new JPanel(new BorderLayout()); top.setOpaque(false);
            AstraMenuBar menuBar = new AstraMenuBar();
            AstraMenu file = new AstraMenu("File");
            file.add(new AstraMenuItem("New Project"));
            file.add(new AstraMenuItem("Open…"));
            file.add(new AstraMenuItem("Save"));
            file.addSeparator();
            file.add(new AstraMenuItem("Exit"));
            AstraMenu help = new AstraMenu("Help");
            help.add(new AstraMenuItem("About"));
            menuBar.add(file); menuBar.add(Box.createHorizontalStrut(8)); menuBar.add(help);

            top.add(menuBar, BorderLayout.WEST);
            win.add(top, BorderLayout.NORTH);

            // Center content
            JPanel center = new JPanel(); center.setOpaque(false); center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS)); center.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));
            AstraLabel lbl = new AstraLabel("Detected OS: Ubuntu 24.04 – 64-bit"); lbl.setGradientBackground(true); lbl.setRoundedBackground(true);
            // use your uploaded image path as icon
            try { lbl.setIcon(new ImageIcon("file:///mnt/data/ChatGPT Image Nov 20, 2025 at 03_20_09 AM.png")); } catch(Exception ignored){}
            center.add(lbl); center.add(Box.createVerticalStrut(12));
            AstraButton btn = new AstraButton("Create Bootable Drive"); btn.setPreferredSize(new Dimension(260,48)); center.add(btn); center.add(Box.createVerticalStrut(12));
            AstraProgressPanel p = new AstraProgressPanel(); p.setTitle("Preparing…"); p.setEstimatedTime("Estimated write time: 4 min"); p.setValueAnimated(34); center.add(p);

            win.add(center, BorderLayout.CENTER);

            // Small footer
            JPanel foot = new JPanel(new FlowLayout(FlowLayout.RIGHT)); foot.setOpaque(false); foot.setBorder(BorderFactory.createEmptyBorder(6,12,12,12));
            JLabel ref = new JLabel("Reference image: file:///mnt/data/ChatGPT Image Nov 20, 2025 at 03_20_09 AM.png"); ref.setForeground(new Color(0x9A9A9A)); foot.add(ref);
            win.add(foot, BorderLayout.SOUTH);

            win.pack(); win.setSize(720,420); win.setLocationRelativeTo(null); win.setVisible(true);
        });
    }
}
