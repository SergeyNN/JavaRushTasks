package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private static StatisticManager statisticManager = null;
    private StatisticStorage statisticStorage = new StatisticStorage();
    private Set<Cook> cooks = new HashSet<>();


    private StatisticManager() {

    }

    public static StatisticManager getInstance() {
        if (statisticManager == null) {
            statisticManager = new StatisticManager();
        }
        return statisticManager;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public void register(Cook cook) {
        cooks.add(cook);
    }

    public Set<Cook> getCooks() {
        return cooks;
    }

    public Map<String, Double> getAllVideoSelectedEventsData() {
        Map<String, Double> resultMap = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> eventDataRowList = statisticStorage.get(EventType.SELECTED_VIDEOS);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (EventDataRow eventDataRow : eventDataRowList) {
            String eventDate = simpleDateFormat.format(eventDataRow.getDate()); // удаляем время из даты
            double amount = (double) ((VideoSelectedEventDataRow) eventDataRow).getAmount();
            if (!resultMap.containsKey(eventDate)) { // если нет даты делаем новую запись
                resultMap.put(eventDate, amount);
            } else {
                double tmpAmount = resultMap.get(eventDate) + amount;
                resultMap.put(eventDate, tmpAmount);
            }
        }
        return resultMap;
    }

    public Map<String, Map<String, Long>> getAllCookedOrderEventsData() {
        Map<String, Map<String, Long>> resultMap = new TreeMap<>(Collections.reverseOrder()); // мапа с ключами в виде дат с обнуленным временем, значения в виде мапы (имя повара, время работы за день)
        List<EventDataRow> eventDataRowList = statisticStorage.get(EventType.COOKED_ORDER);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        for (EventDataRow eventDataRow : eventDataRowList) {
            String eventDate = simpleDateFormat.format(eventDataRow.getDate()); // удаляем время из даты
            String cookName = ((CookedOrderEventDataRow) eventDataRow).getCookName();
            long cookingTime = eventDataRow.getTime();

            if (!resultMap.containsKey(eventDate)) { // если нет даты делаем новую запись
                Map<String, Long> tmpMap = new TreeMap<String, Long>();
                tmpMap.put(cookName, cookingTime);
                resultMap.put(eventDate, tmpMap);
            } else if ((resultMap.get(eventDate)).containsKey(cookName)) { // если повар уже был записан, прибавляем время работы к текущему значению
                Map<String, Long> tmpMap = resultMap.get(eventDate);
                Long time = tmpMap.get(cookName) + cookingTime;
                tmpMap.put(cookName, time);
            } else { // иначе добавляем повара и его время работы
                Map<String, Long> tmpMap = resultMap.get(eventDate);
                tmpMap.put(cookName, cookingTime);
            }

        }
        return resultMap;
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage = new HashMap<>();
            for (EventType eventType : EventType.values()) {
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        private List<EventDataRow> get(EventType eventType) {
            return storage.get(eventType);
        }

    }

    private Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
