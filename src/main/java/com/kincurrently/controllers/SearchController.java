package com.kincurrently.controllers;

import com.kincurrently.models.Event;
import com.kincurrently.models.Task;
import com.kincurrently.models.User;
import com.kincurrently.models.UserRole;
import com.kincurrently.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class SearchController {

    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;
    private final CategoryRepository catRepo;
    private final FamilyRepository familyRepo;
    private final Roles rolesRepo;

    public SearchController(EventRepository eventRepository, TaskRepository taskRepository, CategoryRepository catRepo, FamilyRepository familyRepo, Roles rolesRepo) {
        this.eventRepository = eventRepository;
        this.taskRepository = taskRepository;
        this.catRepo = catRepo;
        this.familyRepo = familyRepo;
        this.rolesRepo = rolesRepo;
    }



    @GetMapping("/search/results")
    public String showSearchResults (Model model, @RequestParam(name="searchTerm") String search,
                                     @RequestParam(value="searchTasks", required = false) String st,
                                     @RequestParam(value="searchEvents", required = false) String se,
                                     @RequestParam(value="searchCategories", required = false) String searchCategory) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ((st != null && se != null) || (st == null && se == null)) {
            if (searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("events", eventRepository.findBySearchTerm(search, familyRepo.findOne(loggedInUser.getFamily().getId())));
                model.addAttribute("allTasks", taskRepository.findBySearchTerm(search, familyRepo.findOne(loggedInUser.getFamily().getId())));
            } else if (!searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("events", eventRepository.findByCategories(searchCategory, search, familyRepo.findOne(loggedInUser.getFamily().getId())));
                model.addAttribute("allTasks", taskRepository.findByCategories(searchCategory, search, familyRepo.findOne(loggedInUser.getFamily().getId())));
            }
        }

        if (se != null) {
            if (searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("events", eventRepository.findBySearchTerm(search, familyRepo.findOne(loggedInUser.getFamily().getId())));
            } else if (!searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("events", eventRepository.findByCategories(searchCategory,search, familyRepo.findOne(loggedInUser.getFamily().getId())));
            }
        }
        if (st != null) {
            if (searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("allTasks", taskRepository.findBySearchTerm(search, familyRepo.findOne(loggedInUser.getFamily().getId())));
            } else if (!searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("allTasks", taskRepository.findByCategories(searchCategory, search, familyRepo.findOne(loggedInUser.getFamily().getId())));
            }
        }
        List<Long> childUserIds = rolesRepo.findByRole("CHILD").stream().map(UserRole::getUserId).collect(Collectors.toList());
        model.addAttribute("childUsers", childUserIds);
        model.addAttribute("categories", catRepo.findAll());
        model.addAttribute("searchTerm", search);
        return "/search/searchResults";
    }
}
