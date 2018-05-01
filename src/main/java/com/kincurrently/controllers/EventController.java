package com.kincurrently.controllers;

import com.kincurrently.models.Event;
import com.kincurrently.models.EventComment;
import com.kincurrently.repositories.EventCommentRepository;
import com.kincurrently.repositories.EventRepository;
import com.kincurrently.repositories.FamilyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class EventController {

    private final EventRepository eventRepository;
    private final FamilyRepository familyRepository;
    private final EventCommentRepository eventCommentRepository;

    public EventController(EventRepository eventRepository, FamilyRepository familyRepository, EventCommentRepository eventCommentRepository) {
        this.eventRepository = eventRepository;
        this.familyRepository = familyRepository;
        this.eventCommentRepository = eventCommentRepository;
    }

    @GetMapping("/events")
    public String eventsIndex (Model model) {
        model.addAttribute("events", eventRepository.findAll() );
        model.addAttribute("event", new Event());

        return "/events/events";
    }


    @GetMapping("events/{id}")
    public String indPostView(@PathVariable long id, Model model) {

        model.addAttribute("event", eventRepository.findOne(id));
        model.addAttribute("eventComment", new EventComment());

        return "events/showEvent";
    }

    @PostMapping("/events/create")
    public String postEvent (@ModelAttribute Event event, @RequestParam String startDate, @RequestParam String endDate,
                             @RequestParam String startTime, @RequestParam String endTime) {
        System.out.println(endDate);
        if (!endDate.equalsIgnoreCase("")) {

            System.out.println(startTime);
            System.out.println(endTime);

            Date startDateFixed = new Date();
            Date endDateFixed = new Date();

            Date startTimeFixed = new Date();
            Date endTimeFixed = new Date();


            try {
                startDateFixed = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                endDateFixed = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                startTimeFixed = new SimpleDateFormat("hh:mm").parse(startTime);
                endTimeFixed = new SimpleDateFormat("hh:mm").parse(endTime);
            } catch (Exception e) {
                e.printStackTrace();
            }


            event.setStart_date(startDateFixed);
            event.setEnd_date(endDateFixed);
            event.setStart_time(startTimeFixed);
            event.setEnd_time(endTimeFixed);


        } else if (endDate.equalsIgnoreCase("")) {
            Date startDateFixed = new Date();

            try {
                startDateFixed = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            } catch (Exception e) {
                e.printStackTrace();
            }

            event.setStart_date(startDateFixed);

            event.setEnd_date(null);
        }


        System.out.println("event.getStart_time() = " + event.getStart_time());
        System.out.println("event.getEnd_time() = " + event.getEnd_time());

        eventRepository.save(event);

        return "redirect:/events";
    }
}
