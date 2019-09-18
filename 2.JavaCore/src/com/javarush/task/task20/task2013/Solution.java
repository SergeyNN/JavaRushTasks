package com.javarush.task.task20.task2013;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/* 
Externalizable Person
*/
public class Solution {
    public static class Person implements Externalizable {
        private String firstName;
        private String lastName;
        private int age;
        private Person mother;
        private Person father;
        private List<Person> children;

        public Person() {
        }

        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        public void setMother(Person mother) {
            this.mother = mother;
        }

        public void setFather(Person father) {
            this.father = father;
        }

        public void setChildren(List<Person> children) {
            this.children = children;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(mother);
            out.writeObject(father);
            out.writeObject(firstName);
            out.writeObject(lastName);
            out.writeInt(age);
            out.writeObject(children);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            mother = (Person) in.readObject();
            father = (Person) in.readObject();
            firstName = (String) in.readObject();
            lastName = (String) in.readObject();
            age = in.readInt();
            children = (List<Person>) in.readObject();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person p1 = new Person("a", "b", 12);
        p1.setMother(new Person("ss", "tt", 26));
        p1.setFather(new Person("qq", "ee", 27));
        List<Person> children = new ArrayList<>();
        children.add(new Person("rr", "jj", 1));
        children.add(new Person("ff", "oo", 2));
        p1.setChildren(children);
        ByteArrayOutputStream buf1 = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(buf1);
        oos.writeObject(p1);
        oos.flush();
        oos.close();
        ByteArrayInputStream buf2 = new ByteArrayInputStream(buf1.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(buf2);
        Person p2 = (Person) ois.readObject();
        System.out.println(p1.equals(p2));
    }

}
