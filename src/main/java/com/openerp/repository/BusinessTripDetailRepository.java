package com.openerp.repository;

import com.openerp.entity.BusinessTrip;
import com.openerp.entity.BusinessTripDetail;
import com.openerp.entity.Employee;
import com.openerp.entity.VacationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BusinessTripDetailRepository extends JpaRepository<BusinessTripDetail, Integer> {
    List<BusinessTripDetail> getBusinessTripDetailsByBusinessTrip_Id(int businessTripId);
    BusinessTripDetail getBusinessTripDetailByEmployeeAndBusinessTripDateAndBusinessTrip_Active(Employee employee, Date businessTripDate, boolean active);
}