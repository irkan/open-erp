package az.sufilter.bpm.controller;

import az.sufilter.bpm.entity.DictionaryType;
import az.sufilter.bpm.entity.User;
import az.sufilter.bpm.repository.*;
import az.sufilter.bpm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    PersonContactRepository personContactRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    OrganizationContactRepository organizationContactRepository;

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

}
