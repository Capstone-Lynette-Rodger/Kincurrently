package com.kincurrently.controllers;

import com.kincurrently.models.Event;
import com.kincurrently.models.Task;
import com.kincurrently.repositories.CategoryRepository;
import com.kincurrently.repositories.EventRepository;
import com.kincurrently.repositories.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SearchController {

    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;
    private final CategoryRepository catRepo;

    public SearchController(EventRepository eventRepository, TaskRepository taskRepository, CategoryRepository catRepo) {
        this.eventRepository = eventRepository;
        this.taskRepository = taskRepository;
        this.catRepo = catRepo;
    }



    @GetMapping("/search/results")
    public String showSearchResults (Model model, @RequestParam(name="searchTerm") String search,
                                     @RequestParam(value="searchTasks", required = false) String st,
                                     @RequestParam(value="searchEvents", required = false) String se,
                                     @RequestParam(value="searchCategories", required = false) String searchCategory) {
        if ((st != null && se != null) || (st == null && se == null)) {
            if (searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("events", eventRepository.findBySearchTerm(search));
                model.addAttribute("allTasks", taskRepository.findBySearchTerm(search));
            } else if (!searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("events", eventRepository.findByCategories(searchCategory, search));
                model.addAttribute("allTasks", taskRepository.findByCategories(searchCategory, search));
            }
        }

        if (se != null) {
            if (searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("events", eventRepository.findBySearchTerm(search));
            } else if (!searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("events", eventRepository.findByCategories(searchCategory,search));
            }
        }
        if (st != null) {
            if (searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("allTasks", taskRepository.findBySearchTerm(search));
            } else if (!searchCategory.equalsIgnoreCase("ALL")) {
                model.addAttribute("allTasks", taskRepository.findByCategories(searchCategory, search));
            }
        }
        model.addAttribute("categories", catRepo.findAll());
        model.addAttribute("searchTerm", search);
        return "/search/searchResults";
    }
}
