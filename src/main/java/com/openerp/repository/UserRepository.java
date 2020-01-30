package com.openerp.repository;

import com.openerp.entity.Organization;
import com.openerp.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUsernameAndPasswordAndActiveTrue(String username, String password);
    User getUserByActiveTrueAndId(int id);
    List<User> getUsersByActiveTrue();
    List<User> getUsersByActiveTrueAndEmployee_Organization(Organization organization);
}