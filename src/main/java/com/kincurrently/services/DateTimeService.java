package com.kincurrently.services;

import com.kincurrently.models.Event;
import com.kincurrently.models.Task;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class DateTimeService {

    public Date parseDate(String dateString) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date parseTime(String dateString) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("hh:mm").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public List<Task> sortTasksByDate(List<Task> tasks) {
        tasks.sort(Comparator.comparing(o -> o.getCompleted_by()));
        return tasks;
    }

    public List<Event> sortEventsByDate(List<Event> events) {
        events.sort(Comparator.comparing(o -> o.getStart_date()));
        return events;
    }

}
