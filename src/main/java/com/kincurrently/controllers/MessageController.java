package com.kincurrently.controllers;

import com.kincurrently.models.Family;
import com.kincurrently.models.Message;
import com.kincurrently.models.User;
import com.kincurrently.repositories.FamilyRepository;
import com.kincurrently.repositories.MessageRepository;
import com.kincurrently.repositories.UserRepository;
import com.kincurrently.services.DateTimeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private DateTimeService dtService;

    public MessageController(MessageRepository messageRepository, UserRepository userRepository, FamilyRepository familyRepository, DateTimeService dtService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
        this.dtService = dtService;
    }

    @PostMapping("/send/message")
        public String postMessage(Model model, @Valid Message instantMessage, @RequestParam List<User> messageRecipients) {

        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        instantMessage.setUser(current);
        instantMessage.setMessageRecipients(messageRecipients);
        instantMessage.setThread(current.getId());
        instantMessage.setCreated_on(new Date());
        messageRepository.save(instantMessage);

            return "redirect:/dashboard";
        }

    @GetMapping("/messages")
        public String getMessages(Model model) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Family family = familyRepository.findByCode(current.getFamily().getCode());
        model.addAttribute("family", family);
        model.addAttribute("messages", dtService.sortMessagesByDateTime(messageRepository.findAllUsersMessages(current.getId())));

        return"/messages/messages";
        }
}
