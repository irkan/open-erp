package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.entity.Module;
import com.openerp.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/route")
public class RouterController extends SkeletonController {

    @GetMapping(value = {"/{path}", "/{path}/{path1}"})
    public String getModules(@PathVariable("path") String path, @PathVariable(name = "path1", value = "", required = false) String path1, RedirectAttributes redirectAttributes) throws Exception {
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
        String page = (path1!=null && path1.trim().length()>0)?path1:modules.get(0).getPath();
        return "redirect:/route/sub/"+path+"/"+page;
    }

    @GetMapping(value = {"/sub/{path1}/{path2}", "/sub/{path1}/{path2}/{data}", "/sub/{path1}/{path2}/org/{id}", "/sub/{path1}/{path2}/{data}/org/{id}"})
    public String getSubModules(@PathVariable("path1") String path1, @PathVariable("path2") String path2, @PathVariable("data") Optional<String> data, @PathVariable("id") Optional<String> id, RedirectAttributes redirectAttributes) throws Exception {
        session.removeAttribute(Constants.SESSION_FILTER);
        redirectAttributes.addFlashAttribute(Constants.PAGE, path2);
        String description = "";
        List<Module> moduleList = (List<Module>) session.getAttribute(Constants.MODULES);
        for(Module m: moduleList){
            if(m.getPath().equalsIgnoreCase(path2)){
                description = m.getDescription();
                break;
            }
            for(Module mdl: m.getChildren()){
                if(mdl.getPath().equalsIgnoreCase(path2)){
                    description = mdl.getDescription();
                    break;
                }
            }
        }
        if(!id.equals(Optional.empty())){
            Organization organization = organizationRepository.getOrganizationByIdAndActiveTrue(Integer.parseInt(id.get()));
            if(organization!=null){
                session.setAttribute(Constants.ORGANIZATION, organization);
            } else {
                List<Organization> organizations = (List<Organization>)session.getAttribute(Constants.ORGANIZATIONS);
                for(Organization org: organizations){
                    if(org.getId()==Integer.parseInt(id.get())){
                        organization = org;
                        break;
                    }
                }
            }
            session.setAttribute(Constants.ORGANIZATION_SELECTED, organization);
        }
        redirectAttributes.addFlashAttribute(Constants.MODULE_DESCRIPTION, description);
        return "redirect:/"+path1+"/"+path2+(!data.equals(Optional.empty())?("/"+data.get().toString().trim()):"");
    }
}
