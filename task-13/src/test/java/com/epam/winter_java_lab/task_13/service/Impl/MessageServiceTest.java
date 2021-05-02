package com.epam.winter_java_lab.task_13.service.Impl;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.repos.MessageRepo;
import com.epam.winter_java_lab.task_13.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @MockBean
    private MessageRepo messageRepo;

    @Test
    public void findAllMessagesTest(){
        when(messageRepo.findAll()).thenReturn(Stream.of(new Message(), new Message()).collect(Collectors.toList()));
        List<Message> messages = (List<Message>) messageService.findAllMessages();
        assertEquals(2, messages.size());
        assertNotNull(messages);
    }

    @Test
    public void addMessageTest() {
        Message message = new Message();
        messageService.saveMessage(message);

        Mockito.verify(messageRepo, Mockito.times(1)).save(message);
    }

    @Test
    public void findByTagTest(){
        Message message1 = new Message();
        message1.setTag("hello");

        Message message2 = new Message();
        message2.setTag("hi");

        when(messageRepo.findByTag("hello")).thenReturn(Stream.of(message1, message2).collect(Collectors.toList()));
        List<Message> messages = (List<Message>) messageService.findMessagesByFilter("hello");

        assertEquals(message1.getTag(), messages.get(0).getTag());
    }

    @Test
    public void getMessagesByCreatedDateTimeTest(){
        Message message1 = new Message();
        message1.setCreatedDateTime(1001L);

        Message message2 = new Message();
        message2.setCreatedDateTime(1000L);

        Message message3 = new Message();
        message3.setCreatedDateTime(1002L);

        when(messageRepo.getMessagesByCreatedDateTime()).thenReturn(Stream.of(message1, message2, message3).collect(Collectors.toList()));
        List<Message> messages = (List<Message>) messageService.getMessagesByCreatedDateTime();

        LocalDateTime createdDateTime1 = messages.get(0).getCreatedDateTime();
        assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(1001L), ZoneId.systemDefault()), createdDateTime1);

        LocalDateTime createdDateTime2 = messages.get(1).getCreatedDateTime();
        assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(1000L), ZoneId.systemDefault()), createdDateTime2);

        LocalDateTime createdDateTime3 = messages.get(2).getCreatedDateTime();
        assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(1002L), ZoneId.systemDefault()), createdDateTime3);
    }

    @Test
    public void deleteMessageByIdTest(){

        messageService.deleteMessageById((long) 1);

        verify(messageRepo, times(1)).deleteById((long) 1);
    }

    @Test
    public void editMessageTest(){
        User user = new User();
        Message message = new Message();
        message.setTag("old");
        message.setAuthor(user);

        messageService.editMessage(user, message, "HI", "new");
        assertEquals("new", message.getTag());
    }

    @Test
    public void editMessageErrorTest(){
        User user = new User();
        Message message = new Message();
        message.setTag("old");

        assertThrows(NullPointerException.class, () ->{
            messageService.editMessage(user, message, "HI", "new");
        });
    }

    @Test
    public void getSortMessagesByAscTest(){
        User user = new User();
        Set<Message> messages = new HashSet<>();

        Message message1 = new Message();
        message1.setCreatedDateTime(1005L);

        Message message2 = new Message();
        message2.setCreatedDateTime(1001L);

        Message message3 = new Message();
        message3.setCreatedDateTime(1002L);

        Message message4 = new Message();
        message4.setCreatedDateTime(1003L);

        Message message5 = new Message();
        message5.setCreatedDateTime(1004L);

        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        messages.add(message4);
        messages.add(message5);

        user.setMessages(messages);
        TreeSet<Message> sortedSet = (TreeSet<Message>) messageService.getSortMessagesByAsc(user);

        assertEquals(message1, sortedSet.pollLast());
        assertEquals(message5, sortedSet.pollLast());
        assertEquals(message4, sortedSet.pollLast());
        assertEquals(message3, sortedSet.pollLast());
        assertEquals(message2, sortedSet.pollLast());
        assertEquals(0, sortedSet.size());
    }

    @Test
    public void getSortMessagesByDescTest(){
        User user = new User();
        Set<Message> messages = new HashSet<>();

        Message message1 = new Message();
        message1.setCreatedDateTime(1005L);

        Message message2 = new Message();
        message2.setCreatedDateTime(1001L);

        Message message3 = new Message();
        message3.setCreatedDateTime(1002L);

        Message message4 = new Message();
        message4.setCreatedDateTime(1003L);

        Message message5 = new Message();
        message5.setCreatedDateTime(1004L);

        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        messages.add(message4);
        messages.add(message5);

        user.setMessages(messages);
        TreeSet<Message> sortedSet = (TreeSet<Message>) messageService.getSortMessagesByDesc(user);

        assertEquals(message2, sortedSet.pollLast());
        assertEquals(message3, sortedSet.pollLast());
        assertEquals(message4, sortedSet.pollLast());
        assertEquals(message5, sortedSet.pollLast());
        assertEquals(message1, sortedSet.pollLast());
        assertEquals(0, sortedSet.size());
    }

}
