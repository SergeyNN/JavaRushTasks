package com.javarush.task.task14.task1420;

/* 
НОД
*/

import java.io.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int a = Integer.parseInt(reader.readLine());
        int b = Integer.parseInt(reader.readLine());
        if (a <= 0 || b <= 0) throw new Exception();
        int max = Math.max(a, b);
        for (int i = max; i>0; i--){
            if (a % i == 0  && b % i == 0) {
                System.out.println(i);
                break;
            }
        }
    }
}
