package com.openerp.service;


import com.openerp.entity.BusinessTripDetail;
import com.openerp.entity.Employee;
import com.openerp.entity.VacationDetail;
import com.openerp.repository.BusinessTripDetailRepository;
import com.openerp.repository.VacationDetailRepository;

import java.util.Date;

public final class StaticUtils {

    private static VacationDetailRepository vacationDetailRepository;
    private static BusinessTripDetailRepository businessTripDetailRepository;

    public static void setConfig(VacationDetailRepository vacationDetailRepository, BusinessTripDetailRepository businessTripDetailRepository) {
        StaticUtils.vacationDetailRepository = vacationDetailRepository;
        StaticUtils.businessTripDetailRepository = businessTripDetailRepository;
    }

    public static VacationDetail getVacationDetailByEmployeeAndVacationDateAndVacation_Active(Employee employee, Date vacationDate, boolean active) {
        return vacationDetailRepository.getVacationDetailByEmployeeAndVacationDateAndVacation_Active(employee, vacationDate, active);
    }

    public static BusinessTripDetail getBusinessTripDetailByEmployeeAndBusinessTripDateAndBusinessTrip_Active(Employee employee, Date businessTripDate, boolean active) {
        return businessTripDetailRepository.getBusinessTripDetailByEmployeeAndBusinessTripDateAndBusinessTrip_Active(employee, businessTripDate, active);
    }
}