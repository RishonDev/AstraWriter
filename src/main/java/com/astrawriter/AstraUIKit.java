package com.astrawriter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * AstraUIKit.java
 *
 * Single-file Astra UI kit with:
 * - runtime theme (AstraTheme)
 * - full antialiasing in painting
 * - label glow disabled by default (toggle via enableGlow)
 * - all Astra components (AstraLabel, AstraButton, AstraComboBox, etc.)
 *
 * NOTE: This file is intended to be a single, self-contained UI kit.
 */
public class AstraUIKit {


    public static class AstraTheme {
        public enum Mode { DARK, LIGHT }

        private static Mode mode = Mode.DARK;

        // Dark palette
        private static final Color D_BG = new Color(0x070707);
        private static final Color D_PANEL = new Color(0x1E1E1E);
        private static final Color D_BORDER = new Color(0x505050);
        private static final Color D_TEXT = new Color(0xE6E0C8);
        private static final Color D_SOFT = new Color(0xE6E0C8);
        private static final Color D_TRACK_TOP = new Color(0x0E0E0E);
        private static final Color D_TRACK_BOTTOM = new Color(0x151515);
        private static final Color D_GOLD = new Color(0xD4AF37);
        private static final Color D_GOLD_DARK = new Color(0xA67C1A);

        // Light palette
        private static final Color L_BG = new Color(0xF3F3F3);
        private static final Color L_PANEL = new Color(0xFFFFFF);
        private static final Color L_BORDER = new Color(0xD0D0D0);
        private static final Color L_TEXT = new Color(0x222222);
        private static final Color L_SOFT = new Color(0x222222);
        private static final Color L_TRACK_TOP = new Color(0xF0F0F0);
        private static final Color L_TRACK_BOTTOM = new Color(0xEAEAEA);
        private static final Color L_GOLD = new Color(0xD4AF37);      // Keep gold same
        private static final Color L_GOLD_DARK = new Color(0xA67C1A);

        // getters for current theme
        public static void setMode(Mode m) {
            mode = m;
        }
        public static Mode getMode() { return mode; }

        public static Color background() { return mode == Mode.DARK ? D_BG : L_BG; }
        public static Color panel()      { return mode == Mode.DARK ? D_PANEL : L_PANEL; }
        public static Color border()     { return mode == Mode.DARK ? D_BORDER : L_BORDER; }
        public static Color text()       { return mode == Mode.DARK ? D_TEXT : L_TEXT; }
        public static Color softText()   { return mode == Mode.DARK ? D_SOFT : L_SOFT; }
        public static Color trackTop()   { return mode == Mode.DARK ? D_TRACK_TOP : L_TRACK_TOP; }
        public static Color trackBottom(){ return mode == Mode.DARK ? D_TRACK_BOTTOM : L_TRACK_BOTTOM; }
        public static Color gold()       { return mode == Mode.DARK ? D_GOLD : L_GOLD; }
        public static Color goldDark()   { return mode == Mode.DARK ? D_GOLD_DARK : L_GOLD_DARK; }
    }


    /* ============================================================
     *  STYLES (FONTS)
     * ============================================================ */
    public static class AstraStyles {
        public static Font mediumBold(float size) { return new Font("Dialog", Font.BOLD, (int)size); }
        public static Font medium(float size)     { return new Font("Dialog", Font.PLAIN, (int)size); }
    }


    /* ============================================================
     *  BASE LABEL
     *  - glow disabled by default, enableGlow(true) to enable
     * ============================================================ */
    public static class AstraLabel extends JComponent {
        private String text;
        private Icon icon;
        private boolean hoverGlow = false;     // default disabled
        private boolean gradientBackground = false;
        private boolean roundedBackground = false;
        private Font font = AstraStyles.mediumBold(13f);

        public AstraLabel(String text) {
            this.text = text == null ? "" : text;
            setOpaque(false);
            setCursor(Cursor.getDefaultCursor());
        }

