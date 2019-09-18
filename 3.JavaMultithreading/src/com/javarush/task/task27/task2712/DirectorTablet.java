package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class DirectorTablet {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    public void printAdvertisementProfit() {
        Map<String, Double> map = StatisticManager.getInstance().getAllVideoSelectedEventsData();
        if(map == null) return;
        if (map.isEmpty()) return;
        ArrayList<String> resultList = new ArrayList<>();
        double total = 0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            total += entry.getValue();
            resultList.add(String.format(Locale.ENGLISH, "%s - %.2f", entry.getKey(), entry.getValue() / 100));

        }
        for (String str : resultList) ConsoleHelper.writeMessage(str);
        if (total > 0) ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "Total - %.2f", total / 100));
    }

    public void printCookWorkloading() {
        Map<String, Map<String, Long>> map = StatisticManager.getInstance().getAllCookedOrderEventsData();
        if(map == null) return;
        if (map.isEmpty()) return;
        for (Map.Entry<String, Map<String, Long>> dateEntry : map.entrySet()) {
            ConsoleHelper.writeMessage(dateEntry.getKey());
            for (Map.Entry<String, Long> workEntry : dateEntry.getValue().entrySet()) {
                if (workEntry.getValue() > 0) {
                    long tmpTime = workEntry.getValue(); // получаем в секундах
                    if (tmpTime % 60 > 0) tmpTime = tmpTime / 60 + 1; // округляем в большую сторону
                    else tmpTime = tmpTime / 60;
                    ConsoleHelper.writeMessage(workEntry.getKey() + " - " + tmpTime + " min");
                }
            }
        }
    }

    public void printActiveVideoSet() {
        StatisticAdvertisementManager manager = StatisticAdvertisementManager.getInstance();

        for(Advertisement a : manager.getVideoList(StatisticAdvertisementManager.VideoType.Active)){
            ConsoleHelper.writeMessage(a.getName() + " - " + a.getHits());
        }
    }

    public void printArchivedVideoSet() {
        StatisticAdvertisementManager manager = StatisticAdvertisementManager.getInstance();

        for(Advertisement a : manager.getVideoList(StatisticAdvertisementManager.VideoType.NotActive)){
            ConsoleHelper.writeMessage(a.getName());
        }


    }
}
