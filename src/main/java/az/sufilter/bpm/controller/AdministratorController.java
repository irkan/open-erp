package az.sufilter.bpm.controller;

import az.sufilter.bpm.entity.*;
import az.sufilter.bpm.util.Constants;
import az.sufilter.bpm.util.Util;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
            model.addAttribute(Constants.ICONS, dictionaryRepository.getDictionariesByDictionaryType_Attr1("icon"));
            model.addAttribute(Constants.PARENTS, moduleRepository.findAllByModuleIsNull());
            model.addAttribute(Constants.LIST, moduleRepository.findAll());
            model.addAttribute(Constants.FORM, new Module());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.OPERATION)){
            model.addAttribute(Constants.ICONS, dictionaryRepository.getDictionariesByDictionaryType_Attr1("icon"));
            model.addAttribute(Constants.LIST, operationRepository.findAll());
            model.addAttribute(Constants.FORM, new Operation());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY)){
            model.addAttribute(Constants.LIST, dictionaryRepository.findAll());
            model.addAttribute(Constants.FORM, new Dictionary());
            model.addAttribute(Constants.DICTIONARY_TYPES, dictionaryTypeRepository.findAll());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DICTIONARY_TYPE)){
            model.addAttribute(Constants.LIST, dictionaryTypeRepository.findAll());
            model.addAttribute(Constants.FORM, new DictionaryType());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.USER)){
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.findAll());
            model.addAttribute(Constants.EMPLOYEES, employeeRepository.findAll());
            model.addAttribute(Constants.LIST, userRepository.findAll());
            model.addAttribute(Constants.FORM, new User());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.MODULE_OPERATION)){
            model.addAttribute(Constants.MODULES, moduleRepository.findAll());
            model.addAttribute(Constants.OPERATIONS, operationRepository.findAll());
            model.addAttribute(Constants.LIST, moduleOperationRepository.findAll());
            model.addAttribute(Constants.FORM, new ModuleOperation());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.USER_ACCESS)){
            model.addAttribute(Constants.USERS, userRepository.findAll());
            model.addAttribute(Constants.MODULES, moduleRepository.findAll());
            model.addAttribute(Constants.OPERATIONS, operationRepository.findAll());
            model.addAttribute(Constants.LIST, moduleOperationRepository.findAll());
            model.addAttribute(Constants.FORM, new UserModuleOperation());
        }
        return "layout";
    }

    @PostMapping(value = "/dictionary-type")
    public String postDictionaryType(Model model, @ModelAttribute(Constants.FORM) @Validated DictionaryType dictionaryType, BindingResult binding) throws Exception {
        if(!binding.hasErrors()){
            dictionaryTypeRepository.save(dictionaryType);
        }
        model.addAttribute(Constants.FORM, dictionaryType);
        model.addAttribute(Constants.LIST, dictionaryTypeRepository.findAll());
        return "layout";
    }

    @PostMapping(value = "/dictionary")
    public String postDictionary(Model model, @ModelAttribute(Constants.FORM) @Validated Dictionary dictionary, BindingResult binding) throws Exception {
        dictionaryRepository.save(dictionary);
        return "redirect:/admin/page/dictionary";
    }

    @PostMapping(value = "/module")
    public String postModule(Model model, @ModelAttribute(Constants.FORM) @Validated Module module, BindingResult binding) throws Exception {
        if(!binding.hasErrors()){
            moduleRepository.save(module);
            model.addAttribute(Constants.FORM, new Module());
        } else {
            model.addAttribute(Constants.FORM, module);
        }
        model.addAttribute(Constants.ICONS, dictionaryRepository.getDictionariesByDictionaryType_Attr1("icon"));
        model.addAttribute(Constants.PARENTS, moduleRepository.findAllByModuleIsNull());
        model.addAttribute(Constants.LIST, moduleRepository.findAll());
        return "layout";
    }

    @PostMapping(value = "/operation")
    public String postOperation(Model model, @ModelAttribute(Constants.FORM) @Validated Operation operation, BindingResult binding) throws Exception {
        operationRepository.save(operation);
        return "layout";
    }

    @PostMapping(value = "/user")
    public String postUser(Model model, @ModelAttribute(Constants.FORM) @Validated User user, BindingResult binding) throws Exception {
        if(!binding.hasErrors()){
            userRepository.save(user);
            model.addAttribute(Constants.FORM, new User());
        } else {
            model.addAttribute(Constants.FORM, user);
        }
        model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.findAll());
        model.addAttribute(Constants.EMPLOYEES, employeeRepository.findAll());
        model.addAttribute(Constants.LIST, userRepository.findAll());
        return "layout";
    }

    @PostMapping(value = "/module-operation")
    public String postModuleOperation(Model model, @ModelAttribute(Constants.FORM) @Validated ModuleOperation moduleOperation, BindingResult binding) throws Exception {
        if(!binding.hasErrors()){
            moduleOperationRepository.save(moduleOperation);
            model.addAttribute(Constants.FORM, new ModuleOperation());
        } else {
            model.addAttribute(Constants.FORM, moduleOperation);
        }
        model.addAttribute(Constants.MODULES, moduleRepository.findAll());
        model.addAttribute(Constants.OPERATIONS, operationRepository.findAll());
        model.addAttribute(Constants.LIST, moduleOperationRepository.findAll());
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
