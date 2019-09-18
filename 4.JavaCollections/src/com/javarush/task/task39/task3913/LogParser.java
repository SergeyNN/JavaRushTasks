package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;
import com.javarush.task.task39.task3913.query.UserQuery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogParser implements IPQuery, UserQuery {
    private Path logDir;
    private List<String> linesList;
    private List<Record> records;

    public LogParser(Path logDir) {
        records= new ArrayList<>();/*набор данных, представленных в логе*/
        this.logDir = logDir;
        readRecords(logDir);


        this.logDir = logDir;
        linesList = getLinesList();
    }

    /*метод для чтения данных из файла*/
    private void readRecords(Path logDir){
        try(DirectoryStream<Path> directoryStream= Files.newDirectoryStream(logDir)){
            for(Path log: directoryStream){/*просматриваем каждый файл в директории*/
                if(Files.isRegularFile(log) && log.toString().endsWith(".log")){// если файл оканчивается на лог
                    BufferedReader reader= Files.newBufferedReader(log, StandardCharsets.UTF_8);
                    while (reader.ready()){
                        Record record= new Record();
                        String str = reader.readLine();
                        String[] entry= str.split("[\\t]");//reader.readLine().split("\t");
                        record.ip= entry[0];
                        record.user= entry[1];
                        record.date= getDate(entry[2]);//formatter.parse(entry[2]);

                        if(entry[3].indexOf(' ')==-1){// если нет свойства event то
                            record.event= Event.valueOf(entry[3]);
                            record.taskNumber= null;
                        }else {
                            String[] event= entry[3].split(" ");
                            record.event= Event.valueOf(event[0]);
                            record.taskNumber= Integer.parseInt(event[1]);
                        }
                        record.status= Status.valueOf(entry[4]);
                        records.add(record);
                    }
                    reader.close();
                }else{/*если у файла нет расширения log*/
                    if(Files.isDirectory(log)){//если сам файл является директорией
                        readRecords(log);// считать информацию с каждого файла в директории
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<String> getLinesList() {
        String[] files = logDir.toFile().list((dir, name) -> name.endsWith(".log"));
        if (files == null) {
            return null;
        }

        List<String> lines = new ArrayList<>();

        for (String file : files) {
            try {
                lines.addAll(Files.readAllLines(Paths.get(logDir + File.separator + file), Charset.defaultCharset()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    private void addStringEntity(Date after, Date before, Set<String> enteties, String[] parts, int part) {
        long lineDateTime = getDate(parts[2]).getTime();

        if (isCompatibleDate(lineDateTime, after, before)) {
            enteties.add(parts[part]);
        }
    }

    private Date getDate(String part) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(part);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private boolean isCompatibleDate(long lineDateTime, Date after, Date before) {
        if (after == null && before == null) {
            return true;
        } else if (after == null) {
            return lineDateTime <= before.getTime();
        } else if (before == null) {
            return lineDateTime >= after.getTime();
        } else {
            return lineDateTime >= after.getTime() && lineDateTime <= before.getTime();
        }
    }


    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<Record> filteredRecors= getFilteredEntries(after,before);// отсортированные записи
        Set<String> ips = new HashSet<>();

        for(Record record: filteredRecors){
            ips.add(record.ip);
        }

        return ips;
    }


    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<Record> filteredRecords = getFilteredEntries(after, before);
        Set<String> ips = new HashSet<>();

        for (Record record : filteredRecords) {
            if (record.user.equals(user))
                ips.add(record.ip);
        }

        return ips;
    }
    //должен возвращать IP, с которых было произведено переданное событие.
    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<Record> filteredRecords = getFilteredEntries(after, before);
        Set<String> ips = new HashSet<>();

        for (Record record : filteredRecords) {
            if (record.event.equals(event))
                ips.add(record.ip);
        }
        return ips;
    }
    //должен возвращать IP, события с которых закончилось переданным статусом.
    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<Record> filteredRecords = getFilteredEntries(after, before);
        Set<String> ips = new HashSet<>();

        for (Record record : filteredRecords) {
            if (record.status.equals(status))
                ips.add(record.ip);
        }

        return ips;
    }


   /* @Override
    public Set<String> getAllUsers() {
        Set<String> allUsers= new HashSet<>();
        for(Record line: records){
            allUsers.add(line.user);
        }
        return allUsers;

    }*/

    @Override
    public Set<String> getAllUsers() {
        Set<String> allUsers= new HashSet<>();
        for(Record line: records){
            allUsers.add(line.user);
        }
        return allUsers;
    }

    /*должен возвращать количество уникальных пользователей*/
    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<Record> filteredRecords = getFilteredEntries(after, before);
        Set<String> users = new HashSet<>();

        for (Record record : filteredRecords) {
            users.add(record.user);
        }

        return users.size();
    }
    /*должен возвращать количество событий от определенного пользователя.*/
    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<Record> filteredRecords = getFilteredEntries(after, before);
        Set<Event> userEvents= new HashSet<>();
        for(Record record: filteredRecords){
            if(user.equals(record.user)) {
                userEvents.add(record.event);
            }
        }
        return userEvents.size();
    }
    //должен возвращать пользователей с определенным IP.Несколько пользователей могут использовать один и тот же IP.
    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> userIp= new HashSet<>();
        Set<Record> filteredRecords= getFilteredEntries(after, before);
        for(Record line:filteredRecords){
            if(ip.equals(line.ip)){
                userIp.add(line.user);
            }
        }
        return userIp;
    }
    /*должен возвращать пользователей, которые были залогинены.*/
    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set<String> loggedUsers= new HashSet<>();
        Set<Record> filteredRecords= getFilteredEntries(after,before);
        for(Record line: filteredRecords){
            if(Event.LOGIN.equals(line.event) ){
                loggedUsers.add(line.user);
            }
        }

        return loggedUsers;
    }
    //должен возвращать пользователей, которые скачали плагин.
    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set<String> usersPluginDownladed= new HashSet<>();
        Set<Record> filteredRecords= getFilteredEntries(after,before);
        for(Record line: filteredRecords){
            if(Event.DOWNLOAD_PLUGIN.equals(line.event)){
                usersPluginDownladed.add(line.user);
            }
        }
        return usersPluginDownladed;
    }
    //должен возвращать пользователей, которые отправили сообщение.
    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Set<String> usersWroteMessage= new HashSet<>();
        Set<Record> filteredRecords= getFilteredEntries(after,before);
        for(Record line: filteredRecords){
            if(Event.WRITE_MESSAGE.equals(line.event)){
                usersWroteMessage.add(line.user);
            }
        }
        return usersWroteMessage;
    }
    //должен возвращать пользователей, которые решали любую задачу.
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set<String> usersSolvedTask= new HashSet<>();
        Set<Record> filteredRecords= getFilteredEntries(after,before);
        for(Record line: filteredRecords){
            if(Event.SOLVE_TASK.equals(line.event)){
                usersSolvedTask.add(line.user);
            }
        }
        return usersSolvedTask;
    }
    //должен возвращать пользователей, которые решали задачу с номером task.
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set<String> usersSolvedTask= new HashSet<>();
        Set<Record> filteredRecords= getFilteredEntries(after,before);
        for(Record line: filteredRecords){
            if(Event.SOLVE_TASK.equals(line.event) && line.taskNumber.equals(task) ){
                usersSolvedTask.add(line.user);
            }
        }
        return usersSolvedTask;
    }
    //должен возвращать пользователей, которые решали любую задачу.
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set<String>usersDoneTask= new HashSet<>();
        Set<Record> filteredRecords= getFilteredEntries(after, before);
        for(Record line: filteredRecords){
            if(Event.DONE_TASK.equals(line.event)){
                usersDoneTask.add(line.user);
            }
        }
        return usersDoneTask;
    }
    //должен возвращать пользователей, которые решали задачу с номером task.
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set<String>usersDoneTask= new HashSet<>();
        Set<Record> filteredRecords= getFilteredEntries(after, before);
        for(Record line: filteredRecords){
            if(Event.DONE_TASK.equals(line.event) && line.taskNumber.equals(task)){
                usersDoneTask.add(line.user);
            }
        }
        return usersDoneTask;
    }





    private String[] queryParser(String query) {

        //get ip for user="Amigo"
        String[] result = new String[5];
        String str = query.substring(query.indexOf(' ') + 1);
        if (query.indexOf("for") > 0) {
            result[0] = str.substring(0, str.indexOf(' '));//ip
            str = query.substring(query.indexOf("for") + 4);
            result[1] = str.substring(0, str.indexOf(' '));//user
            str = query.substring(query.indexOf(" = ") + 4);
            result[2] = str.substring(0, str.indexOf('"'));//Amigo
            //get ip for user = "Eduard Petrovich Morozko" and date between "11.12.2013 0:00:00" and "03.01.2014 23:59:59".
            if(query.indexOf("and")>0){
                str= query.substring(query.indexOf("between")+9);
                result[3]= ((str.substring(0,str.indexOf('"'))));
                str=query.substring(query.indexOf("between")+34);
                result[4]=str.substring(0,str.indexOf('"'));
            }
        }
        else {
            result[0] = str;
        }
        return result;
    }

    private Set<Object> getQuery(String field1, String field2, String value1, String dateAdvanceFrom, String dateAdvanceTo) {
        Set<Object> results = new HashSet<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(logDir, "*.log")) {
            for (Path path : directoryStream) {
                BufferedReader fileReader = new BufferedReader(new FileReader(path.toString()));
                while (fileReader.ready()) {
                    String str = fileReader.readLine();
                    Map<String, String> data = getDataFromString(str);
                    // информация из лога о пользователе
                    String ipFromData = data.get("ip");
                    String userFromData = data.get("user");
                    Date date = parseDate(data.get("date"));
                    String eventFromData = data.get("event");
                    String statusFromData = data.get("status");


                    //String taskFromData = data.get("task");
                    if (    field2 == null && value1 == null
                            || field2.equals("ip") && value1.equals(ipFromData)
                            || field2.equals("user") && value1.equals(userFromData)
                            || field2.equals("date") && parseDate(value1).equals(date)
                            || field2.equals("event") && value1.equals(eventFromData)
                            || field2.equals("status") && value1.equals(statusFromData)
                            && (dateAdvanceFrom==null && dateAdvanceTo==null)// если нет дополнительного условия
                    ) {
                        switch (field1) {
                            case "ip" :
                                results.add(ipFromData);
                                break;
                            case "user" :
                                results.add(userFromData);
                                break;
                            case "date" :
                                results.add(date);
                                break;
                            case "event" :
                                results.add(Event.valueOf(eventFromData));
                                break;
                            case "status" :
                                results.add(Status.valueOf(statusFromData));
                                break;
                        }
                    }else if((dateAdvanceFrom!=null && dateAdvanceTo!=null)){// если нет дополнительного условия)
                        switch (field1) {
                            case "ip":
                                if (isBetween(date, parseDate(dateAdvanceFrom), parseDate(dateAdvanceTo))) {

                                    results.add(ipFromData);
                                }
                                break;
                            case "user":
                                if (isBetween(date, parseDate(dateAdvanceFrom), parseDate(dateAdvanceTo))) {

                                    results.add(userFromData);
                                }
                                break;
                            case "date":
                                if (isBetween(date, parseDate(dateAdvanceFrom), parseDate(dateAdvanceTo))) {

                                    results.add(date);
                                }
                                break;
                            case "event":
                                if (isBetween(date, parseDate(dateAdvanceFrom), parseDate(dateAdvanceTo))) {

                                    results.add(Event.valueOf(eventFromData));
                                }
                                break;
                            case "status":
                                if (isBetween(date, parseDate(dateAdvanceFrom), parseDate(dateAdvanceTo))) {

                                    results.add(Status.valueOf(statusFromData));
                                }
                                break;
                        }
                    }
                }
                fileReader.close();
                path.getFileName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    //считывает информацию с файла лога о всех пользователях
    private Map<String, String> getDataFromString(String s) {
        Map<String, String> result = new HashMap<>();

        String ip = s.substring(0, s.indexOf('\t'));
        result.put("ip", ip);
        String str = s.substring(s.indexOf('\t')+1);

        String user = str.substring(0, str.indexOf('\t'));
        result.put("user", user);
        str = str.substring(str.indexOf('\t')+1);

        String date = str.substring(0, str.indexOf('\t'));
        result.put("date", date);
        str = str.substring(str.indexOf('\t')+1);

        String event = str.substring(0, str.indexOf('\t'));
        if (event.indexOf(' ') > 0) {
            String taskNumber = event.substring(event.indexOf(' ')+1);
            event = event.substring(0, event.indexOf(' '));
            result.put("task", taskNumber);
        }
        result.put("event", event);
        str = str.substring(str.indexOf('\t')+1);

        String status = str;
        result.put("status", status);

        return result;
    }

    //парсинг даты
    private Date parseDate(String s) {
        String string = s;
        int date = Integer.parseInt(string.substring(0, string.indexOf('.')));
        string = string.substring(string.indexOf('.')+1);
        int month = Integer.parseInt(string.substring(0, string.indexOf('.'))) - 1;
        string = string.substring(string.indexOf('.')+1);
        int year = Integer.parseInt(string.substring(0, string.indexOf(' ')));
        string = string.substring(string.indexOf(' ')+1);
        int hrs = Integer.parseInt(string.substring(0, string.indexOf(':')));
        string = string.substring(string.indexOf(':')+1);
        int min = Integer.parseInt(string.substring(0, string.indexOf(':')));
        string = string.substring(string.indexOf(':')+1);
        int sec = Integer.parseInt(string);
        return (new GregorianCalendar(year, month, date, hrs, min, sec)).getTime();
    }

    private class Record{
        /*набор данных , представленный в логе*/
        String ip;
        String user;
        Date date;
        Event event;
        Integer taskNumber;
        Status status;

        @Override
        public String toString() {
            return "Record{" +
                    "ip='" + ip + '\'' +
                    ", user='" + user + '\'' +
                    ", date=" + date +
                    ", event=" + event +
                    ", taskNumber=" + taskNumber +
                    ", status=" + status +
                    '}';
        }
    }

    private boolean isBetween(Date date, Date after, Date before) {
        return (after == null || date.after(after) ||date.equals(after)) &&
                (before == null || date.before(before) || date.equals(before));
    }

    private Set<Record> getFilteredEntries(Date after, Date before) {
        Set<Record> filteredRecords= new HashSet<>();
        for(Record record:records){
            if(isBetween(record.date,after,before)){
                filteredRecords.add(record);
            }
        }
        return filteredRecords;
    }


}