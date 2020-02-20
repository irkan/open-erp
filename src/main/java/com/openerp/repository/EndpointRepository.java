package com.openerp.repository;

import com.openerp.entity.Endpoint;
import com.openerp.entity.Organization;
import com.openerp.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EndpointRepository extends JpaRepository<Endpoint, Integer>, JpaSpecificationExecutor<Endpoint> {
    Endpoint getEndpointById(Integer id);
}