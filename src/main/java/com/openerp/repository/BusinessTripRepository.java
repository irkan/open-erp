package com.openerp.repository;

import com.openerp.entity.Account;
import com.openerp.entity.BusinessTrip;
import com.openerp.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessTripRepository extends JpaRepository<BusinessTrip, Integer> {
    List<BusinessTrip> getBusinessTripsByActiveTrue();
    BusinessTrip getBusinessTripById(int id);
}