package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.specification.internal.Filter;
import com.openerp.util.Constants;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = {"/sub/{path1}/{path2}", "/sub/{path1}/{path2}/{data}", "/sub/{path1}/{path2}/org/{id}", "/sub/{path1}/{path2}/{data}/org/{id}"})
    public String getSubModules(@PathVariable("path1") String path1, @PathVariable("path2") String path2, @PathVariable("data") Optional<String> data, @PathVariable("id") Optional<String> id, RedirectAttributes redirectAttributes) throws Exception {
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
        redirectAttributes.addFlashAttribute(Constants.FILTER_FORM, new Filter());
        return "redirect:/"+path1+"/"+path2+(!data.equals(Optional.empty())?("/"+data.toString().trim()):"");
    }

    @PostMapping(value = {"/filter/{path1}/{path2}", "/sub/{path1}/{path2}/{data}"})
    public String postFilter(@ModelAttribute(Constants.FILTER_FORM) @Validated Filter filter, @PathVariable("path1") String path1, @PathVariable("path2") String path2, @PathVariable("data") Optional<String> data, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        String url = "/route/sub/"+path1 + "/" + path2 + (!data.equals(Optional.empty())?("/"+data.get()):"");
        return mapFilter(Filter.convertFilter(filter), binding, redirectAttributes, url);
    }
}
