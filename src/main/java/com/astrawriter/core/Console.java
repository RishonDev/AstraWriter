package com.astrawriter.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


@SuppressWarnings("ALL")
public class Console {
    final static Runtime runtime = Runtime.getRuntime();

    public static void runCommand(String command) throws Exception {
        runtime.exec(command);
    }

    public static String[] getRuntimeInput(String[] commands) throws IOException {
        ArrayList<String> runtimeOutput = new ArrayList<String>();
        Process proc = runtime.exec(commands);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));
        // Read the output from the command
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            runtimeOutput.add(s);
        }
        // Read any errors from the attempted command
        while ((s = stdError.readLine()) != null) {
            runtimeOutput.add(s);
        }
        String[] array = new String[runtimeOutput.size()];
        // Assign all the values from the array list to an array to return the output
        for (int i = 0; i < runtimeOutput.size(); i++) {
            array[i] = runtimeOutput.get(i);
        }
        return array;
    }

    public static void wget(String line) throws IOException {
        runtime.exec("wget " + line);
    }

    public static void wget(String URL, String args) throws IOException {
        Console.wget("-" + args + " " + URL);
    }

    public static void wget(String URL, String args, String outputDir) throws IOException {
        wget("-O" + args + " " + outputDir + " " + URL);
    }

    public Runtime getRuntime() {
        return runtime;
    }
}
