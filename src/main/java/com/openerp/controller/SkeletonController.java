package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.service.StaticUtils;
import com.openerp.util.Constants;
import com.openerp.repository.*;
import com.openerp.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class SkeletonController {
    protected static final Logger log = Logger.getLogger(SkeletonController.class);

    @Value("${cbar.currencies.endpoint}")
    String cbarCurrenciesEndpoint;

    @Value("${spring.mail.username}")
    String springEmailUserName;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeRestDayRepository employeeRestDayRepository;

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
    EmployeePayrollDetailRepository employeePayrollDetailRepository;

    @Autowired
    EmployeeSaleDetailRepository employeeSaleDetailRepository;

    @Autowired
    WorkingHourRecordEmployeeRepository workingHourRecordEmployeeRepository;

    @Autowired
    WorkingHourRecordEmployeeIdentifierRepository workingHourRecordEmployeeIdentifierRepository;

    @Autowired
    WorkingHourRecordEmployeeDayCalculationRepository workingHourRecordEmployeeDayCalculationRepository;

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
    AdvanceRepository advanceRepository;

    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    SalaryEmployeeRepository salaryEmployeeRepository;

    @Autowired
    FinancingRepository financingRepository;

    @Autowired
    SalaryEmployeeDetailRepository salaryEmployeeDetailRepository;

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    SalesInventoryRepository salesInventoryRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentRegulatorNoteRepository paymentRegulatorNoteRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ConfigurationRepository configurationRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    DemonstrationRepository demonstrationRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    @PostConstruct
    public void init() {
        StaticUtils.setConfig(vacationDetailRepository, businessTripDetailRepository, illnessDetailRepository, nonWorkingDayRepository, employeeRestDayRepository);
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

    boolean isHeadOffice() {
        if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
            return true;
        }
        return false;
    }

    Organization getUserOrganization() {
        return getSessionUser().getEmployee().getOrganization();
    }

    Organization getSessionOrganization() {
        Organization organization = (Organization) session.getAttribute(Constants.ORGANIZATION);
        if(organization==null || organization.getId().intValue()==0){
            organization = getUserOrganization();
        }
        return organization;
    }

    boolean canViewAll(){
        Organization organization = (Organization) session.getAttribute(Constants.ORGANIZATION_SELECTED);
        if(organization==null || organization.getId().intValue()==0){
            return true;
        }
        return false;
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

    String mapPost2(Object object, BindingResult binding, RedirectAttributes redirectAttributes, String redirect){
        redirectAttributes.addFlashAttribute(Constants.FORM_RESULT_BINDING, binding);
        redirectAttributes.addFlashAttribute(Constants.FORM, object);
        return "redirect:"+redirect;
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
        if(employee.getContractStartDate().getTime()>date.getTime()) {
            EmployeeRestDay employeeRestDay = StaticUtils.getEmployeeRestDayByEmployeeAndDay(employee, date);
            List<NonWorkingDay> nonWorkingDays = StaticUtils.getNonWorkingDaysByNonWorkingDateAndActiveTrue(date);
            if(employeeRestDay != null){
                return "İ";
            } else if (nonWorkingDays.size() > 0) {
                NonWorkingDay nonWorkingDay = nonWorkingDays.get(0);
                if (nonWorkingDay.getIdentifier().contentEquals("İ")) {
                    if (StaticUtils.getEmployeeRestDaysByEmployee(employee).size() == 0) {
                        return nonWorkingDay.getIdentifier();
                    }
                } else if (nonWorkingDay.getIdentifier().contentEquals("B")) {
                    return nonWorkingDay.getIdentifier();
                } else if (nonWorkingDay.getIdentifier().contentEquals("QİG")) {
                    return nonWorkingDay.getIdentifier();
                }
            }
            return "";
        }
        EmployeeRestDay employeeRestDay = StaticUtils.getEmployeeRestDayByEmployeeAndDay(employee, date);
        if (employeeRestDay != null) {
            return "İ";
        }
        List<VacationDetail> vacationDetails = StaticUtils.getVacationDetailsByEmployeeAndVacationDateAndVacation_Active(employee, date, true);
        if (vacationDetails != null && vacationDetails.size()>0) {
            for(VacationDetail vacationDetail: vacationDetails){
                return vacationDetail.getVacation().getIdentifier().getAttr1();
            }
        }
        List<BusinessTripDetail> businessTripDetails = StaticUtils.getBusinessTripDetailsByEmployeeAndBusinessTripDateAndBusinessTrip_Active(employee, date, true);
        if (businessTripDetails != null && businessTripDetails.size()>0) {
            for(BusinessTripDetail businessTripDetail: businessTripDetails){
                return businessTripDetail.getBusinessTrip().getIdentifier().getAttr1();
            }
        }
        List<IllnessDetail> illnessDetails = StaticUtils.getIllnessDetailsByEmployeeAndIllnessDateAndIllness_Active(employee, date, true);
        if (illnessDetails != null && illnessDetails.size()>0) {
            for(IllnessDetail illnessDetail: illnessDetails){
                return illnessDetail.getIllness().getIdentifier().getAttr1();
            }
        }
        List<NonWorkingDay> nonWorkingDays = StaticUtils.getNonWorkingDaysByNonWorkingDateAndActiveTrue(date);
        if (nonWorkingDays.size() > 0) {
            NonWorkingDay nonWorkingDay = nonWorkingDays.get(0);
            if (nonWorkingDay.getIdentifier().contentEquals("İ")) {
                if (StaticUtils.getEmployeeRestDaysByEmployee(employee).size() == 0) {
                    return nonWorkingDay.getIdentifier();
                }
            } else if (nonWorkingDay.getIdentifier().contentEquals("B")) {
                return nonWorkingDay.getIdentifier();
            } else if (nonWorkingDay.getIdentifier().contentEquals("QİG")) {
                return nonWorkingDay.getIdentifier();
            }
        }
        return "İG";
    }

    void sendEmail(Organization organization, String emails, String subject, String message, String description){
        for(String email: emails.split(";")){
            if(email.matches(Constants.REGEX.REGEX4)){
                Notification notification = new Notification();
                notification.setType(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("email", "notification"));
                notification.setOrganization(organization);
                notification.setFrom(springEmailUserName);
                notification.setTo(email);
                notification.setDescription(description);
                notification.setSubject(subject);
                notification.setMessage(message);
                notificationRepository.save(notification);
            }
        }
    }

    void balance(Transaction transaction){
        Account account = accountRepository.getAccountById(transaction.getAccount().getId());
        double balance = account.getBalance() + (transaction.getDebt()?transaction.getSumPrice():-1*transaction.getSumPrice());
        account.setBalance(balance);
        accountRepository.save(account);
        transaction.setBalance(account.getBalance());
        transactionRepository.save(transaction);
    }

    void log(String tableName, String operation, int rowId, String encapsulate){
        Log log = new Log(tableName, operation, rowId, encapsulate, getSessionUser().getUsername());
        logRepository.save(log);
        sessionLog(log);
    }

    void log(String operation, String description){
        Log log = new Log(operation, description, getSessionUser().getUsername());
        logRepository.save(log);
        sessionLog(log);
    }

    private void sessionLog(Log log){
        List<Log> logs = (ArrayList<Log>)session.getAttribute(Constants.LOGS);
        logs.add(log);
        Collections.reverse(logs);
        session.setAttribute(Constants.LOGS, logs);
    }
}
