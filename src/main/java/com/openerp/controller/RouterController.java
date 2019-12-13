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
    public String getModules(@PathVariable("path") String path) throws Exception {
        User user = getSessionUser();
        List<Module> modules = new ArrayList<>();
        Module parent = null;
        for(UserModuleOperation umo: user.getUserModuleOperations()){
            if(umo.getModuleOperation().getModule().getModule().getModule()==null){
                parent = umo.getModuleOperation().getModule().getModule();
            } else if (umo.getModuleOperation().getModule().getModule().getModule().getModule()==null){
                parent = umo.getModuleOperation().getModule().getModule().getModule();
            } else if (umo.getModuleOperation().getModule().getModule().getModule().getModule().getModule()==null){
                parent = umo.getModuleOperation().getModule().getModule().getModule().getModule();
            }
            if(parent!=null && parent.getPath().equalsIgnoreCase(path)){
                if (umo.getModuleOperation().getModule().getModule().getModule()==null && !modules.contains(umo.getModuleOperation().getModule())) {
                    modules.add(umo.getModuleOperation().getModule());
                }
            }
        }
        session.setAttribute(Constants.PARENT, parent);
        session.setAttribute(Constants.MODULES, modules);
        return "redirect:sub/"+path+"/"+modules.get(0).getPath();
    }

    @GetMapping(value = "/sub/{path1}/{path2}")
    public String getSubModules(@PathVariable("path1") String path1, @PathVariable("path2") String path2) throws Exception {
        session.setAttribute(Constants.PAGE, path2);
        String description = "";
        List<Module> moduleList = (List<Module>) session.getAttribute(Constants.MODULES);
        for(Module m: moduleList){
            if(m.getPath().equalsIgnoreCase(path2)){
                description = m.getDescription();
                break;
            }
        }
        session.setAttribute(Constants.MODULE_DESCRIPTION, description);
        return "redirect:/"+path1+"/"+path2;
    }
}
