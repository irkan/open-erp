package com.openerp.task;

import com.openerp.controller.SkeletonController;
import com.openerp.domain.Address;
import com.openerp.entity.*;
import com.openerp.repository.*;
import com.openerp.util.Util;
import com.openerp.util.UtilJson;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class MigrationTask {
    private static final Logger log = Logger.getLogger(MigrationTask.class);

    @Autowired
    MigrationRepository migrationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    MigrationDetailRepository migrationDetailRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CurrencyRateRepository currencyRateRepository;

    @Autowired
    AccountRepository accountRepository;

    @Scheduled(fixedDelay = 1000000, initialDelay = 5000)
    public void writeTable() {
        try{
            log.info("Migration Task Start read data from excel and insert table");
            List<Migration> migrations = migrationRepository.getMigrationsByActiveTrueAndStatusOrderByUploadDate(0);
            for(Migration migration: migrations){
                try {
                    Path path = Files.write(Paths.get(migration.getFileName()), migration.getFileContent());
                    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(path.toFile()));
                    XSSFSheet sheet = wb.getSheetAt(0);
                    XSSFRow row;
                    Iterator rows = sheet.rowIterator();
                    while (rows.hasNext()) {
                        try{
                            row=(XSSFRow) rows.next();
                            if(row.getRowNum()>0){
                                MigrationDetail md = new MigrationDetail();
                                md.setMigration(migration);
                                md.setActive(true);
                                md.setStatus(0);
                                md.setSalesDate(row.getCell(8).getDateCellValue());
                                md.setCustomerFullName(getString(row.getCell(1)));
                                md.setCustomerContactAddress(getString(row.getCell(2)));
                                md.setCustomerContactPhoneNumbers(getString(row.getCell(3)));
                                md.setEmployeeVanLeader(getString(row.getCell(4)));
                                md.setVanLeader(parseEmployeeVanLeader(row.getCell(4), migration.getOrganization()));
                                md.setEmployeeConsole(getString(row.getCell(4)));
                                md.setConsole(parseEmployee(row.getCell(4), migration.getOrganization()));
                                md.setEmployeeCanvasser(getString(row.getCell(5)));
                                md.setCanvasser(parseEmployee(row.getCell(5), migration.getOrganization()));
                                md.setEmployeeDealer(getString(row.getCell(6)));
                                md.setDealer(parseEmployee(row.getCell(6), migration.getOrganization()));
                                md.setEmployeeServicer(getString(row.getCell(7)));
                                md.setServicer(parseEmployee(row.getCell(7), migration.getOrganization()));
                                md.setSalesPaymentLastPrice(getNumeric(row.getCell(9)));
                                md.setSalesPaymentDown(getNumeric(row.getCell(10)));
                                md.setSalesPaymentPayed(getNumeric(row.getCell(12))-md.getSalesPaymentDown());
                                md.setSalesPaymentPeriod(getInteger(row.getCell(13)));
                                md.setSalesPaymentCash(false); //Payment cash tamamlanmalidir
                                if(md.getSalesPaymentPeriod()==29){
                                    md.setSalesPaymentPeriod(1);
                                } else if(md.getSalesPaymentPeriod()==30){
                                    md.setSalesPaymentPeriod(2);
                                } else if(md.getSalesPaymentPeriod()==31){
                                    md.setSalesPaymentPeriod(3);
                                }
                                md.setPeriod(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1(String.valueOf(md.getSalesPaymentPeriod()), "payment-period"));
                                md.setSalesPaymentSchedule(getInteger(row.getCell(15)));
                                if(md.getSalesPaymentSchedule()>30){
                                    md.setSalesPaymentSchedule(30);
                                }
                                md.setSchedule(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1(String.valueOf(md.getSalesPaymentSchedule()), "payment-schedule"));

                                md.setSalesPaymentGift(md.getSalesPaymentLastPrice()==0?true:false);
                                md.setSalesInventoryName(getString(row.getCell(16)).trim().toUpperCase());

                                migrationDetailRepository.save(md);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            log.error(e.getMessage(), e);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
                migration.setStatus(1);
                migrationRepository.save(migration);
            }
            log.info("Migration Task End read data from excel and insert table");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }

    public void startMigration(Migration migration) {
        try{
            log.info("Migration Task Start read data from excel and insert table");
            for(MigrationDetail md: migration.getMigrationDetails()){
                if(md.getStatus()==0){
                    String errors = "";
                    try {
                        Employee vanLeader = md.getVanLeader();
                        md.setStatus(1);
                        if(vanLeader!=null){
                            String inventoryName = (md.getSalesInventoryName()!=null && md.getSalesInventoryName().length()>0)?md.getSalesInventoryName():"AVADANLIQ NONAME";
                            inventoryName = inventoryName.toUpperCase();
                            List<Inventory> inventories = inventoryRepository.getInventoriesByNameAndActiveTrue(inventoryName);
                            Inventory inventory = null;
                            if(inventories.size()>0){
                                inventory = inventories.get(0);
                            } else {
                                inventory = new Inventory();
                                inventory.setOrganization(migration.getOrganization());
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
                            buy.setSupplier(migration.getSupplier());
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


                            Customer customer = parseCustomer(md);
                            customerRepository.save(customer);

                            Employee canvasser = md.getCanvasser();
                            Employee dealer = md.getDealer();
                            Employee console = md.getDealer();
                            Employee servicer = md.getServicer();
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
                            sales.setConsole(console);
                            sales.setOrganization(md.getMigration().getOrganization());
                            sales.setServicer(servicer);
                            sales.setActive(true);
                            sales.setApprove(false);
                            sales.setSaled(false);
                            sales.setSaleDate(md.getSalesDate());
                            sales.setGuarantee(24);
                            sales.setGuaranteeExpire(Util.guarantee(sales.getSaleDate()==null?new Date():sales.getSaleDate(), sales.getGuarantee()));
                            Payment payment = new Payment();
                            payment.setCash(md.getSalesPaymentCash());
                            payment.setActive(true);
                            payment.setPrice(md.getSalesPaymentLastPrice());
                            payment.setLastPrice(md.getSalesPaymentLastPrice());

                            if(payment.getCash()){
                                payment.setPeriod(null);
                                payment.setSchedule(null);
                                payment.setSchedulePrice(null);
                                payment.setDown(payment.getLastPrice());
                            } else {
                                payment.setDown(md.getSalesPaymentDown());
                                payment.setSchedule(md.getSchedule());
                                payment.setPeriod(md.getPeriod());
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

                            sales.setApprove(true);
                            sales.setApproveDate(new Date());
                            salesRepository.save(sales);

                            Dictionary sell = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("sell", "action");
                            if(invoicePrice>0){
                                Invoice invoice = new Invoice();
                                invoice.setSales(sales);
                                invoice.setApprove(true);
                                invoice.setApproveDate(new Date());
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

                                Transaction transaction = new Transaction();
                                transaction.setApprove(true);
                                transaction.setApproveDate(new Date());
                                transaction.setDebt(invoice.getPrice()>0?true:false);
                                transaction.setInventory(invoice.getSales().getSalesInventories().get(0).getInventory());
                                transaction.setOrganization(invoice.getOrganization());
                                transaction.setPrice(Math.abs(invoice.getPrice()));
                                transaction.setCurrency("AZN");
                                transaction.setAccount(accountRepository.getAccountsByActiveTrueAndCurrency(transaction.getCurrency()).get(0));
                                transaction.setRate(Util.getRate(currencyRateRepository.getCurrencyRateByCode(transaction.getCurrency().toUpperCase())));
                                double sumPrice = Util.amountChecker(transaction.getAmount()) * transaction.getPrice() * transaction.getRate();
                                transaction.setSumPrice(sumPrice);
                                transaction.setAction(sell); //burda duzelis edilmelidir
                                transaction.setDescription("Satış|Servis, Kod: "+invoice.getSales().getId() + " -> "
                                        + invoice.getSales().getCustomer().getPerson().getFullName() + " -> "
                                        + " barkod: " + invoice.getSales().getSalesInventories().get(0).getInventory().getName()
                                        + " " + invoice.getSales().getSalesInventories().get(0).getInventory().getBarcode()
                                );
                                transactionRepository.save(transaction);
                                balance(transaction);
                            }

                            if(md.getSalesPaymentPayed()>0){
                                Invoice invoice = new Invoice();
                                invoice.setSales(sales);
                                invoice.setApprove(true);
                                invoice.setApproveDate(new Date());
                                invoice.setCreditable(true);
                                if(!sales.getService()){
                                    invoice.setAdvance(true);
                                }
                                invoice.setPrice(md.getSalesPaymentPayed());
                                invoice.setOrganization(sales.getOrganization());
                                invoice.setDescription("Satışdan əldə edilən ilkin ödəniş " + md.getSalesPaymentPayed() + " AZN");
                                invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
                                invoiceRepository.save(invoice);
                                invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
                                invoiceRepository.save(invoice);

                                Transaction transaction = new Transaction();
                                transaction.setApprove(true);
                                transaction.setApproveDate(new Date());
                                transaction.setDebt(invoice.getPrice()>0?true:false);
                                transaction.setInventory(invoice.getSales().getSalesInventories().get(0).getInventory());
                                transaction.setOrganization(invoice.getOrganization());
                                transaction.setPrice(Math.abs(invoice.getPrice()));
                                transaction.setCurrency("AZN");
                                transaction.setAccount(accountRepository.getAccountsByActiveTrueAndCurrency(transaction.getCurrency()).get(0));
                                transaction.setRate(Util.getRate(currencyRateRepository.getCurrencyRateByCode(transaction.getCurrency().toUpperCase())));
                                double sumPrice = Util.amountChecker(transaction.getAmount()) * transaction.getPrice() * transaction.getRate();
                                transaction.setSumPrice(sumPrice);
                                transaction.setAction(sell); //burda duzelis edilmelidir
                                transaction.setDescription("Satış|Servis, Kod: "+invoice.getSales().getId() + " -> "
                                        + invoice.getSales().getCustomer().getPerson().getFullName() + " -> "
                                        + " barkod: " + invoice.getSales().getSalesInventories().get(0).getInventory().getName()
                                        + " " + invoice.getSales().getSalesInventories().get(0).getInventory().getBarcode()
                                );
                                transactionRepository.save(transaction);
                                balance(transaction);
                            }
                        } else {
                            md.setStatus(2);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        log.error(e.getMessage(), e);
                        md.setStatus(2);
                        errors += e.getMessage() + "\n";
                    }
                    md.setErrors(errors);
                    migrationDetailRepository.save(md);
                }
            }
            log.info("Migration Task End read data from excel and insert table");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }

    public String getString(XSSFCell cell){
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

    public Employee parseEmployeeVanLeader(XSSFCell cell, Organization organization) {
        try{
            Person person = parsePerson2(cell);
            List<Employee> employees = new ArrayList<>();
            if(person.getFirstName()!=null &&
                    person.getLastName()!=null &&
                    person.getFirstName().trim().length()>0 &&
                    person.getLastName().trim().length()>0){
                employees = employeeRepository.getEmployeesByPersonFirstNameStartingWithAndPersonLastNameStartingWithAndOrganization(person.getFirstName(), person.getLastName(), organization);
                if(employees.size()==0){
                    employees = employeeRepository.getEmployeesByPersonFirstNameStartingWithAndPersonLastNameStartingWith(person.getFirstName(), person.getLastName());
                }
            } else if(employees.size()==0 &&
                    person.getFirstName()!=null &&
                    person.getFirstName().trim().length()>0){
                employees = employeeRepository.getEmployeesByPersonFirstNameStartingWithAndOrganization(person.getFirstName(), organization);
                if(employees.size()==0){
                    employees = employeeRepository.getEmployeesByPersonFirstNameStartingWith(person.getFirstName());
                }
            }
            return employees.size()>0?employees.get(0):null;
        } catch (Exception e){
            log.error(e.getMessage(), e);

        }
        return null;
    }

    public Employee parseEmployee(XSSFCell cell, Organization organization) {
        try{
            Person person = parsePerson2(cell);
            List<Employee> employees = new ArrayList<>();
            if(person.getFirstName()!=null &&
                    person.getLastName()!=null &&
                    person.getFirstName().trim().length()>0 &&
                    person.getLastName().trim().length()>0){
                employees = employeeRepository.getEmployeesByPersonFirstNameStartingWithAndPersonLastNameStartingWithAndOrganization(person.getFirstName(), person.getLastName(), organization);
            } else if(employees.size()==0 &&
                    person.getFirstName()!=null &&
                    person.getFirstName().trim().length()>0){
                employees = employeeRepository.getEmployeesByPersonFirstNameStartingWithAndOrganization(person.getFirstName(), organization);
            }
            return employees.size()>0?employees.get(0):null;
        } catch (Exception e){
            log.error(e.getMessage(), e);

        }
        return null;
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

    private double getNumeric(XSSFCell cell){
        double value = 0;
        try{
            if(cell!=null && cell.getCellType()==0){
                value = cell.getNumericCellValue();
            } else if(cell!=null && cell.getCellType()==1){
                value = Double.parseDouble(cell.getStringCellValue());
            } else if(cell!=null && cell.getCellType()==2){
                value = Double.parseDouble(cell.getRawValue());
            }
        } catch (Exception e){

            log.error(e.getMessage(), e);
        }
        return value;
    }

    private Integer getInteger(XSSFCell cell){
        Double value = 0d;
        try{
            if(cell!=null && cell.getCellType()==0){
                value = cell.getNumericCellValue();
            } else if(cell!=null && cell.getCellType()==1){
                value = Double.parseDouble(cell.getStringCellValue());
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return value.intValue();
    }

    public Customer parseCustomer(MigrationDetail migrationDetail) {
        Customer customer = new Customer();
        customer.setOrganization(migrationDetail.getMigration().getOrganization());
        Person person = parsePerson(migrationDetail.getCustomerFullName(), migrationDetail.getCustomerContactAddress(), migrationDetail.getCustomerContactPhoneNumbers());
        customer.setPerson(person);
        return customer;
    }

    private Person parsePerson(String fullName, String address, String telephones){ //tam ad, unvanlar, telefonlar
        Person person = new Person();
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
            person.setContact(parseContact(address, telephones));
        }
        return person;
    }

    private Contact parseContact(String address, String phones){ //unvanlar, telefonlar
        try{
            Contact contact = new Contact();
            List<String> telephones = parseTelephone(phones);
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
            List<Address> addresses = parseAddress(address);
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
                String number = s.replaceAll("[^0123456789]","");
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
                    List<Dictionary> dictionaries = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1AndNameStartingWith("city", s.trim().substring(0, 3));
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

    void balance(Transaction transaction){
        if(transaction!=null && transaction.getAccount()!=null){
            Account account = transaction.getAccount();
            double balance = account.getBalance() + Double.parseDouble(Util.format((transaction.getDebt() ? transaction.getSumPrice() : -1 * transaction.getSumPrice())/Util.getRate(currencyRateRepository.getCurrencyRateByCode(account.getCurrency().toUpperCase()))));
            account.setBalance(balance);
            accountRepository.save(account);
            transaction.setBalance(account.getBalance());
            transactionRepository.save(transaction);
        }
    }
}
