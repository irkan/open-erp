package com.openerp.controller;

import com.openerp.domain.Address;
import com.openerp.domain.Schedule;
import com.openerp.dummy.DummyContact;
import com.openerp.dummy.DummyEmployee;
import com.openerp.dummy.DummyPerson;
import com.openerp.dummy.DummyUtil;
import com.openerp.entity.*;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/migration")
public class MigrationController extends SkeletonController {
    @GetMapping("/contact")
    public String contact() throws Exception {
        List<Dictionary> cities = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city");
        List<Contact> contacts = new ArrayList<>();
        for(int i=1; i<400; i++){
            Contact contact = new DummyContact().getContact(cities);
            contacts.add(contact);
        }
        contactRepository.saveAll(contacts);
      //  log("accounting_financing", "create/edit", contacts.getId(), contacts.toString());
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
       // log("accounting_financing", "create/edit", persons.getId(), persons.toString());
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
       // log("accounting_financing", "create/edit", employees.getId(), employees.toString());
        return "redirect:/login";
    }

    @PostMapping(value = "/db/upload", consumes = {"multipart/form-data"})
    public String postNonWorkingDayUpload(@RequestParam("file") MultipartFile file, @RequestParam("organization") int organziationId, RedirectAttributes redirectAttributes) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        Iterator rows = sheet.rowIterator();
        Organization organization = organizationRepository.getOrganizationByIdAndActiveTrue(organziationId);
        while (rows.hasNext()) {
            try{
                row=(XSSFRow) rows.next();
                Employee vanLeader = parseEmployee(row.getCell(4), organization);
                if(row.getRowNum()>0 && vanLeader!=null){
                    String inventoryName = getString(row.getCell(16)).length()>0?getString(row.getCell(16)):"Empty";
                    inventoryName = inventoryName.toUpperCase();
                    List<Inventory> inventories = inventoryRepository.getInventoriesByNameAndActiveTrue(inventoryName);
                    Inventory inventory = null;
                    if(inventories.size()>0){
                        inventory = inventories.get(0);
                    } else {
                        inventory = new Inventory();
                        inventory.setOrganization(organization);
                        inventory.setOld(false);
                        inventory.setName(inventoryName);
                        inventory.setGroup(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("filters", "inventory-group"));
                        inventory.setBarcode(Util.generateBarcode(inventory.getGroup().getId()));
                        inventory.setInventoryDate(new Date());
                        inventoryRepository.save(inventory);
                    }

                    Action buy = new Action();
                    buy.setInventory(inventory);
                    buy.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("buy", "action"));
                    buy.setOrganization(inventory.getOrganization());
                    buy.setAmount(1);
                    buy.setSupplier(supplierRepository.getSuppliersByName("Anbar qalıq").get(0));
                    actionRepository.save(buy);

                    Action consolidate = new Action();
                    consolidate.setOld(buy.getOld());
                    consolidate.setInventory(buy.getInventory());
                    consolidate.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("consolidate", "action"));
                    consolidate.setSupplier(buy.getSupplier());
                    consolidate.setOrganization(buy.getOrganization());
                    consolidate.setEmployee(vanLeader);
                    consolidate.setApprove(true);
                    consolidate.setApproveDate(new Date());
                    consolidate.setAmount(1);
                    actionRepository.save(consolidate);

                    buy.setAmount(buy.getAmount() - consolidate.getAmount());
                    actionRepository.save(buy);


                    Customer customer = parseCustomer(row, organization);
                    customerRepository.save(customer);

                    Employee canvasser = parseEmployee(row.getCell(5), organization);
                    Employee dealer = parseEmployee(row.getCell(6), organization);
                    Employee servicer = parseEmployee(row.getCell(7), organization);
                    Sales sales = new Sales();
                    SalesInventory si = new SalesInventory();
                    si.setInventory(inventory);
                    si.setSales(sales);
                    si.setSalesType(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("sales", "sales-type"));
                    List<SalesInventory> sis = new ArrayList<>();
                    sis.add(si);
                    sales.setSalesInventories(sis);
                    sales.setCustomer(customer);
                    sales.setVanLeader(vanLeader);
                    sales.setCanavasser(canvasser);
                    sales.setDealer(dealer);
                    sales.setOrganization(organization);
                    sales.setActive(true);
                    sales.setApprove(false);
                    sales.setSaled(false);
                    sales.setSaleDate(row.getCell(8).getDateCellValue());
                    sales.setGuarantee(24);
                    sales.setGuaranteeExpire(Util.guarantee(sales.getSaleDate()==null?new Date():sales.getSaleDate(), sales.getGuarantee()));
                    Payment payment = new Payment();
                    payment.setCash(parseCash(row));
                    payment.setActive(true);
                    payment.setPrice(getNumeric(row.getCell(9)));
                    payment.setLastPrice(getNumeric(row.getCell(9)));

                    if(payment.getCash()){
                        payment.setPeriod(null);
                        payment.setSchedule(null);
                        payment.setSchedulePrice(null);
                        payment.setDown(payment.getLastPrice());
                    } else {
                        payment.setDown(getNumeric(row.getCell(10)));
                        payment.setSchedule(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1(getString(row.getCell(15)).split(Pattern.quote("."))[0], "payment-schedule"));
                        payment.setPeriod(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1(getString(row.getCell(13)).split(Pattern.quote("."))[0], "payment-period"));
                        double schedulePrice = Util.schedulePrice(payment.getSchedule(), payment.getLastPrice(), payment.getDown());
                        payment.setSchedulePrice(schedulePrice<0?0:schedulePrice);
                    }
                    sales.setPayment(payment);
                    salesRepository.save(sales);


                    for(SalesInventory salesInventory: sales.getSalesInventories()){
                        List<Action> oldActions = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndInventoryAndEmployeeAndAction_Attr1AndAmountGreaterThanOrderById(true, salesInventory.getInventory(), vanLeader, "consolidate", 0);
                        if(oldActions.size()>0){
                            Action action = new Action();
                            action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("sell", "action"));
                            action.setAmount(1);
                            action.setInventory(salesInventory.getInventory());
                            action.setOrganization(sales.getOrganization());
                            action.setEmployee(vanLeader);
                            action.setSupplier(oldActions.get(0).getSupplier());
                            actionRepository.save(action);

                            Action oldAction = oldActions.get(0);
                            oldAction.setAmount(oldAction.getAmount()-1);
                            actionRepository.save(oldAction);
                        }
                    }

