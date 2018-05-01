package com.kincurrently.controllers;

import com.kincurrently.models.EventComment;
import com.kincurrently.models.User;
import com.kincurrently.repositories.EventCommentRepository;
import com.kincurrently.repositories.EventRepository;
import com.kincurrently.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private final EventRepository eventRepository;
    private final EventCommentRepository eventCommentRepository;
    private final UserRepository userRepository;

    public CommentController(EventRepository eventRepository, EventCommentRepository eventCommentRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.eventCommentRepository = eventCommentRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/events/comment")
    public String addComment(@RequestParam(name = "eventId") long id, EventComment eventComment) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        eventComment.setEvent(eventRepository.findOne(id));
        eventComment.setUser(current);
        eventCommentRepository.save(eventComment);
        return "redirect:/events/" + id;
    }
}