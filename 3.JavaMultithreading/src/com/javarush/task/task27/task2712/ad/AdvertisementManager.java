package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;
    public List<Advertisement>  adList;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos(){
        List<Advertisement> videos = storage.list();
        Comparator<Advertisement> comparator=new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                int c=0;
                if (o1.getAmountPerOneDisplaying()>o2.getAmountPerOneDisplaying()){
                    c=-1;
                }
                else if (o1.getAmountPerOneDisplaying()<o2.getAmountPerOneDisplaying()){
                    c=1;
                }
                else if (o1.getAmountPerOneDisplaying()==o2.getAmountPerOneDisplaying()){
                    if (o1.getDuration()>o2.getDuration()){
                        c=-1;
                    }
                    else if (o1.getDuration()<o2.getDuration()){
                        c=1;
                    }
                    else c=0;
                }
                return c;
            }
        };
        Collections.sort(videos, comparator);
        List<Advertisement> videosToShow = new ArrayList<>();
        getMaximumProceedsVideos(videosToShow,0);

        if (storage.list().isEmpty()){
            throw new NoVideoAvailableException();
        }




        Collections.sort(videosToShow, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                int c=0;
                if (o1.getAmountPerOneDisplaying()>o2.getAmountPerOneDisplaying()){
                    c=-1;
                }
                else if (o1.getAmountPerOneDisplaying()<o2.getAmountPerOneDisplaying()){
                    c=1;
                }
                else if (o1.getAmountPerOneDisplaying()==o2.getAmountPerOneDisplaying()){
                    if (o1.getOneSecondCostInThousandsParts()>o2.getOneSecondCostInThousandsParts()){
                        c=1;
                    }
                    else if (o1.getOneSecondCostInThousandsParts()<o2.getOneSecondCostInThousandsParts()){
                        c=-1;
                    }
                    else c=0;
                }
                return c;
            }
        });

        int totalDuration = 0;
        long totalAmount = 0;
        for (Advertisement advertisement:videosToShow){
            totalAmount += advertisement.getAmountPerOneDisplaying();
            totalDuration += advertisement.getDuration();
            ConsoleHelper.writeMessage(String.format("%s is displaying... %d, %d",advertisement.getName(),advertisement.getAmountPerOneDisplaying(),advertisement.getOneSecondCostInThousandsParts()));
            advertisement.revalidate();
        }

//Register event before showing videos
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(
                videosToShow, totalAmount, totalDuration));

    }

    public void getMaximumProceedsVideos(List<Advertisement> list,int index){
        if ((storage.list().size()>index)){
            int totalDuration=0;
            for (Advertisement advertisement:list){
                totalDuration+=advertisement.getDuration();
            }
//            System.out.println("Total duration "+totalDuration+" dlina rolika v spiske "+storage.list().get(index).getDuration()+" index="+index);
            if ((timeSeconds-totalDuration)>=storage.list().get(index).getDuration()){
                if (storage.list().get(index).getHits()>0) {
                    list.add(storage.list().get(index));
                }
            }
            getMaximumProceedsVideos(list,++index);
        }
    }



}
