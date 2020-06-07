package com.openerp.controller;

import com.openerp.domain.LogFile;
import com.openerp.domain.Session;
import com.openerp.entity.*;
import com.openerp.task.MigrationTask;
import com.openerp.util.Constants;
import com.openerp.util.Tail;
import com.openerp.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdministratorController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        if (page.equalsIgnoreCase(Constants.ROUTE.MODULE)) {
            model.addAttribute(Constants.PARENTS, moduleRepository.getModulesByActiveTrueAndModuleIsNull());
            model.addAttribute(Constants.LIST, moduleRepository.getModulesByActiveTrue());
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new Module());
            }
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(moduleRepository.findAll(), redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.OPERATION)) {
            model.addAttribute(Constants.LIST, operationRepository.getOperationsByActiveTrue());
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new Operation());
            }
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(operationRepository.findAll(), redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY)) {
            model.addAttribute(Constants.LIST, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Active(true));
            model.addAttribute(Constants.DICTIONARY_TYPES, dictionaryTypeRepository.getDictionaryTypesByActiveTrue());
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new Dictionary());
            }
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(dictionaryRepository.findAll(), redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY_TYPE)) {
            model.addAttribute(Constants.LIST, dictionaryTypeRepository.getDictionaryTypesByActiveTrue());
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new DictionaryType());
            }
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(dictionaryTypeRepository.findAll(), redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.USER)) {
            model.addAttribute(Constants.LANGUAGES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("language"));
            List<Employee> employees;
            List<Organization> organizations = organizationRepository.getOrganizationsByActiveTrueAndType_Attr1("branch");
            List<User> users;
            if (canViewAll()) {
                employees = employeeRepository.getEmployeesByContractEndDateIsNullAndActiveTrue();
                users = userRepository.getUsersByActiveTrue();
            } else {
                employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization());
                users = userRepository.getUsersByActiveTrueAndEmployee_Organization(getSessionOrganization());
            }
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByOrganization(employees, organizations));
            model.addAttribute(Constants.LIST, users);
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new User());
            }
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(userRepository.findAll(), redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.MODULE_OPERATION)) {
            model.addAttribute(Constants.MODULES, moduleRepository.getModulesByActiveTrue());
            model.addAttribute(Constants.OPERATIONS, operationRepository.getOperationsByActiveTrue());
            model.addAttribute(Constants.LIST, moduleOperationRepository.findAll());
            model.addAttribute(Constants.FORM, new ModuleOperation());
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(moduleOperationRepository.findAll(), redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.USER_MODULE_OPERATION)) {
            List<ModuleOperation> list = Util.getModuleOperations(userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(getSessionUser().getId(), true));
            if (getSessionUser().getUsername().equalsIgnoreCase("admin")) {
                list = moduleOperationRepository.getModuleOperationsByModule_ActiveAndOperation_Active(true, true);
            }
            List<Organization> organizations = organizationRepository.getOrganizationsByActiveTrue();
            List<User> users;
            if (canViewAll()) {
                users = userRepository.getUsersByActiveTrue();
            } else {
                users = userRepository.getUsersByActiveTrueAndEmployee_Organization(getSessionOrganization());
            }
            model.addAttribute(Constants.USERS, Util.convertedUsersByOrganization(Util.getAccessibleUsers(users, list.size()), organizations));
            model.addAttribute(Constants.MODULES, Util.removeDuplicateModules(list));
            model.addAttribute(Constants.OPERATIONS, Util.removeDuplicateOperations(list));
            model.addAttribute(Constants.LIST, list);
            model.addAttribute(Constants.TEMPLATES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("template"));
            model.addAttribute(Constants.FORM, new UserModuleOperation());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.TEMPLATE_MODULE_OPERATION)) {
            List<ModuleOperation> list = Util.getModuleOperations(userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(getSessionUser().getId(), true));
            model.addAttribute(Constants.TEMPLATES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("template"));
            model.addAttribute(Constants.MODULES, Util.removeDuplicateModules(list));
            model.addAttribute(Constants.OPERATIONS, Util.removeDuplicateOperations(list));
            model.addAttribute(Constants.LIST, list);
            model.addAttribute(Constants.FORM, new TemplateModuleOperation());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.CURRENCY_RATE)) {
            model.addAttribute(Constants.LIST, currencyRateRepository.findAll());
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new CurrencyRate());
            }
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(currencyRateRepository.findAll(), redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.CONFIGURATION)) {
            model.addAttribute(Constants.LIST, configurationRepository.getConfigurationsByActiveTrue());
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new Configuration());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.APPROVER_EXCEPTION)) {
            model.addAttribute(Constants.USERS, canViewAll() ? userRepository.getUsersByActiveTrue() : userRepository.getUsersByActiveTrueAndEmployee_Organization(getSessionOrganization()));
            List<ApproverException> approverExceptions = canViewAll() ? approverExceptionRepository.findAll() : approverExceptionRepository.getApproverExceptionsByOrganization(getSessionOrganization());
            model.addAttribute(Constants.LIST, approverExceptions);
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new ApproverException(getSessionOrganization()));
            }
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(approverExceptions, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SESSION)) {
            List<HttpSession> httpSessions = httpSessionConfig.getActiveSessions();
            List<Session> sessions = Util.convertHttpSession(httpSessions);
            model.addAttribute(Constants.LIST, sessions);
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(sessions, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.WEB_SERVICE_AUTHENTICATOR)) {
            model.addAttribute(Constants.LIST, webServiceAuthenticatorRepository.findAll());
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new WebServiceAuthenticator(true));
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.PERIOD)) {
            model.addAttribute(Constants.LIST, periodRepository.findAll());
            model.addAttribute(Constants.USERS, userRepository.getUsersByActiveTrue());
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new Period());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.NOTIFICATION)) {
            model.addAttribute(Constants.NOTIFICATIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("notification"));
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new Notification(getSessionOrganization()));
            }
            if (!model.containsAttribute(Constants.FILTER)) {
                model.addAttribute(Constants.FILTER, new Notification(!canViewAll() ? getSessionOrganization() : null, null));
            }
            Page<Notification> notifications = notificationService.findAll((Notification) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, notifications);
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(notifications, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.LOG)) {
            if (!model.containsAttribute(Constants.FILTER)) {
                model.addAttribute(Constants.FILTER, new Log(null));
            }
            Page<Log> logs = logService.findAll((Log) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, logs);
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new Log());
            }
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(logs, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.LOG_FILE)) {
            if (!model.containsAttribute(Constants.FILTER)) {
                model.addAttribute(Constants.FILTER, new LogFile());
            }
            String path = "";
            if (!data.equals(Optional.empty())) {
                for (String s : data.get().split("---")) {
                    path += s + "/";
                }
            }
            path = path.trim().length() > 0 ? path : logFilePath;
            model.addAttribute(Constants.PATH, path);
            File logs = new File(path);
            if (!logs.isDirectory() && !logs.getName().matches(Constants.REGEX.REGEX6)) {
                LogFile logFile = (LogFile) model.asMap().get(Constants.FILTER);
                List<String> content = Tail.tailFile(logs.toPath(), logFile.getCount());
                Collections.reverse(content);
                model.addAttribute(Constants.CONTENTS, content);
            } else {
                model.addAttribute(Constants.FILES, logs.listFiles());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ENDPOINT)) {
            model.addAttribute(Constants.CONNECTION_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("connection-type"));
            if (!model.containsAttribute(Constants.FILTER)) {
                model.addAttribute(Constants.FILTER, new Endpoint());
            }
            Page<Endpoint> endpoints = endpointService.findAll((Endpoint) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, endpoints);
            if (!model.containsAttribute(Constants.FORM)) {
                model.addAttribute(Constants.FORM, new Endpoint());
            }
            if (!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)) {
                return exportExcel(endpoints, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.MIGRATION)) {
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrue());
            model.addAttribute(Constants.SUPPLIERS, supplierRepository.getSuppliersByActiveTrue());
            model.addAttribute(Constants.FORM, new Migration());
            List<Migration> migrations = new ArrayList<>();
            for(Migration migration: migrationRepository.getMigrationsByActiveTrue()){
                migration.setDataCount(migrationDetailRepository.getMigrationDetailsByActiveTrueAndMigrationId(migration.getId()).size());
                migration.setInsertedCount(migrationDetailRepository.getMigrationDetailsByActiveTrueAndMigrationIdAndStatus(migration.getId(), 1).size());
                migration.setErrorCount(migrationDetailRepository.getMigrationDetailsByActiveTrueAndMigrationIdAndStatus(migration.getId(), 2).size());
                migrations.add(migration);
            }
            model.addAttribute(Constants.LIST, migrations);
        } else if (page.equalsIgnoreCase(Constants.ROUTE.MIGRATION_DETAIL)) {
            model.addAttribute(Constants.LIST, migrationDetailRepository.getMigrationDetailsByActiveTrueAndMigrationId(Integer.parseInt(data.get())));
        } else if (page.equalsIgnoreCase(Constants.ROUTE.MIGRATION_DETAIL_SERVICE_REGULATOR)) {
            model.addAttribute(Constants.LIST, migrationDetailServiceRegulatorRepository.getMigrationDetailServiceRegulatorsByMigrationId(Integer.parseInt(data.get())));
        }
        return "layout";
    }

    @PostMapping(value = "/notification")
    public String postNotification(@ModelAttribute(Constants.FORM) @Validated Notification notification, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            if (notification.getType() != null && notification.getType().getAttr1().equalsIgnoreCase("email")) {
                notification.setFrom(springEmailUserName);
            }
            notificationRepository.save(notification);
            log(notification, "admin_notification", "create/edit", notification.getId(), notification.toString());
        }
        return mapPost(notification, binding, redirectAttributes);
    }

    @PostMapping(value = "/notification/filter")
    public String postNotificationFilter(@ModelAttribute(Constants.FILTER) @Validated Notification notification, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(notification, binding, redirectAttributes, "/admin/notification");
    }

    @PostMapping(value = "/endpoint")
    public String postEndpoint(@ModelAttribute(Constants.FORM) @Validated Endpoint endpoint, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            endpointRepository.save(endpoint);
            log(endpoint, "admin_endpoint", "create/edit", endpoint.getId(), endpoint.toString());
        }
        return mapPost(endpoint, binding, redirectAttributes);
    }

    @PostMapping(value = "/endpoint/filter")
    public String postEndpointFilter(@ModelAttribute(Constants.FILTER) @Validated Endpoint endpoint, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(endpoint, binding, redirectAttributes, "/admin/endpoint");
    }

    @PostMapping(value = "/module")
    public String postModule(@ModelAttribute(Constants.FORM) @Validated Module module, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            moduleRepository.save(module);
            log(module, "admin_module", "create/edit", module.getId(), module.toString());
            /*File source = new File(request.getRealPath("/WEB-INF/jsp/pages/empty.jsp"));
            File dest = new File(request.getRealPath("/WEB-INF/jsp/pages/"+module.getPath()+".jsp"));
            if(Files.notExists(dest.toPath())){
                Files.copy(source.toPath(), dest.toPath());
            }*/
        }
        return mapPost(module, binding, redirectAttributes);
    }

    @PostMapping(value = "/module/move")
    public String postModuleMove(@RequestParam(name = "parent-id", defaultValue = "0") int parentId,
                                 @RequestParam(name = "sub-id", defaultValue = "0") int subId) throws Exception {
        Module parent = moduleRepository.getModuleById(parentId);
        Module sub = moduleRepository.getModuleById(subId);
        parent.setModule(sub);
        moduleRepository.save(parent);
        log(parent, "admin_module", "move", parent.getId(), parent.toString());
        return "redirect:/admin/module";
    }

    @PostMapping(value = "/operation")
    public String postOperation(@ModelAttribute(Constants.FORM) @Validated Operation operation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            operationRepository.save(operation);
            log(operation, "admin_operation", "create/edit", operation.getId(), operation.toString());
        }
        return mapPost(operation, binding, redirectAttributes);
    }

    @PostMapping(value = "/dictionary-type")
    public String postDictionaryType(@ModelAttribute(Constants.FORM) @Validated DictionaryType dictionaryType, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            dictionaryTypeRepository.save(dictionaryType);
            log(dictionaryType, "admin_dictionary_type", "create/edit", dictionaryType.getId(), dictionaryType.toString());
        }
        return mapPost(dictionaryType, binding, redirectAttributes);
    }

    @PostMapping(value = "/log")
    public String postLog(@ModelAttribute(Constants.FORM) @Validated Log log, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            Log lg = logRepository.getLogById(log.getId());
            log.setUsername(lg.getUsername());
            log.setOperationDate(lg.getOperationDate());
            log.setEncapsulate(lg.getEncapsulate());
            logRepository.save(log);
        }
        return mapPost(log, binding, redirectAttributes);
    }

    @PostMapping(value = "/log/filter")
    public String postLogFilter(@ModelAttribute(Constants.FILTER) @Validated Log log, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(log, binding, redirectAttributes, "/admin/log");
    }

    @PostMapping(value = "/log-file/filter")
    public String postLogFileFilter(@ModelAttribute(Constants.FILTER) @Validated LogFile logFile, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(logFile, binding, redirectAttributes, "/admin/log-file");
    }

    @PostMapping(value = "/dictionary")
    public String postDictionary(@ModelAttribute(Constants.FORM) @Validated Dictionary dictionary, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            dictionaryRepository.save(dictionary);
            log(dictionary, "admin_dictionary", "create/edit", dictionary.getId(), dictionary.toString());
        }
        return mapPost(dictionary, binding, redirectAttributes);
    }

    @PostMapping(value = "/user")
    public String postUser(@ModelAttribute(Constants.FORM) @Validated User user, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if (user.getEmployee() == null) {
            FieldError fieldError = new FieldError("", "", "İstifadəçi yaradılmadı! Əməkdaş tapılmadı!");
            binding.addError(fieldError);
        }
        if (user.getEmployee() != null &&
                user.getEmployee().getPerson() == null) {
            FieldError fieldError = new FieldError("", "", "İstifadəçi yaradılmadı! Əməkdaşın şəxs məlumatları tapılmadı!");
            binding.addError(fieldError);
        }
        if (user.getEmployee() != null &&
                user.getEmployee().getPerson() != null &&
                user.getEmployee().getPerson().getContact() == null) {
            FieldError fieldError = new FieldError("", "", "İstifadəçi yaradılmadı! " + user.getEmployee().getPerson().getFullName() + " aid laqə məlumatları tapılmadı!");
            binding.addError(fieldError);
        }
        if (user.getEmployee() != null &&
                user.getEmployee().getPerson() != null &&
                user.getEmployee().getPerson().getContact() != null &&
                (user.getEmployee().getPerson().getContact().getEmail() == null ||
                        user.getEmployee().getPerson().getContact().getEmail().trim().length() < 5 ||
                        user.getEmployee().getPerson().getContact().getEmail().trim().length() > 249)) {
            FieldError fieldError = new FieldError("", "", "İstifadəçi yaradılmadı! " + user.getEmployee().getPerson().getFullName() + " - əməkdaşa doğru email təyin edin!");
            binding.addError(fieldError);
        }

        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            String password = user.getPassword();
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            if (user.getId() != null) {
                user.setPassword(userRepository.getUserByActiveTrueAndId(user.getId()).getPassword());
            }
            userRepository.save(user);
            log(user, "admin_user", "create/edit", user.getId(), user.toString());

            try {
                if (user.getId() == null || user.getId() == 0) {
                    String message = "Hörmətli " + user.getEmployee().getPerson().getFirstName() + ",<br/><br/>" +
                            "Sizin məlumatlarınıza əsasən istifadəçi adı və şifrəsi.<br/><br/>" +
                            "İstifadəçi adınız: " + user.getUsername() + "<br/>" +
                            "Şifrəniz: " + password + "<br/><br/>";
                    sendEmail(user.getEmployee().getOrganization(), user.getEmployee().getPerson().getContact().getEmail(),
                            "Yeni istifadəçi!",
                            message,
                            null
                    );
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log(null, "error", "admin_notification", "", null, "", e.getMessage());
            }


            if (user.getId() == null || user.getId() == 0) {
                List<ModuleOperation> moduleOperations = moduleOperationRepository.getModuleOperationsByModuleIn(moduleRepository.getModulesByActiveTrueAndPath("profile").get(0).getChildren());
                List<UserModuleOperation> userModuleOperations = new ArrayList<>();
                for (ModuleOperation mo : moduleOperations) {
                    userModuleOperations.add(new UserModuleOperation(user, mo));
                }
                userModuleOperationRepository.saveAll(userModuleOperations);
                log(userModuleOperations, "admin_user_module_operation", "create/edit", null, null, "Profil yaradıldı");
            }
        }
        return mapPost(user, binding, redirectAttributes);
    }

    @PostMapping(value = "/user/change-password")
    public String postUserChangePassword(@ModelAttribute(Constants.FORM) @Validated User user, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            try {
                User usr = userRepository.getUserByActiveTrueAndId(user.getId());
                usr.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
                log(usr, "admin_user", "change-password", usr.getId(), usr.toString());
                String message = "Hörmətli " + usr.getEmployee().getPerson().getFirstName() + ",<br/><br/>" +
                        "Sizin məlumatlarınıza əsasən yeni istifadəçi yaradılmışdır.<br/><br/>" +
                        "İstifadəçi adınız: " + usr.getUsername() + "<br/>" +
                        "Şifrəniz: " + user.getPassword() + "<br/><br/>";
                sendEmail(usr.getEmployee().getOrganization(), usr.getEmployee().getPerson().getContact().getEmail(),
                        "Yeni istifadəçi!",
                        message,
                        null
                );
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log(null, "error", "admin_notification", "", null, "", e.getMessage());
            }
        }
        return mapPost(user, binding, redirectAttributes, "/admin/user");
    }

    @PostMapping(value = "/module-operation")
    public String postModuleOperation(@ModelAttribute(Constants.FORM) @Validated ModuleOperation moduleOperation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            moduleOperationRepository.save(moduleOperation);
            log(moduleOperation, "admin_module_operation", "create/edit", moduleOperation.getId(), moduleOperation.toString());
        }
        return mapPost(moduleOperation, binding, redirectAttributes);
    }

    @PostMapping(value = "/user-module-operation")
    public String postUserAccess(@ModelAttribute(Constants.FORM) @Validated UserModuleOperation userModuleOperation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if (!binding.hasErrors() && userModuleOperation.getUser() != null) {
            List<UserModuleOperation> userModuleOperations = userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(userModuleOperation.getUser().getId(), true);
            userModuleOperationRepository.deleteInBatch(userModuleOperations);
            for (ModuleOperation mo : userModuleOperation.getModuleOperations()) {
                mo.setModuleOperations(null);
                userModuleOperationRepository.save(new UserModuleOperation(userModuleOperation.getUser(), mo));
            }
            userDetailRepository.save(userModuleOperation.getUser().getUserDetail());
            log(userModuleOperation, "admin_user_module_operation", "create/edit", userModuleOperation.getUser().getId(), userModuleOperation.toString(), userModuleOperation.getUser().getUsername() + " icazələri yeniləndi");

            try {
                String message = "Hörmətli " + userModuleOperation.getUser().getEmployee().getPerson().getFirstName() + ",<br/><br/>" +
                        "Sistemdə icazələriniz yeniləndi.<br/><br/>";
                sendEmail(userModuleOperation.getUser().getEmployee().getOrganization(), userModuleOperation.getUser().getEmployee().getPerson().getContact().getEmail(),
                        "İcazələriniz yeniləndi!",
                        message,
                        null
                );
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log(null, "error", "admin_notification", "", null, "", e.getMessage());
            }
        }
        return mapPost(userModuleOperation, binding, redirectAttributes);
    }

    @ResponseBody
    @GetMapping(value = "/get-user-module-operation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserModuleOperation> getUserModuleOperation(@PathVariable("id") int id) throws Exception {
        return userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(id, true);
    }

    @ResponseBody
    @GetMapping(value = "/get-template-module-operation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TemplateModuleOperation> getTemplateModuleOperation(@PathVariable("id") int id) throws Exception {
        return templateModuleOperationRepository.getTemplateModuleOperationsByTemplate_Id(id);
    }

    @ResponseBody
    @GetMapping(value = "/get-user-detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDetail postGetUserDetail(@PathVariable("id") int id) throws Exception {
        return userRepository.getUserByActiveTrueAndId(id).getUserDetail();
    }

    @PostMapping(value = "/template-module-operation")
    public String postTemplateModuleOperation(Model model, @ModelAttribute(Constants.FORM) @Validated TemplateModuleOperation templateModuleOperation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Dictionary template = templateModuleOperation.getTemplate();
        List<TemplateModuleOperation> templateModuleOperations = templateModuleOperationRepository.getTemplateModuleOperationsByTemplate_Id(template != null ? template.getId() : 0);
        if (!binding.hasErrors() && template != null) {
            templateModuleOperationRepository.deleteInBatch(templateModuleOperations);
            for (ModuleOperation mo : templateModuleOperation.getModuleOperations()) {
                mo.setModuleOperations(null);
                templateModuleOperationRepository.save(new TemplateModuleOperation(templateModuleOperation.getTemplate(), mo));
            }
            log(template, "admin_template_module_operation", "create/edit", template.getId(), "", template.getName() + " şablonu yeniləndi");
            model.addAttribute(Constants.FORM, new TemplateModuleOperation());
        }
        return mapPost(templateModuleOperation, binding, redirectAttributes);
    }

    @ResponseBody
    @PostMapping(value = "/employee/get/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployees(@PathVariable("organizationId") int organizationId) {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(organizationRepository.getOrganizationByIdAndActiveTrue(organizationId));
        } catch (Exception e) {
            log(null, "error", "", "", null, "", e.getMessage());
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return employees;
    }

    @ResponseBody
    @GetMapping(value = "/log/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Log getLog(@PathVariable("id") int id) {
        try {
            Log log = logRepository.getLogById(id);
            return new Log(log.getId(), log.getEncapsulate(), log.getJson());
        } catch (Exception e) {
            log(null, "error", "", "", null, "", e.getMessage());
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @PostMapping(value = "/currency-rate")
    public String postCurrencyRate(@ModelAttribute(Constants.FORM) @Validated CurrencyRate currencyRate, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        currencyRateRepository.deleteAll();
        List<CurrencyRate> currencyRates = Util.getCurrenciesRate(cbarCurrenciesEndpoint);
        currencyRateRepository.saveAll(currencyRates);
        log(currencyRates, "admin_currency_rate", "reload", null, null, "Məzənnə yeniləndi");
        return mapPost(currencyRate, binding, redirectAttributes);
    }

    @PostMapping(value = "/configuration")
    public String postConfiguration(@ModelAttribute(Constants.FORM) @Validated Configuration configuration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            configurationRepository.save(configuration);
            log(configuration, "admin_configuration", "create/edit", configuration.getId(), configuration.toString());
        }
        return mapPost(configuration, binding, redirectAttributes);
    }

    @PostMapping(value = "/approver-exception")
    public String postApproverException(@ModelAttribute(Constants.FORM) @Validated ApproverException approverException, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            approverExceptionRepository.save(approverException);
            log(approverException, "admin_approver_exception", "create/edit", approverException.getId(), approverException.toString());
        }
        return mapPost(approverException, binding, redirectAttributes);
    }

    @PostMapping(value = "/web-service-authenticator")
    public String postWebServiceAuthenticator(@ModelAttribute(Constants.FORM) @Validated WebServiceAuthenticator webServiceAuthenticator, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            webServiceAuthenticator.setActive(true);
            webServiceAuthenticatorRepository.save(webServiceAuthenticator);
            log(webServiceAuthenticator, "admin_web_service_authenticator", "create/edit", webServiceAuthenticator.getId(), webServiceAuthenticator.toString());
        }
        return mapPost(webServiceAuthenticator, binding, redirectAttributes);
    }

    @PostMapping(value = "/period")
    public String postPeriod(@ModelAttribute(Constants.FORM) @Validated Period period, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        List<Period> periods = periodRepository.getPeriodsByUser(period.getUser());
        if (periods != null && periods.size() > 0) {
            FieldError fieldError = new FieldError("user", "user", "İstifadəçi mövcuddur!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            periodRepository.save(period);
            log(period, "admin_period", "create/edit", period.getId(), period.toString());
        }
        return mapPost(period, binding, redirectAttributes);
    }

    @PostMapping(value = "/migration/upload", consumes = {"multipart/form-data"})
    public String postMigrationUpload(@RequestParam("file") MultipartFile file, @ModelAttribute(Constants.FORM) @Validated Migration migration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            migration.setFileName(file.getOriginalFilename());
            migration.setFileContent(file.getBytes());
            migrationRepository.save(migration);
            log(migration, "admin_migration", "upload", migration.getId(), migration.toString(), migration.getFileName() + " uploaded to server");
        }
        return mapPost(migration, binding, redirectAttributes, "/admin/migration");
    }

    @PostMapping(value = "/migration/reload")
    public String postMigrationReload(@ModelAttribute(Constants.FORM) @Validated Migration migration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Migration mg = migrationRepository.getMigrationById(migration.getId());
        mg.setStatus(0);
        migrationRepository.save(mg);
        if(mg.getOperationType().equalsIgnoreCase("satış")){
            migrationDetailRepository.deleteAll(migrationDetailRepository.getMigrationDetailsByMigrationId(mg.getId()));
            migrationTask.writeTable();
        } else if(mg.getOperationType().equalsIgnoreCase("servis")){
            migrationDetailServiceRegulatorRepository.deleteAll(migrationDetailServiceRegulatorRepository.getMigrationDetailServiceRegulatorsByMigrationId(mg.getId()));
            migrationTask.writeTableServiceRegulator();
        }
        return mapPost(mg, binding, redirectAttributes, "/admin/migration");
    }

    @PostMapping(value = "/migration/start")
    public String postMigrationStart(@ModelAttribute(Constants.FORM) @Validated Migration migration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Migration mg = migrationRepository.getMigrationById(migration.getId());
        if(mg.getOperationType().equalsIgnoreCase("satış")){
            migrationTask.startMigrationSalesItems(mg);
        } else if(mg.getOperationType().equalsIgnoreCase("servis")){
            migrationTask.startMigrationServiceRefreshItems(mg);
        }
        return mapPost(mg, binding, redirectAttributes, "/admin/migration");
    }
}
