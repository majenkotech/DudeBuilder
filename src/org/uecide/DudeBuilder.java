package org.uecide;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class DudeBuilder {

    public static void fail(String message) {
        System.err.println("Error: " + message);
        System.exit(10);
    }

    public static void appendParts(String dir, StringBuilder out) {
        File df = new File(dir);
        if (! df.exists()) {
            fail("Directory " + dir + " + doesn't exist");
        }

        if (! df.isDirectory()) {
            fail(dir + " isn't a directory");
        }

        File[] list = df.listFiles();
        Arrays.sort(list);
        for (File file : list) {
            if (file.getName().endsWith(".conf")) {
                InputStream fis;
                BufferedReader br;
                String line;
                try {
                    fis = new FileInputStream(file);
                    br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
                    while ((line = br.readLine()) != null) {
                        out.append(line + "\n");
                    }
                    br.close();
                } catch (Exception e) { 
                    fail(e.getMessage());
                }
            }
        }
    }

    public static void saveFile(String file, StringBuilder out) {
        File f = new File(file);
        try {
            PrintWriter pw = new PrintWriter(f, "UTF-8");
            pw.write(out.toString());
            pw.close();
        } catch (Exception e) { 
            fail(e.getMessage());
        }
    }

    public static void main(String args[]) {
        StringBuilder out = new StringBuilder();
        appendParts("header", out);
        appendParts("programmers", out);
        appendParts("parts", out);
        saveFile("avrdude.conf", out);
    }
}
