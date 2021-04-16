package com.epam.winter_java_lab.task_13.repos;

import com.epam.winter_java_lab.task_13.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}



