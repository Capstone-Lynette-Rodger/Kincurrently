package com.kincurrently.controllers;

import com.kincurrently.models.Event;
import com.kincurrently.models.EventComment;
import com.kincurrently.models.User;
import com.kincurrently.repositories.EventCommentRepository;
import com.kincurrently.repositories.EventRepository;
import com.kincurrently.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/events/comment/{id}/edit")
    public String indCommentView(@PathVariable long id, Model model) {

        model.addAttribute("editComment", eventCommentRepository.findOne(id));

        return "/events/editComment";
    }

    @PostMapping("/events/comment/edit")
    public String updateEventComment (EventComment editComment, @RequestParam(name = "eventId") long id) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        editComment.setUser(current);
        editComment.setEvent(eventRepository.findById(id));
        eventCommentRepository.save(editComment);
        return "redirect:/events/" + id;
    }



    @PostMapping("/events/comment/delete")
    public String deleteEventComment (@RequestParam long id, @RequestParam(name = "eventId") long idd) {
        eventCommentRepository.delete(id);

        return "redirect:/events/" + idd;
    }
}