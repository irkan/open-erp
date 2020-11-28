package com.openerp.controller;

import com.openerp.dummy.DummyContact;
import com.openerp.dummy.DummyEmployee;
import com.openerp.dummy.DummyPerson;
import com.openerp.dummy.DummyUtil;
import com.openerp.entity.*;
import com.openerp.util.DateUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dummy")
public class DummyController extends SkeletonController {
    @GetMapping("/service-regulator")
    public String serviceRegulator() throws Exception {
        List<Sales> salesList = salesRepository.getSalesByActiveTrueAndApproveTrueAndSaledFalseAndServiceFalse();
        List<Dictionary> serviceNotifications = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("service-notification");
        int i=1;
        for(Sales sales: salesList){
            try{
                if(sales.getServiceRegulators().size()<serviceNotifications.size()){
                    List<ServiceRegulator> serviceRegulators = new ArrayList<>();
                    for(Dictionary serviceNotification: serviceNotifications){
                        if(!checkSR(sales.getServiceRegulators(), serviceNotification)){
                            Date approveDate = sales.getApproveDate();
                            approveDate.setYear(DateUtility.addYear(-1).getYear()+1900);
                            approveDate.setMonth(approveDate.getMonth()+1);
                            serviceRegulators.add(new ServiceRegulator(sales, serviceNotification, DateUtility.generate(approveDate.getDate(), approveDate.getMonth(), approveDate.getYear())));
                        }
                    }
                    if(serviceRegulators.size()>0){
                        serviceRegulatorRepository.saveAll(serviceRegulators);
                        log.info(sales.getId() + " : " + serviceNotifications.size());
                    }
                }
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
        return "redirect:/login";
    }

    private boolean checkSR(List<ServiceRegulator> serviceRegulators, Dictionary serviceNotification){
        try {
            for(ServiceRegulator serviceRegulator: serviceRegulators){
                if(serviceRegulator.getServiceNotification().getId().intValue()==serviceNotification.getId().intValue()){
                    return true;
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return false;
    }


    @GetMapping("/contact")
    public String contact() throws Exception {
        List<Dictionary> cities = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city");
        List<Contact> contacts = new ArrayList<>();
        for(int i=1; i<400; i++){
            Contact contact = new DummyContact().getContact(cities);
            contacts.add(contact);
        }
        contactRepository.saveAll(contacts);
      //  log("financing", "create/edit", contacts.getId(), contacts.toString());
        return "redirect:/login";
    }

    @GetMapping("/person")
    public String person() throws Exception {
        List<Dictionary> cities = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city");
        List<Dictionary> genders = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender");
        List<Dictionary> nationalities = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality");
        List<Dictionary> maritalStatuses = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("marital-status");
        List<Person> persons = new ArrayList<>();
        for(int i=1; i<400; i++){
            Contact contact = new DummyContact().getContact(cities);
            Person person = new DummyPerson().getPerson(contact, nationalities, genders, maritalStatuses);
            persons.add(person);
        }
        personRepository.saveAll(persons);
       // log("financing", "create/edit", persons.getId(), persons.toString());
        return "redirect:/login";
    }

    @GetMapping("/employee/{count}")
    public String employee(@PathVariable(name = "count") int count) throws Exception {
        List<Dictionary> cities = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city");
        List<Dictionary> genders = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender");
        List<Dictionary> nationalities = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality");
        List<Dictionary> maritalStatuses = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("marital-status");
        List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
        List<Organization> organizations = organizationRepository.getOrganizationsByActiveTrueAndType_Attr1("branch");
        List<Employee> employees = new ArrayList<>();
        for(int i=1; i<=count; i++){
            Contact contact = new DummyContact().getContact(cities);
            Person person = new DummyPerson().getPerson(contact, nationalities, genders, maritalStatuses);
            Employee employee = new DummyEmployee().getEmployee(person, positions, organizations);
            List<EmployeePayrollDetail> employeePayrollDetails = new ArrayList<>();
            for(Dictionary dictionary: dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-payroll-field")){
                String previousWorkExperience = DummyUtil.randomPreviousWorkExperience();
                if(dictionary.getAttr1().equalsIgnoreCase("{salary}")){
                    dictionary.setAttr2(DummyUtil.randomSalary());
                } else if(dictionary.getAttr1().equalsIgnoreCase("{gross_salary}")){
                    dictionary.setAttr2(DummyUtil.randomGrossSalary());
                } else if(dictionary.getAttr1().equalsIgnoreCase("{previous_work_experience}")){
                    dictionary.setAttr2(previousWorkExperience);
                } else if(dictionary.getAttr1().equalsIgnoreCase("{main_vacation_days}")){
                    dictionary.setAttr2(DummyUtil.calculateMainVacationDays(dictionary.getAttr2(), employee.getPerson().getDisability(), employee.getSpecialistOrManager()));
                } else if(dictionary.getAttr1().equalsIgnoreCase("{additional_vacation_days}")){
                    dictionary.setAttr2(DummyUtil.calculateAdditionalVacationDays(dictionary.getAttr2(), employee.getContractStartDate(), previousWorkExperience, employee.getPerson().getDisability()));
                }
                employeePayrollDetails.add(new EmployeePayrollDetail(employee, dictionary, dictionary.getAttr1(), dictionary.getAttr2()));
            }
            employee.setEmployeePayrollDetails(employeePayrollDetails);

            List<EmployeeSaleDetail> employeeSaleDetails = new ArrayList<>();
            for(Dictionary dictionary: dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-sale-field")){
                employeeSaleDetails.add(new EmployeeSaleDetail(employee, dictionary, dictionary.getAttr1(), dictionary.getAttr2()));
            }
            employee.setEmployeeSaleDetails(employeeSaleDetails);

            List<Dictionary> weekDays = DummyUtil.randomWeekDay(dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("week-day"));
            List<EmployeeRestDay> employeeRestDays = new ArrayList<>();
            for(Dictionary weekDay: weekDays){
                employeeRestDays.add(new EmployeeRestDay(employee, weekDay.getName(), weekDay.getAttr1(), Integer.parseInt(weekDay.getAttr2()), weekDay));
            }
            employee.setEmployeeRestDays(employeeRestDays);
            employees.add(employee);

        }
        employeeRepository.saveAll(employees);
       // log("financing", "create/edit", employees.getId(), employees.toString());
        return "redirect:/login";
    }
}
