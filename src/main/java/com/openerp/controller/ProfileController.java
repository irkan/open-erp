package com.openerp.controller;

import com.openerp.domain.ChangePassword;
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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController extends SkeletonController {

    @GetMapping(value = "")
    public String profile(){
        session.setAttribute(Constants.PAGE, Constants.ROUTE.PROFILE);
        session.setAttribute(Constants.PROFILE_SUB_PAGE, "profile/overview");
        session.setAttribute(Constants.MODULE_DESCRIPTION, "");
        return "layout";
    }

    @GetMapping(value = "/{page}")
    public String route(Model model, @PathVariable("page") String page) throws Exception {
        session.setAttribute(Constants.PROFILE_SUB_PAGE, "profile/"+page);

        if (page.equalsIgnoreCase(Constants.ROUTE.CHANGE_PASSWORD)) {
            model.addAttribute(Constants.FORM, new ChangePassword());
        }
        return "layout";
    }

    @PostMapping(value = "/change-password")
    public String postChangePassword(Model model, @ModelAttribute(Constants.FORM) @Validated ChangePassword changePassword,
                                    BindingResult binding) throws Exception {
        if(!binding.hasErrors()){
            User user = getSessionUser();
            if(user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(changePassword.getOldPassword().getBytes()))){
                user.setPassword(DigestUtils.md5DigestAsHex(changePassword.getNewPassword().getBytes()));
                userRepository.save(user);
                model.addAttribute(Constants.FORM, new ChangePassword());
            }
        } else {
            model.addAttribute(Constants.FORM, changePassword);
        }

        return "layout";
    }
}
