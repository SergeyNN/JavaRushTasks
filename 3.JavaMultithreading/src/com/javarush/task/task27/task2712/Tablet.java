package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet extends Observable {
    final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();
    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder() {
        Order order = null;
        try
        {
            order = new Order(this);
            if (!order.isEmpty()) {
                ConsoleHelper.writeMessage(order.toString());
                setChanged();
                notifyObservers(order);

            }
            new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
        return order;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }

    public void createTestOrder(){
        TestOrder testOrder = null;

        try {
            testOrder = new TestOrder(this);
            manageOrder(testOrder);
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
        catch (NoVideoAvailableException e){
            Tablet.logger.log(Level.INFO, "No video is available for the order " + testOrder);
        }
    }

    private void manageOrder(Order order) {
        /*if (!order.isEmpty()) {
            ConsoleHelper.writeMessage(order.toString());

            AdvertisementManager manager = new AdvertisementManager(order.getTotalCookingTime() * 60);

            try {
                manager.processVideos();
            }catch (NoVideoAvailableException e){
                logger.log(Level.INFO,"No video is available for the order " + order);
            }

            queue.add(order);
        }*/

        if (!order.isEmpty()) {
            ConsoleHelper.writeMessage(order.toString());
            //setChanged();
            //this.notifyObservers(order);

            try {
                queue.put(order);
            } catch (InterruptedException e) {

            }


            new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
        }
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

}
