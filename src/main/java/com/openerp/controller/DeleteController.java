package com.openerp.controller;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Module;
import com.openerp.entity.User;
import com.openerp.entity.UserModuleOperation;
import com.openerp.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/delete")
public class DeleteController extends SkeletonController {

    @PostMapping(value = "/{path}")
    public String getSubModules(@PathVariable("path") String path, @RequestParam(name="deletedId", defaultValue = "0") int id) throws Exception {
        User user = getSessionUser();
        String parent = "admin";
        for(UserModuleOperation umo: user.getUserModuleOperations()){
            if(umo.getModuleOperation()!=null && umo.getModuleOperation().getModule()!=null
                    && umo.getModuleOperation().getModule().getPath().equalsIgnoreCase(path)){
                parent = umo.getModuleOperation().getModule().getModule().getPath();
                break;
            }

        }
        if(path.equalsIgnoreCase("dictionary-type")){
            dictionaryTypeRepository.deleteById(id);
        } else if(path.equalsIgnoreCase("dictionary")){
            dictionaryRepository.deleteById(id);
        } else if(path.equalsIgnoreCase("module")){
            moduleRepository.deleteById(id);
        } else if(path.equalsIgnoreCase("operation")){
            moduleRepository.deleteById(id);
        } else if(path.equalsIgnoreCase("module-operation")){
            moduleOperationRepository.deleteById(id);
        } else if(path.equalsIgnoreCase("user")){
            userRepository.deleteById(id);
        } else if(path.equalsIgnoreCase("employee")){
            employeeRepository.deleteById(id);
        } else if(path.equalsIgnoreCase("organization")){
            organizationRepository.deleteById(id);
        }
        return "redirect:/"+parent+"/"+path;
    }
}
