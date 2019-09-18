package com.openerp.controller;

import com.openerp.entity.*;
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
            DictionaryType dictionaryType = dictionaryTypeRepository.getDictionaryTypeById(id);
            dictionaryType.setActive(false);
            dictionaryTypeRepository.save(dictionaryType);
            if(dictionaryType!=null){
                for(Dictionary dictionary: dictionaryRepository.getDictionariesByDictionaryType_Id(dictionaryType.getId())){
                    dictionary.setActive(false);
                    dictionaryRepository.save(dictionary);
                }
            }
        } else if(path.equalsIgnoreCase("dictionary")){
            Dictionary dictionary = dictionaryRepository.getDictionaryById(id);
            dictionary.setActive(false);
            dictionaryRepository.save(dictionary);
        } else if(path.equalsIgnoreCase("module")){
            Module module = moduleRepository.getModuleById(id);
            module.setActive(false);
            moduleRepository.save(module);
            for(ModuleOperation moduleOperation: moduleOperationRepository.getModuleOperationsByModule_Active(false)){
                userModuleOperationRepository.deleteInBatch(userModuleOperationRepository.getUserModuleOperationsByModuleOperation_Id(moduleOperation.getId()));
                moduleOperationRepository.deleteById(moduleOperation.getId());
            }
        } else if(path.equalsIgnoreCase("operation")){
            Operation operation = operationRepository.getOperationById(id);
            operation.setActive(false);
            operationRepository.save(operation);
            for(ModuleOperation moduleOperation: moduleOperationRepository.getModuleOperationsByOperation_Active(false)){
                userModuleOperationRepository.deleteInBatch(userModuleOperationRepository.getUserModuleOperationsByModuleOperation_Id(moduleOperation.getId()));
                moduleOperationRepository.deleteById(moduleOperation.getId());
            }
        } else if(path.equalsIgnoreCase("module-operation")){
            moduleOperationRepository.deleteById(id);
        } else if(path.equalsIgnoreCase("user")){
            User userObject = userRepository.getUserByActiveTrueAndId(id);
            userRepository.save(userObject);
            List<UserModuleOperation> userModuleOperations = userModuleOperationRepository.findAllByUser_Id(user.getId());
            userModuleOperationRepository.deleteInBatch(userModuleOperations);
        } else if(path.equalsIgnoreCase("employee")){
            employeeRepository.deleteById(id);
        } else if(path.equalsIgnoreCase("organization")){
            organizationRepository.deleteById(id);
        }
        return "redirect:/"+parent+"/"+path;
    }
}
