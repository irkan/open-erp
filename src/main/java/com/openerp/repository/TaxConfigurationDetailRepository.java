package com.openerp.repository;

import com.openerp.entity.Log;
import com.openerp.entity.TaxConfigurationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaxConfigurationDetailRepository extends JpaRepository<TaxConfigurationDetail, Integer>, JpaSpecificationExecutor<TaxConfigurationDetail> {
    TaxConfigurationDetail getTaxConfigurationDetailRepositoryById(Integer id);
    List<TaxConfigurationDetail> getTaxConfigurationDetailsByActiveTrue();
}