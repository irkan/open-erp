package com.openerp.repository;

import com.openerp.entity.Account;
import com.openerp.entity.BusinessTrip;
import com.openerp.entity.Organization;
import com.openerp.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BusinessTripRepository extends JpaRepository<BusinessTrip, Integer>, JpaSpecificationExecutor<BusinessTrip> {
    List<BusinessTrip> getBusinessTripsByActiveTrue();
    BusinessTrip getBusinessTripById(int id);
}