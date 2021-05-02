package com.epam.winter_java_lab.task_13.service.Impl;

import com.epam.winter_java_lab.task_13.service.MessageService;
import com.epam.winter_java_lab.task_13.utils.ErrorUtil;
import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepo messageRepo;

    @Autowired
    public MessageServiceImpl(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public Iterable<Message> findAllMessages(){
        Iterable<Message> messages = messageRepo.findAll();

        return messages;
    }

    @Override
    public void saveMessage(Message message){
        messageRepo.save(message);
    }

    @Override
    public Iterable<Message> findMessagesByFilter(String filter){
        Iterable<Message> messages;
        if(filter != null && !filter.isEmpty()){
            messages = messageRepo.findByTag(filter);
        }else {
            messages = findAllMessages();
        }

        return messages;
    }

    @Override
    public Iterable<Message> getMessagesByCreatedDateTime(){
        Iterable<Message> messages = messageRepo.getMessagesByCreatedDateTime();

        return messages;
    }

    @Override
    public void deleteMessageById(Long messageId){
        messageRepo.deleteById(messageId);
    }

    @Override
    public void editMessage(User currentUser, Message message, String text, String tag) {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }

            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }
            message.setUpdatedDateTime(System.currentTimeMillis());
            saveMessage(message);
        }
    }

    @Override
    public void addMessage(User user, Message message, BindingResult bindingResult) {
        message.setAuthor(user);

        if(bindingResult.hasErrors()){
            Map<String, String> errorsMap = ErrorUtil.getErrors(bindingResult);
        }else {
            message.setCreatedDateTime(System.currentTimeMillis());
            saveMessage(message);
        }
    }

    @Override
    public Set<Message> getSortMessagesByAsc(User user) {
        Set<Message> sortedMessagesAsc = new TreeSet<>(Comparator.comparing(Message::getCreatedDateTime));
        sortedMessagesAsc.addAll(user.getMessages());

        return sortedMessagesAsc;
    }

    @Override
    public Set<Message> getSortMessagesByDesc(User user) {
        Set<Message> sortedMessagesDesc = new TreeSet<>((o2, o1) -> o1.getCreatedDateTime().compareTo(o2.getCreatedDateTime()));
        sortedMessagesDesc.addAll(user.getMessages());

        return sortedMessagesDesc;
    }

}
