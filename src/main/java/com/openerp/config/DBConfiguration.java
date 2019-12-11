package com.openerp.config;

import com.openerp.util.DateUtility;
import com.openerp.entity.*;
import com.openerp.repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DBConfiguration {
    private static final Logger log = Logger.getLogger(DBConfiguration.class);

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    ModuleOperationRepository moduleOperationRepository;

    @Autowired
    UserModuleOperationRepository userModuleOperationRepository;

    @Autowired
    DictionaryTypeRepository dictionaryTypeRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PayrollConfigurationRepository payrollConfigurationRepository;

    @Autowired
    EmployeePayrollDetailRepository employeeDetailRepository;

    @Autowired
    ConfigurationRepository configurationRepository;

    @Value("${default.admin.username}")
    private String defaultAdminUsername;

    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    @PostConstruct
    public void run() {
        try{
            List<Configuration> configurations = new ArrayList<>();
            configurations.add(new Configuration("Problemli müştəri", "troubled_customer", "60", "Gecikmə 60 gündən çox olduqda müştəri problemli sayılır"));
            configurations.add(new Configuration("Hesab-faktura sayı", "invoice_count", "2", "Eyni hesab-fakturanın çap formasındakı sayı"));
            configurations.add(new Configuration("Şirkərin adı", "company_name", "Sual", "Şirkətin adı"));
            configurations.add(new Configuration("Qaynar xətt", "company_hot_line", "+994 55 546 06 61", "Şirkətin qaynar xətt nömrəsi"));
            configurations.add(new Configuration("Telefon", "company_telephone", "+994 12 480 10 76", "Şirkətin telefon nömrəsi"));
            configurations.add(new Configuration("Mobil", "company_mobile", "+994 55 546 06 61", "Şirkətin mobil nömrəsi"));
            configurations.add(new Configuration("Email", "company_email", "sual.office@gmail.com", "Şirkətin email ünvanı"));
            configurationRepository.saveAll(configurations);

            List<DictionaryType> types = new ArrayList<>();
            DictionaryType genderType = new DictionaryType("Cins", "gender", null);
            types.add(genderType);
            DictionaryType maritalStatusType = new DictionaryType("Ailə vəziyyəti", "marital-status", null);
            types.add(maritalStatusType);
            DictionaryType cityType = new DictionaryType("Şəhər", "city", null);
            types.add(cityType);
            DictionaryType positionType = new DictionaryType("Vəzifə", "position", null);
            types.add(positionType);
            DictionaryType nationalityType = new DictionaryType("Milliyət", "nationality", null);
            types.add(nationalityType);
            DictionaryType documentType = new DictionaryType("Sənəd tipi", "document-type", null);
            types.add(documentType);
            DictionaryType templateType = new DictionaryType("İcazə şablonu", "template", null);
            types.add(templateType);
            DictionaryType systemLanguage = new DictionaryType("Sistemin dili", "language", null);
            types.add(systemLanguage);
            DictionaryType organizationType = new DictionaryType("Struktur tipi", "organization-type", null);
            types.add(organizationType);
            DictionaryType actionType = new DictionaryType("Hərəkət", "action", null);
            types.add(actionType);
            DictionaryType reasonType = new DictionaryType("Səbəb", "reason", null);
            types.add(reasonType);
            DictionaryType inventoryGroupType = new DictionaryType("İnventar qrupu", "inventory-group", null);
            types.add(inventoryGroupType);
            DictionaryType currencyType = new DictionaryType("Valyuta", "currency", null);
            types.add(currencyType);
            DictionaryType shortenedTimeType = new DictionaryType("Qısaldılmış iş saatı", "shortened-time", null);
            types.add(shortenedTimeType);
            DictionaryType formulaType = new DictionaryType("Formula tipi", "formula-type", null);
            types.add(formulaType);
            DictionaryType employeePayrollDetailType = new DictionaryType("Əlavə əmək haqqı sahələri", "employee-payroll-field", null);
            types.add(employeePayrollDetailType);
            DictionaryType employeeSaleDetailType = new DictionaryType("Əlavə satış sahələri", "employee-sale-field", null);
            types.add(employeeSaleDetailType);
            DictionaryType identifierType = new DictionaryType("İdentifikator", "identifier", null);
            types.add(identifierType);
            DictionaryType weekDay = new DictionaryType("Həftənin Günü", "week-day", null);
            types.add(weekDay);
            DictionaryType salePriceType = new DictionaryType("Satış qiyməti", "sale-price", null);
            types.add(salePriceType);
            DictionaryType advanceType = new DictionaryType("Avans", "advance", null);
            types.add(advanceType);
            DictionaryType schedulePaymentType = new DictionaryType("Ödəniş qrafiki", "payment-schedule", null);
            types.add(schedulePaymentType);
            DictionaryType periodPaymentType = new DictionaryType("Ödəniş periodu", "payment-period", null);
            types.add(periodPaymentType);
            DictionaryType guaranteeType = new DictionaryType("Zəmanət müddəti", "guarantee", null);
            types.add(guaranteeType);
            DictionaryType contactChannelType = new DictionaryType("Əlaqə kanalı", "contact-channel", null);
            types.add(contactChannelType);
            DictionaryType paymentChannelType = new DictionaryType("Ödəniş kanalı", "payment-channel", null);
            types.add(paymentChannelType);
            DictionaryType monthType = new DictionaryType("Ay", "month", null);
            types.add(monthType);
            DictionaryType notificationType = new DictionaryType("Xəbərdarlıq", "notification", null);
            types.add(notificationType);

            dictionaryTypeRepository.saveAll(types);


            List<Dictionary> dictionaries = new ArrayList<>();
            Dictionary male = new Dictionary("Kişi", "Male", null, genderType);
            dictionaries.add(male);
            Dictionary female = new Dictionary("Qadın", "Female", null, genderType);
            dictionaries.add(female);
            Dictionary single = new Dictionary("Subay", "single", null, maritalStatusType);
            dictionaries.add(single);
            Dictionary married = new Dictionary("Evli", "married", null, maritalStatusType);
            dictionaries.add(married);
            Dictionary baku = new Dictionary("Bakı", "Baku", null, cityType);
            dictionaries.add(baku);
            Dictionary sumgait = new Dictionary("Sumqayıt", "Sumgait", null, cityType);
            dictionaries.add(sumgait);
            Dictionary ganja = new Dictionary("Gəncə", "Ganja", null, cityType);
            dictionaries.add(ganja);
            Dictionary lankaran = new Dictionary("Lənkəran", "Lankaran", null, cityType);
            dictionaries.add(lankaran);
            Dictionary mingacheur = new Dictionary("Mingəçevir", "Mingacheur", null, cityType);
            dictionaries.add(mingacheur);
            Dictionary khachmaz = new Dictionary("Xaçmaz", "Khachmaz", null, cityType);
            dictionaries.add(khachmaz);
            Dictionary yevlakh = new Dictionary("Yevlax", "Yevlakh", null, cityType);
            dictionaries.add(yevlakh);
            Dictionary khirdalan = new Dictionary("Xırdalan", "Khirdalan", null, cityType);
            dictionaries.add(khirdalan);
            Dictionary position1 = new Dictionary("Sistem inzibatçısı", "Administrator", null, positionType);
            dictionaries.add(position1);
            Dictionary position2 = new Dictionary("Canvasser", "Canvasser", null, positionType);
            dictionaries.add(position2);
            Dictionary position21 = new Dictionary("Baş canvasser", "Head canvasser", null, positionType);
            dictionaries.add(position21);
            Dictionary position3 = new Dictionary("Diller", "Dealer", null, positionType);
            dictionaries.add(position3);
            Dictionary position4 = new Dictionary("Mühasib", "Accauntant", null, positionType);
            dictionaries.add(position4);
            Dictionary position5 = new Dictionary("Baş mühasib", "Head  accauntant", null, positionType);
            dictionaries.add(position5);
            Dictionary position7 = new Dictionary("Anbardar", "Warehouse", null, positionType);
            dictionaries.add(position7);
            Dictionary position8 = new Dictionary("Baş anbardar", "Head warehouse", null, positionType);
            dictionaries.add(position8);
            Dictionary position9 = new Dictionary("Usta", "Master", null, positionType);
            dictionaries.add(position9);
            Dictionary position10 = new Dictionary("Yığımçı", "Collector", null, positionType);
            dictionaries.add(position10);
            Dictionary position11 = new Dictionary("Ven lider", "Van lead", null, positionType);
            dictionaries.add(position11);
            Dictionary position12 = new Dictionary("Konsul", "Consul", null, positionType);
            dictionaries.add(position12);
            Dictionary position13 = new Dictionary("Direktor", "Director", null, positionType);
            dictionaries.add(position13);
            Dictionary azerbaijanNationality = new Dictionary("Azərbaycan", "Azerbaijan", null, nationalityType);
            dictionaries.add(azerbaijanNationality);
            Dictionary russianNationality = new Dictionary("Rus", "Russian", null, nationalityType);
            dictionaries.add(russianNationality);
            Dictionary englishNationality = new Dictionary("İngilis", "English", null, nationalityType);
            dictionaries.add(englishNationality);
            Dictionary otherNationality = new Dictionary("Digər", "Other", null, nationalityType);
            dictionaries.add(otherNationality);
            Dictionary profilePicture = new Dictionary("Profil şəkli", "profile picture", null, documentType);
            dictionaries.add(profilePicture);
            Dictionary idCard = new Dictionary("Şəxsiyyət vəsiqəsi", "id card", null, documentType);
            dictionaries.add(idCard);
            Dictionary passport = new Dictionary("Pasport", "passport", null, documentType);
            dictionaries.add(passport);
            Dictionary birthLicense = new Dictionary("Doğum haqqında şəhadətnamə", "birth license", null, documentType);
            dictionaries.add(birthLicense);
            Dictionary driverLicense = new Dictionary("Sürücülük vəsiqəsi", "driver license", null, documentType);
            dictionaries.add(driverLicense);
            Dictionary template1 = new Dictionary("Şablon №1", "template 1", null, templateType);
            dictionaries.add(template1);
            Dictionary template2 = new Dictionary("Şablon №2", "template 2", null, templateType);
            dictionaries.add(template2);
            Dictionary template3 = new Dictionary("Şablon №3", "template 3", null, templateType);
            dictionaries.add(template3);
            Dictionary template4 = new Dictionary("Şablon №4", "template 4", null, templateType);
            dictionaries.add(template4);
            Dictionary azerbaijanLanguage = new Dictionary("Azərbaycan", "az", "021-azerbaijan.png", systemLanguage);
            dictionaries.add(azerbaijanLanguage);
            Dictionary russianLanguage = new Dictionary("Русский", "ru", "013-russia.svg", systemLanguage);
            dictionaries.add(russianLanguage);
            Dictionary englishLanguage = new Dictionary("English", "en", "020-flag.svg", systemLanguage);
            dictionaries.add(englishLanguage);
            Dictionary branchOrganization = new Dictionary("Flial", "branch", "flaticon-edit", organizationType);
            dictionaries.add(branchOrganization);
            Dictionary warehouseOrganization = new Dictionary("Anbar", "warehouse", "flaticon-plus", organizationType);
            dictionaries.add(warehouseOrganization);
            Dictionary departmentOrganization = new Dictionary("Departament", "department", "flaticon-plus", organizationType);
            dictionaries.add(departmentOrganization);
            Dictionary unitOrganization = new Dictionary("Şöbə", "unit", "flaticon-plus", organizationType);
            dictionaries.add(unitOrganization);
            Dictionary moveAction = new Dictionary("Daşınma", "transport", "expense", actionType);
            dictionaries.add(moveAction);
            Dictionary sendAction = new Dictionary("Göndərmə", "send", null, actionType);
            dictionaries.add(sendAction);
            Dictionary buyAction = new Dictionary("Alış", "buy", null, actionType);
            dictionaries.add(buyAction);
            Dictionary consolidateDic = new Dictionary("Təhkim edilmə", "consolidate", null, actionType);
            dictionaries.add(consolidateDic);
            Dictionary returnAction = new Dictionary("Qaytarılma", "return", null, actionType);
            dictionaries.add(returnAction);
            Dictionary sellAction = new Dictionary("Satış", "sell", null, actionType);
            dictionaries.add(sellAction);
            Dictionary cancellationAction = new Dictionary("Ləğv edilmə", "cancellation", null, actionType);
            dictionaries.add(cancellationAction);
            Dictionary customCostAction = new Dictionary("Gömrük", "custom", "expense", actionType);
            dictionaries.add(customCostAction);
            Dictionary taxExpenseAction = new Dictionary("Vergi", "tax", "expense", actionType);
            dictionaries.add(taxExpenseAction);
            Dictionary insuranceExpenseAction = new Dictionary("Sığorta", "insurance", "expense", actionType);
            dictionaries.add(insuranceExpenseAction);
            Dictionary reason1 = new Dictionary("Xarabdır", "break", null, reasonType);
            dictionaries.add(reason1);
            Dictionary inventoryGroup1 = new Dictionary("İnventar qrup #1", "group1", null, inventoryGroupType);
            dictionaries.add(inventoryGroup1);
            Dictionary inventoryGroup2 = new Dictionary("İnventar qrup #2", "group2", null, inventoryGroupType);
            dictionaries.add(inventoryGroup2);
            Dictionary inventoryGroup3 = new Dictionary("İnventar qrup #3", "group3", null, inventoryGroupType);
            dictionaries.add(inventoryGroup3);
            Dictionary inventoryGroup4 = new Dictionary("İnventar qrup #4", "group4", null, inventoryGroupType);
            dictionaries.add(inventoryGroup4);
            Dictionary inventoryGroup5 = new Dictionary("İnventar qrup #5", "group5", null, inventoryGroupType);
            dictionaries.add(inventoryGroup5);
            Dictionary AZN = new Dictionary("AZN", "AZN", null, currencyType);
            dictionaries.add(AZN);
            Dictionary USD = new Dictionary("USD", "USD", null, currencyType);
            dictionaries.add(USD);
            Dictionary EUR = new Dictionary("EUR", "EUR", null, currencyType);
            dictionaries.add(EUR);
            Dictionary GBP = new Dictionary("GBP", "GBP", null, currencyType);
            dictionaries.add(GBP);
            Dictionary RUB = new Dictionary("RUB", "RUB", null, currencyType);
            dictionaries.add(RUB);
            Dictionary TL = new Dictionary("TL", "TL", null, currencyType);
            dictionaries.add(TL);
            Dictionary hour1 = new Dictionary("1 saat", "1", null, shortenedTimeType);
            dictionaries.add(hour1);
            Dictionary hour2 = new Dictionary("2 saat", "2", null, shortenedTimeType);
            dictionaries.add(hour2);
            Dictionary hour3 = new Dictionary("3 saat", "3", null, shortenedTimeType);
            dictionaries.add(hour3);
            Dictionary hour4 = new Dictionary("4 saat", "4", null, shortenedTimeType);
            dictionaries.add(hour4);
            Dictionary hour5 = new Dictionary("5 saat", "5", null, shortenedTimeType);
            dictionaries.add(hour5);
            Dictionary hour6 = new Dictionary("6 saat", "6", null, shortenedTimeType);
            dictionaries.add(hour6);
            Dictionary hour7 = new Dictionary("7 saat", "7", null, shortenedTimeType);
            dictionaries.add(hour7);
            Dictionary hour8 = new Dictionary("8 saat", "8", null, shortenedTimeType);
            dictionaries.add(hour8);
            Dictionary formulaType1 = new Dictionary("Maaş", "salary", null, formulaType);
            dictionaries.add(formulaType1);
            Dictionary formulaType2 = new Dictionary("Məzuniyyət günü", "vacation-day", null, formulaType);
            dictionaries.add(formulaType2);
            Dictionary formulaType3 = new Dictionary("Məzuniyyət haqqı", "vacation", null, formulaType);
            dictionaries.add(formulaType3);
            Dictionary employeePayrollDetail1 = new Dictionary("Ümumi əmək haqqı (rəsmi)", "{gross_salary}", "0", employeePayrollDetailType);
            dictionaries.add(employeePayrollDetail1);
            Dictionary employeePayrollDetail2 = new Dictionary("Ümumi əmək haqqı", "{salary}", "0", employeePayrollDetailType);
            dictionaries.add(employeePayrollDetail2);
            Dictionary employeePayrollDetail3 = new Dictionary("Keçmiş iş stajı (illərin cəmi)", "{previous_work_experience}", "0", employeePayrollDetailType);
            dictionaries.add(employeePayrollDetail3);
            Dictionary employeePayrollDetail4 = new Dictionary("Həmkərlar ittifaqı, üzvülük haqqı", "{membership_fee_for_trade_union_fee}", "0", employeePayrollDetailType);
            dictionaries.add(employeePayrollDetail4);
            Dictionary employeePayrollDetail5 = new Dictionary("Güzəşt", "{allowance}", "0", employeePayrollDetailType);
            dictionaries.add(employeePayrollDetail5);
            Dictionary employeePayrollDetail6 = new Dictionary("Əsas məzuniyyət günlərinin sayı", "{main_vacation_days}", "21", employeePayrollDetailType);
            dictionaries.add(employeePayrollDetail6);
            Dictionary employeePayrollDetail7 = new Dictionary("Əlavə məzuniyyət günlərinin sayı", "{additional_vacation_days}", "0", employeePayrollDetailType);
            dictionaries.add(employeePayrollDetail7);
            Dictionary employeeSaleDetail1 = new Dictionary("Canvasser", "{canvasser}", "{sale_price}>1698?30:25", employeeSaleDetailType);
            dictionaries.add(employeeSaleDetail1);
            Dictionary employeeSaleDetail2 = new Dictionary("Diller", "{dealer}", "{sale_price}>1698?120:100", employeeSaleDetailType);
            dictionaries.add(employeeSaleDetail2);
            Dictionary employeeSaleDetail3 = new Dictionary("Ven lider", "{van_leader}", "{sale_price}>1698?50:40", employeeSaleDetailType);
            dictionaries.add(employeeSaleDetail3);
            Dictionary employeeSaleDetail4 = new Dictionary("Konsul", "{consul}", "30", employeeSaleDetailType);
            dictionaries.add(employeeSaleDetail4);
            Dictionary identifier6 = new Dictionary("İş Günü", "İG", null, identifierType);
            dictionaries.add(identifier6);
            Dictionary identifier8 = new Dictionary("Qısaldılmış İş Günü (Səhər)", "QİG(S)", null, identifierType);
            dictionaries.add(identifier8);
            Dictionary identifier9 = new Dictionary("Qısaldılmış İş Günü (Axşam)", "QİG(A)", null, identifierType);
            dictionaries.add(identifier9);
            Dictionary identifier10 = new Dictionary("1-ci növbə", "I", null, identifierType);
            dictionaries.add(identifier10);
            Dictionary identifier11 = new Dictionary("2-ci növbə", "II", null, identifierType);
            dictionaries.add(identifier11);
            Dictionary identifier1 = new Dictionary("İstirahət", "İ", null, identifierType);
            dictionaries.add(identifier1);
            Dictionary identifier2 = new Dictionary("Bayram", "B", null, identifierType);
            dictionaries.add(identifier2);
            Dictionary identifier7 = new Dictionary("Qeyri İş Günü", "QİG", null, identifierType);
            dictionaries.add(identifier7);
            Dictionary identifier4 = new Dictionary("Məzuniyyət", "M", "vacation", identifierType);
            dictionaries.add(identifier4);
            Dictionary identifier18 = new Dictionary("Daxili Məzuniyyət", "DM", "vacation", identifierType);
            dictionaries.add(identifier18);
            Dictionary identifier12 = new Dictionary("Ödənişsiz Məzuniyyət", "ÖM", "vacation", identifierType);
            dictionaries.add(identifier12);
            Dictionary identifier14 = new Dictionary("Rəhbər Məzuniyyəti", "RM", "vacation", identifierType);
            dictionaries.add(identifier14);
            Dictionary identifier5 = new Dictionary("Analıq Məzuniyyəti", "AM", "vacation", identifierType);
            dictionaries.add(identifier5);
            Dictionary identifier19 = new Dictionary("Təhsil Məzuniyyəti", "TM", "vacation", identifierType);
            dictionaries.add(identifier19);
            Dictionary identifier13 = new Dictionary("Ezamiyyət", "E", "business-trip", identifierType);
            dictionaries.add(identifier13);
            Dictionary identifier15 = new Dictionary("Xəstəlik", "X", "illness", identifierType);
            dictionaries.add(identifier15);
            Dictionary identifier20 = new Dictionary("Hesablanmış Məzuniyyət Qalığı", "HMQ", null, identifierType);
            dictionaries.add(identifier20);
            Dictionary identifier16 = new Dictionary("Ümumi İş Günü", "ÜİG", null, identifierType);
            dictionaries.add(identifier16);
            Dictionary identifier17 = new Dictionary("Hesablanmış İş Günü", "HİG", null, identifierType);
            dictionaries.add(identifier17);
            Dictionary weekDay6 = new Dictionary("Şənbə", "ş", "6", weekDay);
            dictionaries.add(weekDay6);
            Dictionary weekDay0 = new Dictionary("Bazar", "b", "0", weekDay);
            dictionaries.add(weekDay0);
            Dictionary weekDay1 = new Dictionary("Bazar Ertəsi", "b.e", "1", weekDay);
            dictionaries.add(weekDay1);
            Dictionary weekDay2 = new Dictionary("Çərşənbə Axşamı", "ç.a", "2", weekDay);
            dictionaries.add(weekDay2);
            Dictionary weekDay3 = new Dictionary("Çərşənbə", "ç", "3", weekDay);
            dictionaries.add(weekDay3);
            Dictionary weekDay4 = new Dictionary("Cümə Axşamı", "c.a", "4", weekDay);
            dictionaries.add(weekDay4);
            Dictionary weekDay5 = new Dictionary("Cümə", "c", "5", weekDay);
            dictionaries.add(weekDay5);
            Dictionary salePrice1 = new Dictionary("1499 AZN", "1499", null, salePriceType);
            dictionaries.add(salePrice1);
            Dictionary salePrice2 = new Dictionary("1599 AZN", "1599", null, salePriceType);
            dictionaries.add(salePrice2);
            Dictionary salePrice3 = new Dictionary("1699 AZN", "1699", null, salePriceType);
            dictionaries.add(salePrice3);
            Dictionary advance1 = new Dictionary("Məzuniyyət", "vacation-advance", null, advanceType);
            dictionaries.add(advance1);
            Dictionary advance2 = new Dictionary("Ezamiyyət", "business-trip-advance", null, advanceType);
            dictionaries.add(advance2);
            Dictionary advance3 = new Dictionary("Xəstəlik", "illness-advance", null, advanceType);
            dictionaries.add(advance3);
            Dictionary advance4 = new Dictionary("Bonus - Satışdan", "bonus-sale-advance", null, advanceType);
            dictionaries.add(advance4);
            Dictionary advance5 = new Dictionary("Bonus - Gəlirdən", "bonus-revenue-advance", null, advanceType);
            dictionaries.add(advance5);
            Dictionary schedulePayment2 = new Dictionary("2 ay", "2", null, schedulePaymentType);
            dictionaries.add(schedulePayment2);
            Dictionary schedulePayment3 = new Dictionary("3 ay", "3", null, schedulePaymentType);
            dictionaries.add(schedulePayment3);
            Dictionary schedulePayment6 = new Dictionary("6 ay", "6", null, schedulePaymentType);
            dictionaries.add(schedulePayment6);
            Dictionary schedulePayment9 = new Dictionary("9 ay", "9", null, schedulePaymentType);
            dictionaries.add(schedulePayment9);
            Dictionary schedulePayment12 = new Dictionary("12 ay", "12", null, schedulePaymentType);
            dictionaries.add(schedulePayment12);
            Dictionary schedulePayment15 = new Dictionary("15 ay", "15", null, schedulePaymentType);
            dictionaries.add(schedulePayment15);
            Dictionary schedulePayment18 = new Dictionary("18 ay", "18", null, schedulePaymentType);
            dictionaries.add(schedulePayment18);
            Dictionary schedulePayment21 = new Dictionary("21 ay", "21", null, schedulePaymentType);
            dictionaries.add(schedulePayment21);
            Dictionary schedulePayment24 = new Dictionary("24 ay", "24", null, schedulePaymentType);
            dictionaries.add(schedulePayment24);
            Dictionary schedulePayment27 = new Dictionary("27 ay", "27", null, schedulePaymentType);
            dictionaries.add(schedulePayment27);
            Dictionary schedulePayment30 = new Dictionary("30 ay", "30", null, schedulePaymentType);
            dictionaries.add(schedulePayment30);
            Dictionary periodPayment1 = new Dictionary("Hər ayın 1-i", "1", null, periodPaymentType);
            dictionaries.add(periodPayment1);
            Dictionary periodPayment2 = new Dictionary("Hər ayın 2-si", "2", null, periodPaymentType);
            dictionaries.add(periodPayment2);
            Dictionary periodPayment3 = new Dictionary("Hər ayın 3-ü", "3", null, periodPaymentType);
            dictionaries.add(periodPayment3);
            Dictionary periodPayment4 = new Dictionary("Hər ayın 4-ü", "4", null, periodPaymentType);
            dictionaries.add(periodPayment4);
            Dictionary periodPayment5 = new Dictionary("Hər ayın 5-i", "5", null, periodPaymentType);
            dictionaries.add(periodPayment5);
            Dictionary periodPayment10 = new Dictionary("Hər ayın 10-u", "10", null, periodPaymentType);
            dictionaries.add(periodPayment10);
            Dictionary periodPayment15 = new Dictionary("Hər ayın 15-i", "15", null, periodPaymentType);
            dictionaries.add(periodPayment15);
            Dictionary periodPayment20 = new Dictionary("Hər ayın 20-si", "20", null, periodPaymentType);
            dictionaries.add(periodPayment20);
            Dictionary periodPayment25 = new Dictionary("Hər ayın 25-i", "25", null, periodPaymentType);
            dictionaries.add(periodPayment25);
            Dictionary guarantee24 = new Dictionary("24 ay", "24", null, guaranteeType);
            dictionaries.add(guarantee24);
            Dictionary contactChannel1 = new Dictionary("Zəng", "call", null, contactChannelType);
            dictionaries.add(contactChannel1);
            Dictionary contactChannel2 = new Dictionary("Whatsapp", "whatsapp", null, contactChannelType);
            dictionaries.add(contactChannel2);
            Dictionary contactChannel3 = new Dictionary("Email", "Email", null, contactChannelType);
            dictionaries.add(contactChannel3);
            Dictionary paymentChannel1 = new Dictionary("Nağd", "cash", null, paymentChannelType);
            dictionaries.add(paymentChannel1);
            Dictionary paymentChannel2 = new Dictionary("MilliÖN", "million", null, paymentChannelType);
            dictionaries.add(paymentChannel2);
            Dictionary month1 = new Dictionary("Yanvar", "january", "1", monthType);
            dictionaries.add(month1);
            Dictionary month2 = new Dictionary("Fevral", "february", "2", monthType);
            dictionaries.add(month2);
            Dictionary month3 = new Dictionary("Mart", "march", "3", monthType);
            dictionaries.add(month3);
            Dictionary month4 = new Dictionary("Aprel", "april", "4", monthType);
            dictionaries.add(month4);
            Dictionary month5 = new Dictionary("May", "may", "5", monthType);
            dictionaries.add(month5);
            Dictionary month6 = new Dictionary("İyun", "june", "6", monthType);
            dictionaries.add(month6);
            Dictionary month7 = new Dictionary("İyul", "jule", "7", monthType);
            dictionaries.add(month7);
            Dictionary month8 = new Dictionary("Avqust", "august", "8", monthType);
            dictionaries.add(month8);
            Dictionary month9 = new Dictionary("Sentyabr", "september", "9", monthType);
            dictionaries.add(month9);
            Dictionary month10 = new Dictionary("Oktyabr", "october", "10", monthType);
            dictionaries.add(month10);
            Dictionary month11 = new Dictionary("Noyabr", "november", "11", monthType);
            dictionaries.add(month11);
            Dictionary month12 = new Dictionary("Dekabr", "december", "12", monthType);
            dictionaries.add(month12);
            Dictionary notification1 = new Dictionary("SMS", "sms", null, notificationType);
            dictionaries.add(notification1);
            Dictionary notification2 = new Dictionary("Email", "email", null, notificationType);
            dictionaries.add(notification2);

            dictionaryRepository.saveAll(dictionaries);


            List<Module> modules = new ArrayList<>();
            Module module = new Module("İnzibatçı", "Sistemə nəzarət", "admin", "flaticon-settings-1", null);
            modules.add(module);
            Module subModule8 = new Module("Modul və Əməliyyat", "Modul və Əməliyyat", "module-operation", "flaticon-interface-10", module);
            modules.add(subModule8);
            Module subModule1 = new Module("Modul", "Modul", "module", "flaticon-menu-button", subModule8);
            modules.add(subModule1);
            Module subModule4 = new Module("Əməliyyat", "Əməliyyat", "operation", "flaticon-interface-7", subModule8);
            modules.add(subModule4);
            Module subModule2 = new Module("Sorğu", "Sorğu", "dictionary", "flaticon-folder-1", module);
            modules.add(subModule2);
            Module subModule3 = new Module("Sorğu tipi", "Sorğu tipi", "dictionary-type", "flaticon-layers", subModule2);
            modules.add(subModule3);
            Module configuration = new Module("Sazlama", "Sazlama", "configuration", "flaticon-settings", module);
            modules.add(configuration);
            Module subModule5 = new Module("İstifadəçi", "İstifadəçi", "user", "flaticon-users", module);
            modules.add(subModule5);
            Module subModule9 = new Module("İstifadəçi icazəsi", "İstifadəçi icazəsi", "user-module-operation", "flaticon-clipboard", subModule5);
            modules.add(subModule9);
            Module subModule10 = new Module("İcazə şablonu", "İcazə şablonu", "template-module-operation", "flaticon-squares-3", subModule5);
            modules.add(subModule10);
            Module notification = new Module("Xəbərdarlıq", "Xəbərdarlıq", "notification", "flaticon2-black-back-closed-envelope-shape", module);
            modules.add(notification);
            Module module1 = new Module("İnsan Resursu", "İnsan Resursu", "hr", "flaticon-profile-1", null);
            modules.add(module1);
            Module subModule6 = new Module("Struktur", "Struktur", "organization", "flaticon-map", module1);
            modules.add(subModule6);
            Module employee = new Module("Əməkdaş", "Əməkdaş", "employee", "flaticon-users", module1);
            modules.add(employee);
            Module subModule11 = new Module("İkon", "İkon", "flat-icon", "flaticon-paper-plane-1", module);
            modules.add(subModule11);
            Module subModule12 = new Module("Flat ikon", "Flat ikon", "flat-icon", null, subModule11);
            modules.add(subModule12);
            Module subModule13 = new Module("LineAwesome ikon", "LineAwesome ikon", "line-awesome-icon", null, subModule11);
            modules.add(subModule13);
            Module warehouse = new Module("Satınalma & Anbar", "İnventarın idarə edilməsi", "warehouse", "flaticon-home-2", null);
            modules.add(warehouse);
            Module inventory = new Module("İnventar", "İnventar", "inventory", "flaticon-tool", warehouse);
            modules.add(inventory);
            Module consolidate = new Module("Təhkim edilənlər", "Təhkim edilənlər", "consolidate", "flaticon2-medical-records", warehouse);
            modules.add(consolidate);
            Module action = new Module("Hərəkət", "Hərəkət", "action", "flaticon2-delivery-truck", inventory);
            modules.add(action);
            Module supplier = new Module("Tədarükçü", "Tədarükçü", "supplier", "flaticon-network", warehouse);
            modules.add(supplier);
            Module accounting = new Module("Mühasibatlıq", "Mühasibatlıq", "accounting", "flaticon2-line-chart", null);
            modules.add(accounting);
            Module dashboard = new Module("Panel", "Qrafik status", "dashboard", "flaticon-analytics", null);
            modules.add(dashboard);
            Module currencyRate = new Module("Valyuta kursu", "Valyuta kursu", "currency-rate", "la la-euro", module);
            modules.add(currencyRate);
            Module transaction = new Module("Tranzaksiya", "Tranzaksiya", "transaction", "la la-money", accounting);
            modules.add(transaction);
            Module account = new Module("Hesab", "Hesab", "account", "la la-bank", accounting);
            modules.add(account);
            Module financing = new Module("Maliyyətləndirmə", "Maliyyətləndirmə", "financing", "la la-eur", accounting);
            modules.add(financing);
            Module nonWorkingDay = new Module("Qeyri iş günü", "Qeyri iş günü", "non-working-day", "la la-calendar", module1);
            modules.add(nonWorkingDay);
            Module shortenedWorkingDay = new Module("Qısaldılmış iş günü", "Qısaldılmış iş günü", "shortened-working-day", "la la-calendar-minus-o", nonWorkingDay);
            modules.add(shortenedWorkingDay);
            Module workAttendance = new Module("İşə davamiyyət", "İşə davamiyyət", "work-attendance", "la la-calendar", module1);
            modules.add(workAttendance);
            Module vacation = new Module("Məzuniyyət", "Məzuniyyət", "vacation", "la la-calendar", workAttendance);
            modules.add(vacation);
            Module businessTrip = new Module("Ezamiyyət", "Ezamiyyət", "business-trip", "la la-calendar", workAttendance);
            modules.add(businessTrip);
            Module illness = new Module("Xəstəlik", "Xəstəlik", "illness", "la la-calendar", workAttendance);
            modules.add(illness);
            Module payroll = new Module("Əmək haqqı", "Maaşların hesablanması", "payroll", "flaticon-security", null);
            modules.add(payroll);
            Module workingHourRecord = new Module("İş vaxtının uçotu", "İş vaxtının uçotu", "working-hour-record", "flaticon2-schedule", payroll);
            modules.add(workingHourRecord);
            Module advance = new Module("Avans", "Avans", "advance", "flaticon2-percentage", payroll);
            modules.add(advance);
            Module salary = new Module("Maaş", "Maaş", "salary", "flaticon-rotate", payroll);
            modules.add(salary);
            Module payrollConfiguration = new Module("Sazlama", "Sazlama", "payroll-configuration", "flaticon2-settings", payroll);
            modules.add(payrollConfiguration);
            Module sale = new Module("Satış", "Satışın idarə edilməsi", "sale", "flaticon2-delivery-truck", null);
            modules.add(sale);
            Module sales = new Module("Satış", "Satış", "sales", "flaticon-price-tag", sale);
            modules.add(sales);
            Module calculator = new Module("Kalkulyator", "Kalkulyator", "calculator", "la la-calculator", sale);
            modules.add(calculator);
            Module invoice = new Module("Hesab-faktura", "Hesab-faktura", "invoice", "flaticon2-group", sale);
            modules.add(invoice);
            Module collect = new Module("Yığım", "Yığımların idarə edilməsi", "collect", "flaticon2-cardiogram", null);
            modules.add(collect);
            Module paymentRegulator = new Module("Ödəniş requlyatoru", "Ödəniş requlyatoru", "payment-regulator", "flaticon-book", collect);
            modules.add(paymentRegulator);
            Module paymentRegulatorNote = new Module("Əlaqə tarixçəsi", "Əlaqə tarixçəsi", "payment-regulator-note", "flaticon-whatsapp", paymentRegulator);
            modules.add(paymentRegulatorNote);
            Module troubledCustomer = new Module("Problemli müştəri", "Problemli müştəri", "troubled-customer", "flaticon2-warning", collect);
            modules.add(troubledCustomer);
            Module idgroup = new Module("İD Qrup", "Daxili sistem və inteqrasiyalar", "idgroup", "flaticon2-analytics-2", null);
            modules.add(idgroup);
            Module item = new Module("İnventar", "İnventar", "item", "flaticon2-open-box", idgroup);
            modules.add(item);
            Module crm = new Module("CRM", "CRM", "crm", "flaticon-rotate", null);
            modules.add(crm);
            Module customer = new Module("Müştəri", "Müştəri", "customer", "flaticon2-group", crm);
            modules.add(customer);

            moduleRepository.saveAll(modules);

            List<Operation> operations = new ArrayList<>();
            Operation view = new Operation("Baxış", "view", "flaticon-medical");
            operations.add(view);
            Operation create = new Operation("Yarat", "create", "flaticon-plus");
            operations.add(create);
            Operation edit = new Operation("Redaktə", "edit", "flaticon-edit-1");
            operations.add(edit);
            Operation delete = new Operation("Sil", "delete", "flaticon-delete");
            operations.add(delete);
            Operation export = new Operation("İxrac", "export", "flaticon2-print");
            operations.add(export);
            Operation approve = new Operation("Təsdiq", "approve", "flaticon2-check-mark");
            operations.add(approve);
            Operation cancel = new Operation("Təsdiqi ləğv", "cancel", "flaticon2-cancel-music");
            operations.add(cancel);
            Operation upload = new Operation("Yüklə", "upload", "flaticon-upload");
            operations.add(upload);
            Operation reload = new Operation("Yenilə", "reload", "flaticon2-reload");
            operations.add(reload);
            Operation actions = new Operation("Hərəkətlər", "actions", "flaticon-logout");
            operations.add(actions);
            Operation search = new Operation("Axtar", "search", "la la-search");
            operations.add(search);
            Operation payrollOperation = new Operation("Əmək haqqı detalları", "payroll", "flaticon-security");
            operations.add(payrollOperation);
            Operation saleOperation = new Operation("Satış detalları", "sale", "flaticon2-delivery-truck");
            operations.add(saleOperation);
            Operation save = new Operation("Yadda saxla", "save", "la la-save");
            operations.add(save);
            Operation calculate = new Operation("Hesabla", "calculate", "la la-calculator");
            operations.add(calculate);
            Operation transfer = new Operation("Göndərmə", "transfer", "flaticon-reply");
            operations.add(transfer);
            Operation consolidateOperation = new Operation("Təhkim edilmə", "consolidate", "la la-user");
            operations.add(consolidateOperation);
            Operation returnOperation = new Operation("Qaytarılma", "return", "flaticon2-fast-back");
            operations.add(returnOperation);
            Operation detail = new Operation("Detal", "detail", "la la-external-link");
            operations.add(detail);

            operationRepository.saveAll(operations);

            List<ModuleOperation> moduleOperations = new ArrayList<>();

            ModuleOperation createModuleOperation1 = new ModuleOperation(subModule1, create, null);
            moduleOperations.add(createModuleOperation1);
            ModuleOperation createModuleOperation2 = new ModuleOperation(subModule2, create, null);
            moduleOperations.add(createModuleOperation2);
            ModuleOperation createModuleOperation3 = new ModuleOperation(subModule3, create, null);
            moduleOperations.add(createModuleOperation3);
            ModuleOperation createModuleOperation4 = new ModuleOperation(subModule4, create, null);
            moduleOperations.add(createModuleOperation4);
            ModuleOperation createModuleOperation5 = new ModuleOperation(subModule5, create, null);
            moduleOperations.add(createModuleOperation5);
            ModuleOperation createModuleOperation6 = new ModuleOperation(subModule6, create, null);
            moduleOperations.add(createModuleOperation6);
            ModuleOperation createModuleOperation7 = new ModuleOperation(employee, create, null);
            moduleOperations.add(createModuleOperation7);
            ModuleOperation createModuleOperation8 = new ModuleOperation(subModule8, create, null);
            moduleOperations.add(createModuleOperation8);
            ModuleOperation createModuleOperation9 = new ModuleOperation(inventory, create, null);
            moduleOperations.add(createModuleOperation9);
            ModuleOperation createModuleOperation10 = new ModuleOperation(supplier, create, null);
            moduleOperations.add(createModuleOperation10);
            ModuleOperation createModuleOperation13 = new ModuleOperation(account, create, null);
            moduleOperations.add(createModuleOperation13);
            ModuleOperation createModuleOperation14 = new ModuleOperation(transaction, create, null);
            moduleOperations.add(createModuleOperation14);
            ModuleOperation createModuleOperation15 = new ModuleOperation(nonWorkingDay, create, null);
            moduleOperations.add(createModuleOperation15);
            ModuleOperation createModuleOperation16 = new ModuleOperation(shortenedWorkingDay, create, null);
            moduleOperations.add(createModuleOperation16);
            ModuleOperation createModuleOperation18 = new ModuleOperation(vacation, create, null);
            moduleOperations.add(createModuleOperation18);
            ModuleOperation createModuleOperation19 = new ModuleOperation(businessTrip, create, null);
            moduleOperations.add(createModuleOperation19);
            ModuleOperation createModuleOperation20 = new ModuleOperation(payrollConfiguration, create, null);
            moduleOperations.add(createModuleOperation20);
            ModuleOperation createModuleOperation21 = new ModuleOperation(illness, create, null);
            moduleOperations.add(createModuleOperation21);
            ModuleOperation createModuleOperation22 = new ModuleOperation(advance, create, null);
            moduleOperations.add(createModuleOperation22);
            ModuleOperation createModuleOperation24 = new ModuleOperation(invoice, create, null);
            moduleOperations.add(createModuleOperation24);
            ModuleOperation createModuleOperation25 = new ModuleOperation(sales, create, null);
            moduleOperations.add(createModuleOperation25);
            ModuleOperation createModuleOperation26 = new ModuleOperation(item, create, null);
            moduleOperations.add(createModuleOperation26);
            ModuleOperation createModuleOperation27 = new ModuleOperation(paymentRegulatorNote, create, null);
            moduleOperations.add(createModuleOperation27);
            ModuleOperation createModuleOperation28 = new ModuleOperation(customer, create, null);
            moduleOperations.add(createModuleOperation28);
            ModuleOperation createModuleOperation29 = new ModuleOperation(configuration, create, null);
            moduleOperations.add(createModuleOperation29);
            ModuleOperation createModuleOperation30 = new ModuleOperation(notification, create, null);
            moduleOperations.add(createModuleOperation30);


            ModuleOperation editModuleOperation1 = new ModuleOperation(subModule1, edit, null);
            moduleOperations.add(editModuleOperation1);
            ModuleOperation editModuleOperation2 = new ModuleOperation(subModule2, edit, null);
            moduleOperations.add(editModuleOperation2);
            ModuleOperation editModuleOperation3 = new ModuleOperation(subModule3, edit, null);
            moduleOperations.add(editModuleOperation3);
            ModuleOperation editModuleOperation4 = new ModuleOperation(subModule4, edit, null);
            moduleOperations.add(editModuleOperation4);
            ModuleOperation editModuleOperation6 = new ModuleOperation(subModule6, edit, null);
            moduleOperations.add(editModuleOperation6);
            ModuleOperation editModuleOperation7 = new ModuleOperation(employee, edit, null);
            moduleOperations.add(editModuleOperation7);
            ModuleOperation editModuleOperation8 = new ModuleOperation(subModule8, edit, null);
            moduleOperations.add(editModuleOperation8);
            ModuleOperation editModuleOperation9 = new ModuleOperation(inventory, edit, null);
            moduleOperations.add(editModuleOperation9);
            ModuleOperation editModuleOperation10 = new ModuleOperation(supplier, edit, null);
            moduleOperations.add(editModuleOperation10);
            ModuleOperation editModuleOperation13 = new ModuleOperation(account, edit, null);
            moduleOperations.add(editModuleOperation13);
            ModuleOperation editModuleOperation14 = new ModuleOperation(transaction, edit, null);
            moduleOperations.add(editModuleOperation14);
            ModuleOperation editModuleOperation15 = new ModuleOperation(nonWorkingDay, edit, null);
            moduleOperations.add(editModuleOperation15);
            ModuleOperation editModuleOperation16 = new ModuleOperation(shortenedWorkingDay, edit, null);
            moduleOperations.add(editModuleOperation16);
            ModuleOperation editModuleOperation18 = new ModuleOperation(vacation, edit, null);
            moduleOperations.add(editModuleOperation18);
            ModuleOperation editModuleOperation19 = new ModuleOperation(businessTrip, edit, null);
            moduleOperations.add(editModuleOperation19);
            ModuleOperation editModuleOperation20 = new ModuleOperation(payrollConfiguration, edit, null);
            moduleOperations.add(editModuleOperation20);
            ModuleOperation editModuleOperation21 = new ModuleOperation(illness, edit, null);
            moduleOperations.add(editModuleOperation21);
            ModuleOperation editModuleOperation22 = new ModuleOperation(advance, edit, null);
            moduleOperations.add(editModuleOperation22);
            ModuleOperation editModuleOperation23 = new ModuleOperation(action, edit, null);
            moduleOperations.add(editModuleOperation23);
            ModuleOperation editModuleOperation24 = new ModuleOperation(invoice, edit, null);
            moduleOperations.add(editModuleOperation24);
            ModuleOperation editModuleOperation25 = new ModuleOperation(sales, edit, null);
            moduleOperations.add(editModuleOperation25);
            ModuleOperation editModuleOperation26 = new ModuleOperation(item, edit, null);
            moduleOperations.add(editModuleOperation26);
            ModuleOperation editModuleOperation27 = new ModuleOperation(paymentRegulatorNote, edit, null);
            moduleOperations.add(editModuleOperation27);
            ModuleOperation editModuleOperation28 = new ModuleOperation(customer, edit, null);
            moduleOperations.add(editModuleOperation28);
            ModuleOperation editModuleOperation29 = new ModuleOperation(configuration, edit, null);
            moduleOperations.add(editModuleOperation29);

            ModuleOperation deleteModuleOperation1 = new ModuleOperation(subModule1, delete, null);
            moduleOperations.add(deleteModuleOperation1);
            ModuleOperation deleteModuleOperation2 = new ModuleOperation(subModule2, delete, null);
            moduleOperations.add(deleteModuleOperation2);
            ModuleOperation deleteModuleOperation3 = new ModuleOperation(subModule3, delete, null);
            moduleOperations.add(deleteModuleOperation3);
            ModuleOperation deleteModuleOperation4 = new ModuleOperation(subModule4, delete, null);
            moduleOperations.add(deleteModuleOperation4);
            ModuleOperation deleteModuleOperation5 = new ModuleOperation(subModule5, delete, null);
            moduleOperations.add(deleteModuleOperation5);
            ModuleOperation deleteModuleOperation6 = new ModuleOperation(subModule6, delete, null);
            moduleOperations.add(deleteModuleOperation6);
            ModuleOperation deleteModuleOperation7 = new ModuleOperation(employee, delete, null);
            moduleOperations.add(deleteModuleOperation7);
            ModuleOperation deleteModuleOperation8 = new ModuleOperation(subModule8, delete, null);
            moduleOperations.add(deleteModuleOperation8);
            ModuleOperation deleteModuleOperation9 = new ModuleOperation(inventory, delete, null);
            moduleOperations.add(deleteModuleOperation9);
            ModuleOperation deleteModuleOperation10 = new ModuleOperation(supplier, delete, null);
            moduleOperations.add(deleteModuleOperation10);
            ModuleOperation deleteModuleOperation13 = new ModuleOperation(account, delete, null);
            moduleOperations.add(deleteModuleOperation13);
            ModuleOperation deleteModuleOperation14 = new ModuleOperation(transaction, delete, null);
            moduleOperations.add(deleteModuleOperation14);
            ModuleOperation deleteModuleOperation15 = new ModuleOperation(nonWorkingDay, delete, null);
            moduleOperations.add(deleteModuleOperation15);
            ModuleOperation deleteModuleOperation16 = new ModuleOperation(shortenedWorkingDay, delete, null);
            moduleOperations.add(deleteModuleOperation16);
            ModuleOperation deleteModuleOperation18 = new ModuleOperation(vacation, delete, null);
            moduleOperations.add(deleteModuleOperation18);
            ModuleOperation deleteModuleOperation19 = new ModuleOperation(businessTrip, delete, null);
            moduleOperations.add(deleteModuleOperation19);
            ModuleOperation deleteModuleOperation20 = new ModuleOperation(payrollConfiguration, delete, null);
            moduleOperations.add(deleteModuleOperation20);
            ModuleOperation deleteModuleOperation21 = new ModuleOperation(workingHourRecord, delete, null);
            moduleOperations.add(deleteModuleOperation21);
            ModuleOperation deleteModuleOperation22 = new ModuleOperation(illness, delete, null);
            moduleOperations.add(deleteModuleOperation22);
            ModuleOperation deleteModuleOperation23 = new ModuleOperation(advance, delete, null);
            moduleOperations.add(deleteModuleOperation23);
            ModuleOperation deleteModuleOperation24 = new ModuleOperation(salary, delete, null);
            moduleOperations.add(deleteModuleOperation24);
            ModuleOperation deleteModuleOperation25 = new ModuleOperation(action, delete, null);
            moduleOperations.add(deleteModuleOperation25);
            ModuleOperation deleteModuleOperation26 = new ModuleOperation(invoice, delete, null);
            moduleOperations.add(deleteModuleOperation26);
            ModuleOperation deleteModuleOperation27 = new ModuleOperation(sales, delete, null);
            moduleOperations.add(deleteModuleOperation27);
            ModuleOperation deleteModuleOperation28 = new ModuleOperation(item, delete, null);
            moduleOperations.add(deleteModuleOperation28);
            ModuleOperation deleteModuleOperation29 = new ModuleOperation(paymentRegulatorNote, delete, null);
            moduleOperations.add(deleteModuleOperation29);
            ModuleOperation deleteModuleOperation30 = new ModuleOperation(customer, delete, null);
            moduleOperations.add(deleteModuleOperation30);
            ModuleOperation deleteModuleOperation31 = new ModuleOperation(configuration, delete, null);
            moduleOperations.add(deleteModuleOperation31);
            ModuleOperation deleteModuleOperation32 = new ModuleOperation(notification, delete, null);
            moduleOperations.add(deleteModuleOperation32);

            ModuleOperation viewModuleOperation6 = new ModuleOperation(subModule6, view, null);
            moduleOperations.add(viewModuleOperation6);
            ModuleOperation viewModuleOperation7 = new ModuleOperation(employee, view, null);
            moduleOperations.add(viewModuleOperation7);
            ModuleOperation viewModuleOperation9 = new ModuleOperation(subModule9, view, null);
            moduleOperations.add(viewModuleOperation9);
            ModuleOperation viewModuleOperation10 = new ModuleOperation(subModule10, view, null);
            moduleOperations.add(viewModuleOperation10);
            ModuleOperation viewModuleOperation11 = new ModuleOperation(subModule11, view, null);
            moduleOperations.add(viewModuleOperation11);
            ModuleOperation viewModuleOperation12 = new ModuleOperation(subModule12, view, null);
            moduleOperations.add(viewModuleOperation12);
            ModuleOperation viewModuleOperation13 = new ModuleOperation(subModule13, view, null);
            moduleOperations.add(viewModuleOperation13);
            ModuleOperation viewModuleOperation14 = new ModuleOperation(inventory, view, null);
            moduleOperations.add(viewModuleOperation14);
            ModuleOperation viewModuleOperation15 = new ModuleOperation(currencyRate, view, null);
            moduleOperations.add(viewModuleOperation15);
            ModuleOperation viewModuleOperation16 = new ModuleOperation(transaction, view, null);
            moduleOperations.add(viewModuleOperation16);
            ModuleOperation viewModuleOperation17 = new ModuleOperation(workAttendance, view, null);
            moduleOperations.add(viewModuleOperation17);
            ModuleOperation viewModuleOperation18 = new ModuleOperation(workingHourRecord, view, null);
            moduleOperations.add(viewModuleOperation18);
            ModuleOperation viewModuleOperation19 = new ModuleOperation(financing, view, null);
            moduleOperations.add(viewModuleOperation19);
            ModuleOperation viewModuleOperation20 = new ModuleOperation(salary, view, null);
            moduleOperations.add(viewModuleOperation20);
            ModuleOperation viewModuleOperation21 = new ModuleOperation(paymentRegulator, view, null);
            moduleOperations.add(viewModuleOperation21);
            ModuleOperation viewModuleOperation22 = new ModuleOperation(troubledCustomer, view, null);
            moduleOperations.add(viewModuleOperation22);
            ModuleOperation viewModuleOperation23 = new ModuleOperation(sales, view, null);
            moduleOperations.add(viewModuleOperation23);

            ModuleOperation exportModuleOperation1 = new ModuleOperation(subModule1, export, null);
            moduleOperations.add(exportModuleOperation1);
            ModuleOperation exportModuleOperation2 = new ModuleOperation(subModule2, export, null);
            moduleOperations.add(exportModuleOperation2);
            ModuleOperation exportModuleOperation3 = new ModuleOperation(subModule3, export, null);
            moduleOperations.add(exportModuleOperation3);
            ModuleOperation exportModuleOperation4 = new ModuleOperation(subModule4, export, null);
            moduleOperations.add(exportModuleOperation4);
            ModuleOperation exportModuleOperation5 = new ModuleOperation(subModule5, export, null);
            moduleOperations.add(exportModuleOperation5);
            ModuleOperation exportModuleOperation6 = new ModuleOperation(subModule6, export, null);
            moduleOperations.add(exportModuleOperation6);
            ModuleOperation exportModuleOperation7 = new ModuleOperation(employee, export, null);
            moduleOperations.add(exportModuleOperation7);
            ModuleOperation exportModuleOperation8 = new ModuleOperation(subModule8, export, null);
            moduleOperations.add(exportModuleOperation8);
            ModuleOperation exportModuleOperation9 = new ModuleOperation(inventory, export, null);
            moduleOperations.add(exportModuleOperation9);
            ModuleOperation exportModuleOperation10 = new ModuleOperation(supplier, export, null);
            moduleOperations.add(exportModuleOperation10);
            ModuleOperation exportModuleOperation13 = new ModuleOperation(account, export, null);
            moduleOperations.add(exportModuleOperation13);
            ModuleOperation exportModuleOperation14 = new ModuleOperation(transaction, export, null);
            moduleOperations.add(exportModuleOperation14);
            ModuleOperation exportModuleOperation15 = new ModuleOperation(nonWorkingDay, export, null);
            moduleOperations.add(exportModuleOperation15);
            ModuleOperation exportModuleOperation16 = new ModuleOperation(shortenedWorkingDay, export, null);
            moduleOperations.add(exportModuleOperation16);
            ModuleOperation exportModuleOperation17 = new ModuleOperation(workAttendance, export, null);
            moduleOperations.add(exportModuleOperation17);
            ModuleOperation exportModuleOperation18 = new ModuleOperation(workingHourRecord, export, null);
            moduleOperations.add(exportModuleOperation18);
            ModuleOperation exportModuleOperation19 = new ModuleOperation(advance, export, null);
            moduleOperations.add(exportModuleOperation19);
            ModuleOperation exportModuleOperation20 = new ModuleOperation(salary, export, null);
            moduleOperations.add(exportModuleOperation20);
            ModuleOperation exportModuleOperation21 = new ModuleOperation(action, export, null);
            moduleOperations.add(exportModuleOperation21);
            ModuleOperation exportModuleOperation22 = new ModuleOperation(invoice, export, null);
            moduleOperations.add(exportModuleOperation22);
            ModuleOperation exportModuleOperation23 = new ModuleOperation(sales, export, null);
            moduleOperations.add(exportModuleOperation23);
            ModuleOperation exportModuleOperation24 = new ModuleOperation(item, export, null);
            moduleOperations.add(exportModuleOperation24);
            ModuleOperation exportModuleOperation25 = new ModuleOperation(customer, export, null);
            moduleOperations.add(exportModuleOperation25);
            ModuleOperation exportModuleOperation26 = new ModuleOperation(notification, export, null);
            moduleOperations.add(exportModuleOperation26);

            ModuleOperation approveModuleOperation3 = new ModuleOperation(transaction, approve, null);
            moduleOperations.add(approveModuleOperation3);
            ModuleOperation approveModuleOperation4 = new ModuleOperation(workingHourRecord, approve, null);
            moduleOperations.add(approveModuleOperation4);
            ModuleOperation approveModuleOperation5 = new ModuleOperation(advance, approve, null);
            moduleOperations.add(approveModuleOperation5);
            ModuleOperation approveModuleOperation6 = new ModuleOperation(action, approve, null);
            moduleOperations.add(approveModuleOperation6);
            ModuleOperation approveModuleOperation7 = new ModuleOperation(invoice, approve, null);
            moduleOperations.add(approveModuleOperation7);

            ModuleOperation uploadModuleOperation1 = new ModuleOperation(nonWorkingDay, upload, null);
            moduleOperations.add(uploadModuleOperation1);
            ModuleOperation uploadModuleOperation2 = new ModuleOperation(shortenedWorkingDay, upload, null);
            moduleOperations.add(uploadModuleOperation2);
            ModuleOperation uploadModuleOperation3 = new ModuleOperation(item, upload, null);
            moduleOperations.add(uploadModuleOperation3);

            ModuleOperation reloadModuleOperation1 = new ModuleOperation(currencyRate, reload, null);
            moduleOperations.add(reloadModuleOperation1);
            ModuleOperation reloadModuleOperation2 = new ModuleOperation(workingHourRecord, reload, null);
            moduleOperations.add(reloadModuleOperation2);

            ModuleOperation actionsModuleOperation1 = new ModuleOperation(inventory, actions, null);
            moduleOperations.add(actionsModuleOperation1);

            ModuleOperation searchModuleOperation1 = new ModuleOperation(workingHourRecord, search, null);
            moduleOperations.add(searchModuleOperation1);

            ModuleOperation saveModuleOperation1 = new ModuleOperation(workingHourRecord, save, null);
            moduleOperations.add(saveModuleOperation1);

            ModuleOperation cancelModuleOperation1 = new ModuleOperation(workingHourRecord, cancel, null);
            moduleOperations.add(cancelModuleOperation1);

            ModuleOperation calculateModuleOperation1 = new ModuleOperation(salary, calculate, null);
            moduleOperations.add(calculateModuleOperation1);
            ModuleOperation calculateModuleOperation2 = new ModuleOperation(calculator, calculate, null);
            moduleOperations.add(calculateModuleOperation2);

            ModuleOperation transferModuleOperation1 = new ModuleOperation(action, transfer, null);
            moduleOperations.add(transferModuleOperation1);
            ModuleOperation transferModuleOperation2 = new ModuleOperation(paymentRegulator, transfer, null);
            moduleOperations.add(transferModuleOperation2);
            ModuleOperation transferModuleOperation3 = new ModuleOperation(troubledCustomer, transfer, null);
            moduleOperations.add(transferModuleOperation3);

            ModuleOperation consolidateModuleOperation1 = new ModuleOperation(action, consolidateOperation, null);
            moduleOperations.add(consolidateModuleOperation1);
            ModuleOperation consolidateModuleOperation2 = new ModuleOperation(invoice, consolidateOperation, null);
            moduleOperations.add(consolidateModuleOperation2);

            ModuleOperation returnModuleOperation1 = new ModuleOperation(action, returnOperation, null);
            moduleOperations.add(returnModuleOperation1);
            ModuleOperation returnModuleOperation2 = new ModuleOperation(consolidate, returnOperation, null);
            moduleOperations.add(returnModuleOperation2);

            ModuleOperation payrollModuleOperation1 = new ModuleOperation(employee, payrollOperation, null);
            moduleOperations.add(payrollModuleOperation1);

            ModuleOperation saleModuleOperation1 = new ModuleOperation(employee, saleOperation, null);
            moduleOperations.add(saleModuleOperation1);

            ModuleOperation detailModuleOperation1 = new ModuleOperation(salary, detail, null);
            moduleOperations.add(detailModuleOperation1);
            ModuleOperation detailModuleOperation2 = new ModuleOperation(paymentRegulator, detail, null);
            moduleOperations.add(detailModuleOperation2);
            ModuleOperation detailModuleOperation3 = new ModuleOperation(troubledCustomer, detail, null);
            moduleOperations.add(detailModuleOperation3);
            ModuleOperation detailModuleOperation4 = new ModuleOperation(sales, detail, null);
            moduleOperations.add(detailModuleOperation4);

            moduleOperationRepository.saveAll(moduleOperations);

            List<Organization> organizations = new ArrayList<>();

            Contact headBranchContact = new Contact("503442323", "125656776", "head.office@sual.az", "M.Xiyəbani 194A", baku);
            Organization headBranch = new Organization(headBranchContact,"Baş ofis", "Baş ofis", null, branchOrganization);
            organizations.add(headBranch);

            Contact khirdalanBranchContact = new Contact("503442323", "125656776", "khirdalan.office@sual.az", "A.Şaiq 33", khirdalan);
            Organization khirdalanBranch = new Organization(khirdalanBranchContact, "Xırdalan flialı", "Xırdalan flialı", headBranch, branchOrganization);
            organizations.add(khirdalanBranch);

            Contact lankaranBranchContact = new Contact("503442323", "125656776", "lankaran.office@sual.az", "X.Natəvan 24B", lankaran);
            Organization lankaranBranch = new Organization(lankaranBranchContact, "Lənkəran flialı", "Lənkəran flialı", headBranch, branchOrganization);
            organizations.add(lankaranBranch);

            Contact ganjaBranchContact = new Contact("503442323", "125656776", "ganja.office@sual.az", "S.Rüstəm 144", ganja);
            Organization ganjaBranch = new Organization(ganjaBranchContact, "Gəncə flialı", "Gəncə flialı", headBranch, branchOrganization);
            organizations.add(ganjaBranch);

            Contact khachmazBranchContact = new Contact("503442323", "125656776", "khachmaz.office@sual.az", "M.F.Axundov 32", khachmaz);
            Organization khachmazBranch = new Organization(khachmazBranchContact,"Xaçmaz flialı", "Xaçmaz flialı", headBranch, branchOrganization);
            organizations.add(khachmazBranch);

            Contact yevlakhBranchContact = new Contact("503442323", "125656776", "yevlakh.office@sual.az", "T.Rəcəbli 784D", yevlakh);
            Organization yevlakhBranch = new Organization(yevlakhBranchContact,"Yevlax flialı", "Yevlax flialı", headBranch, branchOrganization);
            organizations.add(yevlakhBranch);

            organizationRepository.saveAll(organizations);

            Contact contact1 = new Contact("502535110", null, "irkan.ehmedov@gmail.com", "Ü.Hacıbəyov 195A", baku);
            Person person = new Person(contact1, "İrkan", "Əhmədov", "Əflatun", DateUtility.getUtilDate("25.09.1989"), male, azerbaijanNationality, married, "4HWL0AM", "AA0844002", false, null);
            Employee employee0 = new Employee(person, position1, new Date(), null, headBranch);
            List<EmployeePayrollDetail> employeePayrollDetails = new ArrayList<>();
            for(Dictionary dictionary: dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-payroll-field")){
                EmployeePayrollDetail employeePayrollDetail = new EmployeePayrollDetail(employee0, dictionary, dictionary.getAttr1(), dictionary.getAttr2());
                employeePayrollDetails.add(employeePayrollDetail);
            }
            employee0.setEmployeePayrollDetails(employeePayrollDetails);

            List<EmployeeSaleDetail> employeeSaleDetails = new ArrayList<>();
            for(Dictionary dictionary: dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-sale-field")){
                EmployeeSaleDetail employeeSaleDetail = new EmployeeSaleDetail(employee0, dictionary, dictionary.getAttr1(), dictionary.getAttr2());
                employeeSaleDetails.add(employeeSaleDetail);
            }
            employee0.setEmployeeSaleDetails(employeeSaleDetails);

            employeeRepository.save(employee0);


            User user = new User(defaultAdminUsername, DigestUtils.md5DigestAsHex("admin".getBytes()), employee0, new UserDetail("az"));

            userRepository.save(user);

            List<UserModuleOperation> userModuleOperations = new ArrayList<>();

            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation2));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation3));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation4));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation5));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation6));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation7));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation8));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation9));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation10));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation13));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation14));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation15));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation16));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation18));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation19));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation20));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation21));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation22));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation24));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation25));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation26));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation27));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation28));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation29));
            userModuleOperations.add(new UserModuleOperation(user, createModuleOperation30));

            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation2));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation3));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation4));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation6));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation7));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation8));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation9));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation10));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation13));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation14));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation15));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation16));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation18));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation19));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation20));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation21));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation22));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation23));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation24));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation25));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation26));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation27));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation28));
            userModuleOperations.add(new UserModuleOperation(user, editModuleOperation29));

            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation2));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation3));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation4));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation5));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation6));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation7));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation8));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation9));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation10));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation13));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation14));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation15));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation16));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation18));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation19));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation20));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation21));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation22));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation23));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation24));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation25));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation26));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation27));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation28));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation29));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation30));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation31));
            userModuleOperations.add(new UserModuleOperation(user, deleteModuleOperation32));

            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation2));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation3));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation4));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation5));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation6));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation7));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation8));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation9));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation10));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation13));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation14));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation15));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation16));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation17));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation18));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation19));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation20));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation21));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation22));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation23));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation24));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation25));
            userModuleOperations.add(new UserModuleOperation(user, exportModuleOperation26));

            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation6));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation7));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation9));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation10));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation11));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation12));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation13));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation14));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation15));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation16));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation17));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation18));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation19));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation20));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation21));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation22));
            userModuleOperations.add(new UserModuleOperation(user, viewModuleOperation23));

            userModuleOperations.add(new UserModuleOperation(user, approveModuleOperation3));
            userModuleOperations.add(new UserModuleOperation(user, approveModuleOperation4));
            userModuleOperations.add(new UserModuleOperation(user, approveModuleOperation5));
            userModuleOperations.add(new UserModuleOperation(user, approveModuleOperation6));
            userModuleOperations.add(new UserModuleOperation(user, approveModuleOperation7));

            userModuleOperations.add(new UserModuleOperation(user, uploadModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, uploadModuleOperation2));
            userModuleOperations.add(new UserModuleOperation(user, uploadModuleOperation3));

            userModuleOperations.add(new UserModuleOperation(user, reloadModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, reloadModuleOperation2));

            userModuleOperations.add(new UserModuleOperation(user, actionsModuleOperation1));

            userModuleOperations.add(new UserModuleOperation(user, searchModuleOperation1));

            userModuleOperations.add(new UserModuleOperation(user, saveModuleOperation1));

            userModuleOperations.add(new UserModuleOperation(user, cancelModuleOperation1));

            userModuleOperations.add(new UserModuleOperation(user, calculateModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, calculateModuleOperation2));

            userModuleOperations.add(new UserModuleOperation(user, transferModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, transferModuleOperation2));
            userModuleOperations.add(new UserModuleOperation(user, transferModuleOperation3));

            userModuleOperations.add(new UserModuleOperation(user, consolidateModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, consolidateModuleOperation2));

            userModuleOperations.add(new UserModuleOperation(user, returnModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, returnModuleOperation2));

            userModuleOperations.add(new UserModuleOperation(user, payrollModuleOperation1));

            userModuleOperations.add(new UserModuleOperation(user, saleModuleOperation1));

            userModuleOperations.add(new UserModuleOperation(user, detailModuleOperation1));
            userModuleOperations.add(new UserModuleOperation(user, detailModuleOperation2));
            userModuleOperations.add(new UserModuleOperation(user, detailModuleOperation3));
            userModuleOperations.add(new UserModuleOperation(user, detailModuleOperation4));

            userModuleOperationRepository.saveAll(userModuleOperations);

            List<Supplier> suppliers = new ArrayList<>();

            Contact supplier1Contact = new Contact("702046451", null, "aqualine.az@gmail.com", "Keşlə bazarı", baku);
            Person supplier1Person = new Person(supplier1Contact, "Əli", "Vəliyev", null, null, male, azerbaijanNationality, married, null, null, false, null);
            Supplier supplier1 = new Supplier("Aqualine MMC", "Sintra təminatçısı", supplier1Person);
            suppliers.add(supplier1);

            Contact supplier2Contact = new Contact("553128122", null, "samir.bagirov@gmail.com", "Tonqal restoranının yanı", baku);
            Person supplier2Person = new Person(supplier2Contact, "Samir", "Bağırov", null, null, male, azerbaijanNationality, single, null, null, false, null);
            Supplier supplier2 = new Supplier("Techflow MMC", "Təminatçı", supplier2Person);
            suppliers.add(supplier2);

            Contact supplier3Contact = new Contact("552263010", null, "sintra.az@gmail.com", "Binəqədi rayonu", baku);
            Person supplier3Person = new Person(supplier3Contact, "Elmar", "Məmmədov", null, null, male, azerbaijanNationality, single, null, null, false, null);
            Supplier supplier3 = new Supplier("Sintra MMC", "Təminatçı", supplier3Person);
            suppliers.add(supplier3);

            Contact supplier4Contact = new Contact(null, null, "sadarak@gmail.com", null, baku);
            Person supplier4Person = new Person(supplier4Contact, "Sədərək", "Ticarət mərkəzi", null, null, male, azerbaijanNationality, single, null, null, false, null);
            Supplier supplier4 = new Supplier("Sədərək", "Təminatçı", supplier4Person);
            suppliers.add(supplier4);

            Contact supplier5Contact = new Contact(null, null, "other.supplier@gmail.com", null, baku);
            Person supplier5Person = new Person(supplier5Contact, "Digər", "Təminatçı", null, null, male, azerbaijanNationality, single, null, null, false, null);
            Supplier supplier5 = new Supplier("Digər", "Təminatçı", supplier5Person);
            suppliers.add(supplier5);

            supplierRepository.saveAll(suppliers);

            List<Account> accounts = new ArrayList<>();
            for(Organization organization: organizations){
                if(organization.getOrganizationType().getAttr1().equalsIgnoreCase("branch")){
                    String descriptionAccount = organization.getName() + " AZN hesabı";
                    Account acc = new Account(organization, "AZN", descriptionAccount);
                    accounts.add(acc);
                }
            }

            accountRepository.saveAll(accounts);

            List<PayrollConfiguration> payrollConfigurations = new ArrayList<>();
            payrollConfigurations.add(new PayrollConfiguration(formulaType1, "Minimal əmək haqqı", "{minimal_salary}=200", "Azərbaycan Respublikasında müəyyənləşdirilmiş minimal əmək haqqı"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Ümumi iş günlərinin sayı", "{total_working_days}={uig}", "Ümumi iş günlərinin sayı"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Hesablanmış iş günlərinin sayı", "{calculated_working_days}={hig}", "Hesablanmış iş günlərinin sayı"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Hesablanmış əmək haqqı (Rəsmi hissə)", "{calculated_gross_salary}={gross_salary}*{calculated_working_days}/{total_working_days}", "Hesablanmış ümumi əmək haqqı = ümumi əmək haqqı * hesablanmış iş günlərinin sayı / ümumi iş günlərinin sayı"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Hesablanmış əmək haqqı (Qeyri rəsmi hissə)", "{calculated_salary}={salary}*{calculated_working_days}/{total_working_days}", "Ümumi əmək haqqı = əmək haqqı * hesablanmış iş günlərinin sayı / ümumi iş günlərinin sayı"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Vergiyə cəlb olunan məbləğ", "{tax_amount_involved}={calculated_gross_salary}-{allowance}", "Vergiyə cəlb olunan məbləğ = Hesablanan aylıq əmək haqqı - Güzəşt"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Gəlir vergisi", "{tax_income}=({tax_amount_involved})>8000?({tax_amount_involved}-8000)*14%:0", "Gəlir vergisi: Vergiyə cəlb olunan məbləğ 8 000 manatadək olduqda: 0; Vergiyə cəlb olunan məbləğ 8 000 manatdan çox olduqda: Vergiyə cəlb olunan məbləğ *14%"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"DSMF ayırmaları", "{dsmf_deduction}={calculated_gross_salary}>{minimal_salary}?{minimal_salary}*3%+({calculated_gross_salary}-{minimal_salary})*10%:{calculated_gross_salary}*3%", "DSMF ayırmaları: Əmək haqqı 200 manatadək olduqda: Hesablanan aylıq əmək haqqı * 3%; Əmək haqqı 200 manatdan çox olduqda: 6 + (Hesablanan aylıq əmək haqqı-200) * 10%;"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"İşsizlikdən sığorta haqqı", "{unemployment_insurance}={calculated_gross_salary}*0.5%", "İşsizlikdən sığorta haqqı = Hesablanan aylıq əmək haqqı * 0.5%"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Həmkarlar təşkilatına üzvlük haqqı", "{membership_fee_for_trade_union}={calculated_gross_salary}*{membership_fee_for_trade_union_fee}", "Həmkarlar təşkilatına üzvlük haqqı = Hesablanan aylıq əmək haqqı * x%"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"İcbari tibbi sığorta haqqı", "{compulsory_health_insurance}=10", "İcbari tibbi sığorta haqqı = 10"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Yekun ödəniləcək məbləğ (Rəsmi hissə)", "{total_amount_payable_official}={calculated_gross_salary}-{tax_income}-{dsmf_deduction}-{unemployment_insurance}-{compulsory_health_insurance}-{membership_fee_for_trade_union}", "Yekun ödəniləcək məbləğ = Hesablanan əmək haqqı - Gəlir vergisi - DSMF ayırmaları - İşsizlikdən sığorta haqqı - İcbari tibbi sığprta haqqı - Həmkarlar təşkilatına üzvlük haqqı"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"İş stajına görə əmək haqqına əlavə", "{work_experience_salary}={work_experience}>10?{salary}*15%:{work_experience}>5?{salary}*10%:{work_experience}>3?{salary}*5%:0", "İş stajına görə əmək haqqına əlavə = İş stajı 10 ildən artıq olduqda əlavə 15%, 5 ildən artıq olduqda əlavə 10%, 3 ildən artıq olduqda əlavə 5%"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Ümumi əmək haqqı", "{total_salary}={salary}+{work_experience_salary}", "Ümumi əmək haqqı = İş stajına görə əmək haqqına əlavə + Əmək haqqı"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType1,"Yekun ödəniləcək məbləğ (Qeyri rəsmi hissə)", "{total_amount_payable_non_official}={total_salary}-{total_amount_payable_official}", "Yekun ödəniləcək məbləğ (Qeyri rəsmi hissə) = Ümumi əmək haqqı - Yekun ödəniləcək məbləğ (Rəsmi hissə)"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType2,"Əsas məzuniyyət günlərinin sayı", "{main_vacation_days}={disability}?43:{specialist_or_manager}?30:21", "AR ƏM əsasən əsas məzuniyyət günlərinin sayı əlilliyi olan şəxslər üçün 43, mütəxəssis və ya rəhbər şəxslər üçün 30, digər hallar üçün 21 gün təyin edilmişdir"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType2,"Əlavə məzuniyyət günlərinin sayı", "{additional_vacation_days}={work_experience}>15?6:{work_experience}>10?4:{work_experience}>5?2:0", "İş stajı 15 ildən çox olduqda 9 gün, iş stajı 10 ildən çox olduqda 6 gün, iş stajı 5 ildən çox olduqda 3 gün əlavə məzuniyyət tətbiq edilir"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType3,"Orta aylıq əmək haqqı", "{average_salary}={sum_work_month_salary_max_12}/{work_month_salary_count_max_12}", "Orta aylıq əmək haqqı = məzuniyyətdən əvvəlki 12 təqvim ayının əmək haqqının cəmlənmiş məbləği / 12"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType3,"Bir günlük əmək haqqı", "{one_day_salary}={average_salary}/30.4", "Bir günlük əmək haqqı = orta aylıq əmək haqqı / 30.4"));
            payrollConfigurations.add(new PayrollConfiguration(formulaType3,"Məzuniyyət haqqı", "{vacation_pay}={one_day_salary}*{taken_vacation_day}", "Məzuniyyət haqqı = Bir günlük əmək haqqı * Məzuniyyət günləri"));

            payrollConfigurationRepository.saveAll(payrollConfigurations);

        } catch (Exception e){
            e.printStackTrace();
            log.error(e);
        } finally {
            log.info("System was running!");
        }
    }
}