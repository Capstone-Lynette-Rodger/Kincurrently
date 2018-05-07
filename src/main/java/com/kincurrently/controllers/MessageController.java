package com.kincurrently.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {

    @PostMapping("/send/message")
        public String postMessage(Model model) {

            return "redirect:/dashboard";
        }
}
