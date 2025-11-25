package com.astrawriter.core.GUI;


import com.astrawriter.core.SoftwareInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("ALL")
public class Settings {
    static BufferedWriter writer;
    static JFrame frame = new JFrame("Settings");
    static JRadioButton png = new JRadioButton("");
    static JRadioButton png1 = new JRadioButton("");
    static JRadioButton png2 = new JRadioButton("");
    static JRadioButton png3 = new JRadioButton("");
    static JRadioButton png4 = new JRadioButton("");
    static JRadioButton png5 = new JRadioButton("");
    static JRadioButton png6 = new JRadioButton("");
    static JRadioButton png7 = new JRadioButton("");
    static JRadioButton png8 = new JRadioButton("l");
    static JPanel ApplicationPanel = new JPanel();
    static JButton save = new JButton("Save Settings");

    static {
        try {
            writer = new BufferedWriter(new FileWriter(".settings"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Settings() throws IOException {
    }

    public static void init() throws IOException {
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (png.isSelected()) {
                    try {
                        writer.write("bg=" + SoftwareInfo.getHomeDirectory() + ".waveUSB/1.jpeg");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (png1.isSelected()) {
                    try {
                        writer.write("bg=" + SoftwareInfo.getHomeDirectory() + ".waveUSB/2.jpeg");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (png2.isSelected()) {
                    try {
                        writer.write("bg=" + SoftwareInfo.getHomeDirectory() + ".waveUSB/3.jpeg");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (png3.isSelected()) {
                    try {
                        writer.write("bg=" + SoftwareInfo.getHomeDirectory() + ".waveUSB/4.jpeg");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (png4.isSelected()) {
                    try {
                        writer.write("bg=" + SoftwareInfo.getHomeDirectory() + ".waveUSB/5.jpeg");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (png5.isSelected()) {
                    try {
                        writer.write("bg=" + SoftwareInfo.getHomeDirectory() + ".waveUSB/6.jpeg");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (png6.isSelected()) {
                    try {
                        writer.write("bg=" + SoftwareInfo.getHomeDirectory() + ".waveUSB/7.jpeg");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (png7.isSelected()) {
                    try {
                        writer.write("bg=" + SoftwareInfo.getHomeDirectory() + ".waveUSB/8.jpeg");
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

        });
        writer.write("bg=");
        writer.close();
        png.setBounds(225, 20, 100, 30);
        ApplicationPanel.add(png);
        png1.setBounds(225, 50, 100, 30);
        ApplicationPanel.add(png1);
        png2.setBounds(225, 80, 100, 30);
        ApplicationPanel.add(png2);
        png3.setBounds(225, 110, 100, 30);
        ApplicationPanel.add(png3);
        png4.setBounds(225, 140, 100, 30);
        ApplicationPanel.add(png4);
        png5.setBounds(225, 170, 100, 30);
        ApplicationPanel.add(png5);
        png6.setBounds(225, 200, 100, 30);
        ApplicationPanel.add(png6);
        png7.setBounds(225, 230, 100, 30);
        ApplicationPanel.add(png7);
        png8.setBounds(225, 260, 100, 30);
        ApplicationPanel.add(png8);
        save.setBounds(300, 300, 200, 30);
        ApplicationPanel.add(save);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        ApplicationPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        ApplicationPanel.setLayout(null);
        frame.add(ApplicationPanel);
        frame.setVisible(true);
    }
}
