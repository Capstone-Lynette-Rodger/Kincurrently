package com.kincurrently.controllers;

import com.kincurrently.models.Event;
import com.kincurrently.models.EventComment;
import com.kincurrently.models.TaskComment;
import com.kincurrently.models.User;
import com.kincurrently.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class CommentController {

    private final EventRepository eventRepository;
    private final EventCommentRepository eventCommentRepository;
    private final UserRepository userRepository;
    private final TaskCommentRepository tcRepo;
    private final TaskRepository taskRepo;

    public CommentController(EventRepository eventRepository, EventCommentRepository eventCommentRepository, UserRepository userRepository, TaskRepository taskRepo, TaskCommentRepository tcRepo) {
        this.eventRepository = eventRepository;
        this.eventCommentRepository = eventCommentRepository;
        this.userRepository = userRepository;
        this.taskRepo = taskRepo;
        this.tcRepo = tcRepo;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EventComment comment = eventCommentRepository.findOne(id);
        if(user.getId() != comment.getUser().getId()) {
            return "redirect:/events";
        }
        model.addAttribute("editComment", comment);

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

    @PostMapping("/tasks/comment")
    public String saveTaskComment(@Valid TaskComment newComment, Errors errors, Model model, @RequestParam Long taskId){
        if(errors.hasErrors()) {
            model.addAttribute("errors", errors);
            model.addAttribute("newComment", newComment);
            return "tasks/showTask";
        }
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newComment.setCreated_on(new Date());
        newComment.setTask(taskRepo.findById(taskId));
        newComment.setUser(loggedInUser);
        tcRepo.save(newComment);

        return "redirect:/tasks/" + taskId;
    }

    @GetMapping("/tasks/comment/{id}/edit")
    public String viewEditTaskCommentForm(@PathVariable long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskComment comment = tcRepo.findOne(id);
        if(user.getId() != comment.getUser().getId()) {
            return "redirect:/tasks";
        }
        model.addAttribute("editComment", comment);
        return "/tasks/editComment";
    }

    @PostMapping("/tasks/comment/edit")
    public String updateTaskComment (TaskComment editComment, @RequestParam(name = "taskId") long id) {
        TaskComment dbTaskComment = tcRepo.findOne(id);
        editComment.setUser(dbTaskComment.getUser());
        editComment.setCreated_on(dbTaskComment.getCreated_on());
        editComment.setTask(taskRepo.findById(id));
        tcRepo.save(editComment);
        return "redirect:/tasks/" + id;
    }

    @PostMapping("/tasks/comment/delete")
    public String deleteTaskComment (@RequestParam long id) {
        tcRepo.delete(id);

        return "redirect:/tasks/" + id;
    }
}