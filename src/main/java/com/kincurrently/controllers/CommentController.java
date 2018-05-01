package com.kincurrently.controllers;

import com.kincurrently.models.EventComment;
import com.kincurrently.repositories.EventCommentRepository;
import com.kincurrently.repositories.EventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private final EventRepository eventRepository;
    private final EventCommentRepository eventCommentRepository;

    public CommentController(EventRepository eventRepository, EventCommentRepository eventCommentRepository) {
        this.eventRepository = eventRepository;
        this.eventCommentRepository = eventCommentRepository;
    }

    @PostMapping("/events/comment")
    public String addComment(@RequestParam(name = "eventId") long id, EventComment eventComment) {
        eventComment.setEvent(eventRepository.findOne(id));
        eventCommentRepository.save(eventComment);
        return "redirect:/events/" + id;
    }
}