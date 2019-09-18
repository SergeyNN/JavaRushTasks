package com.javarush.task.task19.task1921;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/* 
Хуан Хуанович
*/

public class Solution {
    public static final List<Person> PEOPLE = new ArrayList<Person>();

    public static void main(String[] args) throws IOException {
        String filename = args[0];
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));
        while (fileReader.ready()) {
            String line = fileReader.readLine();
            String nameStr = line.replaceAll("[\\w]", "").trim();
            String[] digitalParts = line.substring(nameStr.length()).trim().split(" ");
            int day = Integer.parseInt(digitalParts[0]);
            int month = Integer.parseInt(digitalParts[1]);
            int year = Integer.parseInt(digitalParts[2]);
            Date date = new GregorianCalendar(year, month-1, day).getTime();

            PEOPLE.add(new Person(nameStr, date));
        }
        fileReader.close();
    }
}
