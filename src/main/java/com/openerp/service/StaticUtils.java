package com.openerp.service;


import com.openerp.entity.*;
import com.openerp.repository.BusinessTripDetailRepository;
import com.openerp.repository.IllnessDetailRepository;
import com.openerp.repository.NonWorkingDayRepository;
import com.openerp.repository.VacationDetailRepository;

import java.util.Date;

public final class StaticUtils {

    private static VacationDetailRepository vacationDetailRepository;
    private static BusinessTripDetailRepository businessTripDetailRepository;
    private static IllnessDetailRepository illnessDetailRepository;
    private static NonWorkingDayRepository nonWorkingDayRepository;

    public static void setConfig(VacationDetailRepository vacationDetailRepository, BusinessTripDetailRepository businessTripDetailRepository, IllnessDetailRepository illnessDetailRepository, NonWorkingDayRepository nonWorkingDayRepository) {
        StaticUtils.vacationDetailRepository = vacationDetailRepository;
        StaticUtils.businessTripDetailRepository = businessTripDetailRepository;
        StaticUtils.illnessDetailRepository = illnessDetailRepository;
        StaticUtils.nonWorkingDayRepository = nonWorkingDayRepository;
    }

    public static VacationDetail getVacationDetailByEmployeeAndVacationDateAndVacation_Active(Employee employee, Date vacationDate, boolean active) {
        return vacationDetailRepository.getVacationDetailByEmployeeAndVacationDateAndVacation_Active(employee, vacationDate, active);
    }

    public static BusinessTripDetail getBusinessTripDetailByEmployeeAndBusinessTripDateAndBusinessTrip_Active(Employee employee, Date businessTripDate, boolean active) {
        return businessTripDetailRepository.getBusinessTripDetailByEmployeeAndBusinessTripDateAndBusinessTrip_Active(employee, businessTripDate, active);
    }

    public static IllnessDetail getIllnessDetailByEmployeeAndIllnessDateAndIllness_Active(Employee employee, Date illnessDate, boolean active) {
        return illnessDetailRepository.getIllnessDetailByEmployeeAndIllnessDateAndIllness_Active(employee, illnessDate, active);
    }

    public static NonWorkingDay getNonWorkingDayByNonWorkingDateAndActiveTrue(Date date) {
        return nonWorkingDayRepository.getNonWorkingDayByNonWorkingDateAndActiveTrue(date);
    }
}