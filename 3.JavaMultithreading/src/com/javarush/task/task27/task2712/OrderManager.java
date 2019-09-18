package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderManager implements Observer {
    /*private static final LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();
    public OrderManager() {
        Thread threadDaemon = new Thread(new Runnable() {
            Set<Cook> cooks = StatisticManager.getInstance().getCooks();

            @Override
            public void run() {
                while (true) {
                    try {
                        if (!queue.isEmpty()) {
                            for (final Cook cook : cooks) {
                                if (!cook.isBusy() && !queue.isEmpty()) {
                                    final Order order = queue.poll();
                                    cook.startCookingOrder(order);
                                }
                            }
                        }
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        //break;
                    }
                }
            }
        });

        threadDaemon.setDaemon(true);
        threadDaemon.start();
    }


    @Override
    public void update(Observable o, Object arg) {
        queue.offer((Order) arg);
    }*/




    private LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public OrderManager() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Set<Cook> cooks = StatisticManager.getInstance().getCooks();
                while (true) {
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {}
                    if (!orderQueue.isEmpty()) {
                        for (Cook cook : cooks) {
                            if (!cook.isBusy()) {
                                Order order = orderQueue.poll();
                                if (order != null)
                                    cook.startCookingOrder(order);
                            }
                            if (orderQueue.isEmpty())
                                break;
                        }
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            orderQueue.put((Order) arg);
        }
        catch (InterruptedException e) {}
    }

}
