package az.sufilter.bpm.config;

import az.sufilter.bpm.entity.*;
import az.sufilter.bpm.repository.*;
import az.sufilter.bpm.util.DateUtility;
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
    PersonContactRepository personContactRepository;

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
    OrganizationContactRepository organizationContactRepository;

    @Value("${default.admin.username}")
    private String defaultAdminUsername;

    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    @Value("${default.admin.module.name}")
    private String defaultAdminModuleName;

    @Value("${default.admin.module.description}")
    private String defaultAdminModuleDescription;

    @Value("${default.admin.module.path}")
    private String defaultAdminModulePath;

    @Value("${default.admin.sub1.module.name}")
    private String defaultAdminSub1ModuleName;

    @Value("${default.admin.sub1.module.description}")
    private String defaultAdminSub1ModuleDescription;

    @Value("${default.admin.sub1.module.path}")
    private String defaultAdminSub1ModulePath;

    @Value("${default.admin.sub2.module.name}")
    private String defaultAdminSub2ModuleName;

    @Value("${default.admin.sub2.module.description}")
    private String defaultAdminSub2ModuleDescription;

    @Value("${default.admin.sub2.module.path}")
    private String defaultAdminSub2ModulePath;

    @Value("${default.admin.sub3.module.name}")
    private String defaultAdminSub3ModuleName;

    @Value("${default.admin.sub3.module.description}")
    private String defaultAdminSub3ModuleDescription;

    @Value("${default.admin.sub3.module.path}")
    private String defaultAdminSub3ModulePath;

    @Value("${default.admin.sub4.module.name}")
    private String defaultAdminSub4ModuleName;

    @Value("${default.admin.sub4.module.description}")
    private String defaultAdminSub4ModuleDescription;

    @Value("${default.admin.sub4.module.path}")
    private String defaultAdminSub4ModulePath;

    @Value("${default.admin.sub5.module.name}")
    private String defaultAdminSub5ModuleName;

    @Value("${default.admin.sub5.module.description}")
    private String defaultAdminSub5ModuleDescription;

    @Value("${default.admin.sub5.module.path}")
    private String defaultAdminSub5ModulePath;

    @PostConstruct
    public void run() {
        try{

            List<DictionaryType> types = new ArrayList<>();
            DictionaryType genderType = new DictionaryType("Cins", "gender", null);
            types.add(genderType);
            DictionaryType iconType = new DictionaryType("İkon", "icon", null);
            types.add(iconType);
            DictionaryType cityType = new DictionaryType("Şəhər", "city", null);
            types.add(cityType);
            DictionaryType positionType = new DictionaryType("Vəzifə", "position", null);
            types.add(positionType);
            DictionaryType nationalityType = new DictionaryType("Milliyət", "nationality", null);
            types.add(nationalityType);
            DictionaryType documentType = new DictionaryType("Sənəd tipi", "document-type", null);
            types.add(documentType);

            List<Dictionary> dictionaries = new ArrayList<>();
            Dictionary male = new Dictionary("Kişi", "Male", null, genderType);
            dictionaries.add(male);
            Dictionary female = new Dictionary("Qadın", "Female", null, genderType);
            dictionaries.add(female);
            Dictionary laUser = new Dictionary("la-user", "la-user", null, iconType);
            dictionaries.add(laUser);
            Dictionary laRing = new Dictionary("la-ring", "la-ring", null, iconType);
            dictionaries.add(laRing);
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
            Dictionary position2 = new Dictionary("Satış təmsilçisi", "Sales  person", null, positionType);
            dictionaries.add(position2);
            Dictionary position3 = new Dictionary("Satış meneceri", "Sales  manager", null, positionType);
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


            dictionaryTypeRepository.saveAll(types);
            dictionaryRepository.saveAll(dictionaries);



            List<Module> modules = new ArrayList<>();
            Module module = new Module(defaultAdminModuleName, defaultAdminModuleDescription, defaultAdminModulePath, laUser, null, null);
            modules.add(module);
            Module subModule1 = new Module(defaultAdminSub1ModuleName, defaultAdminSub1ModuleDescription, defaultAdminSub1ModulePath, laUser, null, module);
            modules.add(subModule1);
            Module subModule2 = new Module(defaultAdminSub2ModuleName, defaultAdminSub2ModuleDescription, defaultAdminSub2ModulePath, laUser, null, module);
            modules.add(subModule2);
            Module subModule3 = new Module(defaultAdminSub3ModuleName, defaultAdminSub3ModuleDescription, defaultAdminSub3ModulePath, laUser, null, module);
            modules.add(subModule3);
            Module subModule4 = new Module(defaultAdminSub4ModuleName, defaultAdminSub4ModuleDescription, defaultAdminSub4ModulePath, laUser, null, module);
            modules.add(subModule4);
            Module subModule5 = new Module(defaultAdminSub5ModuleName, defaultAdminSub5ModuleDescription, defaultAdminSub5ModulePath, laUser, null, module);
            modules.add(subModule5);
            Module module1 = new Module("İnsan Resursu", "İnsan Resursu", "hr", laUser, null, null);
            modules.add(module1);
            Module subModule6 = new Module("Struktur", "Struktur", "organization", laUser, null, module1);
            modules.add(subModule6);
            Module subModule7 = new Module("Əməkdaş", "Əməkdaş", "employee", laUser, null, module1);
            modules.add(subModule7);
            Module subModule8 = new Module("Modul və Əməliyyat", "Modul və Əməliyyat", "module-operation", laUser, null, module);
            modules.add(subModule8);
            Module subModule9 = new Module("İstifadəçi icazəsi", "İstifadəçi icazəsi", "user-access", laUser, null, module);
            modules.add(subModule9);

            List<Operation> operations = new ArrayList<>();
            Operation view = new Operation("Baxış", "view", laUser, null);
            operations.add(view);
            Operation create = new Operation("Yarat", "create", laUser, null);
            operations.add(create);
            Operation edit = new Operation("Redaktə", "edit", laUser, null);
            operations.add(edit);
            Operation delete = new Operation("Sil", "delete", laUser, null);
            operations.add(delete);
            Operation export = new Operation("İxrac", "export", laUser, null);
            operations.add(export);


            List<ModuleOperation> moduleOperations = new ArrayList<>();
            ModuleOperation moduleOperation1 = new ModuleOperation(module, null, null);
            moduleOperations.add(moduleOperation1);
            ModuleOperation moduleOperation2 = new ModuleOperation(subModule1, create, null);
            moduleOperations.add(moduleOperation2);
            ModuleOperation moduleOperation3 = new ModuleOperation(subModule2, create, null);
            moduleOperations.add(moduleOperation3);
            ModuleOperation moduleOperation4 = new ModuleOperation(subModule3, create, null);
            moduleOperations.add(moduleOperation4);
            ModuleOperation moduleOperation5 = new ModuleOperation(subModule4, create, null);
            moduleOperations.add(moduleOperation5);
            ModuleOperation moduleOperation6 = new ModuleOperation(subModule5, create, null);
            moduleOperations.add(moduleOperation6);
            ModuleOperation moduleOperation7 = new ModuleOperation(module1, null, null);
            moduleOperations.add(moduleOperation7);
            ModuleOperation moduleOperation8 = new ModuleOperation(subModule6, create, null);
            moduleOperations.add(moduleOperation8);
            ModuleOperation moduleOperation9 = new ModuleOperation(subModule7, create, null);
            moduleOperations.add(moduleOperation9);
            ModuleOperation moduleOperation10 = new ModuleOperation(subModule8, create, null);
            moduleOperations.add(moduleOperation10);
            ModuleOperation moduleOperation11 = new ModuleOperation(subModule9, create, null);
            moduleOperations.add(moduleOperation11);



            Person person = new Person("İrkan", "Əhmədov", "Əflatun", DateUtility.getUtilDate("25.09.1989"), male, azerbaijanNationality, "4HWL0AM", null);
            personRepository.save(person);

            PersonContact personContact = new PersonContact(person, "502535110", null, "irkan.ehmedov@gmail.com", "Ü.Hacıbəyov 195A", baku);
            personContactRepository.save(personContact);

            List<Organization> organizations = new ArrayList<>();
            Organization headBranch = new Organization("Baş ofis", "Baş ofis", null);
            organizations.add(headBranch);
            Organization khirdalanBranch = new Organization("Xırdalan flialı", "Xırdalan flialı", headBranch);
            organizations.add(khirdalanBranch);
            Organization lankaranBranch = new Organization("Lənkəran flialı", "Lənkəran flialı", headBranch);
            organizations.add(lankaranBranch);
            Organization ganjaBranch = new Organization("Gəncə flialı", "Gəncə flialı", headBranch);
            organizations.add(ganjaBranch);
            Organization khachmazBranch = new Organization("Xaçmaz flialı", "Xaçmaz flialı", headBranch);
            organizations.add(khachmazBranch);
            Organization yevlakhBranch = new Organization("Yevlax flialı", "Yevlax flialı", headBranch);
            organizations.add(yevlakhBranch);

            List<OrganizationContact> organizationContacts = new ArrayList<>();
            OrganizationContact headBranchContact = new OrganizationContact(headBranch, "503442323", "125656776", "head.office@sual.az", "M.Xiyəbani 194A", baku);
            organizationContacts.add(headBranchContact);
            OrganizationContact khirdalanBranchContact = new OrganizationContact(khirdalanBranch, "503442323", "125656776", "khirdalan.office@sual.az", "A.Şaiq 33", khirdalan);
            organizationContacts.add(khirdalanBranchContact);
            OrganizationContact ganjaBranchContact = new OrganizationContact(ganjaBranch, "503442323", "125656776", "ganja.office@sual.az", "S.Rüstəm 144", ganja);
            organizationContacts.add(ganjaBranchContact);
            OrganizationContact lankaranBranchContact = new OrganizationContact(lankaranBranch, "503442323", "125656776", "lankaran.office@sual.az", "X.Natəvan 24B", lankaran);
            organizationContacts.add(lankaranBranchContact);
            OrganizationContact khachmazBranchContact = new OrganizationContact(khachmazBranch, "503442323", "125656776", "khachmaz.office@sual.az", "M.F.Axundov 32", khachmaz);
            organizationContacts.add(khachmazBranchContact);
            OrganizationContact yevlakhBranchContact = new OrganizationContact(yevlakhBranch, "503442323", "125656776", "yevlakh.office@sual.az", "T.Rəcəbli 784D", yevlakh);
            organizationContacts.add(yevlakhBranchContact);

            organizationRepository.saveAll(organizations);
            organizationContactRepository.saveAll(organizationContacts);

            Employee employee = new Employee(person, position1, new Date(), null, (double) 0, headBranch);
            employeeRepository.save(employee);

            User user = new User(defaultAdminUsername, DigestUtils.md5DigestAsHex("admin".getBytes()), employee);
            UserDetail userDetail = new UserDetail(user);
            List<UserModuleOperation> userModuleOperations = new ArrayList<>();
            UserModuleOperation userModuleOperation1 = new UserModuleOperation(user, moduleOperations.get(0));
            userModuleOperations.add(userModuleOperation1);
            UserModuleOperation userModuleOperation2 = new UserModuleOperation(user, moduleOperations.get(1));
            userModuleOperations.add(userModuleOperation2);
            UserModuleOperation userModuleOperation3 = new UserModuleOperation(user, moduleOperations.get(2));
            userModuleOperations.add(userModuleOperation3);
            UserModuleOperation userModuleOperation4 = new UserModuleOperation(user, moduleOperations.get(3));
            userModuleOperations.add(userModuleOperation4);
            UserModuleOperation userModuleOperation5 = new UserModuleOperation(user, moduleOperations.get(4));
            userModuleOperations.add(userModuleOperation5);
            UserModuleOperation userModuleOperation6 = new UserModuleOperation(user, moduleOperations.get(5));
            userModuleOperations.add(userModuleOperation6);
            UserModuleOperation userModuleOperation7 = new UserModuleOperation(user, moduleOperations.get(6));
            userModuleOperations.add(userModuleOperation7);
            UserModuleOperation userModuleOperation8 = new UserModuleOperation(user, moduleOperations.get(7));
            userModuleOperations.add(userModuleOperation8);
            UserModuleOperation userModuleOperation9 = new UserModuleOperation(user, moduleOperations.get(8));
            userModuleOperations.add(userModuleOperation9);
            UserModuleOperation userModuleOperation10 = new UserModuleOperation(user, moduleOperations.get(9));
            userModuleOperations.add(userModuleOperation10);
            UserModuleOperation userModuleOperation11 = new UserModuleOperation(user, moduleOperations.get(10));
            userModuleOperations.add(userModuleOperation11);

            userRepository.save(user);
            userDetailRepository.save(userDetail);
            moduleRepository.saveAll(modules);
            operationRepository.saveAll(operations);
            moduleOperationRepository.saveAll(moduleOperations);
            userModuleOperationRepository.saveAll(userModuleOperations);
        } catch (Exception e){
            log.error(e);
        } finally {
            log.info("System was running!");
        }
    }
}
