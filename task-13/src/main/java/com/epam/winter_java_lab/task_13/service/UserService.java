package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean addUser(User user);

    List<User> findAll();

    void saveUser(User user, String username, Map<String, String> form);

}
