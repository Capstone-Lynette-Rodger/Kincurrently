package com.kincurrently.controllers;

import com.kincurrently.models.Family;
import com.kincurrently.models.User;
import com.kincurrently.models.UserRole;
import com.kincurrently.repositories.FamilyRepository;
import com.kincurrently.repositories.Roles;
import com.kincurrently.repositories.UserRepository;
import com.kincurrently.services.DateTimeService;
import com.kincurrently.services.UserDetailsLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private UserDetailsLoader userService;
    private Roles rolesRepo;
    private FamilyRepository familyRepo;
    private DateTimeService dtService;

    public UserController(UserRepository userRepo, PasswordEncoder passwordEncoder, UserDetailsLoader userService, Roles rolesRepo, FamilyRepository familyRepo, DateTimeService dtSservice) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.rolesRepo = rolesRepo;
        this.familyRepo = familyRepo;
        this.dtService = dtSservice;
    }

    @GetMapping("/")
    public String showMainPage() {
        return "index";
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("family", new Family());
        return "users/register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid User user, Errors userErrors, Model model, @Valid Family family, Errors familyErrors,
                           @RequestParam String verifyPassword, @RequestParam String birthdate, @RequestParam(required=false) boolean joinFamily) {
        //validating user registration for email, username, and password validation
        userErrors = userService.checkRegistration(user, userErrors);
        if(!user.getPassword().equals(verifyPassword)) {
            userErrors.rejectValue(
                    "password",
                    "user.password",
                    "Passwords do not match."
            );
        }
        //needed here to check if joinFamily is checked, do not show error
        if(!joinFamily && family.getName().trim().equals("")) {
            familyErrors.rejectValue(
                    "name",
                    "family.name",
                    "Family name cannot be blank."
            );
        }
        if(userErrors.hasErrors()) {
            model.addAttribute("errors", userErrors);
            model.addAttribute("user", user);
            return "users/register";
        }
//      Need to parse birthdate string into date object
        user.setBirthdate(dtService.parseDate(birthdate));
//      Need to encode password before storing in database
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setFamily(family);
//      Save family, user, and user role
        if(!joinFamily) {
            familyRepo.save(family);
        } else {
            family = familyRepo.findByCode(family.getCode());
            user.setFamily(family);
        }
        if(user.getTitle().trim().equals("")){
            user.setTitle(null);
        }
        userRepo.save(user);
//      Need to assign all new users as having a 'Parent' role
        rolesRepo.save(new UserRole(user.getId(), "PARENT"));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("familyMembers", user.getFamily().getUsers());
        return "users/dashboard";
    }

    @GetMapping("/register/child")
    public String showRegisterChildForm(Model model){
        model.addAttribute("user", new User());
        return "users/register-child";
    }
    @PostMapping("/register/child")
    public String saveChildUser(@Valid User user, Errors userErrors, Model model, @RequestParam String verifyPassword, @RequestParam String birthdate) {
        //validating user registration for email, username, and password validation
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userErrors = userService.checkRegistration(user, userErrors);
        if(!user.getPassword().equals(verifyPassword)) {
            userErrors.rejectValue(
                    "password",
                    "user.password",
                    "Passwords do not match."
            );
        }
        if(userErrors.hasErrors()) {
            model.addAttribute("errors", userErrors);
            model.addAttribute("user", user);
            return "users/register-child";
        }
//      Need to parse birthdate string into date object
        user.setBirthdate(dtService.parseDate(birthdate));
//      Need to encode password before storing in database
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setFamily(loggedInUser.getFamily());
        if(user.getTitle().trim().equals("")){
            user.setTitle(null);
        }
//      Save child user and user role
        userRepo.save(user);
//      Need to assign all new users as having a 'Parent' role
        rolesRepo.save(new UserRole(user.getId(), "CHILD"));
        return "redirect:/dashboard";
    }
}
