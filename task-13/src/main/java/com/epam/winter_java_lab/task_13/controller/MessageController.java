package com.epam.winter_java_lab.task_13.controller;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
@Api(value = "message resources")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    @ApiOperation(value = "show latest posts", response = Iterable.class)
        public Iterable<Message> greeting() {
            Iterable<Message> messages = messageService.getMessagesByCreatedDateTime();

            return messages;
        }

    @GetMapping("/main")
    @ApiOperation(value = "show messages by filter", response = Iterable.class)
        public Iterable<Message> main(@RequestParam(required = false, defaultValue = "") String filter){
            Iterable<Message> messages =  messageService.findMessagesByFilter(filter);

            return messages;
        }

    @PostMapping("/main")
    @ApiOperation(value = "add message", response = ResponseEntity.class)
        public ResponseEntity add(
                @AuthenticationPrincipal User user,
                @Valid Message message,
                BindingResult bindingResult)  {
            messageService.addMessage(user, message, bindingResult);

            return new ResponseEntity("Product saved successfully", HttpStatus.OK);
        }

    @GetMapping("/user-messages/{user}")
    @ApiOperation(value = "show all messages of the current user", response = Iterable.class)
        public Iterable<Message> userMessages (@PathVariable User user)  {
            Iterable<Message> messages = user.getMessages();

            return messages;
        }

    @PutMapping("/user-messages/{user}")
    @ApiOperation(value = "edit message", response = ResponseEntity.class)
    public ResponseEntity updateMessage(
            @AuthenticationPrincipal User currentUser,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag
    )  {
        messageService.editMessage(currentUser, message, text, tag);

        return new ResponseEntity("Message updated successfully", HttpStatus.OK);
    }

    @GetMapping("/del-user-messages/{user}")
    @ApiOperation(value = "delete message", response = ResponseEntity.class)
    public ResponseEntity deleteMessage (@PathVariable Long user,
                                         @RequestParam("message") Long messageId) {
        messageService.deleteMessageById(messageId);

        return new ResponseEntity("Message deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/sortAsc/{user}")
    @ApiOperation(value = "sort messages by asc", response = Set.class)
        public Set<Message> sortMessagesByAsc (@PathVariable User user){
            Set<Message> sortedMessagesAsc = messageService.getSortMessagesByAsc(user);

            return sortedMessagesAsc;
        }

    @GetMapping("/sortDesc/{user}")
    @ApiOperation(value = "sort messages by desc", response = Set.class)
    public Set<Message> sortMessagesByDesc (@PathVariable User user){
        Set<Message> sortedMessagesDesc = messageService.getSortMessagesByDesc(user);

        return sortedMessagesDesc;
    }

}

