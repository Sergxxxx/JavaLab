package com.epam.winter_java_lab.task_13.repos;

import com.epam.winter_java_lab.task_13.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAll();
}



