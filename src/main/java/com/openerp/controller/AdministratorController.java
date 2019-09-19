package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.Util;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdministratorController extends SkeletonController {

    @GetMapping(value = "/{page}")
    public String route(Model model, @PathVariable("page") String page) throws Exception {
        session.setAttribute(Constants.PAGE, page);
        String description = "";
        List<Module> moduleList = (List<Module>) session.getAttribute(Constants.MODULES);
        for(Module m: moduleList){
            if(m.getPath().equalsIgnoreCase(page)){
                description = m.getDescription();
                break;
            }
        }
        session.setAttribute(Constants.MODULE_DESCRIPTION, description);

        if (page.equalsIgnoreCase(Constants.ROUTE.MODULE)) {
            model.addAttribute(Constants.ICONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("icon"));
            model.addAttribute(Constants.PARENTS, moduleRepository.findAllByModuleIsNullAndActiveTrue());
            model.addAttribute(Constants.LIST, moduleRepository.getModulesByActiveTrue());
            if(session.getAttribute(Constants.BINDING)==null){
                model.addAttribute(Constants.FORM, new Module());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.OPERATION)){
            model.addAttribute(Constants.ICONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("icon"));
            model.addAttribute(Constants.LIST, operationRepository.getOperationsByActiveTrue());
            if(session.getAttribute(Constants.BINDING)==null){
                model.addAttribute(Constants.FORM, new Operation());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY)){
            model.addAttribute(Constants.LIST, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Active(true));
            model.addAttribute(Constants.DICTIONARY_TYPES, dictionaryTypeRepository.getDictionaryTypesByActiveTrue());
            if(session.getAttribute(Constants.BINDING)==null){
                model.addAttribute(Constants.FORM, new Dictionary());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY_TYPE)){
            model.addAttribute(Constants.LIST, dictionaryTypeRepository.getDictionaryTypesByActiveTrue());
            if(session.getAttribute(Constants.BINDING)==null){
                model.addAttribute(Constants.FORM, new DictionaryType());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.USER)){
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.findAll());
            model.addAttribute(Constants.EMPLOYEES, employeeRepository.findAll());
            model.addAttribute(Constants.LIST, userRepository.findAll());
            model.addAttribute(Constants.FORM, new User());
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
        }
        return "layout";
    }

    @PostMapping(value = "/module")
    public String postModule(@ModelAttribute(Constants.FORM) @Validated Module module, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            moduleRepository.save(module);
        }
        return mapPost(module, binding, redirectAttributes);
    }

    @PostMapping(value = "/operation")
    public String postOperation(@ModelAttribute(Constants.FORM) @Validated Operation operation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            operationRepository.save(operation);
        }
        return mapPost(operation, binding, redirectAttributes);
    }

    @PostMapping(value = "/dictionary-type")
    public String postDictionaryType(@ModelAttribute(Constants.FORM) @Validated DictionaryType dictionaryType, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            dictionaryTypeRepository.save(dictionaryType);
        }
        return mapPost(dictionaryType, binding, redirectAttributes);
    }

    @PostMapping(value = "/dictionary")
    public String postDictionary(@ModelAttribute(Constants.FORM) @Validated Dictionary dictionary, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            dictionaryRepository.save(dictionary);
        }
        return mapPost(dictionary, binding, redirectAttributes);
    }

    @PostMapping(value = "/user")
    public String postUser(@ModelAttribute(Constants.FORM) @Validated User user, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            userRepository.save(user);
        }
        return mapPost(user, binding, redirectAttributes);
    }

    @PostMapping(value = "/module-operation")
    public String postModuleOperation(@ModelAttribute(Constants.FORM) @Validated ModuleOperation moduleOperation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
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
            List<UserModuleOperation> userModuleOperations = userModuleOperationRepository.findAllByUser_Id(userModuleOperation.getUser().getId());
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
    public String postGetTemplate(Model model, @ModelAttribute(Constants.FORM) @Validated TemplateModuleOperation templateModuleOperation, BindingResult binding) throws Exception {
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

    @PostMapping(value = "/get-user-template")
    public String postGetUserTemplate(Model model, @ModelAttribute(Constants.FORM) @Validated UserModuleOperation userModuleOperation,
                                      @RequestParam(name="template", required = false, defaultValue = "0") int templateId,
                                      @RequestParam(name="user", required = false, defaultValue = "0") int userId,BindingResult binding) throws Exception {
        model.addAttribute(Constants.FORM, userModuleOperation);
        model.addAttribute(Constants.USERS, userRepository.findAll());
        List<ModuleOperation>  list = moduleOperationRepository.findAll();
        model.addAttribute(Constants.MODULES, Util.removeDuplicateModules(list));
        model.addAttribute(Constants.OPERATIONS, Util.removeDuplicateOperations(list));
        model.addAttribute(Constants.LIST, list);
        model.addAttribute(Constants.TEMPLATE_ID, templateId);
        model.addAttribute(Constants.TEMPLATES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("template"));
        model.addAttribute(Constants.TEMPLATE_MODULE_OPERATIONS, templateModuleOperationRepository.findAllByTemplate_Id(templateId));
        model.addAttribute(Constants.USER_MODULE_OPERATIONS,  userModuleOperationRepository.findAllByUser_Id(userId));
        return "layout";
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
            employees = employeeRepository.getEmployeesByOrganization_Id(organizationId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return employees;
    }
}
