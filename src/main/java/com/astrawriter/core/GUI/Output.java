package com.astrawriter.core.GUI;



import com.astrawriter.core.SoftwareInfo;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;

public class Output {
    private SoftwareInfo SoftwareInfo;
    BufferedWriter writer = new BufferedWriter(new FileWriter(com.astrawriter.core.SoftwareInfo.getAppDataDirectory() + "/logs/OutputClassLog.txt"));
    private JTextArea textArea;

    public Output(JTextArea textArea) throws IOException {
        this.textArea = textArea;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void showOutputOfCommand(String command) throws IOException {
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) textArea.append(line + "\n");
        } catch (IOException e) {
            System.err.println("Internal Library error: Output class\n");
            e.printStackTrace();
            String[] err = Arrays.toString(e.getStackTrace()).split("\n");
            for (String er : err) writer.write(er);
        }
    }
}