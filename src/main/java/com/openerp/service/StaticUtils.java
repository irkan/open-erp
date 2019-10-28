package com.openerp.service;


import com.openerp.entity.*;
import com.openerp.repository.*;

import java.util.Date;
import java.util.List;

public final class StaticUtils {

    private static VacationDetailRepository vacationDetailRepository;
    private static BusinessTripDetailRepository businessTripDetailRepository;
    private static IllnessDetailRepository illnessDetailRepository;
    private static NonWorkingDayRepository nonWorkingDayRepository;
    private static EmployeeRestDayRepository employeeRestDayRepository;

    public static void setConfig(VacationDetailRepository vacationDetailRepository, BusinessTripDetailRepository businessTripDetailRepository, IllnessDetailRepository illnessDetailRepository, NonWorkingDayRepository nonWorkingDayRepository, EmployeeRestDayRepository employeeRestDayRepository) {
        StaticUtils.vacationDetailRepository = vacationDetailRepository;
        StaticUtils.businessTripDetailRepository = businessTripDetailRepository;
        StaticUtils.illnessDetailRepository = illnessDetailRepository;
        StaticUtils.nonWorkingDayRepository = nonWorkingDayRepository;
        StaticUtils.employeeRestDayRepository = employeeRestDayRepository;
    }

    public static List<EmployeeRestDay> getEmployeeRestDaysByEmployee(Employee employee) {
        return employeeRestDayRepository.getEmployeeRestDaysByEmployee(employee);
    }

    public static EmployeeRestDay getEmployeeRestDayByEmployeeAndDay(Employee employee, Date date) {
        int day = date.getDay();
        List<EmployeeRestDay> employeeRestDays = employeeRestDayRepository.getEmployeeRestDaysByEmployeeAndDay(employee, day);
        if(employeeRestDays.size()>0){
            return employeeRestDays.get(0);
        }
        return null;
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

    public static List<NonWorkingDay> getNonWorkingDaysByNonWorkingDateAndActiveTrue(Date date) {
        return nonWorkingDayRepository.getNonWorkingDaysByNonWorkingDateAndActiveTrue(date);
    }
}