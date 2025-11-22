import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Icons {
    private static final Color GOLD = new Color(220, 170, 70);
    private static final Color TEXT_PRIMARY = new Color(240, 240, 240);

    public static Icon createLockIcon(int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GOLD);
                g2.setStroke(new BasicStroke(2));

                int lockX = x + size / 4;
                int lockY = y + size / 3;
                int lockW = size / 2;
                int lockH = size / 2;

                // Draw shackle
                Arc2D shackle = new Arc2D.Double(lockX, lockY - lockH / 2, lockW, lockH, 0, 180, Arc2D.OPEN);
                g2.drawArc(lockX, lockY - lockH / 2, lockW, lockH, 0, 180);

                // Draw body
                g2.fillRect(lockX, lockY, lockW, lockH);
                g2.setColor(new Color(20, 20, 20));
                g2.fillRect(lockX + 2, lockY + 2, lockW - 4, lockH - 4);

                // Draw keyhole
                g2.setColor(GOLD);
                g2.fillOval(lockX + lockW / 2 - 3, lockY + lockH / 3, 6, 6);
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    public static Icon createMoonIcon(int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GOLD);

                int moonX = x + size / 4;
                int moonY = y + size / 4;
                int moonSize = size / 2;

                // Draw moon crescent
                Ellipse2D moon = new Ellipse2D.Double(moonX, moonY, moonSize, moonSize);
                g2.fill(moon);

                // Create dark overlay for crescent effect
                g2.setColor(new Color(20, 20, 20));
                Ellipse2D overlay = new Ellipse2D.Double(moonX + size / 8, moonY, moonSize, moonSize);
                g2.fill(overlay);
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    public static Icon createSettingsIcon(int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GOLD);
                g2.setStroke(new BasicStroke(2));

                int centerX = x + size / 2;
                int centerY = y + size / 2;
                int innerRadius = size / 6;
                int outerRadius = size / 2;

                // Draw center circle
                g2.fillOval(centerX - innerRadius, centerY - innerRadius, innerRadius * 2, innerRadius * 2);

                // Draw teeth
                for (int i = 0; i < 8; i++) {
                    double angle = (Math.PI * 2 / 8) * i;
                    double x1 = centerX + Math.cos(angle) * innerRadius;
                    double y1 = centerY + Math.sin(angle) * innerRadius;
                    double x2 = centerX + Math.cos(angle) * outerRadius;
                    double y2 = centerY + Math.sin(angle) * outerRadius;
                    g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
                }
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    public static Icon createUbuntuIcon(int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GOLD);

                int centerX = x + size / 2;
                int centerY = y + size / 2;
                int radius = size / 3;

                // Draw main circle
                g2.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

                // Draw orbiting circles
                int orbitRadius = radius + size / 6;
                for (int i = 0; i < 3; i++) {
                    double angle = (Math.PI * 2 / 3) * i;
                    int dotX = centerX + (int) (Math.cos(angle) * orbitRadius);
                    int dotY = centerY + (int) (Math.sin(angle) * orbitRadius);
                    g2.fillOval(dotX - 4, dotY - 4, 8, 8);
                }
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    public static Icon createCheckIcon(int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(GOLD);
                g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Draw checkmark
                int[] xPoints = {x + size / 4, x + size / 3 + 2, x + size - size / 4};
                int[] yPoints = {y + size / 2, y + size - size / 4, y + size / 4};
                g2.drawPolyline(xPoints, yPoints, 3);
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    public static Icon createArrowIcon(int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TEXT_PRIMARY);
                g2.setFont(new Font("Arial", Font.BOLD, size));
                g2.drawString("›", x, y + size);
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }
}
