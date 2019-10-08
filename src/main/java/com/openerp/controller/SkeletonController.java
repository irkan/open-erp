package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.service.StaticUtils;
import com.openerp.util.Constants;
import com.openerp.repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class SkeletonController {
    protected static final Logger log = Logger.getLogger(SkeletonController.class);

    @Value("${cbar.currencies.endpoint}")
    String cbarCurrenciesEndpoint;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    DictionaryTypeRepository dictionaryTypeRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    UserModuleOperationRepository userModuleOperationRepository;

    @Autowired
    ModuleOperationRepository moduleOperationRepository;

    @Autowired
    TemplateModuleOperationRepository templateModuleOperationRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CurrencyRateRepository currencyRateRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    NonWorkingDayRepository nonWorkingDayRepository;

    @Autowired
    ShortenedWorkingDayRepository shortenedWorkingDayRepository;

    @Autowired
    PayrollConfigurationRepository payrollConfigurationRepository;

    @Autowired
    EmployeeDetailRepository employeeDetailRepository;

    @Autowired
    WorkingHourRecordEmployeeRepository workingHourRecordEmployeeRepository;

    @Autowired
    WorkingHourRecordEmployeeIdentifierRepository workingHourRecordEmployeeIdentifierRepository;

    @Autowired
    WorkingHourRecordRepository workingHourRecordRepository;

    @Autowired
    VacationRepository vacationRepository;

    @Autowired
    VacationDetailRepository vacationDetailRepository;

    @Autowired
    BusinessTripRepository businessTripRepository;

    @Autowired
    BusinessTripDetailRepository businessTripDetailRepository;

    @Autowired
    IllnessRepository illnessRepository;

    @Autowired
    IllnessDetailRepository illnessDetailRepository;


    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    @PostConstruct
    public void init() {
        StaticUtils.setConfig(vacationDetailRepository, businessTripDetailRepository, illnessDetailRepository);
    }


    protected void checkSession() throws Exception {
        if(session.getAttribute(Constants.USER) == null) {
            throw new Exception("Session user is null");
        }
    }

    protected User getSessionUser() {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(Constants.USER);
    }

    String mapPost(Object object, BindingResult binding, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(Constants.FORM_RESULT_BINDING, binding);
        redirectAttributes.addFlashAttribute(Constants.FORM, object);
        if(!binding.hasErrors()){
            redirectAttributes.getFlashAttributes().remove(Constants.FORM);
        }
        return "redirect:"+request.getRequestURI();
    }

    String mapPost2(Object object, BindingResult binding, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(Constants.FORM_RESULT_BINDING, binding);
        redirectAttributes.addFlashAttribute(Constants.FORM, object);
        return "redirect:"+request.getRequestURI();
    }

    String mapPost(Object object, BindingResult binding, RedirectAttributes redirectAttributes, String redirect){
        redirectAttributes.addFlashAttribute(Constants.FORM_RESULT_BINDING, binding);
        redirectAttributes.addFlashAttribute(Constants.FORM, object);
        if(!binding.hasErrors()){
            redirectAttributes.getFlashAttributes().remove(Constants.FORM);
        }
        return "redirect:"+redirect;
    }

    String mapPost(RedirectAttributes redirectAttributes, String redirect){
        redirectAttributes.getFlashAttributes().remove(Constants.FORM);
        return "redirect:"+redirect;
    }

    double getRate(String currency){
        if(currency!=null && currency.trim().length()>0){
            if(currency.trim().equalsIgnoreCase("AZN")){
                return 1;
            } else {
                CurrencyRate currencyRate = currencyRateRepository.getCurrencyRateByCode(currency.toUpperCase());
                if(currencyRate!=null){
                    return currencyRate.getValue();
                }
            }
        }
        return 0;
    }

    double price(boolean debt, double price){
        if(!debt){
            return price*(-1.0d);
        }
        return price;
    }

    public static String identify(Employee employee, Date date){
        VacationDetail vacationDetail = StaticUtils.getVacationDetailByEmployeeAndVacationDateAndVacation_Active(employee, date, true);
        if(vacationDetail!=null){
            return vacationDetail.getVacation().getIdentifier().getAttr1();
        }
        BusinessTripDetail businessTripDetail = StaticUtils.getBusinessTripDetailByEmployeeAndBusinessTripDateAndBusinessTrip_Active(employee, date, true);
        if(businessTripDetail!=null){
            return businessTripDetail.getBusinessTrip().getIdentifier().getAttr1();
        }
        IllnessDetail illnessDetail = StaticUtils.getIllnessDetailByEmployeeAndIllnessDateAndIllness_Active(employee, date, true);
        if(illnessDetail!=null){
            return illnessDetail.getIllness().getIdentifier().getAttr1();
        }
        return "İG";
    }

}
