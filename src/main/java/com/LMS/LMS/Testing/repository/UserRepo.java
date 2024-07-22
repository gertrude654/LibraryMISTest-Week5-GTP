package com.LMS.LMS.Testing.repository;

import com.LMS.LMS.Testing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUserName(String username);
}
