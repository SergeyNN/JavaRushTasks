package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) {
        String fileName = args[0];
        int number = Integer.parseInt(args[1]);
        String text = args[2];

        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            int len = text.length();
            byte[] buf = new byte[len];
            raf.seek(number);
            raf.read(buf, 0, len);
            String string = new String(buf);
            if(string.equals(text)){
                raf.seek(raf.length());
                raf.write("true".getBytes());
            } else {
                raf.seek(raf.length());
                raf.write("false".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