                    double invoicePrice = 0d;
                    if(sales.getPayment().getCash()){
                        invoicePrice = sales.getPayment().getLastPrice();
                    } else {
                        invoicePrice = sales.getPayment().getDown();
                    }

                    if(invoicePrice>0){
                        Invoice invoice = new Invoice();
                        invoice.setSales(sales);
                        invoice.setApprove(false);
                        invoice.setCreditable(true);
                        if(!sales.getService()){
                            invoice.setAdvance(true);
                        }
                        invoice.setPrice(invoicePrice);
                        invoice.setOrganization(sales.getOrganization());
                        invoice.setDescription("Satışdan əldə edilən ilkin ödəniş " + invoicePrice + " AZN");
                        invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
                        invoiceRepository.save(invoice);
                        invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
                        invoiceRepository.save(invoice);
                    }
                    sales.setApprove(true);
                    sales.setApproveDate(new Date());
                    salesRepository.save(sales);

                    System.out.println(row.getRowNum() + " yuklendi!");
                }
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
        return mapPost(redirectAttributes, "/route/sub/admin/migration");
    }

    @GetMapping("/tax/sales")
    public String salesTaxMigration() throws Exception {
        try{
            log.info("Başladı: " + new Date());
            for(Organization organization: organizationRepository.getOrganizationsByActiveTrue()){
                List<Sales> salesList = salesRepository.getSalesByActiveTrueAndApproveTrueAndSaledFalseAndOrganization(organization);
                List<TaxConfiguration> taxConfigurations = taxConfigurationRepository.getTaxConfigurationsByActiveTrueAndOrganization(organization);
                log.info("\n---------------------------"+organization.getName()+"-----------------------------\n");
                log.info("\nAktiv muqavilelerin sayi: "+salesList.size());
                if(salesList.size()>0 && taxConfigurations.size()>0){
                    for(int i=0; i<salesList.size(); i=i+taxConfigurations.size()){
                        for(int j=0; j<taxConfigurations.size(); j++){
                            try{
                                if(salesList.get(i+j)!=null){
                                    Sales sales = salesList.get(i+j);
                                    sales.setTaxConfiguration(taxConfigurations.get(j));
                                    salesRepository.save(sales);
                                    log.info(salesList.size() + "/" + (i+j+1));
                                }
                            } catch (Exception e){

                            }
                        }
                    }
                }
                log.info("\n--------------------------------------------------------\n");
            }
            log.info("Bitdi: " + new Date());
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return "redirect:/login";
    }

    private Object getCell(HSSFCell cell){
        Object object = null;
        switch (cell.getCellType()) {
            case  0:
                object = cell.getNumericCellValue();
                break;
            case 1:
                object = cell.getStringCellValue();
                break;
            case 2:
                object = cell.getCellFormula();
                break;
            case 3:
                object = cell.getCellFormula();
                break;
            case 4:
                object = cell.getBooleanCellValue();
                break;
            case 5:
                object = cell.getErrorCellValue();
                break;
            default:
                object = null;
                break;
        }

        //public final static int CELL_TYPE_NUMERIC = 0;
        //public final static int CELL_TYPE_STRING = 1;
        //public final static int CELL_TYPE_FORMULA = 2;
        //public final static int CELL_TYPE_BLANK = 3;
        //public final static int CELL_TYPE_BOOLEAN = 4;
        //public final static int CELL_TYPE_ERROR = 5;
        return object;
    }

    private double getNumeric(XSSFCell cell){
        double value = 0;
        try{
            if(cell!=null && cell.getCellType()==0){
                value = cell.getNumericCellValue();
            } else if(cell!=null && cell.getCellType()==1){
                value = Double.parseDouble(cell.getStringCellValue());
            }
        } catch (Exception e){
            
            log.error(e.getMessage(), e);
        }
        return value;
    }

    private String getString(XSSFCell cell){
        String value = "";
        if(cell!=null){
            if(cell.getCellType()==1){
                value = cell.getStringCellValue();
            } else if(cell.getCellType()==0){
                value = String.valueOf(cell.getNumericCellValue());
            }
        }
        return value;
    }

    private boolean parseCash(XSSFRow row) {
        try{
            double value = getNumeric(row.getCell(15));
            if(value!=0){
                return false;
            }
        } catch (Exception e){
            
            log.error(e.getMessage(), e);
        }
        return true;
    }

    private Employee parseEmployee(XSSFCell cell, Organization organization) {
        try{
            Person person = parsePerson2(cell);
            List<Employee> employees = new ArrayList<>();
            if(person.getFirstName()!=null && 
                    person.getLastName()!=null && 
                    person.getFirstName().trim().length()>0 && 
                    person.getLastName().trim().length()>0){
                employees = employeeRepository.getEmployeesByPersonFirstNameStartingWithAndPersonLastNameStartingWithAndOrganizationAndActiveTrue(person.getFirstName(), person.getLastName(), organization);
            } else if(employees.size()==0 &&
                    person.getFirstName()!=null &&
                    person.getFirstName().trim().length()>0){
                employees = employeeRepository.getEmployeesByPersonFirstNameStartingWithAndOrganizationAndActiveTrue(person.getFirstName(), organization);
            }
            return employees.size()>0?employees.get(0):null;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            
        }
        return null;
    }


    public Customer parseCustomer(XSSFRow row, Organization organization) {
        Customer customer = new Customer();
        customer.setOrganization(organization);
        Person person = parsePerson(row.getCell(1), row.getCell(2), row.getCell(3));
        customer.setPerson(person);
        return customer;
    }

    private Person parsePerson2(XSSFCell cell1){ //tam ad
        try{
            Person person = new Person();
            String fullName = getString(cell1);
            if(fullName.length()>5 && fullName.trim().split(" ").length>1){
                String[] names = fullName.trim().split(" ");
                person.setFirstName("");
                if(names.length>0){
                    person.setFirstName(names[0]);
                }
                person.setLastName("");
                if(names.length>1){
                    person.setLastName(names[1]);
                }
                person.setFatherName("");
                if(names.length>2){
                    person.setFatherName(names[2]);
                }
                person.setGender(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("Male", "gender"));
                if(names[0].endsWith("va")){
                    person.setGender(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("Female", "gender"));
                }
            }
            return person;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            
        }
        return null;
    }

    private Person parsePerson(XSSFCell cell1, XSSFCell cell2, XSSFCell cell3){ //tam ad, unvanlar, telefonlar
        Person person = new Person();
        String fullName = getString(cell1);
        if(fullName.length()>5 && fullName.trim().split(" ").length>1){
            String[] names = fullName.trim().split(" ");
            person.setLastName("");
            if(names.length>0){
                person.setLastName(names[0]);
            }
            person.setFirstName("");
            if(names.length>1){
                person.setFirstName(names[1]);
            }
            person.setFatherName("");
            if(names.length>2){
                person.setFatherName(names[2]);
            }
            person.setGender(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("Male", "gender"));
            if(names[0].endsWith("va")){
                person.setGender(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("Female", "gender"));
            }
            person.setContact(parseContact(cell2, cell3));
        }
        return person;
    }

    private Contact parseContact(XSSFCell cell1, XSSFCell cell2){ //unvanlar, telefonlar
        try{
            Contact contact = new Contact();
            List<String> telephones = parseTelephone(getString(cell2));
            if(telephones.size()>0){
                contact.setMobilePhone(telephones.get(0));
            }
            if(telephones.size()>1){
                contact.setHomePhone(telephones.get(1));
            }
            if(telephones.size()>2){
                contact.setRelationalPhoneNumber1(telephones.get(2));
            }
            if(telephones.size()>3){
                contact.setRelationalPhoneNumber2(telephones.get(3));
            }
            if(telephones.size()>4){
                contact.setRelationalPhoneNumber3(telephones.get(4));
            }
            List<Address> addresses = parseAddress(getString(cell1));
            if(addresses.size()>0){
                if(addresses.get(0).getCity()!=null){
                    contact.setCity(addresses.get(0).getCity());
                }
                contact.setAddress(addresses.get(0).getAddress());
            }
            if(addresses.size()>1){
                if(addresses.get(1).getCity()!=null){
                    contact.setCity(addresses.get(1).getCity());
                }
                contact.setAddress(addresses.get(1).getAddress());
            }
            return contact;
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private List<String> parseTelephone(String telephone){
        List<String> telephones = new ArrayList<>();
        try{
            String[] array1 = telephone.split(Pattern.quote("//"));
            String[] array2 = telephone.split(Pattern.quote("\\"));
            String[] telephoneArray = array1.length>array2.length?array1:array2;
            for (String s: telephoneArray){
                String number = "000" + s.replaceAll("[^0123456789]","");
                if(number.length()>8){
                    String value = "(0" + number.substring(number.length()-9, number.length()-7)+") "+number.substring(number.length()-7, number.length()-4)+"-"+number.substring(number.length()-4, number.length());
                    telephones.add(value);
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return telephones;
    }

    private List<Address> parseAddress(String address){
        List<Address> addresses = new ArrayList<>();
        try{
            String[] array1 = address.split(Pattern.quote("//"));
            String[] array2 = address.split(Pattern.quote("\\"));
            String[] addressArray = array1.length>array2.length?array1:array2;
            Address address1 = null;
            for(String s: addressArray){
                if(s.length()>3){
                    address1 = new Address();
                    List<Dictionary> dictionaries = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1AndNameStartingWithIgnoreCase("city", s.trim().substring(0, 3));
                    if(dictionaries.size()>0){
                        address1.setCity(dictionaries.get(0));
                    }
                    address1.setAddress(s);
                    addresses.add(address1);
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return addresses;
    }

    public static String stripNonDigits(
            final CharSequence input /* inspired by seh's comment */){
        final StringBuilder sb = new StringBuilder(
                input.length() /* also inspired by seh's comment */);
        for(int i = 0; i < input.length(); i++){
            final char c = input.charAt(i);
            if(c > 47 && c < 58){
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
