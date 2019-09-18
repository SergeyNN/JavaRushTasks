package com.javarush.task.task22.task2207;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* 
Обращенные слова
*/
public class Solution {
    public static List<Pair> result = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = consoleReader.readLine();
        consoleReader.close();

        //Get Lines from file
        //List<String> lines = Files.readAllLines(Paths.get(fileName)); //так проще, но не пропускается
        List<String> lines = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            while (fileReader.ready()) {
                String line = fileReader.readLine();
                String[] words = line.split(" ");
                for (String entry : words) {
                    lines.add(entry.trim());
                }
                //lines.add(fileReader.readLine());
            }

        }

        for (int i = 0; i < lines.size(); i ++) {
            String word = lines.get(i);
            String reversedWord = new StringBuilder(word).reverse().toString();
            lines.remove(i);
            i--;
            if (lines.contains(reversedWord)) {
                Pair pair = new Pair();
                pair.first = reversedWord;
                pair.second = word;
                result.add(pair);
                lines.remove(reversedWord);
            }
        }

        for (Pair entry : result) {
            System.out.println(entry.toString());
        }
    }

    public static String reverseWord(String word){
        StringBuilder strB = new StringBuilder(word);
        return strB.reverse().toString();
    }

    public static class Pair {
        String first;
        String second;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;

        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return  first == null && second == null ? "" :
                    first == null && second != null ? second :
                            second == null && first != null ? first :
                                    first.compareTo(second) < 0 ? first + " " + second : second + " " + first;

        }
    }

}
