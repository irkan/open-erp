package com.openerp.repository;

import com.openerp.entity.ApproverException;
import com.openerp.entity.Organization;
import com.openerp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ApproverExceptionRepository extends JpaRepository<ApproverException, Integer> {
    ApproverException getApproverExceptionById(Integer id);
    List<ApproverException> getApproverExceptionsByActiveTrueAndUser(User user);
    List<ApproverException> getApproverExceptionsByActiveTrueOrderByPermissionDateToDesc();
    List<ApproverException> getApproverExceptionsByUserAndActiveTrueAndPermissionDateFromLessThanEqualAndPermissionDateToGreaterThanEqualOrderByPermissionDateToDesc(User user, Date from, Date to);
}