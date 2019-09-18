package com.javarush.task.task33.task3308;

import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    public Goods goods;
    public int count;
    public double profit;
    public String[] secretData;

    private static class Goods {
        @XmlElementWrapper(name="goods", nillable = true)
        List<String> names = new ArrayList<>();
    }
}
