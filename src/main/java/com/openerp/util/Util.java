package com.openerp.util;

import com.openerp.entity.ModuleOperation;

import java.util.*;

public class Util {
    public static Object check(Object object){
        try{
            if(object!=null)
                return object;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static <Operation> ArrayList<Operation> removeDuplicateOperations(List<ModuleOperation> list) {
        ArrayList<Operation> newList = new ArrayList<>();
        for (ModuleOperation element : list) {
            if (!newList.contains(element.getOperation()) && element.getOperation()!=null) {
                newList.add((Operation) element.getOperation());
            }
        }
        return newList;
    }

    public static <Module> ArrayList<Module> removeDuplicateModules(List<ModuleOperation> list) {
        ArrayList<Module> newList = new ArrayList<>();
        for (ModuleOperation element : list) {
            if (!newList.contains(element.getModule())) {
                newList.add((Module) element.getModule());
            }
        }
        return newList;
    }
}
