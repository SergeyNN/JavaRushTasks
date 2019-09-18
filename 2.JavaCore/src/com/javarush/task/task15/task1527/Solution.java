package com.javarush.task.task15.task1527;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



/* 
Парсер реквестов
*/

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String subStr = str.substring(str.indexOf('?')+1);
        String[] strArr = subStr.split("&");
        Map<String, String> hashMap = new HashMap<String, String>();
        for (String s:strArr){
            if (s.contains("=")) {
               String par = s.substring(0,s.indexOf('='));
               String val = s.substring(s.indexOf('=')+1);
               hashMap.put(par,val);
                System.out.print(par);
                //System.out.println(val);
            }
            else System.out.print(s);
            System.out.print(" ");
        }
        System.out.println();
        for (String key : hashMap.keySet()) {
            if (key.equals("obj")) {
                String sval = hashMap.get(key);
                try {
                    double dval = Double.parseDouble(sval);
                    alert(dval);
                } catch (NumberFormatException e) {
                    alert(sval);
                }
            }
            //System.out.println("Key: " + key);
        }

    }

    public static void alert(double value) {
        System.out.println("double " + value);
    }

    public static void alert(String value) {
        System.out.println("String " + value);
    }
}
