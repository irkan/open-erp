package com.openerp.task;

import com.openerp.entity.*;
import com.openerp.repository.*;
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

    @Scheduled(fixedDelay = 120000, initialDelay = 5000)
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
                                md.setVanLeader(parseEmployee(row.getCell(4), migration.getOrganization()));
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
                                md.setPeriod(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1(String.valueOf(md.getSalesPaymentPeriod()), "payment-period"));
                                md.setSalesPaymentSchedule(getInteger(row.getCell(15)));
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
}
