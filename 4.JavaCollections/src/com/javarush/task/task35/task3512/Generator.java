package com.javarush.task.task35.task3512;

public class Generator<T> {
    Class<T> clazz;
    T newInstance() throws IllegalAccessException, InstantiationException {
        return (T) clazz.newInstance();
    }

    public Generator(Class<T> clazz) {
        this.clazz = clazz;
    }
}
