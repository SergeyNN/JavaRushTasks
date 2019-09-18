package com.javarush.task.task27.task2712;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets;
    private long interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval){
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){

            try{
                while(!Thread.currentThread().isInterrupted())
                {
                    int indexOfTablet = (int) (Math.random() * tablets.size());
                    Tablet tablet = tablets.get(indexOfTablet);
                    tablet.createTestOrder();

                    Thread.sleep(interval);
                }
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }

        }
    }
}
