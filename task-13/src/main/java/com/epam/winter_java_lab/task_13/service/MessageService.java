package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.domain.User;
import org.springframework.validation.BindingResult;

import java.util.Set;

public interface MessageService {
    Iterable<Message> findAllMessages();

    void saveMessage(Message message);

    Iterable<Message> findMessagesByFilter(String filter);

    Iterable<Message> getMessagesByCreatedDateTime();

    void deleteMessageById(Long messageId);

    void editMessage(User currentUser, Message message, String text, String tag);

    void addMessage(User user, Message message, BindingResult bindingResult);

    Set<Message> getSortMessagesByAsc(User user);

    Set<Message> getSortMessagesByDesc(User user);

}
