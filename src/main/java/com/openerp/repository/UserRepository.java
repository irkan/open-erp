package com.openerp.repository;

import com.openerp.entity.Organization;
import com.openerp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPasswordAndActiveTrue(String username, String password);
    User getUserByActiveTrueAndId(int id);
    List<User> getUsersByActiveTrue();
    List<User> getUsersByActiveTrueAndEmployee_Organization(Organization organization);
}