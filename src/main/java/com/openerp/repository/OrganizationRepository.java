package com.openerp.repository;

import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    List<Organization> findOrganizationsByOrganizationIsNullAndActiveTrue();
    List<Organization> getOrganizationsByActiveTrue();
    Organization getOrganizationByIdAndActiveTrue(int id);
    Organization getOrganizationById(Integer id);
    List<Organization> getOrganizationsByActiveTrueAndOrganization(Organization organization);
    List<Organization> getOrganizationsByActiveTrueAndType_Attr1(String attr1);
    List<Organization> getOrganizationsByName(String name);
}