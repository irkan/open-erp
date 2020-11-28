package com.openerp.controller;

import com.openerp.config.HttpSessionConfig;
import com.openerp.dao.ReportingDao;
import com.openerp.domain.SalesSchedule;
import com.openerp.domain.Schedule;
import com.openerp.entity.*;
import com.openerp.service.*;
import com.openerp.task.MigrationTask;
import com.openerp.util.Constants;
import com.openerp.repository.*;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import com.openerp.util.UtilJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Controller
public class SkeletonController {

    @Value("${cbar.currencies.endpoint}")
    String cbarCurrenciesEndpoint;

    @Value("${spring.mail.username}")
    String springEmailUserName;

    @Value("${log.file.path}")
    String logFilePath;

    @Autowired
    HttpSessionConfig httpSessionConfig;

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
    ContactHistoryRepository contactHistoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    GlobalConfigurationRepository configurationRepository;

    @Autowired
    ApproverExceptionRepository approverExceptionRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    DemonstrationRepository demonstrationRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    WebServiceAuthenticatorRepository webServiceAuthenticatorRepository;

    @Autowired
    PeriodRepository periodRepository;

    @Autowired
    ServiceRegulatorRepository serviceRegulatorRepository;

    @Autowired
    EndpointRepository endpointRepository;

    @Autowired
    MigrationRepository migrationRepository;

    @Autowired
    MigrationDetailRepository migrationDetailRepository;

    @Autowired
    PersonDocumentRepository personDocumentRepository;

    @Autowired
    TaxConfigurationRepository taxConfigurationRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    FinancingService financingService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    AdvanceService advanceService;

    @Autowired
    DemonstrationService demonstrationService;

    @Autowired
    LogService logService;

    @Autowired
    NonWorkingDayService nonWorkingDayService;

    @Autowired
    ShortenedWorkingDayService shortenedWorkingDayService;

    @Autowired
    VacationService vacationService;

    @Autowired
    BusinessTripService businessTripService;

    @Autowired
    IllnessService illnessService;

    @Autowired
    SalesService salesService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ActionService actionService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ContactHistoryService contactHistoryService;

    @Autowired
    ServiceRegulatorService serviceRegulatorService;

    @Autowired
    EndpointService endpointService;

    @Autowired
    ReportingDao reportingDao;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    @Autowired
    MigrationTask migrationTask;

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
        User user = null;
        try{
            HttpSession session = request.getSession();
            user =  (User) session.getAttribute(Constants.USER);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return user;
    }

    int paginationSize() {
        try{
            if(getSessionUser().getUserDetail().getPaginationSize()>0){
                return getSessionUser().getUserDetail().getPaginationSize();
            }
        } catch (Exception e){
            log(null, "error", "", "", null, "", e.getMessage());
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return 100;
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
        return Util.canViewAll(organization);
    }

    String exportExcel(Object object, RedirectAttributes redirectAttributes, String page){
        redirectAttributes.addFlashAttribute(Constants.EXPORTS, object);
        return "redirect:/export/"+page;
    }

    String mapPost(Object object, BindingResult binding, RedirectAttributes redirectAttributes){
        //redirectAttributes.addFlashAttribute(Constants.FILTER, filter);
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

    String mapPostCI(Object object, BindingResult binding, RedirectAttributes redirectAttributes, String redirect){
        redirectAttributes.addFlashAttribute(Constants.FORM_RESULT_BINDING, binding);
        redirectAttributes.addFlashAttribute(Constants.CHANGE_INVENTORY_FORM, object);
        if(!binding.hasErrors()){
            redirectAttributes.getFlashAttributes().remove(Constants.CHANGE_INVENTORY_FORM);
        }
        return "redirect:"+redirect;
    }

    String mapFilter(Object object, BindingResult binding, RedirectAttributes redirectAttributes, String redirect){
        session.removeAttribute(Constants.SESSION_FILTER);
        session.setAttribute(Constants.SESSION_FILTER, object);
        redirectAttributes.addFlashAttribute(Constants.FILTER_FORM_RESULT_BINDING, binding);
        redirectAttributes.addFlashAttribute(Constants.FILTER, object);
        return "redirect:"+redirect;
    }

    String mapPost(RedirectAttributes redirectAttributes, String redirect){
        redirectAttributes.getFlashAttributes().remove(Constants.FORM);
        return "redirect:"+redirect;
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

                log(notification, "notification", "create/edit", notification.getId(), notification.toString());
            }
        }
    }

