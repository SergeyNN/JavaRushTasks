package com.javarush.task.task22.task2211;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* 
Смена кодировки
*/
public class Solution {
    static String win1251TestString = "РќР°СЂСѓС€РµРЅРёРµ РєРѕРґРёСЂРѕРІРєРё РєРѕРЅСЃРѕР»Рё?"; //only for your testing
    public static void main(String[] args) throws IOException {
        try(FileInputStream reader = new FileInputStream(args[0]);
            FileOutputStream writer = new FileOutputStream(args[0])){
            byte[] bytes = new byte[reader.available()];
            reader.read(bytes);
            String s = new String(bytes, "Windows-1251");
            bytes = s.getBytes("UTF-8");
            writer.write(bytes);
        }
    }
}
