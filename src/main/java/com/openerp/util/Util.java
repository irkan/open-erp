package com.openerp.util;

import com.openerp.domain.*;
import com.openerp.entity.*;
import com.openerp.entity.Module;
import com.openerp.entity.Dictionary;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Util {
    private static final Logger log = Logger.getLogger(Util.class);

    public static Object check(Object object){
        try{
            if(object!=null)
                return object;
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static Double ifNullReturn0(Double value){
        try{
            if(value!=null){
                return value;
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return 0d;
    }

    public static String checkNull(Object object){
        try{
            if(object!=null)
                return (String) String.valueOf(object);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static Map<Integer, List<Module>> convertParentModulesMap(List<Module> modules){
        Map<Integer, List<Module>> moduleMap = new HashMap<>();
        int length = Math.round(modules.size()/3);
        length = (modules.size() % 3)>0?length+1:length;
        for(int i=0; i<length; i++){
            List<Module> mdls = new ArrayList<>();
            for(int j=3*i; j<(i+1)*3; j++){
                if(modules.size()>j){
                    mdls.add(modules.get(j));
                }
            }
            moduleMap.put(i, mdls);
        }
        return moduleMap;
    }

    public static Organization getUserBranch(Organization organization){
        if(organization.getType().getAttr1().equalsIgnoreCase("branch")){
            return organization;
        } else if(organization.getOrganization().getType().getAttr1().equalsIgnoreCase("branch")){
            return organization.getOrganization();
        } else if(organization.getOrganization().getOrganization().getType().getAttr1().equalsIgnoreCase("branch")){
            return organization.getOrganization().getOrganization();
        }
        return null;
    }

    public static Boolean canViewAll(Organization organization){
        try{
            if(organization==null || (organization.getId()!=null && organization.getId().intValue()==0)){
                return true;
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public static List<JSONObject> getFormattedResult(ResultSet rs) {
        List<JSONObject> resList = new ArrayList<JSONObject>();
        try {
            ResultSetMetaData rsMeta = rs.getMetaData();
            int columnCnt = rsMeta.getColumnCount();
            List<String> columnNames = new ArrayList<String>();
            for(int i=1;i<=columnCnt;i++) {
                columnNames.add(rsMeta.getColumnName(i).toUpperCase());
            }

            while(rs.next()) {
                JSONObject obj = new JSONObject();
                for(int i=1;i<=columnCnt;i++) {
                    String key = columnNames.get(i - 1);
                    String value = rs.getString(i);
                    obj.put(key, value);
                }
                resList.add(obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resList;
    }

    public static InventoryAmount calculateInventoryAmount(List<Action> actions, int organizationId){
        int allItemsCount = 0;
        int oldItemsCount = 0;
        for(Action action: actions){
            if(action.getActive()){
                if(action.getAction().getAttr1().equalsIgnoreCase("send") && !action.getApprove() && action.getOrganization().getId()==organizationId){
                    allItemsCount+=action.getAmount();
                    oldItemsCount+=action.getOld()?action.getAmount():0;
                } else if(action.getAction().getAttr1().equalsIgnoreCase("buy") && (action.getOrganization().getId()==organizationId || organizationId==0)){
                    allItemsCount+=action.getAmount();
                    oldItemsCount+=action.getOld()?action.getAmount():0;
                } else if(action.getAction().getAttr1().equalsIgnoreCase("consolidate") && (action.getOrganization().getId()==organizationId || organizationId==0)){
                    allItemsCount+=action.getAmount();
                    oldItemsCount+=action.getOld()?action.getAmount():0;
                } else if(action.getAction().getAttr1().equalsIgnoreCase("accept") && action.getOrganization().getId()==organizationId){
                    allItemsCount+=action.getAmount();
                    oldItemsCount+=action.getOld()?action.getAmount():0;
                } else if(action.getAction().getAttr1().equalsIgnoreCase("return") && action.getOrganization().getId()==organizationId){
                    allItemsCount+=action.getAmount();
                    oldItemsCount+=action.getOld()?action.getAmount():0;
                }
            }
        }
        return new InventoryAmount(allItemsCount, oldItemsCount);
    }

    public static int calculateApproveOperationCount(List<Action> actions, int organizationId){
        int amount = 0;
        for(Action action: actions){
            if(!action.getApprove() && action.getOrganization().getId()==organizationId){
                amount+=1;
            }

            if(!action.getApprove() && action.getFromOrganization()!=null && action.getFromOrganization().getId()==organizationId){
                amount+=1;
            }
        }
        return amount;
    }

    public static <Operation> ArrayList<Operation> removeDuplicateOperations(List<ModuleOperation> list) {
        ArrayList<Operation> newList = new ArrayList<>();
        for (ModuleOperation element : list) {
            if (!newList.contains(element.getOperation()) && element.getOperation()!=null) {
                newList.add((Operation) element.getOperation());
            }
        }
        return newList;
    }

    public static List<Module> removeDuplicateModules(List<ModuleOperation> list) {
        List<Module> newList = new ArrayList<>();
        for (ModuleOperation element : list) {
            if (!newList.contains(element.getModule())) {
                newList.add(element.getModule());
            }
        }
        return newList;
    }

    public static List<CurrencyRate> getCurrenciesRate(String cbarCurrenciesEndpoint){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String endpoint = cbarCurrenciesEndpoint.replace("{dd.mm.yyyy}", DateUtility.getFormattedDate(new Date()));
        String value = restTemplate.getForObject(endpoint, String.class);

        Document doc = StringToXML.convertStringToXMLDocument( value );
        NodeList nList = doc.getElementsByTagName("ValCurs");
        List<CurrencyRate> currencyRates = new ArrayList<>();
        for(int i=0; i<nList.getLength(); i++){
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                NodeList valTypeNodeList = eElement.getElementsByTagName("ValType");
                for(int j=0; j<valTypeNodeList.getLength(); j++){
                    Node valTypeNode = valTypeNodeList.item(j);
                    if (valTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element valTypeElement = (Element) valTypeNode;
                        NodeList valuteNodeList = valTypeElement.getElementsByTagName("Valute");
                        for(int k=0; k<valuteNodeList.getLength(); k++){
                            Node valuteNode = valuteNodeList.item(k);
                            Element valuteElement = (Element) valuteNode;
                            CurrencyRate currencyRate = new CurrencyRate();
                            currencyRate.setRateDate(DateUtility.getUtilDate(eElement.getAttribute("Date")));
                            currencyRate.setType(valTypeElement.getAttribute("Type"));
                            currencyRate.setCode(valuteElement.getAttribute("Code"));
                            currencyRate.setNominal(valuteElement.getElementsByTagName("Nominal").item(0).getTextContent());
                            currencyRate.setName(valuteElement.getElementsByTagName("Name").item(0).getTextContent());
                            currencyRate.setValue(Double.parseDouble(valuteElement.getElementsByTagName("Value").item(0).getTextContent()));
                            currencyRates.add(currencyRate);
                        }
                    }
                }
            }
        }
        return currencyRates;
    }

    public static String generateBarcode(int groupId){
        return DateUtility.getFormattedDateddMMyy(new Date())+"-"+groupId+"-"+RandomString.getNumeric(3);
    }

    public static Module findParentModule(Module module){
        if(module.getModule()==null){
            return module;
        } else if(module.getModule().getModule()==null){
            return module.getModule();
        } else if(module.getModule().getModule().getModule()==null){
            return module.getModule().getModule();
        } else if(module.getModule().getModule().getModule().getModule()==null){
            return module.getModule().getModule().getModule();
        }
        return null;
    }

    public static String format3(double amount){
        try{
            DecimalFormat df = new DecimalFormat("#");
            df.setRoundingMode(RoundingMode.CEILING);
            return df.format(amount).replace(",", ".");
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return "0";
    }

    public static String format(double amount){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(amount).replace(",", ".");
    }

    public static String format2(double amount){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(amount).replace(",", ".");
    }

    public static String formatRate(double amount){
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(amount).replace(",", ".");
    }

    public static String findWorkingHourRecordEmployeeDayCalculations(List<WorkingHourRecordEmployeeDayCalculation> workingHourRecordEmployeeDayCalculations, String key){
        for(WorkingHourRecordEmployeeDayCalculation whredc: workingHourRecordEmployeeDayCalculations){
            if(whredc.getKey().equalsIgnoreCase(key)){
                return String.valueOf(whredc.getValue());
            }
        }
        return "0";
    }

    public static String findEmployeeDetail(List<EmployeePayrollDetail> employeeDetails, String key){
        for(EmployeePayrollDetail ed: employeeDetails){
            if(ed.getKey().equalsIgnoreCase(key)){
                return ed.getValue();
            }
        }
        return "0";
    }

    public static String findEmployeeDetailDescription(List<Dictionary> dictionaries, String key){
        for(Dictionary d: dictionaries){
            if(d.getAttr1().trim().equalsIgnoreCase(key)){
                return d.getName();
            }
        }
        return "0";
    }

    public static String findPayrollConfiguration(List<PayrollConfiguration> payrollConfigurations, String key){
        for(PayrollConfiguration pc: payrollConfigurations){
            String[] formulas = pc.getFormula().split("=");
            for(int i=0; i<formulas.length; i++){
                if(formulas[i].trim().equalsIgnoreCase(key)){
                    return formulas[i+1];
                }
            }
        }
        return "0";
    }

    public static String findPayrollConfigurationDescription(List<PayrollConfiguration> payrollConfigurations, String key){
        for(PayrollConfiguration pc: payrollConfigurations){
            String[] formulas = pc.getFormula().split("=");
            for(int i=0; i<formulas.length; i++){
                if(formulas[i].trim().equalsIgnoreCase(key)){
                    return pc.getName();
                }
            }
        }
        return null;
    }

    public static String calculateWorkExperience(Date workExperience){
        Date today = new Date();
        double year = ((today.getTime()-workExperience.getTime())/31536)/1000000d;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(year).replace(",", ".");
    }

    public static List<WorkingHourRecordEmployeeDayCalculation> calculateWorkingHourRecordEmployeeDay(WorkingHourRecordEmployee workingHourRecordEmployee, List<Dictionary> identifiers, double balanceVacationDays){
        List<WorkingHourRecordEmployeeDayCalculation> whredcs = new ArrayList<>();
        List<WorkingHourRecordEmployeeDayCalculation> whredcsCurrent = workingHourRecordEmployee.getWorkingHourRecordEmployeeDayCalculations();
        List<WorkingHourRecordEmployeeIdentifier> whreisCurrent = workingHourRecordEmployee.getWorkingHourRecordEmployeeIdentifiers();
        for(Dictionary identifier: identifiers){
            if(!identifier.getAttr1().contentEquals("HİG") && !identifier.getAttr1().contentEquals("ÜİG") && !identifier.getAttr1().contentEquals("HMQ")) {
                WorkingHourRecordEmployeeDayCalculation whredc = findWorkingHourRecordEmployeeDayCalculation(whredcsCurrent, identifier.getAttr1());
                whredc.setWorkingHourRecordEmployee(workingHourRecordEmployee);
                whredc.setDescription(identifier.getName());
                whredc.setKey(identifier.getAttr1());
                whredc.setValue(Util.calculateIdentifier(workingHourRecordEmployee, identifier.getAttr1()));
                whredc.setIdentifier(identifier);
                whredcs.add(whredc);
            }
        }
        for(Dictionary identifier: identifiers){
            if(identifier.getAttr1().contentEquals("HMQ")){
                double vacationDays = findEmployeeVacationCount(workingHourRecordEmployee.getEmployee());
                int currentVacationCount = Util.calculateIdentifier(workingHourRecordEmployee, "M");
                double vacation =  Double.parseDouble(Util.format(vacationDays/12)) + balanceVacationDays - currentVacationCount;
                WorkingHourRecordEmployeeDayCalculation whredc = findWorkingHourRecordEmployeeDayCalculation(whredcsCurrent, identifier.getAttr1());
                whredc.setWorkingHourRecordEmployee(workingHourRecordEmployee);
                whredc.setDescription(identifier.getName());
                whredc.setKey(identifier.getAttr1());
                whredc.setValue(vacation);
                whredc.setIdentifier(identifier);
                whredcs.add(whredc);
            }
        }
        for(Dictionary identifier: identifiers){
            if(identifier.getAttr1().contentEquals("ÜİG")){
                WorkingHourRecordEmployeeDayCalculation whredc = findWorkingHourRecordEmployeeDayCalculation(whredcsCurrent, identifier.getAttr1());
                whredc.setWorkingHourRecordEmployee(workingHourRecordEmployee);
                whredc.setDescription(identifier.getName());
                whredc.setKey(identifier.getAttr1());
                whredc.setValue(Util.calculateUIG(whreisCurrent, whredcs));
                whredc.setIdentifier(identifier);
                whredcs.add(whredc);
            }
        }
        for(Dictionary identifier: identifiers){
            if(identifier.getAttr1().contentEquals("HİG")){
                WorkingHourRecordEmployeeDayCalculation whredc = findWorkingHourRecordEmployeeDayCalculation(whredcsCurrent, identifier.getAttr1());
                whredc.setWorkingHourRecordEmployee(workingHourRecordEmployee);
                whredc.setDescription(identifier.getName());
                whredc.setKey(identifier.getAttr1());
                whredc.setValue(Util.calculateHIG(whredcs));
                whredc.setIdentifier(identifier);
                whredcs.add(whredc);
            }
        }
        return whredcs;
    }

    private static double findEmployeeVacationCount(Employee employee){
        int mainVacationDay=0;
        int additionalVacationDay = 0;
        if(employee!=null && employee.getEmployeePayrollDetails()!=null && employee.getEmployeePayrollDetails().size()>0){
            for(EmployeePayrollDetail epd: employee.getEmployeePayrollDetails()){
                if(epd.getKey().equalsIgnoreCase("{main_vacation_days}")){
                    if(epd.getValue().trim().length()>0){
                        mainVacationDay = Integer.parseInt(epd.getValue());
                    }
                }
                if(epd.getKey().equalsIgnoreCase("{additional_vacation_days}")){
                    if(epd.getValue().trim().length()>0){
                        additionalVacationDay = Integer.parseInt(epd.getValue());
                    }
                }
            }
        }
        return mainVacationDay+additionalVacationDay;
    }

    public static double calculateUIG(List<WorkingHourRecordEmployeeIdentifier> whreis, List<WorkingHourRecordEmployeeDayCalculation> whredcs){
        double iWHREDCCount = findIdentifierCountInWHREDC(whredcs, "İ");
        double iWHREICount = findIdentifierCountInWorkingHourRecordEmployeeIdentifier(whreis, "İ");
        double bWHREDCCount = findIdentifierCountInWHREDC(whredcs, "B");
        double bWHREICount = findIdentifierCountInWorkingHourRecordEmployeeIdentifier(whreis, "B");
        double qigWHREDCCount = findIdentifierCountInWHREDC(whredcs, "QİG");
        double qigWHREICount = findIdentifierCountInWorkingHourRecordEmployeeIdentifier(whreis, "QİG");
        return whreis.size()-(iWHREDCCount>iWHREICount?iWHREDCCount:iWHREICount) - (bWHREDCCount>bWHREICount?bWHREDCCount:bWHREICount) - (qigWHREDCCount>qigWHREICount?qigWHREDCCount:qigWHREICount);
    }

    public static int findIdentifierCountInWorkingHourRecordEmployeeIdentifier(List<WorkingHourRecordEmployeeIdentifier> workingHourRecordEmployeeIdentifiers, String identifier){
        int i=0;
        for(WorkingHourRecordEmployeeIdentifier whrei: workingHourRecordEmployeeIdentifiers){
            if(whrei.getIdentifier()!=null && whrei.getIdentifier().trim().length()>0 && whrei.getIdentifier().contentEquals(identifier)){
                i=i+1;
            }
        }
        return i;
    }

    public static double findIdentifierCountInWHREDC(List<WorkingHourRecordEmployeeDayCalculation> whredcs, String identifier){
        for(WorkingHourRecordEmployeeDayCalculation whredc: whredcs){
            if(whredc.getKey().contentEquals(identifier)){
                return whredc.getValue();
            }
        }
        return 0;
    }

    public static int calculateHIG(List<WorkingHourRecordEmployeeDayCalculation> whredcs){
        int hig = 0;
        for(WorkingHourRecordEmployeeDayCalculation whredc: whredcs){
            if(whredc.getKey().contentEquals("İG") ||
                    whredc.getKey().contentEquals("QİG(S)") ||
                    whredc.getKey().contentEquals("QİG(A)") ||
                    whredc.getKey().contentEquals("I") ||
                    whredc.getKey().contentEquals("II") ||
                    whredc.getKey().contentEquals("DM") ||
                    whredc.getKey().contentEquals("RM") ||
                    whredc.getKey().contentEquals("E") ||
                    whredc.getKey().contentEquals("X")
            ){
                hig+=whredc.getValue();
            }
        }
        return hig;
    }

    public static WorkingHourRecordEmployeeDayCalculation findWorkingHourRecordEmployeeDayCalculation(List<WorkingHourRecordEmployeeDayCalculation> whredcs, String identifier){
        if(whredcs!=null){
            for(WorkingHourRecordEmployeeDayCalculation whredc: whredcs){
                if(whredc.getKey().contentEquals(identifier)){
                    return whredc;
                }
            }
        }
        return new WorkingHourRecordEmployeeDayCalculation();
    }

    public static int calculateIdentifier(WorkingHourRecordEmployee workingHourRecordEmployee, String identifier){
        int i=0;
        for(WorkingHourRecordEmployeeIdentifier whrei: workingHourRecordEmployee.getWorkingHourRecordEmployeeIdentifiers()){
            if(workingHourRecordEmployee.getEmployee().getId()==100780){
                System.out.println(whrei.getIdentifier() + " identifier: " + identifier);
            }
            if(whrei.getIdentifier()!=null && whrei.getIdentifier().trim().length()>0 && whrei.getIdentifier().contentEquals(identifier)){
                i=i+1;
            }
        }
        return i;
    }

    public static Response response(BindingResult binding, String defaultMessage){
        List<String> messages = new ArrayList<>();
        if(binding!=null && binding.getErrorCount()>0){
            for(FieldError fe: binding.getFieldErrors()){
                String message = fe.getDefaultMessage();
                if(message.length()>120){
                    message = "Format səhvdir";
                }
                messages.add(fe.getField() + " : " +message);
            }
            return new Response(Constants.STATUS.ERROR, messages);
        }
        messages.add(defaultMessage);
        return new Response(Constants.STATUS.SUCCESS, messages);
    }

    public static String calculateMainVacationDays(List<PayrollConfiguration> payrollConfigurations, Employee employee) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        return String.valueOf(engine.eval(Util.findPayrollConfiguration(payrollConfigurations,"{main_vacation_days}")
                .replaceAll(Pattern.quote("{disability}"), String.valueOf(employee.getPerson().getDisability()))
                .replaceAll(Pattern.quote("{specialist_or_manager}"), String.valueOf(employee.getSpecialistOrManager()))));
    }

    public static String calculateAdditionalVacationDays(List<PayrollConfiguration> payrollConfigurations, Employee employee, String previousWorkExperience) throws ScriptException {
        Date today = new Date();
        int experience = Integer.parseInt(previousWorkExperience) + today.getYear()-employee.getContractStartDate().getYear();
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String additionalVacationDays = String.valueOf(engine.eval(Util.findPayrollConfiguration(payrollConfigurations,"{additional_vacation_days}")
                .replaceAll(Pattern.quote("{work_experience}"), String.valueOf(experience))));
        if(employee.getPerson().getDisability()){
            additionalVacationDays = "0";
        }
        return additionalVacationDays;
    }

    public static String findPreviousWorkExperience(List<EmployeePayrollDetail> employeePayrollDetails){
        for(EmployeePayrollDetail epd: employeePayrollDetails){
            if(epd.getKey().equalsIgnoreCase("{previous_work_experience}")){
                if(epd.getValue()!=null && epd.getValue().trim().length()>0){
                    return epd.getValue();
                }
            }
        }
        return "0";
    }

    public static Map<String, List<Employee>> convertedEmployeesByPosition(List<Employee> employees, List<Dictionary> positions){
        Map<String, List<Employee>> convertedEmployees = new HashMap<>();
        for(Dictionary position: positions){
            List<Employee> items = new ArrayList<>();
            for(Employee employee: employees){
                if(position.getAttr1().equalsIgnoreCase(employee.getPosition().getAttr1()) || position.getAttr1().contentEquals(employee.getPosition().getAttr1())){
                    items.add(employee);
                }
            }
            if(items.size()>0){
                convertedEmployees.put(position.getName(), items);
            }
        }
        return convertedEmployees;
    }

    public static Map<String, List<Account>> convertedAccountsByOrganization(List<Account> accounts, List<Organization> organizations){
        Map<String, List<Account>> convertedAccounts = new HashMap<>();
        for(Organization organization: organizations){
            List<Account> items = new ArrayList<>();
            for(Account account: accounts){
                if(organization.getId().intValue()==account.getOrganization().getId().intValue()){
                    items.add(account);
                }
            }
            if(items.size()>0){
                convertedAccounts.put(organization.getName(), items);
            }
        }
        return convertedAccounts;
    }

    public static Map<String, List<Employee>> convertedEmployeesByOrganization(List<Employee> employees, List<Organization> organizations){
        Map<String, List<Employee>> convertedEmployees = new HashMap<>();
        for(Organization organization: organizations){
            List<Employee> items = new ArrayList<>();
            for(Employee employee: employees){
                if(organization.getId().intValue()==employee.getOrganization().getId().intValue()){
                    items.add(employee);
                }
            }
            convertedEmployees.put(organization.getName(), items);
        }
        return convertedEmployees;
    }

    public static Map<String, List<User>> convertedUsersByOrganization(List<User> users, List<Organization> organizations){
        Map<String, List<User>> convertedUsers = new HashMap<>();
        for(Organization organization: organizations){
            List<User> items = new ArrayList<>();
            for(User user: users){
                if(organization.getId().intValue()==user.getEmployee().getOrganization().getId().intValue()){
                    items.add(user);
                }
            }
            convertedUsers.put(organization.getName(), items);
        }
        return convertedUsers;
    }

    public static Date guarantee(Date saleDate, int guarantee){
        Calendar cal = Calendar.getInstance();
        cal.setTime(saleDate);
        cal.add(Calendar.MONTH, guarantee);
        return cal.getTime();
    }

    public static List<Integer> getInvoiceIds(String data){
        boolean status = true;
        List<Integer> ids = new ArrayList<>();
         if(data.trim().length()>0 && data.matches(Constants.REGEX.REGEX1)){
            for(String item: data.split(",")){
                if(item.trim().matches(Constants.REGEX.REGEX2)){
                    String[] subItems = item.trim().split("-");
                    if(subItems.length>2){
                        log.error("Format dogru daxil edilmeyib");
                        status = false;
                        break;
                    }
                    if(status){
                        for(String subItem: subItems){
                            if(!subItem.matches(Constants.REGEX.REGEX3)){
                                log.error("Format dogru daxil edilmeyib");
                                status = false;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if(status){
            for(String item: data.split(",")){
                String[] subItems = item.trim().split("-");
                int start = Integer.parseInt(subItems[0].trim());
                int end = subItems.length==1?Integer.parseInt(subItems[0].trim()):Integer.parseInt(subItems[1].trim());
                int loopStart = start<=end?start:end;
                int loopEnd = start<=end?end:start;
                for(int i=loopStart; i<=loopEnd; i++){
                    ids.add(i);
                }
            }
        }

        return ids;
    }

    public static String getPersonLFF(Person person){
        return person.getLastName() + " " + person.getFirstName() + " " + (person.getFatherName()!=null?person.getFatherName():"");
    }

    public static Boolean checkDirectory(File file){
        return file.isDirectory();
    }

    public static Boolean checkTXTFile(File file){
        Pattern pattern = Pattern.compile(Constants.REGEX.REGEX6);
        return pattern.matcher(file.getName()).matches();
    }

    public static Boolean isAdministrator(User user){
        try {
            return user.getUserDetail().getAdministrator();
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public static Boolean checkApprover(User user){
        try {
            if(user!=null && user.getApproverException()!=null){
                return true;
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public static String getPath(File file){
        return file.getPath().split("logs")[1].replaceAll(Pattern.quote("\'"), "/");
    }

    public static String getDigitInWord(String digit){
        String digitInWord = "";
        if(digit.matches("[0-9\\s\\.]+")){
            String[] dgts = digit.split(Pattern.quote("."));
            IntToAZE intToAZE = new IntToAZE();
            try {
                digitInWord += intToAZE.aze(Integer.parseInt(dgts[0]));
                digitInWord += " manat";
                if(dgts.length>0) {
                    if(Integer.parseInt(dgts[1])>0){
                        digitInWord += intToAZE.aze(Integer.parseInt(dgts[1]));
                        digitInWord += " qəpik";
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }
        return digitInWord;
    }

    public static int amountChecker(Integer amount){
        if(amount!=null && amount!=0){
            return amount;
        }
        return 1;
    }

    public static Double calculateInvoice(List<Invoice> invoices){
        double price = 0;
        try{
            for(Invoice invoice: invoices){
                if(invoice.getApprove()){
                    price+=invoice.getPrice();
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return price;
    }

    public static Double calculateInvoiceCurrentMonth(List<Invoice> invoices){
        double price = 0;
        Date now = new Date();
        try{
            for(Invoice invoice: invoices){
                try{
                    if(invoice.getApprove() && invoice.getApproveDate().getYear()==now.getYear() && invoice.getApproveDate().getMonth()==now.getMonth()){
                        price+=invoice.getPrice();
                    }
                } catch (Exception e){}
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return price;
    }

    public static Double calculateInvoiceTerminal1(List<Invoice> invoices){
        double price = 0;
        Date now = new Date();
        try{
            for(Invoice invoice: invoices){
                try{
                    if(invoice.getPaymentChannel()!=null && invoice.getPaymentChannel().getName().trim().equalsIgnoreCase("TERMINAL")){
                        if(invoice.getApprove() && invoice.getApproveDate().getYear()==now.getYear() && invoice.getApproveDate().getMonth()==now.getMonth()){
                            price+=invoice.getPrice();
                        }
                    }
                } catch (Exception e){}
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return price;
    }

    public static Double calculateInvoiceTerminal2(List<Invoice> invoices){
        double price = 0;
        try{
            for(Invoice invoice: invoices){
                try{
                    if(invoice.getPaymentChannel()!=null && invoice.getPaymentChannel().getName().trim().equalsIgnoreCase("TERMINAL")){
                        if(invoice.getApprove()){
                            price+=invoice.getPrice();
                        }
                    }
                } catch (Exception e){}
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return price;
    }

    public static Double calculatePlannedPayment(Sales sales, List<Schedule> schedules){
        double price = sales.getPayment().getDown();
        Date today = new Date();
        for(Schedule schedule: schedules){
            if(schedule.getScheduleDate().getTime() < today.getTime()){
                price+=schedule.getAmount();
            }
        }
        return price;
    }

    public static List<Schedule> calculateSchedule(Sales sales, List<Schedule> schedules, Double sumOfInvoices){
        double price = sumOfInvoices-sales.getPayment().getDown();
        if(price>0){
            for(Schedule schedule: schedules){
                if(price>0){
                    schedule.setPayableAmount(price>schedule.getAmount()?schedule.getAmount():price);
                    price = price>schedule.getAmount()?price-schedule.getAmount():0;
                }
            }
        }
        return schedules;
    }

    public static Integer calculateLatency(List<Schedule> schedules, Double sumOfInvoice, Sales sales){
        Date today = new Date();
        Integer latency = 0;
        if(sumOfInvoice==0){
            return DateUtility.daysBetween(sales.getSaleDate(), today);
        }
        if(sales.getPayment().getCash() && sales.getPayment().getDown()>sumOfInvoice){
            return DateUtility.daysBetween(sales.getSaleDate(), today);
        }
        if(!sales.getPayment().getCash()){
            for(Schedule schedule: schedules){
                if(schedule.getScheduleDate().getTime()<today.getTime()){
                    if(schedule.getPayableAmount()!=null && schedule.getAmount()>schedule.getPayableAmount()){
                        int days = DateUtility.daysBetween(schedule.getScheduleDate(), today);
                        if(days>latency){
                            latency=days;
                        }
                    }
                }
            }
        }
        return latency;
    }

    public static Double calculateSalary(List<SalaryEmployeeDetail> salaryEmployeeDetails){
        Double price = 0d;
        for(SalaryEmployeeDetail sed: salaryEmployeeDetails){
            if(sed.getKey().equalsIgnoreCase("")){

            }
        }
        return price;
    }

    public static Double exchangeRate(Double rate1, Double rate2){
        try {
            return Double.parseDouble(Util.formatRate(rate1/rate2));
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return 1d;
    }

    public static int parseInt(String value, String defaultValue){
        try {
            return Integer.parseInt(value);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }

        try {
            return Integer.parseInt(defaultValue);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }

        return 6;
    }

    public static int parseInt(String value){
        try {
            return Integer.parseInt(value);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    public static int parseInt(Object object){
        try {
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    public static List<ModuleOperation> getModuleOperations(List<UserModuleOperation> userModuleOperations){
        List<ModuleOperation> list = new ArrayList<>();
        for(UserModuleOperation umo: userModuleOperations){
            list.add(umo.getModuleOperation());
        }
        return list;
    }

    public static List<User> getAccessibleUsers(List<User> users, int accesses){
        List<User> list = new ArrayList<>();
        for(User user: users){
            if(user.getUserModuleOperations()!=null && user.getUserModuleOperations().size()<=accesses){
                list.add(user);
            }
        }
        return list;
    }

    public static Double annualAdvance(List<Report> reports) {
        Double annual = 0d;
        for(Report report: reports){
            annual+=report.getDouble1();
        }
        return annual;
    }

    public static List<Schedule> getDatedSchedule(List<Schedule> schedules, Date scheduleDate) {
        List<Schedule> newSchedules = new ArrayList<>();
        for(Schedule schedule: schedules){
            if(schedule.getScheduleDate().getTime()==scheduleDate.getTime()){
                newSchedules.add(schedule);
            }
        }
        if(newSchedules.size()==0){
            for(Schedule schedule: schedules){
                if(schedule.getScheduleDate().getMonth()==scheduleDate.getMonth() && schedule.getScheduleDate().getYear()==scheduleDate.getYear()){
                    newSchedules.add(schedule);
                }
            }
        }
        return newSchedules;
    }

    public static List<Session> convertHttpSession(List<HttpSession> httpSessions) {
        List<Session> sessions = new ArrayList<>();
        Session session;
        for(HttpSession httpSession: httpSessions){
            String username = "";
            session = new Session(httpSession.getId(), new Date(httpSession.getLastAccessedTime()+httpSession.getMaxInactiveInterval()*1000));
            try {
                User user = (User) httpSession.getAttribute(Constants.USER);
                if(user!=null){
                    username = user.getUsername();
                    session.setUsername(username);
                    session.setFullName(user.getEmployee().getPerson().getFullName());
                }
            } catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
            if(username!=null && username.trim().length()>0){
                sessions.add(session);
            }
        }
        return sessions;
    }

    public static Map<Module, List<Module>> accessModuleList(List<UserModuleOperation> userModuleOperations) {
        Map<Module, List<Module>> convertedModules = new HashMap<>();
        for(UserModuleOperation umo: userModuleOperations){
            convertedModules = checkModule(convertedModules, umo.getModuleOperation().getModule());
        }
        return convertedModules;
    }

    private static Map<Module, List<Module>> checkModule(Map<Module, List<Module>> moduleListMap, Module module){
        try {
            Boolean setFlag = false;
            if(!moduleListMap.containsKey(module.getModule()) && module.getModule().getModule()==null /*&& !module.getModule().getPath().equalsIgnoreCase("profile")*/){
                moduleListMap.put(module.getModule(), new ArrayList<>());
            }

            List<Module> modules = moduleListMap.get(module.getModule())==null?new ArrayList<>():moduleListMap.get(module.getModule());
            for(Module m: modules){
                if(m.equals(module)){
                    setFlag = true;
                }
            }
            if(!setFlag){
                modules.add(module);
                moduleListMap.replace(module, modules);
            }
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return moduleListMap;
    }

    public static Double schedulePrice(Dictionary schedule, Double lastPrice, Double down){
        lastPrice = lastPrice - down;
        int scheduleCount = Integer.parseInt(schedule.getAttr1());
        return Math.ceil(lastPrice/scheduleCount);
    }

    public static double getRate(CurrencyRate currencyRate){
        try {
            if(currencyRate!=null && currencyRate.getValue()>0){
                return currencyRate.getValue();
            }
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return 1;
    }

    public static Date getLastPaid(List<Invoice> invoices) {
        Date lastPaid = null;
        if(invoices.size()>0){
            lastPaid = invoices.get(0).getInvoiceDate();
            for(Invoice invoice: invoices){
                if(invoice.getApprove() && invoice.getInvoiceDate()!=null && invoice.getInvoiceDate().getTime()>lastPaid.getTime()){
                    lastPaid = invoice.getInvoiceDate();
                }
            }
        }
        return lastPaid;
    }

    public static Map<Inventory, List<SalesInventory>> groupInventory(List<SalesInventory> salesInventories) {
        Map<Inventory, List<SalesInventory>> salesInventoryMap = new HashMap<>();
        if(salesInventories.size()>0){
            for(SalesInventory salesInventory: salesInventories){
                if (!salesInventoryMap.containsKey(salesInventory.getInventory())) {
                    List<SalesInventory> list = new ArrayList<>();
                    list.add(salesInventory);
                    salesInventoryMap.put(salesInventory.getInventory(), list);
                } else {
                    salesInventoryMap.get(salesInventory.getInventory()).add(salesInventory);
                }
            }
        }
        return salesInventoryMap;
    }

    public static int calculateInventoryAmount(List<Action> actions){
        int a = 0;
        for(Action action: actions){
            a+=action.getAmount();
        }
        return a;
    }

    public static Double calculatePlannedPaymentMonthly(List<Sales> salesList){
        Double plannedPaymentMonthly = 0d;
        for(Sales sales: salesList){
            Double payed = Util.calculateInvoice(sales.getInvoices());
            if(sales.getPayment()!=null && !sales.getPayment().getCash()){
                List<Schedule> schedules = Util.getSchedulePayment(DateUtility.getFormattedDate(sales.getSaleDate()), sales.getPayment().getSchedule(), sales.getPayment().getPeriod(), sales.getPayment().getLastPrice(), sales.getPayment().getDown(), Util.parseInt(sales.getPayment().getGracePeriod()));
                if(schedules.size()>0){
                    plannedPaymentMonthly+=schedules.get(0).getAmount();
                    if(sales.getPayment().getDown()!=null && sales.getPayment().getDown()>0 && payed<sales.getPayment().getDown()){
                        plannedPaymentMonthly+=(sales.getPayment().getDown()-payed)>0?(sales.getPayment().getDown()-payed):0;
                    }
                }
            } else if(sales.getPayment()!=null && sales.getPayment().getCash()){
                if(sales.getPayment().getLastPrice()!=null && sales.getPayment().getLastPrice()>0 && payed<sales.getPayment().getLastPrice()){
                    plannedPaymentMonthly+=(sales.getPayment().getLastPrice()-payed)>0?(sales.getPayment().getLastPrice()-payed):0;
                }
            }
        }
        return plannedPaymentMonthly;
    }

    public static List<Schedule> getSchedulePayment(String saleDate, Dictionary schedule, Dictionary period, Double lastPrice, Double down, Integer gracePeriod){
        lastPrice = lastPrice==null?0:lastPrice;
        int scheduleCount = Integer.parseInt(schedule.getAttr1());
        Date saleDt = saleDate.trim().length()>0? DateUtility.getUtilDate(saleDate):new Date();
        Date startDate = DateUtility.generate(Integer.parseInt(period.getAttr1()), saleDt.getMonth(), saleDt.getYear()+1900);
        List<Schedule> schedules = new ArrayList<>();
        Date scheduleDate = DateUtils.addMonths(startDate, 1+gracePeriod);
        Double schedulePrice = Util.schedulePrice(schedule, lastPrice, down);
        for(int i=0; i<scheduleCount; i++){
            scheduleDate = DateUtils.addMonths(scheduleDate, 1);
            Schedule schedule1 = new Schedule(schedulePrice, scheduleDate);
            schedules.add(schedule1);
        }
        schedules = Util.correctLastSchedulePrice(schedules, lastPrice, down);
        return schedules;
    }

    public static List<Schedule> correctLastSchedulePrice(List<Schedule> schedules, Double lastPrice, Double down) {
        try{
            if(schedules.size()>0){
                Double different = schedules.size()*schedules.get(0).getAmount()-lastPrice-down;
                if(different>0){
                    schedules.get(schedules.size()-1).setAmount(schedules.get(schedules.size()-1).getAmount()-different);
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return schedules;
    }

    public static String getPeriodDay() {
        try {
            Date today = new Date();
            int day = today.getDate();
            if(day==28){
                day = 0;
            } else if(day==29){
                day = 1;
            } else if(day==30){
                day = 2;
            } else if(day==31){
                day = 3;
            }
            day++;
            return Util.checkNull(day);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return "1";
    }

    public static Double parseDouble(Double value) {
        try{
            if(value!=null){
                return value;
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return 0d;
    }
}
