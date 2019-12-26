package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin")
public class AdministratorController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        if (page.equalsIgnoreCase(Constants.ROUTE.MODULE)) {
            model.addAttribute(Constants.PARENTS, moduleRepository.findAllByModuleIsNullAndActiveTrue());
            model.addAttribute(Constants.LIST, moduleRepository.getModulesByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Module());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.OPERATION)){
            model.addAttribute(Constants.LIST, operationRepository.getOperationsByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Operation());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY)){
            model.addAttribute(Constants.LIST, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Active(true));
            model.addAttribute(Constants.DICTIONARY_TYPES, dictionaryTypeRepository.getDictionaryTypesByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Dictionary());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY_TYPE)){
            model.addAttribute(Constants.LIST, dictionaryTypeRepository.getDictionaryTypesByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new DictionaryType());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.USER)){
            model.addAttribute(Constants.LANGUAGES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("language"));
            List<Employee> employees;
            List<Organization> organizations = organizationRepository.getOrganizationsByActiveTrueAndOrganizationType_Attr1("branch");
            List<User> users;
            if(canViewAll()){
                employees = employeeRepository.getEmployeesByContractEndDateIsNull();
                users = userRepository.getUsersByActiveTrue();
            } else {
                employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization());
                users = userRepository.getUsersByActiveTrueAndEmployee_Organization(getSessionOrganization());
            }
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByOrganization(employees, organizations));
            model.addAttribute(Constants.LIST, users);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new User());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.MODULE_OPERATION)){
            model.addAttribute(Constants.MODULES, moduleRepository.getModulesByActiveTrue());
            model.addAttribute(Constants.OPERATIONS, operationRepository.getOperationsByActiveTrue());
            model.addAttribute(Constants.LIST, moduleOperationRepository.findAll());
            model.addAttribute(Constants.FORM, new ModuleOperation());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.USER_MODULE_OPERATION)){
            model.addAttribute(Constants.USERS, userRepository.getUsersByActiveTrue());
            List<ModuleOperation>  list = moduleOperationRepository.findAll();
            model.addAttribute(Constants.MODULES, Util.removeDuplicateModules(list));
            model.addAttribute(Constants.OPERATIONS, Util.removeDuplicateOperations(list));
            model.addAttribute(Constants.LIST, list);
            model.addAttribute(Constants.TEMPLATES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("template"));
            model.addAttribute(Constants.FORM, new UserModuleOperation());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.TEMPLATE_MODULE_OPERATION)){
            List<ModuleOperation>  list = moduleOperationRepository.findAll();
            model.addAttribute(Constants.TEMPLATES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("template"));
            model.addAttribute(Constants.MODULES, Util.removeDuplicateModules(list));
            model.addAttribute(Constants.OPERATIONS, Util.removeDuplicateOperations(list));
            model.addAttribute(Constants.LIST, list);
            model.addAttribute(Constants.FORM, new TemplateModuleOperation());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.CURRENCY_RATE)){
            model.addAttribute(Constants.LIST, currencyRateRepository.findAll());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new CurrencyRate());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.CONFIGURATION)){
            model.addAttribute(Constants.LIST, configurationRepository.getConfigurationsByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Configuration());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.NOTIFICATION)){
            model.addAttribute(Constants.NOTIFICATIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("notification"));
            List<Notification> notifications;
            if(canViewAll()){
                notifications = notificationRepository.getNotificationsByActiveTrue();
            } else {
                notifications = notificationRepository.getNotificationsByActiveTrueAndOrganization(getSessionOrganization());
            }
            model.addAttribute(Constants.LIST, notifications);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Notification());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.LOG)){
            model.addAttribute(Constants.LIST, logRepository.getLogsByActiveTrueOrderByOperationDateDesc());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Log());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/notification")
    public String postNotification(@ModelAttribute(Constants.FORM) @Validated Notification notification, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            if(notification.getType()!=null && notification.getType().getAttr1().equalsIgnoreCase("email")){
                notification.setFrom(springEmailUserName);
            }
            notificationRepository.save(notification);
            log("admin_notification", "create/edit", notification.getId(), notification.toString());
        }
        return mapPost(notification, binding, redirectAttributes);
    }

    @PostMapping(value = "/module")
    public String postModule(@ModelAttribute(Constants.FORM) @Validated Module module, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            moduleRepository.save(module);
            log("admin_module", "create/edit", module.getId(), module.toString());
            File source = new File(request.getRealPath("/WEB-INF/jsp/pages/empty.jsp"));
            File dest = new File(request.getRealPath("/WEB-INF/jsp/pages/"+module.getPath()+".jsp"));
            Files.copy(source.toPath(), dest.toPath());
        }
        return mapPost(module, binding, redirectAttributes);
    }

    @PostMapping(value = "/module/move")
    public String postModuleMove(@RequestParam(name="parent-id", defaultValue = "0") int parentId,
                                 @RequestParam(name="sub-id", defaultValue = "0") int subId) throws Exception {
        Module parent = moduleRepository.getModuleById(parentId);
        Module sub = moduleRepository.getModuleById(subId);
        parent.setModule(sub);
        moduleRepository.save(parent);
        log("admin_module", "move", parent.getId(), parent.toString());
        return "redirect:/admin/module";
    }

    @PostMapping(value = "/operation")
    public String postOperation(@ModelAttribute(Constants.FORM) @Validated Operation operation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            operationRepository.save(operation);
            log("admin_operation", "create/edit", operation.getId(), operation.toString());
        }
        return mapPost(operation, binding, redirectAttributes);
    }

    @PostMapping(value = "/dictionary-type")
    public String postDictionaryType(@ModelAttribute(Constants.FORM) @Validated DictionaryType dictionaryType, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            dictionaryTypeRepository.save(dictionaryType);
            log("admin_dictionary_type", "create/edit", dictionaryType.getId(), dictionaryType.toString());
        }
        return mapPost(dictionaryType, binding, redirectAttributes);
    }

    @PostMapping(value = "/log")
    public String postLog(@ModelAttribute(Constants.FORM) @Validated Log log, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            Log lg = logRepository.getLogById(log.getId());
            log.setUsername(lg.getUsername());
            log.setOperationDate(lg.getOperationDate());
            log.setEncapsulate(lg.getEncapsulate());
            logRepository.save(log);
        }
        return mapPost(log, binding, redirectAttributes);
    }

    @PostMapping(value = "/dictionary")
    public String postDictionary(@ModelAttribute(Constants.FORM) @Validated Dictionary dictionary, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            dictionaryRepository.save(dictionary);
            log("admin_dictionary", "create/edit", dictionary.getId(), dictionary.toString());
        }
        return mapPost(dictionary, binding, redirectAttributes);
    }

    @PostMapping(value = "/user")
    public String postUser(@ModelAttribute(Constants.FORM) @Validated User user, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            String password = user.getPassword();
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            userRepository.save(user);
            log("admin_user", "create/edit", user.getId(), user.toString());
            String message = "Hörmətli " + user.getEmployee().getPerson().getFirstName() + ",<br/><br/>" +
                    "Sizin məlumatlarınıza əsasən yeni istifadəçi yaradılmışdır.<br/><br/>" +
                    "İstifadəçi adınız: "+user.getUsername()+"<br/>" +
                    "Şifrəniz: "+password+"<br/><br/>";
            sendEmail(user.getEmployee().getOrganization(), user.getEmployee().getPerson().getContact().getEmail(),
                    "Yeni istifadəçi!",
                    message,
                    null
            );
        }
        return mapPost(user, binding, redirectAttributes);
    }

    @PostMapping(value = "/module-operation")
    public String postModuleOperation(@ModelAttribute(Constants.FORM) @Validated ModuleOperation moduleOperation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            moduleOperationRepository.save(moduleOperation);
            log("admin_module_operation", "create/edit", moduleOperation.getId(), moduleOperation.toString());
        }
        return mapPost(moduleOperation, binding, redirectAttributes);
    }

    @PostMapping(value = "/user-module-operation")
    public String postUserAccess(@ModelAttribute(Constants.FORM) @Validated UserModuleOperation userModuleOperation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors() && userModuleOperation.getUser()!=null){
            List<UserModuleOperation> userModuleOperations = userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(userModuleOperation.getUser().getId(), true);
            userModuleOperationRepository.deleteInBatch(userModuleOperations);
            for (ModuleOperation mo: userModuleOperation.getModuleOperations()){
                mo.setModuleOperations(null);
                userModuleOperationRepository.save(new UserModuleOperation(userModuleOperation.getUser(), mo));
            }
            userDetailRepository.save(userModuleOperation.getUser().getUserDetail());
            log("admin_user_module_operation", "create/edit", userModuleOperation.getId(), userModuleOperation.toString());
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
        List<TemplateModuleOperation> templateModuleOperations = templateModuleOperationRepository.getTemplateModuleOperationsByTemplate_Id(template!=null?template.getId():0);
        if(!binding.hasErrors() && template!=null){
            templateModuleOperationRepository.deleteInBatch(templateModuleOperations);
            for (ModuleOperation mo: templateModuleOperation.getModuleOperations()){
                mo.setModuleOperations(null);
                templateModuleOperationRepository.save(new TemplateModuleOperation(templateModuleOperation.getTemplate(), mo));
                log("admin_dictionary", "create/edit", templateModuleOperation.getId(), templateModuleOperation.toString());
            }
            model.addAttribute(Constants.FORM, new TemplateModuleOperation());
        }
        return mapPost(templateModuleOperation, binding, redirectAttributes);
    }

    @ResponseBody
    @PostMapping(value = "/employee/get/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployees(@PathVariable("organizationId") int organizationId) {
        List<Employee> employees = new ArrayList<>();
        try{
            employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(organizationRepository.getOrganizationByIdAndActiveTrue(organizationId));
        } catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }

    @PostMapping(value = "/currency-rate")
    public String postCurrencyRate(@ModelAttribute(Constants.FORM) @Validated CurrencyRate currencyRate, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        currencyRateRepository.deleteAll();
        log("admin_currency_rate", "delete-all", currencyRate.getId(), currencyRate.toString());
        currencyRateRepository.saveAll(Util.getCurrenciesRate(cbarCurrenciesEndpoint));
        log("admin_currency_rate", "create/edit-all", currencyRate.getId(), currencyRate.toString());
        return mapPost(currencyRate, binding, redirectAttributes);
    }

    @PostMapping(value = "/configuration")
    public String postConfiguration(@ModelAttribute(Constants.FORM) @Validated Configuration configuration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            configurationRepository.save(configuration);
            log("admin_configuration", "create/edit", configuration.getId(), configuration.toString());
        }
        return mapPost(configuration, binding, redirectAttributes);
    }
}
