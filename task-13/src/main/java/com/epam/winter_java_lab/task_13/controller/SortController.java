package com.epam.winter_java_lab.task_13.controller;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SortController {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/sortAsc/{user}")
    public String sortAsc(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ){
        Iterable<Message> messages =  messageRepo.findByOrderByCreatedDateTimeAsc();
        model.addAttribute("messages", messages);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userMessages";
    }

    @GetMapping("/sortDesc/{user}")
    public String sortDesc(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model
    ){
        Iterable<Message> messages =  messageRepo.findByOrderByCreatedDateTimeDesc();
        model.addAttribute("messages", messages);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userMessages";
    }
}
