package com.kincurrently.controllers;

import com.kincurrently.models.Task;
import com.kincurrently.models.TaskComment;
import com.kincurrently.models.User;
import com.kincurrently.repositories.*;
import com.kincurrently.services.DateTimeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {
    private TaskRepository taskRepo;
    private DateTimeService dtService;
    private StatusRepository statusRepo;
    private TaskCommentRepository tcRepo;
    private CategoryRepository catRepo;
    private FamilyRepository familyRepo;

    public TaskController(TaskRepository taskRepo, DateTimeService dtService, StatusRepository statusRepo, TaskCommentRepository tcRepo, CategoryRepository catRepo, FamilyRepository familyRepo) {
        this.taskRepo = taskRepo;
        this.dtService = dtService;
        this.statusRepo = statusRepo;
        this.tcRepo = tcRepo;
        this.catRepo = catRepo;
        this.familyRepo = familyRepo;
    }

    @GetMapping("/tasks")
    public String showTaskForm(Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("family", familyRepo.findOne(loggedInUser.getFamily().getId()));
        model.addAttribute("allTasks", dtService.sortTasksByDate((List<Task>)taskRepo.findAll()));
        model.addAttribute("myTasks", dtService.sortTasksByDate(taskRepo.findByDesignatedUser(loggedInUser.getId())));
        model.addAttribute("categories", catRepo.findAll());
        model.addAttribute("task", new Task());

        return "tasks/tasks";
    }

    @PostMapping("/tasks/create")
    public String saveTask(@Valid Task task, Errors errors, Model model, @RequestParam String completed_by){
        if(errors.hasErrors()) {
            model.addAttribute("errors", errors);
            model.addAttribute("task", task);
            return "tasks/tasks";
        }
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        task.setCreated_on(new Date());
        task.setCompleted_by(dtService.parseDate(completed_by));
        task.setCreator(loggedInUser);
        task.setStatus(statusRepo.findByStatus("ASSIGNED"));
        taskRepo.save(task);

        return "redirect:/tasks";
    }

    @PostMapping("/tasks/update")
    public String changeTaskStatus(@RequestParam Long taskId) {
        Task task = taskRepo.findById(taskId);
        task.setStatus(statusRepo.findOne(task.getStatus().getId()+1));
        taskRepo.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}")
    public String viewTask(@PathVariable Long id, Model model){
        Task task = taskRepo.findById(id);
        model.addAttribute("task", task);
        List<TaskComment> comments = tcRepo.findByTaskId(task.getId());
        TaskComment newComment = new TaskComment();
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", newComment);
        return "tasks/showTask";
    }

    @GetMapping("/tasks/{id}/edit")
    public String viewEditTaskForm(@PathVariable Long id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepo.findOne(id);
        if(user.getId() == task.getCreator().getId()) {
            model.addAttribute(task);
            return "tasks/editTask";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/edit")
    public String editTask(@ModelAttribute Task task, Errors errors, Model model, @RequestParam String completed_by, @RequestParam Long statusId){
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            model.addAttribute("task", task);
            return "editTask";
        }
        task.setStatus(statusRepo.findOne(statusId));
        task.setCompleted_by(dtService.parseDate(completed_by));
        taskRepo.save(task);
        return "redirect:/tasks/" + task.getId();
    }
    @PostMapping("/tasks/delete")
    public String deleteTask(@RequestParam Long taskId){
        taskRepo.delete(taskId);
        return "redirect:/dashboard";
    }
}
