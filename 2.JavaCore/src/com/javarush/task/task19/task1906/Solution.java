package com.javarush.task.task19.task1906;

/* 
Четные символы
*/

import java.io.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String fileName1 = br.readLine();
        String fileName2 = br.readLine();
        FileReader reader = new FileReader(fileName1);
        FileWriter writer = new FileWriter(fileName2);
        int i=1;
        while (reader.ready()) //пока есть непрочитанные байты в потоке ввода
        {
            int data = reader.read(); //читаем один символ (char будет расширен до int)
            if (i % 2 == 0) writer.write(data); //пишем один символ (int будет обрезан/сужен до char)
            i++;
        }
        writer.close();
        reader.close();
        br.close();
    }
}
