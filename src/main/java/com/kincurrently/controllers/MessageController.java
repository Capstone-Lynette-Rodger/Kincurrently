package com.kincurrently.controllers;

import com.kincurrently.models.Message;
import com.kincurrently.models.User;
import com.kincurrently.repositories.MessageRepository;
import com.kincurrently.repositories.UserRepository;
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

    public MessageController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/send/message")
        public String postMessage(Model model, @Valid Message instantMessage, @RequestParam List<User> messageRecipients) {

        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        instantMessage.setUser(current);
        instantMessage.setMessageRecipients(messageRecipients);
        instantMessage.setCreated_on(new Date());
        System.out.println("current = " + current);
        System.out.println("messageRecipients = " + messageRecipients);
        messageRepository.save(instantMessage);

            return "redirect:/dashboard";
        }

        @GetMapping("/messages")
    public String getMessages(Model model) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("messages", messageRepository.findAllUsersMessages(current.getId()));

        return"/messages/messages";
        }
}
