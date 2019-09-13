package com.openerp.repository;

import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    List<Organization> findOrganizationsByOrganizationIsNull();
}