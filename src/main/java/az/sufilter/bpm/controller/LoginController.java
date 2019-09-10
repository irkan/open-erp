package az.sufilter.bpm.controller;

import az.sufilter.bpm.entity.ModuleOperation;
import az.sufilter.bpm.entity.User;
import az.sufilter.bpm.entity.UserModuleOperation;
import az.sufilter.bpm.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            model.addAttribute("error", "true");
            return "login";
        }
        session.setAttribute(Constants.USER, user);
        session.setAttribute(Constants.PAGE, "module");
        return "redirect:/route/admin";
    }
}
