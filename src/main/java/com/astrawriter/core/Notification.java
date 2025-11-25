package com.astrawriter.core;

import javax.swing.*;
import java.awt.*;

public class Notification {
    public static void showErrorDialog(Component component, String message, String title) {
        JOptionPane.showMessageDialog(component, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoDialog(Component component, String message, String title) {
    }

    public static void showWarningDialog(Component component, String message, String title) {
    }
}
