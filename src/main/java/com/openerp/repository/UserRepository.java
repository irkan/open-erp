package com.openerp.repository;

import com.openerp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPasswordAndActiveTrue(String username, String password);
}