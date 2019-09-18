package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String catalog = reader.readLine();
        Path path = Paths.get(catalog);
        if (Files.isDirectory(path))
        {
            //System.out.println("Существует!");
            MyFileVisitor pf = new MyFileVisitor(); //Создаём наш внутренний класс
            Files.walkFileTree(path,pf);
            System.out.println("Всего папок - " + pf.papok);
            System.out.println("Всего файлов - "+pf.files);
            System.out.println("Общий размер - " +pf.sizeAll);

        }
        else
            System.out.println(path+" - не папка" );
    }

    public static class MyFileVisitor extends SimpleFileVisitor<Path>
    {
        int files=0;
        int papok=-1;
        long sizeAll=0;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
        {
            if (attrs.isDirectory())
                papok+=1;
            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
        {
            // System.out.println(file);
            files+=1;
            sizeAll+= attrs.size();
            return FileVisitResult.CONTINUE;
        }
    }
}
