package com.openerp.dummy;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Organization;

import java.util.List;
import java.util.Random;

public class DummyUtil {
    public static String randomString(List<String> objects){
        return objects.get(new Random().nextInt(objects.size()));
    }

    public static Dictionary randomDictionary(List<Dictionary> objects){
        return objects.get(new Random().nextInt(objects.size()));
    }

    public static Organization randomOrganization(List<Organization> objects){
        return objects.get(new Random().nextInt(objects.size()));
    }
}
