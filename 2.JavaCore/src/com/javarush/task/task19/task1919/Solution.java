package com.javarush.task.task19.task1919;

/* 
Считаем зарплаты
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Solution {
    static TreeMap<String, Double> map = new TreeMap<>();
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));


        while (fileReader.ready()) {
            String s = fileReader.readLine();
            String[] values = s.split(" ");
            addMap(values[0],Double.parseDouble(values[1]));

        }
        for (Map.Entry<String, Double> pair : map.entrySet()) {

            System.out.println(pair.getKey()+" "+pair.getValue());
        }
        fileReader.close();
    }

    private static void addMap(String name, Double value) {
        if (!map.containsKey(name))
            map.put(name,value);
        else
            map.put(name, map.get(name) + value);
    }
}
