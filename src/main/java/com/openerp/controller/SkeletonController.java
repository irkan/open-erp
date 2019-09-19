package com.openerp.controller;

import com.openerp.entity.User;
import com.openerp.util.Constants;
import com.openerp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SkeletonController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    DictionaryTypeRepository dictionaryTypeRepository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    UserModuleOperationRepository userModuleOperationRepository;

    @Autowired
    ModuleOperationRepository moduleOperationRepository;

    @Autowired
    TemplateModuleOperationRepository templateModuleOperationRepository;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    protected void checkSession() throws Exception {
        if(session.getAttribute(Constants.USER) == null) {
            throw new Exception("Session user is null");
        }
    }

    protected User getSessionUser() {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(Constants.USER);
    }

    String mapPost(Object object, BindingResult binding, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(Constants.FORM_RESULT_BINDING, binding);
        redirectAttributes.addFlashAttribute(Constants.FORM, object);
        if(!binding.hasErrors()){
            redirectAttributes.getFlashAttributes().remove(Constants.FORM);
        }
        return "redirect:"+request.getRequestURI();
    }

}
