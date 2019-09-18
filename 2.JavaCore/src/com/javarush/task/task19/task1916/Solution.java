package com.javarush.task.task19.task1916;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* 
Отслеживаем изменения
*/

public class Solution {
    public static List<LineItem> lines = new ArrayList<LineItem>();
    public static ArrayList<String> fc1 = new ArrayList<String>();
    public static ArrayList<String> fc2 = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader file1 = new BufferedReader(new FileReader(reader.readLine()));
        BufferedReader file2 = new BufferedReader(new FileReader(reader.readLine()));
        reader.close();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        while (file1.ready()) {
            String s = file1.readLine();
            if (s.isEmpty()) continue;
            list1.add(s);
        }
        file1.close();

        while (file2.ready()) {
            String s = file2.readLine();
            if (s.isEmpty()) continue;
            list2.add(s);
        }
        file2.close();

        while (true) {
            if (list1.get(0).equals(list2.get(0))) {
                lines.add(new LineItem(Type.SAME, list1.get(0)));
                list1.remove(0);
                list2.remove(0);
            } else if (list1.get(0).equals(list2.get(1))) {
                lines.add(new LineItem(Type.ADDED, list2.get(0)));
                list2.remove(0);
            } else {
                lines.add(new LineItem(Type.REMOVED, list1.get(0)));
                list1.remove(0);
            }
            if (list1.isEmpty()) {
                for (String str : list2) {
                    lines.add(new LineItem(Type.ADDED, str));
                }
                break;
            } else if (list2.isEmpty()) {
                for (String str : list1) {
                    lines.add(new LineItem(Type.REMOVED, str));
                }
                break;
            }
        }
    }


    public static enum Type {
        ADDED,        //добавлена новая строка
        REMOVED,      //удалена строка
        SAME          //без изменений
    }

    public static class LineItem {
        public Type type;
        public String line;

        public LineItem(Type type, String line) {
            this.type = type;
            this.line = line;
        }
    }
}
