package com.openerp.controller;

import com.openerp.entity.User;
import com.openerp.entity.UserModuleOperation;
import com.openerp.util.Constants;
import com.openerp.entity.Module;
import org.springframework.stereotype.Controller;
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
                if (!modules.contains(umo.getModuleOperation().getModule())) {
                    modules.add(umo.getModuleOperation().getModule());
                }
            }
        }
        session.setAttribute(Constants.MODULES, modules);
        return "redirect:/"+path+"/"+modules.get(0).getPath();
    }
}