package com.openerp.repository;

import com.openerp.entity.Module;
import com.openerp.entity.PayrollConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollConfigurationRepository extends JpaRepository<PayrollConfiguration, Integer> {
    List<PayrollConfiguration> getPayrollConfigurationsByActiveTrueOrderById();
    PayrollConfiguration getPayrollConfigurationById(int id);
}