    void balance(Transaction transaction){
        if(transaction!=null && transaction.getAccount()!=null){
            Account account = accountRepository.getAccountById(transaction.getAccount().getId());
            double balance = account.getBalance();
            if(transaction.getAccountable()){
                balance+=Double.parseDouble(Util.format((transaction.getDebt() ? transaction.getSumPrice() : -1 * transaction.getSumPrice())/Util.getRate(currencyRateRepository.getCurrencyRateByCode(account.getCurrency().toUpperCase()))));
            }
            account.setBalance(balance);
            accountRepository.save(account);
            log(account, "account", "create/edit", account.getId(), account.toString());
            transaction.setBalance(account.getBalance());
            transactionRepository.save(transaction);
            log(transaction, "transaction", "create/edit", transaction.getId(), transaction.toString());
        }
    }

    public void log(Object object, String tableName, String operation, String description){
        String json = "";
        try{
            json = UtilJson.toJson(object);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        Log log = new Log(tableName, operation, null, getSessionUser()!=null?getSessionUser().getUsername():"", description, json);
        logRepository.save(log);
        sessionLog(log);
    }

    public void log(Object object, String tableName, String operation, Integer rowId, String encapsulate){
        String json = "";
        try{
            json = UtilJson.toJson(object);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        Log log = new Log(tableName, operation, rowId, encapsulate, getSessionUser()!=null?getSessionUser().getUsername():"", json);
        logRepository.save(log);
        sessionLog(log);
    }

    public void log(Object object, String tableName, String operation, Integer rowId, String encapsulate, String description){
        String json = "";
        try{
            json = UtilJson.toJson(object);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        Log log = new Log(tableName, operation, rowId, encapsulate, getSessionUser()!=null?getSessionUser().getUsername():"", description, json);
        logRepository.save(log);
        sessionLog(log);
    }

    public void log(Object object, String tableName, String operation, Integer rowId, String encapsulate, String description, String username){
        String json = "";
        try{
            json = UtilJson.toJson(object);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        Log log = new Log(tableName, operation, rowId, encapsulate, getSessionUser()!=null?getSessionUser().getUsername():username, description, json);
        logRepository.save(log);
        sessionLog(log);
    }

    public void log(Object object, String type, String tableName, String operation, Integer rowId, String encapsulate, String description){
        String json = "";
        try{
            json = UtilJson.toJson(object);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        Log log = new Log(type, tableName, operation, rowId, encapsulate, getSessionUser()!=null?getSessionUser().getUsername():"", description, json);
        logRepository.save(log);
        sessionLog(log);
    }

    public void log(String operation, String description){
        Log log = new Log(operation, description, getSessionUser()!=null?getSessionUser().getUsername():"");
        logRepository.save(log);
        sessionLog(log);
    }

    private void sessionLog(Log log){
        List<Log> logs = (ArrayList<Log>)session.getAttribute(Constants.LOGS);
        if(logs!=null){
            logs.add(log);
            Collections.reverse(logs);
            session.setAttribute(Constants.LOGS, logs);
        }
    }

    BindingResult checkDate(BindingResult binding, Date date){
        Date today = new Date();
        if(!(today.getMonth()!=date.getMonth())){
            List<Period> periods = periodRepository.getPeriodsByUser_Username(getSessionUser().getUsername());
            if(periods.size()>0){
                Period period = periods.get(0);
                if(period.getStartDate()!=null && period.getStartDate().getTime()<=date.getTime()){
                    FieldError fieldError = new FieldError("startDate", "startDate", DateUtility.getFormattedDate(date) + " başlama tarixindən ("+DateUtility.getFormattedDate(period.getStartDate())+") əvvəldir!");
                    binding.addError(fieldError);
                }

                if(period.getEndDate().getTime()>=date.getTime()){
                    FieldError fieldError = new FieldError("endDate", "endDate", DateUtility.getFormattedDate(date) + " bitmə tarixindən ("+DateUtility.getFormattedDate(period.getEndDate())+") sonradır!");
                    binding.addError(fieldError);
                }
            }
            if(!binding.hasErrors()){
                FieldError fieldError = new FieldError("endDate", "endDate", "Ay bağlanmışdır!");
                binding.addError(fieldError);
            }
        }
        return binding;
    }

    Invoice invoice(Invoice invoice){
        Invoice invc;
        if(invoice.getId()==null){
            invoice.setDescription("Ödəniş " + invoice.getPrice() + " AZN -> " + invoice.getDescription());
            invoice.setApprove(false);
            invc = invoice;
        } else {
            invc = invoiceRepository.getInvoiceById(invoice.getId());
            invc.setSales(invoice.getSales());
            invc.setPrice(invoice.getPrice());
            invc.setInvoiceDate(invoice.getInvoiceDate());
            invc.setDescription(invoice.getDescription());
        }
        invoiceRepository.save(invc);
        addContactHistory(invc.getSales(), "Hesab-faktura yaradıldı. H/f №" + invc.getId(), null);
        return invc;
    }

    boolean checkContactHistory(Sales sales, SalesSchedule salesSchedule) {
        List<ContactHistory> contactHistories = contactHistoryRepository.getContactHistoriesByActiveTrueAndSalesOrderByIdDesc(sales);
        for(ContactHistory contactHistory: contactHistories){
            if(contactHistory.getNextContactDate()!=null && contactHistory.getNextContactDate().getTime()>salesSchedule.getScheduleDate().getTime()){
                return true;
            }
        }
        return false;
    }

    void addContactHistory(Sales sales, String description, Sales childSales){
        addContactHistory(sales, description, null, null, childSales);
    }
    void addContactHistory(Sales sales, String description, Dictionary contactChannel, Date nextContactDate, Sales childSales){
        try{
            ContactHistory ch = new ContactHistory();
            ch.setSales(sales);
            ch.setDescription(description);
            if(contactChannel!=null){
                ch.setContactChannel(contactChannel);
            }
            if(nextContactDate!=null){
                ch.setNextContactDate(nextContactDate);
            }
            if(childSales!=null){
                ch.setChildSales(childSales);
            }
            if(ch.getSales()!=null && ch.getSales().getOrganization()!=null){
                ch.setOrganization(ch.getSales().getOrganization());
            } else {
                ch.setOrganization(getSessionOrganization());
            }
            ch.setUser(getSessionUser());
            contactHistoryRepository.save(ch);
            log(ch, "collect_contact_history", "create/edit", ch.getId(), ch.toString());
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    boolean canApprove(){
        return canApprove(null, null, null);
    }

    boolean canApprove(Organization organization){
        return canApprove(null, organization, null);
    }

    boolean canApprove(Employee employee, Organization organization, String type){
        if(getSessionUser().getEmployee().equals(employee)){
            return true;
        }
        if(organization!=null && organization.getId().intValue()==getUserOrganization().getId().intValue()){
            return true;
        }
        Date today = new Date();
        List<ApproverException> approverExceptions = approverExceptionRepository.getApproverExceptionsByUserAndActiveTrueAndPermissionDateFromLessThanEqualAndPermissionDateToGreaterThanEqualOrderByPermissionDateToDesc(getSessionUser(), today, today);
        if(approverExceptions.size()>0){
            if(getSessionUser().getUserDetail().getAdministrator()){
                return true;
            } else {
                if(getSessionOrganization().getId().intValue()==getUserOrganization().getId().intValue()){
                    return true;
                }
            }
        } else {
            if( employee!=null && getSessionOrganization().getId().intValue()==getUserOrganization().getId().intValue()){
                if(type!=null && !type.equalsIgnoreCase("sales")){ //Sales i ancaq ozu tesdiq ede biler van leader
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkBackDate(Date date){
        int backDays = 0;
        try{
            Date today = new Date();
            ApproverException approverException = null;
            List<ApproverException> approverExceptions = approverExceptionRepository.getApproverExceptionsByUserAndActiveTrueAndPermissionDateFromLessThanEqualAndPermissionDateToGreaterThanEqualOrderByPermissionDateToDesc(getSessionUser(), today, today);
            if(approverExceptions.size()>0){
                approverException = approverExceptions.get(0);
            }
            if(approverException!=null && approverException.getBackOperationDays()!=null && approverException.getBackOperationDays()!=0){
                backDays = approverException.getBackOperationDays();
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }

        if(backDays==0){
            try{
                GlobalConfiguration configuration = configurationRepository.getGlobalConfigurationByKeyAndActiveTrue("default_back_operation_days_count");
                if(configuration!=null && configuration.getAttribute()!=null && configuration.getAttribute().matches(Constants.REGEX.REGEX3)){
                    backDays = Integer.parseInt(configuration.getAttribute());
                }
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }

        Date backDate = DateUtility.addDay(-1*backDays);
        if(backDate.getTime()<=date.getTime()){
            return true;
        }
        return false;
    }

    void reactivateReturnedInventory(Inventory inventory){
        try{
            if(inventory!=null && inventory.getId()!=null){
                Inventory inventorySelected = inventoryRepository.getInventoryById(inventory.getId());
                if(inventorySelected!=null && !inventorySelected.getActive()){
                    inventorySelected.setActive(true);
                    inventoryRepository.save(inventorySelected);
                    log(inventorySelected, "inventory", "create/edit", inventory.getId(), inventory.toString(), "Qaytarılma olduğu üçün silinmiş inventar bərpa edildi avtomatik olaraq");
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    Advance calculateVacationPrice(Vacation vacation){
        List<SalaryEmployee> salaryEmployees = salaryEmployeeRepository.getSalaryEmployeesBySalary_ActiveAndEmployee_IdOrderByEmployeeDesc(true, vacation.getEmployee().getId());
        int count = (salaryEmployees.size()>12?13:salaryEmployees.size())-1;
        count = count==0?1:count;
        double sumSalary = 0d;

        String description = vacation.getIdentifier().getName() + " " + DateUtility.getFormattedDate(vacation.getStartDate()) + " - " + DateUtility.getFormattedDate(vacation.getEndDate()) + ". Məzuniyyət günlərinin sayı: " + vacation.getVacationDetails().size();
        double vacationPrice = (sumSalary/30.4)*vacation.getVacationDetails().size();
        Advance advance = new Advance(
                dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("vacation-advance", "advance"),
                vacation.getEmployee(),
                Util.getUserBranch(vacation.getEmployee().getOrganization()),
                description,
                "",
                new Date(),
                vacationPrice
        );
        advance.setApprove(true);
        advance.setApproveDate(new Date());
        return advance;
    }

    byte canCalculateAdvance(Double price, Integer salesId){ //-1 avansin kredit emeliyyati, 0-hecne, 1 avans emeliyyati
        try{
            price = Util.ifNullReturn0(price);
            Sales sales = salesRepository.getSalesById(salesId);
            if(sales!=null){
                if(!sales.getService()){ //servis deyilse yeni satishdirsa
                    Double payed = Util.calculateInvoice(sales.getInvoices())-Util.ifNullReturn0(price);
                    if(sales.getPayment()!=null){
                        Double sum = Util.ifNullReturn0(sales.getPayment().getPrice())/4;
                        if(sales.getPayment().getCash()){ //cash satilib
                            sum = Util.ifNullReturn0(sales.getPayment().getLastPrice())/3;
                            if(payed<sum && payed+price>=sum){ //invoice odenilib
                                return 1;
                            } else if(payed>=sum && payed+price<sum) { //kredit emeliyyati edilib
                                return -1;
                            }
                        } else { // kreditle satilib
                            sum = Util.ifNullReturn0(sales.getPayment().getDown())+Util.ifNullReturn0(sales.getPayment().getSchedulePrice());
                            if(payed<sum && payed+price>=sum){
                                return 1;
                            } else if(payed>=sum && payed+price<sum) { //kredit emeliyyati edilib
                                return -1;
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    void saleBonusAdvance(Invoice invc){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String percent = "*0.01";
        Dictionary advance = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-sale-advance", "advance");
        Sales sales = invc.getSales();
        List<Advance> advances = advanceRepository.getAdvancesByActiveTrueAndSalesId(invc.getSales().getId());
        byte cofficent = canCalculateAdvance(invc.getPrice(), invc.getSales().getId());
        if(sales!=null && !sales.getService() && invc.getApprove() && cofficent!=0 && advances.size()==0){
            if(cofficent<0){
                advance = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("credit", "action");
            }
            Advance advanceO;
            try{
                if(sales.getCanavasser()!=null){
                    List<Sales> salesInMonth = salesRepository.getSalesByActiveTrueAndReturnedFalseAndApproveDateBetweenAndCanavasser(DateUtility.getStartDate(sales.getApproveDate()), DateUtility.getEndDate(sales.getApproveDate()), sales.getCanavasser());
                    EmployeeSaleDetail canvasserSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getCanavasser(), "{canvasser}");
                    String calculated_bonus = canvasserSaleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("{sale_count}"), Util.checkNull(salesInMonth.size()))
                            .replaceAll(Pattern.quote("%"), percent);
                    advanceO = new Advance(advance,
                            sales.getCanavasser(),
                            Util.getUserBranch(sales.getCanavasser().getOrganization()),
                            sales.getId() + " nömrəli satış və " + invc.getId() + " nömrəli hesab fakturadan əldə edilən bonus. "+canvasserSaleDetail.getEmployee().getPerson().getFullName()+" (Canvasser)",
                            calculated_bonus,
                            invc.getApproveDate(),
                            cofficent*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus)))
                    );
                    advanceO.setSales(invc.getSales());
                    advanceRepository.save(advanceO);
                    log(advanceO, "advance", "create/edit", advanceO.getId(), advanceO.toString(), " Satış No: " + invc.getSales().getId());
                }
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }

            try{
                if(sales.getDealer()!=null){
                    List<Sales> salesInMonth = salesRepository.getSalesByActiveTrueAndReturnedFalseAndApproveDateBetweenAndDealer(DateUtility.getStartDate(sales.getApproveDate()), DateUtility.getEndDate(sales.getApproveDate()), sales.getDealer());
                    EmployeeSaleDetail dealerSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getDealer(), "{dealer}");
                    String calculated_bonus = dealerSaleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("{sale_count}"), Util.checkNull(salesInMonth.size()))
                            .replaceAll(Pattern.quote("%"), percent);
                    advanceO = new Advance(advance,
                            sales.getDealer(),
                            Util.getUserBranch(sales.getDealer().getOrganization()),
                            sales.getId() + " nömrəli satış və " + invc.getId() + " nömrəli hesab fakturadan əldə edilən bonus."+dealerSaleDetail.getEmployee().getPerson().getFullName()+" (Diller)",
                            calculated_bonus,
                            invc.getApproveDate(),
                            cofficent*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus)))
                    );
                    advanceO.setSales(invc.getSales());
                    advanceRepository.save(advanceO);
                    log(advanceO, "advance", "create/edit", advanceO.getId(), advanceO.toString(), " Satış No: " + invc.getSales().getId());
                }
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
            try{
                if(sales.getVanLeader()!=null){
                    List<Sales> salesInMonth = salesRepository.getSalesByActiveTrueAndReturnedFalseAndApproveDateBetweenAndVanLeader(DateUtility.getStartDate(sales.getApproveDate()), DateUtility.getEndDate(sales.getApproveDate()), sales.getVanLeader());
                    EmployeeSaleDetail vanLeaderSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getVanLeader(), "{van_leader}");
                    String calculated_bonus = vanLeaderSaleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("{sale_count}"), Util.checkNull(salesInMonth.size()))
                            .replaceAll(Pattern.quote("%"), percent);
                    advanceO = new Advance(advance,
                            sales.getVanLeader(),
                            Util.getUserBranch(sales.getVanLeader().getOrganization()),
                            sales.getId() + " nömrəli satış və " + invc.getId() + " nömrəli hesab fakturadan əldə edilən bonus."+vanLeaderSaleDetail.getEmployee().getPerson().getFullName()+" (Ven lider)",
                            calculated_bonus,
                            invc.getApproveDate(),
                            cofficent*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus)))
                    );
                    advanceO.setSales(invc.getSales());
                    advanceRepository.save(advanceO);
                    log(advanceO, "advance", "create/edit", advanceO.getId(), advanceO.toString(), " Satış No: " + invc.getSales().getId());
                }
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
            try{
                if(sales.getConsole()!=null){
                    List<Sales> salesInMonth = salesRepository.getSalesByActiveTrueAndReturnedFalseAndApproveDateBetweenAndConsole(DateUtility.getStartDate(sales.getApproveDate()), DateUtility.getEndDate(sales.getApproveDate()), sales.getConsole());
                    EmployeeSaleDetail consulSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getConsole(), "{consul}");
                    String calculated_bonus = consulSaleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("{sale_count}"), Util.checkNull(salesInMonth.size()))
                            .replaceAll(Pattern.quote("%"), percent);
                    advanceO = new Advance(advance,
                            sales.getConsole(),
                            Util.getUserBranch(sales.getConsole().getOrganization()),
                            sales.getId() + " nömrəli satış və " + invc.getId() + " nömrəli hesab fakturadan əldə edilən bonus."+consulSaleDetail.getEmployee().getPerson().getFullName()+" (Konsul)",
                            calculated_bonus,
                            invc.getApproveDate(),
                            cofficent*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus)))
                    );
                    advanceO.setSales(invc.getSales());
                    advanceRepository.save(advanceO);
                    log(advanceO, "advance", "create/edit", advanceO.getId(), advanceO.toString(), " Satış No: " + invc.getSales().getId());
                }
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
    }
}