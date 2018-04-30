package com.kincurrently.controllers;

import com.kincurrently.models.Event;
import com.kincurrently.repositories.EventRepository;
import com.kincurrently.repositories.FamilyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    private final EventRepository eventRepository;
    private final FamilyRepository familyRepository;

    public EventController(EventRepository eventRepository, FamilyRepository familyRepository) {
        this.eventRepository = eventRepository;
        this.familyRepository = familyRepository;
    }

    @GetMapping("/events")
    public String eventsIndex (Model model) {
        model.addAttribute("events", eventRepository.findAll() );
        model.addAttribute("event", new Event());

        return "/events/events";
    }

    @PostMapping("/events/create")
    public String postEvent () {
        return "/events";
    }
}
