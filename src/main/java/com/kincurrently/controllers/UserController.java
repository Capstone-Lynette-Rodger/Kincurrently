package com.kincurrently.controllers;

import com.kincurrently.models.Family;
import com.kincurrently.models.User;
import com.kincurrently.models.UserRole;
import com.kincurrently.repositories.*;
import com.kincurrently.services.DateTimeService;
import com.kincurrently.services.UserDetailsLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    private EventRepository eventRepository;
    private TaskRepository taskRepository;

    public UserController(UserRepository userRepo, PasswordEncoder passwordEncoder,
                          UserDetailsLoader userService, Roles rolesRepo, FamilyRepository familyRepo,
                          DateTimeService dtService, EventRepository eventRepository, TaskRepository taskRepository) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.rolesRepo = rolesRepo;
        this.familyRepo = familyRepo;
        this.dtService = dtService;
        this.eventRepository = eventRepository;
        this.taskRepository = taskRepository;
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
    public String saveUser(@Valid User user, Errors userErrors, Model model, @Valid Family family, Errors familyErrors, @RequestParam String chooseRole,
                           @RequestParam String verifyPassword, @RequestParam String birthdate, @RequestParam(required=false) boolean joinFamily) {
        userErrors = userService.checkRegistration(user, userErrors);
        if(!user.getPassword().equals(verifyPassword)) {
            userErrors.rejectValue(
                    "password",
                    "user.password",
                    "Passwords do not match."
            );
        }
        if(!joinFamily && family.getName().trim().equals("")) {
            familyErrors.rejectValue(
                    "name",
                    "family.name",
                    "Family name cannot be blank."
            );
        }
        if(familyRepo.findByCode(family.getCode()) != null && !joinFamily) {
            familyErrors.rejectValue(
                    "code",
                    "family.code",
                    "This code is already in use by another family."
            );
        }
        if(userErrors.hasErrors() || familyErrors.hasErrors()) {
            model.addAttribute("errors", userErrors);
            model.addAttribute("errors", familyErrors);
            model.addAttribute("user", user);
            model.addAttribute("family", family);
            return "users/register";
        }
        user.setBirthdate(dtService.parseDate(birthdate));
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setFamily(family);
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
        rolesRepo.save(new UserRole(user.getId(), chooseRole));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findById(loggedInUser.getId());
        Family family = familyRepo.findByCode(user.getFamily().getCode());
        model.addAttribute("user", user);
        model.addAttribute("family", family);
        model.addAttribute("events", eventRepository.findByFamilyId(family.getId()) );
        model.addAttribute("tasksCreated", dtService.sortTasksByDate(taskRepository.findByCreatedUser(loggedInUser.getId())));
        model.addAttribute("tasksDesignated", dtService.sortTasksByDate(taskRepository.findByDesignatedUser(loggedInUser.getId())));
        return "users/dashboard";
    }

    @GetMapping("/child/register")
    public String showRegisterChildForm(Model model){
        model.addAttribute("user", new User());
        return "users/register-child";
    }

    @PostMapping("/register/child")
    public String saveChildUser(@Valid User user, Errors userErrors, Model model, @RequestParam String verifyPassword, @RequestParam String birthdate) {
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
        user.setBirthdate(dtService.parseDate(birthdate));
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setFamily(loggedInUser.getFamily());
        if(user.getTitle().trim().equals("")){
            user.setTitle(null);
        }
        userRepo.save(user);
        rolesRepo.save(new UserRole(user.getId(), "CHILD"));
        return "redirect:/dashboard";
    }

    @GetMapping("/profile/update")
    public String updateProfileForm(Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userRepo.findById(loggedInUser.getId()));
        model.addAttribute("userNewPass", userRepo.findById(loggedInUser.getId()));
        model.addAttribute("family", familyRepo.findByCode(loggedInUser.getFamily().getCode()));
        return "users/edit";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid User user, Errors userErrors, Model model, @Valid Family family, Errors familyErrors,
                                @RequestParam String password, @RequestParam String birthdate){
        User dbUser = userRepo.findById(user.getId());
        Family dbFam = familyRepo.findOne(family.getId());
        dbFam.setName(family.getName());
        userErrors = userService.checkUpdate(user, userErrors, dbUser);
        user.setBirthdate(dtService.parseDate(birthdate));
        user.setFamily(dbFam);
        if(!passwordEncoder.matches(password, dbUser.getPassword())) {
            userErrors.rejectValue(
                    "password",
                    "user.password",
                    "Incorrect password. Could not update profile."
            );
        }
        if(userErrors.hasErrors()) {
            model.addAttribute("errors", userErrors);
            model.addAttribute("user", user);
            model.addAttribute("family", dbFam);
            return "users/edit";
        }
        if(user.getTitle().trim().equals("")){
            user.setTitle(null);
        }
        user.setPassword(dbUser.getPassword());
        userRepo.save(user);
        familyRepo.save(dbFam);
        return "redirect:/dashboard";
    }

    @PostMapping("/password/update")
    public String updatePassword(@Valid User userNewPass, Errors errors, Model model, @RequestParam String newPassword, @RequestParam String newPassVerify){
        User dbUser = userRepo.findById(userNewPass.getId());
//        errors = userService.checkPassword(dbUser, errors);
        if(!passwordEncoder.matches(userNewPass.getPassword(), dbUser.getPassword())) {
            errors.rejectValue(
                    "password",
                    "newUserPass.password.user.password",
                    "Incorrect password. Could not update profile."
            );
        }
        if(!newPassword.equals(newPassVerify)) {
            errors.rejectValue(
                    "password",
                    "newUserPass.password.user.password",
                    "Passwords do not match."
            );
        }
        if(errors.hasErrors()) {
            model.addAttribute("errors", errors);
            model.addAttribute("family", familyRepo.findOne(dbUser.getFamily().getId()));
            model.addAttribute("user", dbUser);
            model.addAttribute("userNewPass", userNewPass);
            return "users/edit";
        }
        dbUser.setPassword(passwordEncoder.encode((newPassword)));
        userRepo.save(dbUser);
        return "redirect:/dashboard";
    }


    @PostMapping("/update/profile/delete")
    public String deleteUser(@RequestParam Long id){
        User user = userRepo.findById(id);
        SecurityContextHolder.clearContext();
        userRepo.delete(user);
        return "redirect:/";
    }
}
