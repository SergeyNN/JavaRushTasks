package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.Solution;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {
    public long getTimeToGetIds(Shortener shortener,
                                     Set<String> strings, Set<Long> ids) {
        Date start = new Date();
        //for(String s : strings)
        //    ids.add(shortener.getId(s));
        ids = Solution.getIds(shortener, strings);
        Date end = new Date();
        return end.getTime() - start.getTime();

        /*Long startTime = System.nanoTime()/ 1000;
        strings.forEach(str -> ids.add(shortener.getId(str)));
        Long endTime = System.nanoTime()/ 1000;
        return endTime - startTime;*/
    }

    public long getTimeToGetStrings(Shortener shortener,
                                         Set<Long> ids, Set<String> strings) {
        Date start = new Date();
        strings = Solution.getStrings(shortener, ids);
        Date end = new Date();
        return end.getTime() - start.getTime();

        /*Long startTime = System.nanoTime()/ 1000;
        ids.forEach(id -> strings.add(shortener.getString(id)));
        Long endTime = System.nanoTime()/ 1000;
        return endTime - startTime;*/
    }

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> ids1 = new HashSet<>();
        Set<Long> ids2 = new HashSet<>();
        long timeIds1 = getTimeToGetIds(shortener1, origStrings, ids1);
        long timeIds2 = getTimeToGetIds(shortener2, origStrings, ids2);
        Assert.assertTrue(timeIds1 > timeIds2);
        Set<String> strings1 = new HashSet<>();
        Set<String> strings2 = new HashSet<>();
        long timeStrings1 = getTimeToGetStrings(shortener1, ids1, strings1);
        long timeStrings2 = getTimeToGetStrings(shortener2, ids2, strings2);
        Assert.assertEquals(timeStrings1, timeStrings2, 30);




        /*Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> ids = new HashSet<>();
        Long timeWhithHashMapIds= getTimeToGetIds(shortener1, origStrings, ids);
        Long timeWhithHashMapStrings = getTimeToGetStrings(shortener1,ids,new HashSet<>());

        Set<Long> idsBi = new HashSet<>();
        Long timeWhithHashBiMapIds = getTimeToGetIds(shortener2, origStrings,idsBi);
        Long timeWhithHashBiMapStrings = getTimeToGetStrings(shortener1,idsBi,new HashSet<>());


        Assert.assertTrue(timeWhithHashMapIds > timeWhithHashBiMapIds);
        Assert.assertEquals(timeWhithHashMapStrings, timeWhithHashBiMapStrings,30);*/
    }

}
