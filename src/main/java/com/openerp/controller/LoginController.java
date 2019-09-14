package com.openerp.controller;

import com.openerp.entity.Module;
import com.openerp.entity.User;
import com.openerp.entity.UserModuleOperation;
import com.openerp.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            session.invalidate();
        }
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginDo(Model model, @RequestParam(name="username") String username,
                                @RequestParam(name="password") String password) throws Exception {
        User user = userRepository.findByUsernameAndPasswordAndActiveTrue(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if(user==null){
            model.addAttribute(Constants.ERROR, "true");
            model.addAttribute(Constants.MESSAGE, "İstifadəçi adı və ya şifrəniz yanlışdır!");
            return "login";
        }
        List<Module> parentModules = new ArrayList<>();
        for(UserModuleOperation umo: user.getUserModuleOperations()){
            if (!parentModules.contains(umo.getModuleOperation().getModule().getModule())) {
                parentModules.add(umo.getModuleOperation().getModule().getModule());
            }
        }
        if(parentModules.size()>0){
            String page = user.getUserModuleOperations().get(0).getModuleOperation().getModule().getPath();
            session.setAttribute(Constants.USER, user);
            session.setAttribute(Constants.PAGE, "module");
            session.setAttribute(Constants.PARENT_MODULES, parentModules);
            return "redirect:/route/"+parentModules.get(0).getPath();
        }
        model.addAttribute(Constants.ERROR, "true");
        model.addAttribute(Constants.MESSAGE, "Sistemdən istifadə icazələri ilə təmin edilməmisiniz!");
        return "login";
    }
}
