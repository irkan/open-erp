package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController extends SkeletonController {

    @GetMapping(value = { "/", "/login" })
    public String login() throws Exception {
        if(getSessionUser()!=null){
            return "layout";
        }
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout() throws Exception {
        if(getSessionUser()!=null){
            session.removeAttribute(Constants.USER);
            session.removeAttribute(Constants.LOGS);
            session.invalidate();
        }
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginDo(Model model, @RequestParam(name="username") String username,
                                @RequestParam(name="password") String password,
                                RedirectAttributes redirectAttributes) throws Exception {
        User user = userRepository.findByUsernameAndPasswordAndActiveTrue(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if(user==null){
            model.addAttribute("error", "true");
            model.addAttribute(Constants.MESSAGE, "İstifadəçi adı və ya şifrəniz yanlışdır!");
            return "login";
        }
        List<Module> parentModules = new ArrayList<>();
        for(UserModuleOperation umo: user.getUserModuleOperations()){
            if (umo.getModuleOperation().getModule().getModule().getModule()==null) {
                if(!parentModules.contains(umo.getModuleOperation().getModule().getModule())){
                    if(!umo.getModuleOperation().getModule().getModule().getPath().equalsIgnoreCase("profile")){
                        parentModules.add(umo.getModuleOperation().getModule().getModule());
                    }
                }
            }
        }
        if(parentModules.size()>0){
            session.setAttribute(Constants.USER, user);
            session.setAttribute(Constants.LOGS, new ArrayList<Log>());
            log("enter", "Sistemə giriş edildi: " + user.getUsername());
            redirectAttributes.addFlashAttribute(Constants.PAGE, "module");
            session.setAttribute(Constants.ORGANIZATION, getUserOrganization());
            session.setAttribute(Constants.ORGANIZATION_SELECTED, getUserOrganization());
            session.setAttribute(Constants.ORGANIZATIONS, getOrganization(user.getUserDetail().getAdministrator()));
            session.setAttribute(Constants.PARENT_MODULES_MAP, Util.convertParentModulesMap(parentModules));
            session.setAttribute(Constants.PARENT_MODULES, parentModules);
            session.setAttribute(Constants.VACATION_DETAIL_REPOSITORY, vacationDetailRepository);
            return "redirect:/route/"+parentModules.get(0).getPath();
        }
        model.addAttribute("error", "true");
        model.addAttribute(Constants.MESSAGE, "Sistemdən istifadə icazələri ilə təmin edilməmisiniz!");
        return "login";
    }

    protected List<Organization> getOrganization(boolean administrator) {
        List<Organization> organizations = new ArrayList<>();
        if(administrator){
            organizations.add(getUserOrganization());
            for(Organization organization: getUserOrganization().getChildren()){
                organizations.add(organization);
            }
            if(organizations.size()>1){
                Organization organization = new Organization("Bütün fliallar", "Bütün fliallar üzrə");
                organization.setId(0);
                organizations.add(organization);
            }
        } else {
            organizations.add(getUserOrganization());
        }
        return organizations;
    }
}
