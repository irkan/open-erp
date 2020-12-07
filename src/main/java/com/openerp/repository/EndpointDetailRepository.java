package com.openerp.repository;

import com.openerp.entity.Endpoint;
import com.openerp.entity.EndpointDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EndpointDetailRepository extends JpaRepository<EndpointDetail, Integer>, JpaSpecificationExecutor<EndpointDetail> {
}