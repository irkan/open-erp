package com.openerp.controller;

import com.openerp.domain.ChangePassword;
import com.openerp.domain.Report;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.ReportUtil;
import com.openerp.util.Util;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
            model.addAttribute(Constants.MODULES_MAP, Util.accessModuleList(userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(getSessionUser().getId(), true)));
            model.addAttribute(Constants.LANGUAGES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("language"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, getSessionUser().getUserDetail());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.OVERVIEW)) {
            List<Report> reports = reportingDao.reportLast12MonthAdvance(getUserOrganization().getId(), getSessionUser().getEmployee().getId());
            List<Report> nonPayedReports = reportingDao.reportLast12MonthNonPayedAdvance(getUserOrganization().getId(), getSessionUser().getEmployee().getId());
            Advance advance = advanceRepository.findTopByActiveTrueAndApproveTrueAndEmployeeOrderByAdvanceDateDesc(getSessionUser().getEmployee());
            model.addAttribute(Constants.REPORTS, reports);
            model.addAttribute(Constants.ANNUAL_ADVANCE, Util.annualAdvance(reports));
            model.addAttribute(Constants.ANNUAL_NON_PAYED_ADVANCE, Util.annualAdvance(nonPayedReports));
            model.addAttribute(Constants.LAST_ADVANCE, advance);
            model.addAttribute(Constants.ADVANCE, ReportUtil.calculateAdvance(advanceRepository.getAdvancesByActiveTrueAndEmployee(getSessionUser().getEmployee())));

            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, getSessionUser().getEmployee().getPerson().getContact());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/change-password")
    public String postChangePassword(Model model, @ModelAttribute(Constants.FORM) @Valid ChangePassword changePassword,
                                    BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!changePassword.getNewPassword().equalsIgnoreCase(changePassword.getVerifyPassword())
                || changePassword.getNewPassword().length()==0
                || changePassword.getVerifyPassword().length()==0
        ){

            FieldError fieldError = new FieldError("newPassword", "newPassword", "Xəta baş verdi!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            User user = getSessionUser();
            user.setPassword(DigestUtils.md5DigestAsHex(changePassword.getNewPassword().getBytes()));
            userRepository.save(user);
            log(user, "user", "create/edit", user.getId(), user.toString(), "Şifrə dəyişdirildi profildən");
            model.addAttribute(Constants.FORM, new ChangePassword());
        }
        return mapPost(changePassword, binding, redirectAttributes, "/profile/change-password");
    }

    @PostMapping(value = "/personal-information")
    public String postPersonalInformation(@ModelAttribute(Constants.FORM) @Validated Contact contact,
                                     BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            contactRepository.save(contact);
            log(contact, "contact", "create/edit", contact.getId(), contact.toString());
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
            log(userDetail, "user_detail", "create/edit", userDetail.getId(), userDetail.toString());
            User user = getSessionUser();
            user.setUserDetail(userDetail);
            session.setAttribute(Constants.USER, user);
        }
        return mapPost(userDetail, binding, redirectAttributes);
    }
}
