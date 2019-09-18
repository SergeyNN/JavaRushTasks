package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TestOrder extends Order {
    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {

        /*int randomValuesDishesInTestOrder = (int) Math.round(Math.random() * 9 + 1);
        int valueVariantsOfDishes = Dish.values().length;
        ArrayList<Dish> arrayDishes = new ArrayList<>(Arrays.asList(Dish.values()));

        for (int i = 0; i < randomValuesDishesInTestOrder; i++) {
            dishes.add(arrayDishes.get((int) Math.round(Math.random() * (valueVariantsOfDishes - 1))));
        }*/



        dishes = new ArrayList<>();

        Dish[] values = Dish.values();

        int howManyDishes = new Random().nextInt(values.length)+1;
        while (howManyDishes-- > 0) {
            int dishNumber = new Random().nextInt(values.length);
            dishes.add(values[dishNumber]);
        }
    }
}
