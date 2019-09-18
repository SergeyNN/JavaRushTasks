package com.javarush.task.task18.task1822;

/* 
Поиск данных внутри файла
*/

import java.io.*;

public class Solution {
    public static void main(String[] args) throws IOException {

        if(args.length != 1) return;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader readFile = new BufferedReader(new InputStreamReader(new FileInputStream(reader.readLine())));
        String str;
        while ((str = readFile.readLine()) != null){ //считываем по одной строке за раз
            String[] arrStr = str.split(" "); //разбиваем эту строку по пробелам " "
            if (arrStr[0].equals(args[0])){ //если id совпал, то выводим строку str
                System.out.println(str);
            }
        }
        reader.close();
        readFile.close();
    }
}
