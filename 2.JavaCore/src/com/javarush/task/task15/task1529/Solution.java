package com.javarush.task.task15.task1529;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/* 
Осваивание статического блока
*/

public class Solution {
    public static void main(String[] args) {

    }
    
    static {
        //Scanner sc = new Scanner(System.in);
        //String str = sc.nextLine();
        reset();
    }

    public static Flyable result;

    public static void reset() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str.equals("helicopter")) {
            result = new Helicopter();
        }
        if (str.equals("plane")) {
            String pass = sc.nextLine();
            result = new Plane(Integer.parseInt(pass));
        }
    }
}
