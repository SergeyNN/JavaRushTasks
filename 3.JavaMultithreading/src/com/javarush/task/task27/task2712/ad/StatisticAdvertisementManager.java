package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatisticAdvertisementManager {
    public enum VideoType{Active, NotActive}
    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();
    private AdvertisementStorage storage = AdvertisementStorage.getInstance();
    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    private StatisticAdvertisementManager() {
    }

    public List<Advertisement> getVideoList(VideoType type){
        List<Advertisement> list = new ArrayList<>();

        for (Advertisement a : storage.list()){
            if (type == VideoType.Active){
                if ( a.getHits() > 0)  list.add(a);
            }else if ( type == VideoType.NotActive ){
                if (a.getHits() == 0 )list.add(a);
            }
        }

        Collections.sort(list, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        return list;
    }
}
