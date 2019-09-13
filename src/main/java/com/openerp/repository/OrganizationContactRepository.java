package com.openerp.repository;

import com.openerp.entity.OrganizationContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrganizationContactRepository extends JpaRepository<OrganizationContact, Integer> {
}