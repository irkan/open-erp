package com.openerp.util;

import com.openerp.domain.Status;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.openerp.entity.ModuleOperation;
import com.openerp.entity.TemplateModuleOperation;
import com.openerp.entity.UserModuleOperation;

import java.io.IOException;
import java.util.List;

public class UserAccess {

    public static Status checkAccess(Object moduleOperationObject, Object userModuleOperationObject, Object templateModuleOperationObject, int moduleId, int operationId) throws Exception {
        List<UserModuleOperation> userModuleOperations = (List<UserModuleOperation>) userModuleOperationObject;
        List<TemplateModuleOperation> templateModuleOperations = (List<TemplateModuleOperation>) templateModuleOperationObject;
        List<ModuleOperation> moduleOperations = (List<ModuleOperation>) moduleOperationObject;
        for(ModuleOperation mo: moduleOperations){
            if(mo!=null && mo.getModule()!=null && mo.getOperation()!=null && mo.getModule().getId()==moduleId && mo.getOperation().getId()==operationId){
                if(userModuleOperationObject!=null && userModuleOperations!=null && userModuleOperations.size()>0){
                    for(UserModuleOperation umo: userModuleOperations){
                        if(umo!=null && umo.getModuleOperation().getId()==mo.getId()){
                            return new Status(mo.getId(), true);
                        }
                    }
                }
                if(templateModuleOperationObject!=null && templateModuleOperations!=null && templateModuleOperations.size()>0){
                    for(TemplateModuleOperation tmo: templateModuleOperations){
                        if(tmo!=null && tmo.getModuleOperation().getId()==mo.getId()){
                            return new Status(mo.getId(), true);
                        }
                    }
                }
                return new Status(mo.getId(), false);
            }
        }
        return new Status(0, false);
    }

    public static Status checkOperation(Object object, String module, String operation) throws Exception {
        List<UserModuleOperation> userModuleOperations = (List<UserModuleOperation>) object;
        for(UserModuleOperation mo: userModuleOperations){
            if(mo!=null && mo.getModuleOperation()!=null &&
                    mo.getModuleOperation().getModule()!=null && mo.getModuleOperation().getOperation()!=null &&
                    mo.getModuleOperation().getModule().getPath().equalsIgnoreCase(module) &&
                    mo.getModuleOperation().getOperation().getPath().equalsIgnoreCase(operation)){
                return new Status(mo.getModuleOperation().getOperation(), true);
            }
        }
        return new Status(null, false);
    }
}
