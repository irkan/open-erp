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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController extends SkeletonController {

    @GetMapping(value = "")
    public String profile(){
        session.setAttribute(Constants.PAGE, Constants.ROUTE.PROFILE);
        session.setAttribute(Constants.PROFILE_SUB_PAGE, "profile/overview");
        return "layout";
    }

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        session.setAttribute(Constants.PROFILE_SUB_PAGE, "profile/"+page);
        if (page.equalsIgnoreCase(Constants.ROUTE.CHANGE_PASSWORD)) {
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new ChangePassword());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.PERSONAL_INFORMATION)) {
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, getSessionUser().getEmployee().getPerson().getContact());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ACCOUNT_INFORMATION)) {
            model.addAttribute(Constants.LANGUAGES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("language"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, getSessionUser().getUserDetail());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.OVERVIEW)) {
            model.addAttribute(Constants.REPORTS, reportingDao.reportLast12MonthAdvance(getUserOrganization().getId(), getSessionUser().getEmployee().getId()));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, getSessionUser().getEmployee().getPerson().getContact());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/change-password")
    public String postChangePassword(Model model, @ModelAttribute(Constants.FORM) @Valid ChangePassword changePassword,
                                    BindingResult binding) throws Exception {
        if(!binding.hasErrors()){
            User user = getSessionUser();
            if(user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(changePassword.getOldPassword().getBytes()))){
                user.setPassword(DigestUtils.md5DigestAsHex(changePassword.getNewPassword().getBytes()));
                userRepository.save(user);
                log("admin_user", "create/edit", user.getId(), user.toString());
                model.addAttribute(Constants.FORM, new ChangePassword());
                return "layout";
            }
        }
        model.addAttribute(Constants.FORM, changePassword);
        return "layout";
    }

    @PostMapping(value = "/personal-information")
    public String postPersonalInformation(@ModelAttribute(Constants.FORM) @Validated Contact contact,
                                     BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            contactRepository.save(contact);
            log("common_contact", "create/edit", contact.getId(), contact.toString());
            User user = getSessionUser();
            Person person = user.getEmployee().getPerson();
            person.setContact(contact);
            session.setAttribute(Constants.USER, user);
        }
        return mapPost(contact, binding, redirectAttributes);
    }

    @PostMapping(value = "/account-information")
    public String postAccountInformation(@ModelAttribute(Constants.FORM) @Validated UserDetail userDetail,
                                          BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            userDetailRepository.save(userDetail);
            log("admin_user_detail", "create/edit", userDetail.getId(), userDetail.toString());
            User user = getSessionUser();
            user.setUserDetail(userDetail);
            session.setAttribute(Constants.USER, user);
        }
        return mapPost(userDetail, binding, redirectAttributes);
    }
}
