package com.openerp.dummy;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Organization;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
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

    public static String randomSalary(){
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        double leftLimit = 1300D;
        double rightLimit = 8000D;
        return df.format(leftLimit + new Random().nextDouble() * (rightLimit - leftLimit)).replace(",", ".");
    }

    public static String randomGrossSalary(){
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        double leftLimit = 250D;
        double rightLimit = 1000D;
        return df.format(leftLimit + new Random().nextDouble() * (rightLimit - leftLimit)).replace(",", ".");
    }

    public static String randomPreviousWorkExperience(){
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        double leftLimit = 0D;
        double rightLimit = 12D;
        return df.format(leftLimit + new Random().nextDouble() * (rightLimit - leftLimit)).replace(",", ".");
    }

    public static Date randomContractStartDate(){
        int leftLimit = 1;
        int rightLimit = 144;
        int month = leftLimit + new Random().nextInt(rightLimit-leftLimit);
        Date today = new Date();
        long time = today.getTime() - (month * 2592000000l);
        return new Date(time);
    }
}
