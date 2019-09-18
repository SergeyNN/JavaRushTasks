package com.javarush.task.task18.task1820;

/* 
Округление чисел
*/

import java.io.*;
import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String fileName1 = reader.readLine();
        String fileName2 = reader.readLine();

        //Читаем файл
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader f1 = new BufferedReader(new FileReader(fileName1));
        while (f1.ready())
            list.add(f1.readLine());
        f1.close();

        //Парсим и округляем
        ArrayList<Long> values = new ArrayList<Long>();
        for (String s : list) {
            for (String part : s.split(" ")) {
                double a = Double.parseDouble(part);
                values.add(Math.round(a));
            }
        }
        f1.close();

        //Запись в файл
        FileWriter f2 = new FileWriter(fileName2);
        for (Long value : values)
            f2.write( value.toString()+" ");

        f2.close();
    }
}
