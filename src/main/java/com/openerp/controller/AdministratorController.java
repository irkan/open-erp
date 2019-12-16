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
        /*session.setAttribute(Constants.PAGE, page);
        String description = "";
        List<Module> moduleList = (List<Module>) session.getAttribute(Constants.MODULES);
        for(Module m: moduleList){
            if(m.getPath().equalsIgnoreCase(page)){
                description = m.getDescription();
                break;
            }
        }
        session.setAttribute(Constants.page.description, description);*/

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
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization_Id(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getId());
            List<Organization> organizations = organizationRepository.getOrganizationsByActiveTrueAndOrganizationType_Attr1("branch");
            List<User> users = userRepository.getUsersByActiveTrueAndEmployee_Organization(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()));
            if(isHeadOffice()){
                employees = employeeRepository.getEmployeesByContractEndDateIsNull();
                users = userRepository.getUsersByActiveTrue();
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
            model.addAttribute(Constants.LIST, notificationRepository.getNotificationsByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Notification());
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
        }
        return mapPost(notification, binding, redirectAttributes);
    }

    @PostMapping(value = "/module")
    public String postModule(@ModelAttribute(Constants.FORM) @Validated Module module, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            moduleRepository.save(module);
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
        return "redirect:/admin/module";
    }

    @PostMapping(value = "/operation")
    public String postOperation(@ModelAttribute(Constants.FORM) @Validated Operation operation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            operationRepository.save(operation);
        }
        return mapPost(operation, binding, redirectAttributes);
    }

    @PostMapping(value = "/dictionary-type")
    public String postDictionaryType(@ModelAttribute(Constants.FORM) @Validated DictionaryType dictionaryType, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            dictionaryTypeRepository.save(dictionaryType);
        }
        return mapPost(dictionaryType, binding, redirectAttributes);
    }

    @PostMapping(value = "/dictionary")
    public String postDictionary(@ModelAttribute(Constants.FORM) @Validated Dictionary dictionary, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            dictionaryRepository.save(dictionary);
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
            String message = "Hörmətli " + user.getEmployee().getPerson().getFirstName() + ",<br/><br/>" +
                    "Sizin məlumatlarınıza əsasən yeni istifadəçi yaradılmışdır.<br/><br/>" +
                    "İstifadəçi adınız: "+user.getUsername()+"<br/>" +
                    "Şifrəniz: "+password+"<br/><br/>";
            sendEmail(user.getEmployee().getPerson().getContact().getEmail(),
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
        }
        return mapPost(moduleOperation, binding, redirectAttributes);
    }

    @PostMapping(value = "/user-module-operation")
    public String postUserAccess(Model model, @ModelAttribute(Constants.FORM) @Validated UserModuleOperation userModuleOperation,
                                 @RequestParam(name="template", required = false, defaultValue = "0") int templateId, BindingResult binding) throws Exception {
        model.addAttribute(Constants.FORM, userModuleOperation);
        model.addAttribute(Constants.TEMPLATE_ID, templateId);
        if(!binding.hasErrors() && userModuleOperation.getUser()!=null){
            List<UserModuleOperation> userModuleOperations = userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(userModuleOperation.getUser().getId(), true);
            userModuleOperationRepository.deleteInBatch(userModuleOperations);
            for (ModuleOperation mo: userModuleOperation.getModuleOperations()){
                mo.setModuleOperations(null);
                userModuleOperationRepository.save(new UserModuleOperation(userModuleOperation.getUser(), mo));
            }
            model.addAttribute(Constants.FORM, new UserModuleOperation());
            model.addAttribute(Constants.TEMPLATE_ID, 0);
        }
        model.addAttribute(Constants.USERS, userRepository.findAll());
        List<ModuleOperation>  list = moduleOperationRepository.findAll();
        model.addAttribute(Constants.MODULES, Util.removeDuplicateModules(list));
        model.addAttribute(Constants.OPERATIONS, Util.removeDuplicateOperations(list));
        model.addAttribute(Constants.LIST, list);
        model.addAttribute(Constants.TEMPLATES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("template"));
        model.addAttribute(Constants.TEMPLATE_MODULE_OPERATIONS, templateModuleOperationRepository.findAllByTemplate_Id(templateId));
        return "layout";
    }

    @PostMapping(value = "/get-template")
    public String postGetTemplate(Model model, @ModelAttribute(Constants.FORM) @Validated TemplateModuleOperation templateModuleOperation) throws Exception {
        model.addAttribute(Constants.FORM, templateModuleOperation);
        model.addAttribute(Constants.USERS, userRepository.findAll());
        List<ModuleOperation>  list = moduleOperationRepository.findAll();
        model.addAttribute(Constants.MODULES, Util.removeDuplicateModules(list));
        model.addAttribute(Constants.OPERATIONS, Util.removeDuplicateOperations(list));
        model.addAttribute(Constants.LIST, list);
        model.addAttribute(Constants.TEMPLATES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("template"));
        Dictionary template = templateModuleOperation.getTemplate();
        model.addAttribute(Constants.TEMPLATE_MODULE_OPERATIONS, templateModuleOperationRepository.findAllByTemplate_Id(template!=null?template.getId():0));
        return "layout";
    }

    @ResponseBody
    @GetMapping(value = "/get-user-module-operation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserModuleOperation> postGetUserModuleOperation(@PathVariable("id") int id) throws Exception {
        return userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(id, true);
    }

    @ResponseBody
    @GetMapping(value = "/get-template-module-operation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TemplateModuleOperation> postGetTemplateModuleOperation(@PathVariable("id") int id) throws Exception {
        return templateModuleOperationRepository.findAllByTemplate_Id(id);
    }

    @PostMapping(value = "/template-module-operation")
    public String postTemplateModuleOperation(Model model, @ModelAttribute(Constants.FORM) @Validated TemplateModuleOperation templateModuleOperation, BindingResult binding) throws Exception {
        model.addAttribute(Constants.FORM, templateModuleOperation);
        Dictionary template = templateModuleOperation.getTemplate();
        List<TemplateModuleOperation> templateModuleOperations = templateModuleOperationRepository.findAllByTemplate_Id(template!=null?template.getId():0);
        if(!binding.hasErrors() && template!=null){
            templateModuleOperationRepository.deleteInBatch(templateModuleOperations);
            for (ModuleOperation mo: templateModuleOperation.getModuleOperations()){
                mo.setModuleOperations(null);
                templateModuleOperationRepository.save(new TemplateModuleOperation(templateModuleOperation.getTemplate(), mo));
            }
            model.addAttribute(Constants.FORM, new TemplateModuleOperation());
        }
        model.addAttribute(Constants.USERS, userRepository.findAll());
        List<ModuleOperation>  list = moduleOperationRepository.findAll();
        model.addAttribute(Constants.MODULES, Util.removeDuplicateModules(list));
        model.addAttribute(Constants.OPERATIONS, Util.removeDuplicateOperations(list));
        model.addAttribute(Constants.LIST, list);
        model.addAttribute(Constants.TEMPLATES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("template"));
        model.addAttribute(Constants.TEMPLATE_MODULE_OPERATIONS, templateModuleOperations);
        return "layout";
    }

    @ResponseBody
    @PostMapping(value = "/employee/get/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployees(@PathVariable("organizationId") int organizationId) {
        List<Employee> employees = new ArrayList<>();
        try{
            employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization_Id(organizationId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }

    @PostMapping(value = "/currency-rate")
    public String postCurrencyRate(@ModelAttribute(Constants.FORM) @Validated CurrencyRate currencyRate, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        currencyRateRepository.deleteAll();
        currencyRateRepository.saveAll(Util.getCurrenciesRate(cbarCurrenciesEndpoint));
        return mapPost(currencyRate, binding, redirectAttributes);
    }

    @PostMapping(value = "/configuration")
    public String postConfiguration(@ModelAttribute(Constants.FORM) @Validated Configuration configuration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            configurationRepository.save(configuration);
        }
        return mapPost(configuration, binding, redirectAttributes);
    }
}
