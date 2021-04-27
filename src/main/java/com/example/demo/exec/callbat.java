package com.example.demo.exec;

import java.io.IOException;
import java.io.InputStream;

public class callbat {
    public static void main(String[] args) {
        callCmd("jar xf E:\\resource\\skywalking\\app-monitoring-0.1.jar yyjk");
    }

    public static void callCmd(String locationCmd) {
        try {
            Process child = Runtime.getRuntime().exec("cmd.exe /C start " + locationCmd);
            InputStream in = child.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
            }
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
