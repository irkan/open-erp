package com.openerp.repository;

import com.openerp.entity.Period;
import com.openerp.entity.User;
import com.openerp.entity.WebServiceAuthenticator;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PeriodRepository extends JpaRepository<Period, Integer>{
    List<Period> getPeriodsByUser(User user);
    List<Period> getPeriodsByUser_Username(String username);
}