package com.kincurrently.controllers;

import com.kincurrently.models.Event;
import com.kincurrently.models.Task;
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

    public SearchController(EventRepository eventRepository, TaskRepository taskRepository) {
        this.eventRepository = eventRepository;
        this.taskRepository = taskRepository;
    }



    @GetMapping("/search/results")
    public String showSearchResults (Model model, @RequestParam(name="searchTerm") String search,
                                     @RequestParam(value="searchTasks", required = false) String st,
                                     @RequestParam(value="searchEvents", required = false) String se,
                                     @RequestParam(value="searchCategories") String searchCategory) {

        System.out.println("st = " + st);
        System.out.println("se = " + se);
        if (st != null && se != null) {
            model.addAttribute("events", eventRepository.findBySearchTerm(search));
            model.addAttribute("allTasks", taskRepository.findBySearchTerm(search));
            if (!searchCategory.equalsIgnoreCase(" ")) {
                model.addAttribute("searchCat", searchCategory);
            }
            System.out.println("searchCategory = " + searchCategory);
        }

        if (se != null) {
            model.addAttribute("events", eventRepository.findBySearchTerm(search));
            if (!searchCategory.equalsIgnoreCase(" ")) {
                model.addAttribute("searchCat", searchCategory);
            }
            System.out.println("searchCategory = " + searchCategory);
        }
        if (st != null) {
            model.addAttribute("allTasks", taskRepository.findBySearchTerm(search));
            if (!searchCategory.equalsIgnoreCase(" ")) {
                model.addAttribute("searchCat", searchCategory);
            }
            System.out.println("searchCategory = " + searchCategory);
        }

        return "/search/searchResults";
    }
}
