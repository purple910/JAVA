package com.example.demo.exec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class callshell {
    public static void main(String[] args) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String command = "/export/home/xlg/solarischk.sh";
        Process pcs = rt.exec(command);
        PrintWriter outWriter = new PrintWriter(new File("/export/home/zjg/show.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(pcs.getInputStream()));
        String line = new String();
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            outWriter.write(line);
        }
        try {
            pcs.waitFor();
        } catch (InterruptedException e) {
            System.err.println("processes was interrupted");
        }
        br.close();
        outWriter.flush();
        outWriter.close();
        int ret = pcs.exitValue();
        System.out.println(ret);
        System.out.println("执行完毕!");
    }
}