        public void setText(String text) { this.text = text == null ? "" : text; repaint(); }
        public String getText() { return text; }

        public void setIcon(Icon ic) { icon = ic; repaint(); }
        public Icon getIcon() { return icon; }

        public void setRoundedBackground(boolean b) { this.roundedBackground = b; repaint(); }
        public void setGradientBackground(boolean b) { this.gradientBackground = b; repaint(); }
        public void enableGlow(boolean b) { this.hoverGlow = b; repaint(); }
        public boolean isGlowEnabled() { return hoverGlow; }

        @Override
        public Dimension getPreferredSize() {
            FontMetrics fm = getFontMetrics(font);
            int w = fm.stringWidth(text) + 10;
            int h = fm.getHeight() + 6;
            if (icon != null) { w += icon.getIconWidth() + getFontMetrics(font).charWidth(' '); h = Math.max(h, icon.getIconHeight()+6); }
            return new Dimension(w,h);
        }

        @Override
        public Font getFont() { return font; }
        @Override
        public void setFont(Font f) { this.font = f; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();

                if (gradientBackground || roundedBackground) {
                    GradientPaint gp = new GradientPaint(0, 0, AstraTheme.trackTop(), 0, h, AstraTheme.trackBottom());
                    Shape bg = new RoundRectangle2D.Float(0,0,w-1,h-1, roundedBackground?14:0, roundedBackground?14:0);
                    g2.setPaint(gp);
                    g2.fill(bg);
                    g2.setPaint(AstraTheme.goldDark());
                    g2.setStroke(new BasicStroke(1.2f));
                    g2.draw(bg);
                }

                if (hoverGlow) {
                    g2.setPaint(new Color(212,175,55,70));
                    g2.setStroke(new BasicStroke(10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.draw(new RoundRectangle2D.Float(4,4,w-8,h-8,14,14));
                }

                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();
                int ty = (h + fm.getAscent()) / 2 - fm.getDescent();
                int tx = 6;

                if (icon != null) {
                    icon.paintIcon(this, g2, tx, (h - icon.getIconHeight())/2);
                    tx += icon.getIconWidth() + getFontMetrics(font).charWidth(' ');
                }

                // soft shadow text
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
                g2.setPaint(AstraTheme.goldDark().darker());
                g2.drawString(text, tx, ty+1);

                // actual text
                g2.setComposite(AlphaComposite.SrcOver);
                g2.setPaint(AstraTheme.gold());
                g2.drawString(text, tx, ty);

            } finally { g2.dispose(); }
        }
    }


    /* ============================================================
     *  TITLE & SUBTITLE
     * ============================================================ */
    public static class AstraTitleLabel extends AstraLabel {
        public AstraTitleLabel(String text) {
            super(text);
            setFont(AstraStyles.mediumBold(32f));
            // Title uses soft text color - we draw text via theme gold for emphasis; override in paint if needed.
        }
    }

    public static class AstraSubtitleLabel extends AstraLabel {
        public AstraSubtitleLabel(String text) {
            super(text);
            setFont(AstraStyles.medium(15f));
        }
    }


    /* ============================================================
     *  ICON LABEL (placeholder or real icon)
     * ============================================================ */
    public static class AstraIconLabel extends AstraLabel {
        public AstraIconLabel(String textOrPlaceholder) {
            super(textOrPlaceholder);
            setFont(AstraStyles.mediumBold(13f));
        }
    }


