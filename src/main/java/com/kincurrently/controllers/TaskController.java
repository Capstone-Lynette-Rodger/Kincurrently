package com.kincurrently.controllers;

import com.kincurrently.models.*;
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
    private UserRepository userRepo;

    public TaskController(TaskRepository taskRepo, DateTimeService dtService, StatusRepository statusRepo, TaskCommentRepository tcRepo, CategoryRepository catRepo, FamilyRepository familyRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.dtService = dtService;
        this.statusRepo = statusRepo;
        this.tcRepo = tcRepo;
        this.catRepo = catRepo;
        this.familyRepo = familyRepo;
        this.userRepo = userRepo;
    }
    //Shows all the tasks for parents and tasks assigned for each user
    @GetMapping("/tasks")
    public String showTasks(Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findById(loggedInUser.getId());
        Family family = familyRepo.findByCode(user.getFamily().getCode());
        model.addAttribute("instantMessage", new Message());
        model.addAttribute("user", user);
        model.addAttribute("family", family);
        model.addAttribute("allTasks", dtService.sortTasksByDate((List<Task>)taskRepo.findAll()));
        model.addAttribute("myTasks", dtService.sortTasksByDate(taskRepo.findByDesignatedUser(loggedInUser.getId())));
        return "tasks/tasks";
    }

    @GetMapping("/tasks/create")
    public String showCreateTaskForm (Model model) {
        model.addAttribute("task", new Task());
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("family", familyRepo.findOne(loggedInUser.getFamily().getId()));
        model.addAttribute("categories", catRepo.findAll());
        return "/tasks/createTask";
    }

    @PostMapping("/tasks/create")
    public String saveTask(@Valid Task task, Errors errors, Model model, @RequestParam String completed_by){
        if(errors.hasErrors()) {
            model.addAttribute("errors", errors);
            model.addAttribute("task", task);
            return "tasks/createTask";
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
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepo.findById(id);
        User user = userRepo.findById(loggedInUser.getId());
        Family family = familyRepo.findByCode(user.getFamily().getCode());
        model.addAttribute("instantMessage", new Message());
        model.addAttribute("user", user);
        model.addAttribute("family", family);
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
            model.addAttribute("family", familyRepo.findOne(user.getFamily().getId()));
            Iterable<Category> categories = catRepo.findAll();
            model.addAttribute("categories", categories);
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
