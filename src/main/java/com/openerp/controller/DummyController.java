package com.openerp.controller;

import com.openerp.dummy.DummyContact;
import com.openerp.dummy.DummyEmployee;
import com.openerp.dummy.DummyPerson;
import com.openerp.dummy.DummyUtil;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dummy")
public class DummyController extends SkeletonController {
    @GetMapping("/contact")
    public String contact() throws Exception {
        List<Dictionary> cities = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city");
        List<Contact> contacts = new ArrayList<>();
        for(int i=1; i<400; i++){
            Contact contact = new DummyContact().getContact(cities);
            contacts.add(contact);
        }
        contactRepository.saveAll(contacts);
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
        return "redirect:/login";
    }

    @GetMapping("/employee/{count}")
    public String employee(@PathVariable(name = "count") int count) throws Exception {
        List<Dictionary> cities = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city");
        List<Dictionary> genders = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender");
        List<Dictionary> nationalities = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality");
        List<Dictionary> maritalStatuses = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("marital-status");
        List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
        List<Organization> organizations = organizationRepository.getOrganizationsByActiveTrueAndOrganizationType_Attr1("branch");
        List<Employee> employees = new ArrayList<>();
        for(int i=1; i<=count; i++){
            Contact contact = new DummyContact().getContact(cities);
            Person person = new DummyPerson().getPerson(contact, nationalities, genders, maritalStatuses);
            Employee employee = new DummyEmployee().getEmployee(person, positions, organizations);
            List<EmployeeDetail> employeeDetails = new ArrayList<>();
            for(Dictionary dictionary: dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-additional-field")){
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
                EmployeeDetail employeeDetailField1 = new EmployeeDetail(employee, dictionary.getAttr1(), dictionary.getAttr2());
                employeeDetails.add(employeeDetailField1);
            }
            employee.setEmployeeDetails(employeeDetails);
            List<Dictionary> weekDays = DummyUtil.randomWeekDay(dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("week-day"));
            List<EmployeeRestDay> employeeRestDays = new ArrayList<>();
            for(Dictionary weekDay: weekDays){
                employeeRestDays.add(new EmployeeRestDay(employee, weekDay.getName(), weekDay.getAttr1(), Integer.parseInt(weekDay.getAttr2()), weekDay));
            }
            employee.setEmployeeRestDays(employeeRestDays);
            employees.add(employee);

        }
        employeeRepository.saveAll(employees);
        return "redirect:/login";
    }
}
