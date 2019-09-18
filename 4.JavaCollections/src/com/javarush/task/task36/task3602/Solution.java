package com.javarush.task.task36.task3602;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

/* 
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class<?>[] classes = Collections.class.getDeclaredClasses();
        Class result = null;
        for (Class<?> clazz : classes)
        {
            if (Modifier.isPrivate(clazz.getModifiers()) && Modifier.isStatic(clazz.getModifiers()))
            {
                Method method;
                Constructor constructor;
                Object object;
                try
                {
                    method = clazz.getDeclaredMethod("get",int.class);
                }
                catch (NoSuchMethodException e) {continue;}
                try
                {
                    constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    object = constructor.newInstance();
                    method.setAccessible(true);
                    method.invoke(object,0);
                }
                catch (IndexOutOfBoundsException | InvocationTargetException targetException)
                {
                    result = clazz;
                    break;
                }
                catch (Exception e){
//                    System.out.println(e);
                }
            }
        }
        return result;
    }
}
