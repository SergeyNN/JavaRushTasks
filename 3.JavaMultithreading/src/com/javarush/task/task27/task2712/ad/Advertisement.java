package com.javarush.task.task27.task2712.ad;

public class Advertisement {
    private Object content; // видео
    private String name;
    private long initialAmount; //начальная сумма, стоимость рекламы в копейках.
    private int hits; // количество оплаченных показов
    private int duration; //продолжительность в секундах
    private long amountPerOneDisplaying;
    public Advertisement(Object content, String name, long initialAmount, int hits, int duration)
    {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        this.amountPerOneDisplaying = (hits > 0) ? initialAmount / hits : 0;//initialAmount / hits;
    }


    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public void revalidate() {
        if (hits <= 0)
            //Бросать UnsupportedOperationException, если количество показов не положительное число
            throw new UnsupportedOperationException();
        //Уменьшать количество показов
        hits--;
    }

    public int getHits() {
        return hits;
    }

    public long getOneSecondCostInThousandsParts(){
        return (long) (amountPerOneDisplaying*(1000/(double)duration));
    }

    public void setAmountPerOneDisplaying(long amountPerOneDisplaying) {
        this.amountPerOneDisplaying = amountPerOneDisplaying;
    }
}
