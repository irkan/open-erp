package az.sufilter.bpm.controller;

import az.sufilter.bpm.entity.*;
import az.sufilter.bpm.util.Constants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/route")
public class RouterController extends SkeletonController {

    @GetMapping(value = "/{path}")
    public String getSubModules(@PathVariable("path") String path) throws Exception {
        User user = getSessionUser();
        List<Module> modules = new ArrayList<>();
        for(UserModuleOperation umo: user.getUserModuleOperations()){
            Module parent = umo.getModuleOperation().getModule().getModule();
            if(parent!=null && parent.getPath().equalsIgnoreCase(path)){
                modules.add(umo.getModuleOperation().getModule());
            }
        }
        session.setAttribute(Constants.MODULES, modules);
        return "redirect:/"+path+"/"+modules.get(0).getPath();
    }
}
