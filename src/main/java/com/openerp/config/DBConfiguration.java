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

    @Value("${default.admin.username}")
    private String defaultAdminUsername;

    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    @PostConstruct
    public void run() {
        try{
            List<DictionaryType> types = new ArrayList<>();
            DictionaryType genderType = new DictionaryType("Cins", "gender", null);
            types.add(genderType);
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

            List<Dictionary> dictionaries = new ArrayList<>();
            Dictionary male = new Dictionary("Kişi", "Male", null, genderType);
            dictionaries.add(male);
            Dictionary female = new Dictionary("Qadın", "Female", null, genderType);
            dictionaries.add(female);
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
            Dictionary position2 = new Dictionary("Satış təmsilçisi", "Sales person", null, positionType);
            dictionaries.add(position2);
            Dictionary position3 = new Dictionary("Satış meneceri", "Sales manager", null, positionType);
            dictionaries.add(position3);
            Dictionary position4 = new Dictionary("Mühasib", "Accauntant", null, positionType);
            dictionaries.add(position4);
            Dictionary position5 = new Dictionary("Baş mühasib", "Head  accauntant", null, positionType);
            dictionaries.add(position5);
            Dictionary position6 = new Dictionary("Reklamçı", "Promoter", null, positionType);
            dictionaries.add(position6);
            Dictionary position7 = new Dictionary("Anbardar", "Warehouse", null, positionType);
            dictionaries.add(position7);
            Dictionary position8 = new Dictionary("Baş anbardar", "Head warehouse", null, positionType);
            dictionaries.add(position8);
            Dictionary position9 = new Dictionary("Usta", "Master", null, positionType);
            dictionaries.add(position9);
            Dictionary position10 = new Dictionary("Baş usta", "Head master", null, positionType);
            dictionaries.add(position10);
            Dictionary position11 = new Dictionary("Sürücü", "Driver", null, positionType);
            dictionaries.add(position11);
            Dictionary position12 = new Dictionary("Direktor", "Director", null, positionType);
            dictionaries.add(position12);
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


            dictionaryTypeRepository.saveAll(types);
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
            Module subModule5 = new Module("İstifadəçi", "İstifadəçi", "user", "flaticon-users", module);
            modules.add(subModule5);
            Module subModule9 = new Module("İstifadəçi icazəsi", "İstifadəçi icazəsi", "user-module-operation", "flaticon-clipboard", subModule5);
            modules.add(subModule9);
            Module subModule10 = new Module("İcazə şablonu", "İcazə şablonu", "template-module-operation", "flaticon-squares-3", module);
            modules.add(subModule10);
            Module module1 = new Module("İnsan Resursu", "İnsan Resursu", "hr", "flaticon-profile-1", null);
            modules.add(module1);
            Module subModule6 = new Module("Struktur", "Struktur", "organization", "flaticon-map", module1);
            modules.add(subModule6);
            Module subModule7 = new Module("Əməkdaş", "Əməkdaş", "employee", "flaticon-users", module1);
            modules.add(subModule7);
            Module subModule11 = new Module("İkon", "İkon", "flat-icon", "flaticon-paper-plane-1", module);
            modules.add(subModule11);
            Module subModule12 = new Module("Flat ikon", "Flat ikon", "flat-icon", null, subModule11);
            modules.add(subModule12);
            Module subModule13 = new Module("LineAwesome ikon", "LineAwesome ikon", "line-awesome-icon", null, subModule11);
            modules.add(subModule13);
            Module warehouse = new Module("Anbar", "İnventarın idarə edilməsi", "warehouse", "flaticon-home-2", null);
            modules.add(warehouse);
            Module dashboard = new Module("Panel", "Qrafik status", "dashboard", "flaticon-analytics", null);
            modules.add(dashboard);

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
            Operation approve = new Operation("Təsdiq", "approve", "flaticon-signs");
            operations.add(approve);

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
            ModuleOperation createModuleOperation7 = new ModuleOperation(subModule7, create, null);
            moduleOperations.add(createModuleOperation7);
            ModuleOperation createModuleOperation8 = new ModuleOperation(subModule8, create, null);
            moduleOperations.add(createModuleOperation8);

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
            ModuleOperation editModuleOperation7 = new ModuleOperation(subModule7, edit, null);
            moduleOperations.add(editModuleOperation7);
            ModuleOperation editModuleOperation8 = new ModuleOperation(subModule8, edit, null);
            moduleOperations.add(editModuleOperation8);

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
            ModuleOperation deleteModuleOperation7 = new ModuleOperation(subModule7, delete, null);
            moduleOperations.add(deleteModuleOperation7);
            ModuleOperation deleteModuleOperation8 = new ModuleOperation(subModule8, delete, null);
            moduleOperations.add(deleteModuleOperation8);

            ModuleOperation viewModuleOperation6 = new ModuleOperation(subModule6, view, null);
            moduleOperations.add(viewModuleOperation6);
            ModuleOperation viewModuleOperation7 = new ModuleOperation(subModule7, view, null);
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
            ModuleOperation exportModuleOperation7 = new ModuleOperation(subModule7, export, null);
            moduleOperations.add(exportModuleOperation7);
            ModuleOperation exportModuleOperation8 = new ModuleOperation(subModule8, export, null);
            moduleOperations.add(exportModuleOperation8);

            moduleOperationRepository.saveAll(moduleOperations);

            Contact contact1 = new Contact("502535110", null, "irkan.ehmedov@gmail.com", "Ü.Hacıbəyov 195A", baku);
            Person person = new Person(contact1, "İrkan", "Əhmədov", "Əflatun", DateUtility.getUtilDate("25.09.1989"), male, azerbaijanNationality, "4HWL0AM", null);
            personRepository.save(person);

            List<Organization> organizations = new ArrayList<>();
            Contact headBranchContact = new Contact("503442323", "125656776", "head.office@sual.az", "M.Xiyəbani 194A", baku);
            Organization headBranch = new Organization(headBranchContact,"Baş ofis", "Baş ofis", null);
            organizations.add(headBranch);
            Contact khirdalanBranchContact = new Contact("503442323", "125656776", "khirdalan.office@sual.az", "A.Şaiq 33", khirdalan);
            Organization khirdalanBranch = new Organization(khirdalanBranchContact, "Xırdalan flialı", "Xırdalan flialı", headBranch);
            organizations.add(khirdalanBranch);
            Contact lankaranBranchContact = new Contact("503442323", "125656776", "lankaran.office@sual.az", "X.Natəvan 24B", lankaran);
            Organization lankaranBranch = new Organization(lankaranBranchContact, "Lənkəran flialı", "Lənkəran flialı", headBranch);
            organizations.add(lankaranBranch);
            Contact ganjaBranchContact = new Contact("503442323", "125656776", "ganja.office@sual.az", "S.Rüstəm 144", ganja);
            Organization ganjaBranch = new Organization(ganjaBranchContact, "Gəncə flialı", "Gəncə flialı", headBranch);
            organizations.add(ganjaBranch);
            Contact khachmazBranchContact = new Contact("503442323", "125656776", "khachmaz.office@sual.az", "M.F.Axundov 32", khachmaz);
            Organization khachmazBranch = new Organization(khachmazBranchContact,"Xaçmaz flialı", "Xaçmaz flialı", headBranch);
            organizations.add(khachmazBranch);
            Contact yevlakhBranchContact = new Contact("503442323", "125656776", "yevlakh.office@sual.az", "T.Rəcəbli 784D", yevlakh);
            Organization yevlakhBranch = new Organization(yevlakhBranchContact,"Yevlax flialı", "Yevlax flialı", headBranch);
            organizations.add(yevlakhBranch);

            organizationRepository.saveAll(organizations);

            Employee employee = new Employee(person, position1, new Date(), null, (double) 0, headBranch);
            employeeRepository.save(employee);

            User user = new User(defaultAdminUsername, DigestUtils.md5DigestAsHex("admin".getBytes()), employee, new UserDetail("az"));

            userRepository.save(user);

            List<UserModuleOperation> userModuleOperations = new ArrayList<>();

            UserModuleOperation userCreateModuleOperation1 = new UserModuleOperation(user, createModuleOperation1);
            userModuleOperations.add(userCreateModuleOperation1);
            UserModuleOperation userCreateModuleOperation2 = new UserModuleOperation(user, createModuleOperation2);
            userModuleOperations.add(userCreateModuleOperation2);
            UserModuleOperation userCreateModuleOperation3 = new UserModuleOperation(user, createModuleOperation3);
            userModuleOperations.add(userCreateModuleOperation3);
            UserModuleOperation userCreateModuleOperation4 = new UserModuleOperation(user, createModuleOperation4);
            userModuleOperations.add(userCreateModuleOperation4);
            UserModuleOperation userCreateModuleOperation5 = new UserModuleOperation(user, createModuleOperation5);
            userModuleOperations.add(userCreateModuleOperation5);
            UserModuleOperation userCreateModuleOperation6 = new UserModuleOperation(user, createModuleOperation6);
            userModuleOperations.add(userCreateModuleOperation6);
            UserModuleOperation userCreateModuleOperation7 = new UserModuleOperation(user, createModuleOperation7);
            userModuleOperations.add(userCreateModuleOperation7);
            UserModuleOperation userCreateModuleOperation8 = new UserModuleOperation(user, createModuleOperation8);
            userModuleOperations.add(userCreateModuleOperation8);

            UserModuleOperation userEditModuleOperation1 = new UserModuleOperation(user, editModuleOperation1);
            userModuleOperations.add(userEditModuleOperation1);
            UserModuleOperation userEditModuleOperation2 = new UserModuleOperation(user, editModuleOperation2);
            userModuleOperations.add(userEditModuleOperation2);
            UserModuleOperation userEditModuleOperation3 = new UserModuleOperation(user, editModuleOperation3);
            userModuleOperations.add(userEditModuleOperation3);
            UserModuleOperation userEditModuleOperation4 = new UserModuleOperation(user, editModuleOperation4);
            userModuleOperations.add(userEditModuleOperation4);
            UserModuleOperation userEditModuleOperation6 = new UserModuleOperation(user, editModuleOperation6);
            userModuleOperations.add(userEditModuleOperation6);
            UserModuleOperation userEditModuleOperation7 = new UserModuleOperation(user, editModuleOperation7);
            userModuleOperations.add(userEditModuleOperation7);
            UserModuleOperation userEditModuleOperation8 = new UserModuleOperation(user, editModuleOperation8);
            userModuleOperations.add(userEditModuleOperation8);

            UserModuleOperation userDeleteModuleOperation1 = new UserModuleOperation(user, deleteModuleOperation1);
            userModuleOperations.add(userDeleteModuleOperation1);
            UserModuleOperation userDeleteModuleOperation2 = new UserModuleOperation(user, deleteModuleOperation2);
            userModuleOperations.add(userDeleteModuleOperation2);
            UserModuleOperation userDeleteModuleOperation3 = new UserModuleOperation(user, deleteModuleOperation3);
            userModuleOperations.add(userDeleteModuleOperation3);
            UserModuleOperation userDeleteModuleOperation4 = new UserModuleOperation(user, deleteModuleOperation4);
            userModuleOperations.add(userDeleteModuleOperation4);
            UserModuleOperation userDeleteModuleOperation5 = new UserModuleOperation(user, deleteModuleOperation5);
            userModuleOperations.add(userDeleteModuleOperation5);
            UserModuleOperation userDeleteModuleOperation6 = new UserModuleOperation(user, deleteModuleOperation6);
            userModuleOperations.add(userDeleteModuleOperation6);
            UserModuleOperation userDeleteModuleOperation7 = new UserModuleOperation(user, deleteModuleOperation7);
            userModuleOperations.add(userDeleteModuleOperation7);
            UserModuleOperation userDeleteModuleOperation8 = new UserModuleOperation(user, deleteModuleOperation8);
            userModuleOperations.add(userDeleteModuleOperation8);

            UserModuleOperation userExportModuleOperation1 = new UserModuleOperation(user, exportModuleOperation1);
            userModuleOperations.add(userExportModuleOperation1);
            UserModuleOperation userExportModuleOperation2 = new UserModuleOperation(user, exportModuleOperation2);
            userModuleOperations.add(userExportModuleOperation2);
            UserModuleOperation userExportModuleOperation3 = new UserModuleOperation(user, exportModuleOperation3);
            userModuleOperations.add(userExportModuleOperation3);
            UserModuleOperation userExportModuleOperation4 = new UserModuleOperation(user, exportModuleOperation4);
            userModuleOperations.add(userExportModuleOperation4);
            UserModuleOperation userExportModuleOperation5 = new UserModuleOperation(user, exportModuleOperation5);
            userModuleOperations.add(userExportModuleOperation5);
            UserModuleOperation userExportModuleOperation6 = new UserModuleOperation(user, exportModuleOperation6);
            userModuleOperations.add(userExportModuleOperation6);
            UserModuleOperation userExportModuleOperation7 = new UserModuleOperation(user, exportModuleOperation7);
            userModuleOperations.add(userExportModuleOperation7);
            UserModuleOperation userExportModuleOperation8 = new UserModuleOperation(user, exportModuleOperation8);
            userModuleOperations.add(userExportModuleOperation8);

            UserModuleOperation userViewModuleOperation6 = new UserModuleOperation(user, viewModuleOperation6);
            userModuleOperations.add(userViewModuleOperation6);
            UserModuleOperation userViewModuleOperation7 = new UserModuleOperation(user, viewModuleOperation7);
            userModuleOperations.add(userViewModuleOperation7);
            UserModuleOperation viewUserModuleOperation9 = new UserModuleOperation(user, viewModuleOperation9);
            userModuleOperations.add(viewUserModuleOperation9);
            UserModuleOperation viewUserModuleOperation10 = new UserModuleOperation(user, viewModuleOperation10);
            userModuleOperations.add(viewUserModuleOperation10);
            UserModuleOperation viewUserModuleOperation11 = new UserModuleOperation(user, viewModuleOperation11);
            userModuleOperations.add(viewUserModuleOperation11);
            UserModuleOperation viewUserModuleOperation12 = new UserModuleOperation(user, viewModuleOperation12);
            userModuleOperations.add(viewUserModuleOperation12);
            UserModuleOperation viewUserModuleOperation13 = new UserModuleOperation(user, viewModuleOperation13);
            userModuleOperations.add(viewUserModuleOperation13);

            userModuleOperationRepository.saveAll(userModuleOperations);
        } catch (Exception e){
            log.error(e);
        } finally {
            log.info("System was running!");
        }
    }
}
