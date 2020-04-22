package com.openerp.repository;

import com.openerp.entity.ApproverException;
import com.openerp.entity.Organization;
import com.openerp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApproverExceptionRepository extends JpaRepository<ApproverException, Integer> {
    ApproverException getApproverExceptionById(Integer id);
    List<ApproverException> getApproverExceptionsByOrganization(Organization organization);
    List<ApproverException> getApproverExceptionsByUser(User user);
}