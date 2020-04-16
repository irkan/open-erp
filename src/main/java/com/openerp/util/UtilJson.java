package com.openerp.util;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;

public class UtilJson {
    private static final Logger log = Logger.getLogger(UtilJson.class);

    public static String toJson(Object value) throws JsonGenerationException, JsonMappingException, IOException {
        String returned = "{}";
        try{
            ObjectMapper mapper = new ObjectMapper();
            returned =  mapper.writeValueAsString(value).replaceAll("'", " ");
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return returned;
    }
}
