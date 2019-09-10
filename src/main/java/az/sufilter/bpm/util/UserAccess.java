package az.sufilter.bpm.util;

import az.sufilter.bpm.entity.Module;
import az.sufilter.bpm.entity.ModuleOperation;
import az.sufilter.bpm.entity.Operation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class UserAccess {

    public static int checkAccess(Object value, int moduleId, int operationId) throws JsonGenerationException, JsonMappingException, IOException {
        List<ModuleOperation> moduleOperations = (List<ModuleOperation>) value;
        for(ModuleOperation mo: moduleOperations){
            if(mo!=null && mo.getModule()!=null && mo.getOperation()!=null && mo.getModule().getId()==moduleId && mo.getOperation().getId()==operationId){
                return mo.getId();
            }
        }
        return 0;
    }
}
