package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.Organization;
import com.openerp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUsernameAndPasswordAndActiveTrue(String username, String password);
    List<User> getUsersByActiveTrueAndUsernameAndPassword(String username, String password);
    User getUserByActiveTrueAndId(int id);
    User getUserById(Integer id);
    List<User> getUsersByActiveTrue();
    List<User> getUsersByActiveTrueAndEmployee_Organization(Organization organization);
    List<User> getUsersByEmployeeAndActiveTrue(Employee employee);
    List<User> getUsersByUsernameAndActiveTrue(String username);
}