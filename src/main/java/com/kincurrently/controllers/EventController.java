package com.kincurrently.controllers;

import com.kincurrently.models.*;
import com.kincurrently.repositories.CategoryRepository;
import com.kincurrently.repositories.EventCommentRepository;
import com.kincurrently.repositories.EventRepository;
import com.kincurrently.repositories.FamilyRepository;
import com.kincurrently.services.DateTimeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EventController {

    @Value("${google-api-key}")
    private String api;


    private final EventRepository eventRepository;
    private final FamilyRepository familyRepository;
    private final EventCommentRepository eventCommentRepository;
    private final CategoryRepository categoryRepository;
    private DateTimeService dtService;

    public EventController(EventRepository eventRepository, FamilyRepository familyRepository,
                           EventCommentRepository eventCommentRepository, DateTimeService dtService,
                           CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.familyRepository = familyRepository;
        this.eventCommentRepository = eventCommentRepository;
        this.dtService = dtService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/events")
    public String eventsIndex (Model model) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("events", dtService.sortEventsByDate(eventRepository.findByFamilyId(current.getFamily().getId())));
//        model.addAttribute("event", new Event());
//        Iterable<Category> categories = categoryRepository.findAll();
//        model.addAttribute("categories", categories);

        return "/events/events";
    }

    @GetMapping("/events/create")
    public String eventsCreate (Model model) {
//        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("events", eventRepository.findByFamilyId(current.getFamily().getId()));
//        model.addAttribute("events", dtService.sortEventsByDate(eventRepository.findByFamilyId(current.getFamily().getId())));
        model.addAttribute("api",api);
        model.addAttribute("event", new Event());
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "/events/createEvent";
    }


    @GetMapping("events/{id}")
    public String indEventView(@PathVariable long id, Model model) {

        model.addAttribute("event", eventRepository.findOne(id));
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("eventComment", new EventComment());
        model.addAttribute("api",api);

        return "events/showEvent";
    }

    @PostMapping("/events/create")
    public String createEvent (@Valid Event event, Errors validation, Model model,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                               @RequestParam String startTime,
                               @RequestParam String endTime) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (validation.hasErrors()) {
            model.addAttribute("event", event);
            model.addAttribute("validation", validation);
            Iterable<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            return "/events/events";
        }

        dateSet(event, startDate, endDate, startTime, endTime);

        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("api",api);
        if(event.getLocation().trim().equals("")) {
            event.setLocation(null);
        }

        event.setFamily(current.getFamily());
        event.setUser(current);
        eventRepository.save(event);

        return "redirect:/events";
    }

    @GetMapping("/events/{id}/edit")
    public String editEvent (Model model, @PathVariable long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = eventRepository.findOne(id);
        if(user.getId() != event.getUser().getId()) {
            return "redirect:/events";
        }
        model.addAttribute("event", event);
        model.addAttribute("api",api);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "/events/edit";
    }

    @PostMapping("/events/edit")
    public String makeEventEdit(@Valid Event editEvent, Errors validation, Model model,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                @RequestParam String startTime,
                                @RequestParam String endTime){

        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (validation.hasErrors()) {
            model.addAttribute("editEvent", editEvent);
            model.addAttribute("validation", validation);
            Iterable<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            return "/events/events";
        }

        dateSet(editEvent, startDate, endDate, startTime, endTime);

        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("api",api);

        editEvent.setFamily(current.getFamily());
        editEvent.setUser(current);

        eventRepository.save(editEvent);

        return "redirect:/events";
    }

    private void dateSet(@Valid Event editEvent, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String startTime, @RequestParam String endTime) {
        editEvent.setStart_date(dtService.parseDate(startDate));
        editEvent.setEnd_date(dtService.parseDate(endDate));

        if(startTime.trim().equals("")){
            editEvent.setStart_time(new Date(0));
        } else {
            editEvent.setStart_time(dtService.parseTime(startTime));
        }
        if(endTime.trim().equals("")){
            editEvent.setEnd_time(null);
        } else {
            editEvent.setEnd_time(dtService.parseTime(endTime));
        }
    }

    @PostMapping("/events/delete")
    public String deleteEvent (@RequestParam long id) {
        eventRepository.delete(id);

        return "redirect:/dashboard";
    }
}