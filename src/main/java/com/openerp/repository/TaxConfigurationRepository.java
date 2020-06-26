package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.Organization;
import com.openerp.entity.TaxConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaxConfigurationRepository extends JpaRepository<TaxConfiguration, Integer>, JpaSpecificationExecutor<TaxConfiguration> {
    TaxConfiguration getTaxConfigurationById(Integer id);
    List<TaxConfiguration> getTaxConfigurationsByActiveTrue();
    List<TaxConfiguration> getTaxConfigurationsByActiveTrueAndOrganization(Organization organization);
}