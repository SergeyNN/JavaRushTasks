package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {

        List<Tablet> tablets = new ArrayList<>();

        Cook cook = new Cook("Amigo");
        cook.setQueue(orderQueue);

        Cook cook2 = new Cook("Cook");
        cook2.setQueue(orderQueue);

        Waiter waiter = new Waiter();
        cook.addObserver(waiter);
        cook2.addObserver(waiter);

        //StatisticManager.getInstance().register(cook);
        //StatisticManager.getInstance().register(cook2);

        OrderManager orderManager = new OrderManager();

        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.addObserver(orderManager);
            //tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        Thread t = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        t.start();
        try
        {
            Thread.sleep(1000);

        }
        catch (InterruptedException e)
        {

        }
        t.interrupt();

        /*DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();*/
    }
}