    /* ============================================================
     *  GENERIC PANELS
     * ============================================================ */
    public static class AstraPanel extends JPanel {
        public AstraPanel() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, AstraTheme.trackTop(), 0, h, AstraTheme.trackBottom());
                Shape r = new RoundRectangle2D.Float(0,0,w-1,h-1,18,18);
                g2.setPaint(gp);
                g2.fill(r);
                g2.setColor(AstraTheme.border());
                g2.setStroke(new BasicStroke(1.2f));
                g2.draw(r);
            } finally { g2.dispose(); }
            super.paintComponent(g);
        }
    }

    public static class AstraRoundedPanel extends JPanel {
        private final int radius;
        public AstraRoundedPanel(int radius) { this.radius = radius; setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AstraTheme.panel());
                g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
                g2.setColor(AstraTheme.border());
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
            } finally { g2.dispose(); }
            super.paintComponent(g);
        }
    }


    /* ============================================================
     *  BUTTONS & TOGGLES
     * ============================================================ */
    public static class AstraButton extends JComponent {
        private String text;
        private boolean hover=false, pressed=false;
        public AstraButton(String text) {
            this.text = text;
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(140,36));
            addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent e){ hover=true; repaint(); }
                public void mouseExited(MouseEvent e){ hover=false; pressed=false; repaint(); }
                public void mousePressed(MouseEvent e){ pressed=true; repaint(); }
                public void mouseReleased(MouseEvent e){ pressed=false; repaint(); fireAction(); }
            });
        }
        // Action support
        private ActionListener al;
        public void addActionListener(ActionListener a){ this.al = a; }
        private void fireAction(){ if(al!=null) al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "click")); }

        public void setText(String t){ this.text=t; repaint(); }
        public String getText(){ return text; }

        @Override
        public Dimension getPreferredSize(){ return super.getPreferredSize(); }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                int arc = Math.min(h,28);
                RoundRectangle2D bg = new RoundRectangle2D.Float(0,0,w-1,h-1,arc,arc);
                Color top = AstraTheme.trackTop(), bottom = AstraTheme.trackBottom();
                if(hover){ top = top.brighter(); bottom = bottom.brighter(); }
                if(pressed){ top = top.darker(); bottom = bottom.darker(); }
                g2.setPaint(new GradientPaint(0,0,top,0,h,bottom));
                g2.fill(bg);
                g2.setStroke(new BasicStroke(1.8f));
                g2.setPaint(new GradientPaint(0,0,AstraTheme.gold(),0,h,AstraTheme.goldDark()));
                g2.draw(bg);

                Font f = AstraStyles.mediumBold(13f);
                g2.setFont(f);
                FontMetrics fm = g2.getFontMetrics();
                int tw = fm.stringWidth(text);
                int tx = (w-tw)/2;
                int ty = (h+fm.getAscent())/2 - fm.getDescent();
                if(pressed) ty++;

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.45f));
                g2.setPaint(AstraTheme.goldDark().darker());
                g2.drawString(text, tx, ty+1);
                g2.setComposite(AlphaComposite.SrcOver);
                g2.setPaint(AstraTheme.gold());
                g2.drawString(text, tx, ty);
            } finally { g2.dispose(); }
        }
    }

    public static class AstraToggleButton extends JComponent {
        private String text; private boolean selected=false;
        public AstraToggleButton(String text){
            this.text=text;
            setPreferredSize(new Dimension(90,32));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter(){
                @Override public void mouseClicked(MouseEvent e){
                    boolean old = selected;
                    selected = !selected;
                    repaint();
                    firePropertyChange("selected", old, selected);
                }
            });
        }
        public void setSelected(boolean s){ boolean old = selected; selected=s; repaint(); firePropertyChange("selected", old, selected); }
        public boolean isSelected(){ return selected; }
        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                Shape r = new RoundRectangle2D.Float(0,0,w-1,h-1,14,14);
                if(selected){
                    g2.setPaint(new GradientPaint(0,0,AstraTheme.gold(),0,h,AstraTheme.goldDark()));
                } else {
                    g2.setPaint(new GradientPaint(0,0,new Color(35,35,35),0,h,new Color(20,20,20)));
                }
                g2.fill(r);
                g2.setStroke(new BasicStroke(1.6f));
                g2.setPaint(selected ? AstraTheme.goldDark() : AstraTheme.border());
                g2.draw(r);
                g2.setFont(AstraStyles.mediumBold(13f));
                FontMetrics fm = g2.getFontMetrics();
                int tx = (w - fm.stringWidth(text)) / 2;
                int ty = (h + fm.getAscent())/2 - fm.getDescent();
                g2.setPaint(selected ? Color.BLACK : AstraTheme.softText());
                g2.drawString(text, tx, ty);
            } finally { g2.dispose(); }
        }
    }


    /* ============================================================
     *  SEPARATOR, GROUPBOX, CARD PANEL
     * ============================================================ */
    public static class AstraSeparator extends JComponent {
        public AstraSeparator() { setPreferredSize(new Dimension(200,1)); }
        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AstraTheme.border());
                g2.fillRect(0, getHeight()/2, getWidth(), 1);
            } finally { g2.dispose(); }
        }
    }

    public static class AstraGroupBox extends JComponent {
        private String title;
        private Font font = AstraStyles.mediumBold(12f);
        public AstraGroupBox(String title){ this.title = title; setPreferredSize(new Dimension(180,20)); }
        @Override protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setFont(font);
                g2.setColor(AstraTheme.softText());
                FontMetrics fm = g2.getFontMetrics();
                int ty = fm.getAscent();
                g2.drawString(title, 0, ty);
            } finally { g2.dispose(); }
        }
    }

    public static class AstraCardPanel extends JPanel {
        public AstraCardPanel(){ setOpaque(false); setLayout(null); setBorder(BorderFactory.createEmptyBorder(20,20,20,20)); }
        @Override protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                Shape r = new RoundRectangle2D.Float(0,0,w-1,h-1,18,18);
                g2.setPaint(new GradientPaint(0,0,AstraTheme.panel(),0,h,AstraTheme.panel().darker()));
                g2.fill(r);
                g2.setStroke(new BasicStroke(1.3f));
                g2.setPaint(AstraTheme.border());
                g2.draw(r);
            } finally { g2.dispose(); }
            super.paintComponent(g);
        }
    }


    /* ============================================================
     *  COMBOBOX (custom UI), TEXTFIELD, IMAGEBOX
     * ============================================================ */
    public static class AstraComboBox extends JComboBox<Object> {
        public AstraComboBox(){ super(); init(); }
        public AstraComboBox(Object[] items){ super(items); init(); }
        private void init(){
            setOpaque(false);
            setForeground(AstraTheme.softText());
            setBackground(new Color(0,0,0,0));
            setBorder(BorderFactory.createEmptyBorder(6,10,6,10));
            setFont(AstraStyles.medium(13f));
            setUI(new AstraComboUI());
        }
    }

    public static class AstraComboUI extends javax.swing.plaf.basic.BasicComboBoxUI {
        @Override protected JButton createArrowButton(){
            JButton btn = new JButton(){
                @Override protected void paintComponent(Graphics g){
                    Graphics2D g2 = (Graphics2D) g.create();
                    try {
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        int w = getWidth(), h = getHeight();
                        g2.setColor(new Color(0,0,0,0));
                        g2.fillRect(0,0,w,h);
                        g2.setStroke(new BasicStroke(2f));
                        g2.setColor(AstraTheme.gold());
                        int x = w/2 - 4, y = h/2 - 1;
                        g2.drawLine(x, y, x+4, y+4);
                        g2.drawLine(x+4, y+4, x+8, y);
                    } finally { g2.dispose(); }
                }
            };
            btn.setBorder(null); btn.setContentAreaFilled(false); btn.setFocusPainted(false);
            return btn;
        }

        @Override public void paintCurrentValueBackground(Graphics g, Rectangle r, boolean hasFocus) { }
        @Override public void paintCurrentValue(Graphics g, Rectangle r, boolean hasFocus) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = r.width, h = r.height;
                GradientPaint gp = new GradientPaint(0,0,AstraTheme.trackTop(),0,h,AstraTheme.trackBottom());
                Shape rr = new RoundRectangle2D.Float(0,0,w-1,h-1,14,14);
                g2.setPaint(gp); g2.fill(rr);
                g2.setPaint(new GradientPaint(0,0,AstraTheme.gold(),0,h,AstraTheme.goldDark()));
                g2.setStroke(new BasicStroke(1.4f)); g2.draw(rr);
                super.paintCurrentValue(g2, r, hasFocus);
            } finally { g2.dispose(); }
        }

        @Override protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {
                @Override protected void configurePopup(){ super.configurePopup(); setOpaque(false); }
                @Override public void paint(Graphics g){
                    Graphics2D g2 = (Graphics2D) g.create();
                    try {
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        int w = getWidth(), h = getHeight();
                        Shape bg = new RoundRectangle2D.Float(0,0,w-1,h-1,10,10);
                        g2.setPaint(new Color(20,20,20,170));
                        g2.fill(bg);
                        g2.setPaint(new Color(255,255,255,12));
                        g2.draw(bg);
                    } finally { g2.dispose(); }
                    super.paint(g);
                }
            };
            return popup;
        }
    }

    public static class AstraTextField extends JTextField {
        public AstraTextField(){ init(); }
        public AstraTextField(String txt){ super(txt); init(); }
        private void init(){
            setOpaque(false);
            setForeground(AstraTheme.softText());
            setCaretColor(AstraTheme.gold());
            setFont(AstraStyles.medium(13f));
            setBorder(BorderFactory.createEmptyBorder(10,14,10,14));
        }
        @Override protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0,0,new Color(0x111111),0,h,new Color(0x0C0C0C));
                Shape r = new RoundRectangle2D.Float(0,0,w-1,h-1,14,14);
                g2.setPaint(gp); g2.fill(r);
                g2.setPaint(new GradientPaint(0,0,AstraTheme.gold(),0,h,AstraTheme.goldDark()));
                g2.setStroke(new BasicStroke(1.4f)); g2.draw(r);
            } finally { g2.dispose(); }
            super.paintComponent(g);
        }
    }

    public static class AstraImageBox extends JComponent {
        private Image image;
        public AstraImageBox(){}
        public void setImage(Image img){ this.image = img; repaint(); }
        @Override protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(image != null){
                    int iw = image.getWidth(null), ih = image.getHeight(null);
                    int x = (getWidth()-iw)/2, y = (getHeight()-ih)/2;
                    g2.drawImage(image, x, y, null);
                }
            } finally { g2.dispose(); }
        }
    }


    /* ============================================================
     *  PROGRESS BAR & PANEL
     * ============================================================ */
    public static class AstraProgressBar extends JComponent {
        private int minimum = 0, maximum = 100;
        private int targetValue = 0; private double displayedValue = 0;
        private boolean indeterminate = false;
        private final double easing = 0.18;
        private float shimmerOffset = -0.4f; private final float shimmerSpeed = 0.02f;
        private final Timer animateTimer;
        private final Timer shimmerTimer;

        public AstraProgressBar(int min, int max){
            setOpaque(false); setPreferredSize(new Dimension(340,22));
            this.minimum = min; this.maximum = max; this.targetValue = min; this.displayedValue = min;
            animateTimer = new Timer(16, e -> {
                double diff = targetValue - displayedValue;
                if(Math.abs(diff) < 0.05) displayedValue = targetValue;
                else displayedValue += diff * easing;
                repaint();
                if(!indeterminate && displayedValue == targetValue) ((Timer)((ActionEvent)e).getSource()).stop();
            });
            shimmerTimer = new Timer(16, e -> { shimmerOffset += shimmerSpeed; if(shimmerOffset > 1.4f) shimmerOffset = -0.6f; repaint(); });
        }
        public AstraProgressBar(){ this(0,100); }
        private int clamp(int v){ return Math.max(minimum, Math.min(maximum, v)); }
        public void setValue(int v){ v=clamp(v); targetValue=v; displayedValue=v; repaint(); }
        public void setValueAnimated(int v){ v=clamp(v); targetValue=v; if(!animateTimer.isRunning()) animateTimer.start(); }
        public void setIndeterminate(boolean b){ indeterminate=b; if(b){ animateTimer.stop(); shimmerOffset=-0.6f; shimmerTimer.start(); } else { shimmerTimer.stop(); } repaint(); }
        private double getNormalizedProgress(){ if(maximum==minimum) return 0; return (displayedValue-minimum)/(double)(maximum-minimum); }

        @Override protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                RoundRectangle2D track = new RoundRectangle2D.Float(0,0,w-1,h-1,18,18);
                g2.setPaint(new GradientPaint(0,0,AstraTheme.trackTop(),0,h,AstraTheme.trackBottom()));
                g2.fill(track);
                double pct = getNormalizedProgress();
                int fillW = indeterminate ? w : (int)(pct * (w - 4));
                if(fillW > 0){
                    if(indeterminate){
                        g2.setPaint(new GradientPaint(0,0,new Color(0x8A6A1A),0,h,new Color(0xC8A23D)));
                        g2.fill(new RoundRectangle2D.Float(2,2,w-4,h-4,14,14));
                        int bandW = Math.max(60, w/4);
                        int bandX = (int)((shimmerOffset+0.5f)*w) - bandW/2;
                        Rectangle clip = new Rectangle(Math.max(0, bandX), 0, Math.min(bandW, w - bandX), h);
                        g2.setClip(clip);
                        g2.setPaint(new GradientPaint(bandX,0,new Color(255,255,255,180), bandX+bandW/2f,0, AstraTheme.gold().brighter()));
                        g2.fill(new RoundRectangle2D.Float(2,2,w-4,h-4,14,14));
                        g2.setClip(null);
                    } else {
                        g2.setClip(new Rectangle(0,0,fillW,h));
                        g2.setPaint(new GradientPaint(0,0,AstraTheme.gold(),0,h,AstraTheme.goldDark()));
                        g2.fill(new RoundRectangle2D.Float(2,2,w-4,h-4,14,14));
                        g2.setClip(null);
                    }
                }
                g2.setStroke(new BasicStroke(2f));
                g2.setPaint(new GradientPaint(0,0,AstraTheme.gold(),0,h,AstraTheme.goldDark()));
                g2.draw(track);
            } finally { g2.dispose(); }
        }
    }

    public static class AstraProgressPanel extends JPanel {
        private final AstraLabel titleLabel = new AstraLabel("");
        private final AstraLabel captionLabel = new AstraLabel("");
        private final AstraProgressBar bar = new AstraProgressBar();
        public AstraProgressPanel(){
            setOpaque(false);
            setLayout(null);
            titleLabel.setFont(AstraStyles.mediumBold(13f));
            captionLabel.setFont(AstraStyles.medium(12f));
            add(titleLabel); add(bar); add(captionLabel);
        }
        @Override public void doLayout(){
            int w = getWidth();
            titleLabel.setBounds(0,0,w,20);
            bar.setBounds(0,28,w,22);
            captionLabel.setBounds(0,56,w,20);
        }
        public void setTitle(String t){ titleLabel.setText(t); }
        public void setEstimatedTime(String t){ captionLabel.setText(t); }
        public void setValue(int v){ bar.setValue(v); }
        public void setValueAnimated(int v){ bar.setValueAnimated(v); }
        public void setIndeterminate(boolean b){ bar.setIndeterminate(b); }
    }


    /* ============================================================
     *  ASTRA WINDOW (applies global AA props)
     * ============================================================ */
    public static class AstraWindow extends JFrame {
        public AstraWindow(String title){
            super(title);
            // Encourage antialiasing at JVM level for system text rendering
            try {
                System.setProperty("awt.useSystemAAFontSettings", "on");
                System.setProperty("swing.aatext", "true");
            } catch (Exception ignored){}

            getContentPane().setBackground(AstraTheme.background());
            setUndecorated(false);
        }

        public void applyAstraMenuBar(JMenuBar mb){ setJMenuBar(mb); }
    }

}