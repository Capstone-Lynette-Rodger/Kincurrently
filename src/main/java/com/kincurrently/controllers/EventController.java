package com.kincurrently.controllers;

import com.kincurrently.models.Category;
import com.kincurrently.models.Event;
import com.kincurrently.models.EventComment;
import com.kincurrently.models.User;
import com.kincurrently.repositories.CategoryRepository;
import com.kincurrently.repositories.EventCommentRepository;
import com.kincurrently.repositories.EventRepository;
import com.kincurrently.repositories.FamilyRepository;
import com.kincurrently.services.DateTimeService;
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

        model.addAttribute("events", eventRepository.findByFamilyId(current.getFamily().getId()));
        model.addAttribute("event", new Event());
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "/events/events";
    }


    @GetMapping("events/{id}")
    public String indPostView(@PathVariable long id, Model model) {

        model.addAttribute("event", eventRepository.findOne(id));
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("eventComment", new EventComment());

        return "events/showEvent";
    }

    @PostMapping("/events/create")
    public String postEvent (@Valid Event event, Errors validation, Model model,
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

        System.out.println(endDate);
        if (!endDate.equalsIgnoreCase("")) {


            event.setStart_date(dtService.parseDate(startDate));
            event.setEnd_date(dtService.parseDate(endDate));
            event.setStart_time(dtService.parseTime(startTime));
            event.setEnd_time(dtService.parseTime(endTime));






        } else if (endDate.equalsIgnoreCase("")) {

            event.setStart_date(dtService.parseDate(startDate));
            event.setEnd_date(null);
            event.setStart_time(dtService.parseTime(startTime));
            event.setEnd_time(dtService.parseTime(endTime));




        }

        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        event.setFamily(current.getFamily());
        event.setUser(current);
        System.out.println("event.getUser() = " + current.getUsername());
        eventRepository.save(event);

        return "redirect:/events";
    }
}
