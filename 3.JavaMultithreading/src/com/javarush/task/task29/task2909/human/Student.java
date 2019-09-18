package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student extends UniversityPerson {
    private double averageGrade;

    private Date beginningOfSession;
    private Date endOfSession;
    private int course;

    public Student(String name, int age, double averageGrade) {
        super(name, age);
        this.name = name;
        this.age = age;
        this.averageGrade = averageGrade;
    }



    public void live() {
        learn();
    }

    public void learn() {
    }

    public int getCourse() {
        return course;
    }


    public void incAverageGrade(double delta) {setAverageGrade(getAverageGrade()+ delta);}


    public void setAverageGrade (double avarageGrade)
    {
        this.averageGrade = avarageGrade;
    }

    public void setCourse (int course)
    {
        this.course = course;
    }

    public void setBeginningOfSession(Date data) {
        beginningOfSession = data;
    }

    public void setEndOfSession(Date data) {
        endOfSession = data;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    @Override
    public String getPosition()
    {
        return "Студент";
    }
}