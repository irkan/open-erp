package com.openerp.dummy;

import com.openerp.entity.Dictionary;
import com.openerp.entity.EmployeeRestDay;
import com.openerp.entity.Organization;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
        double leftLimit = 14D;
        double rightLimit = 63D;
        return df.format((leftLimit + new Random().nextDouble() * (rightLimit - leftLimit))*100).replace(",", ".");
    }

    public static String randomGrossSalary(){
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        double leftLimit = 2.5D;
        double rightLimit = 10D;
        return df.format((leftLimit + new Random().nextDouble() * (rightLimit - leftLimit))*100).replace(",", ".");
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

    public static List<Dictionary> randomWeekDay(List<Dictionary> weekDays){
        int leftLimit = 0;
        int rightLimit = weekDays.size()-1;
        Random random = new Random();
        int loop = random.nextInt(4)-1;
        List<Dictionary> dictionaries = new ArrayList<>();
        for(int i=0; i<loop; i++){
            int index = leftLimit + new Random().nextInt(rightLimit-leftLimit);
            dictionaries.add(weekDays.get(index));
        }
        return dictionaries;
    }

    public static String randomSocialCardNumber(){
        String NumericStringSuffix = "0123456789";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = (int)(NumericStringSuffix.length() * Math.random());
            sb.append(NumericStringSuffix.charAt(index));
        }
        return sb.toString();
    }

    public static String randomBankCardNumber(){
        String NumericStringSuffix = "123456789";
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            int index = (int)(NumericStringSuffix.length() * Math.random());
            sb.append(NumericStringSuffix.charAt(index));
        }
        return sb.toString();
    }

    public static String randomIdCardPinCode(){
        String AlphaNumericString = "AZNUSDERGBPTLWZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static String randomIdCardSerialNumber(){
        String AlphaNumericString = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return "AA" + sb.toString();
    }

    public static String randomVoen(){
        String AlphaNumericString = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return "AA" + sb.toString();
    }

    public static String randomBankAccountNumber(){
        String AlphaString = "AZNUSDERGBPTL";
        String NumericString = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            int index = (int)(NumericString.length() * Math.random());
            sb.append(NumericString.charAt(index));
        }
        for (int i = 0; i < 4; i++) {
            int index = (int)(AlphaString.length() * Math.random());
            sb.append(AlphaString.charAt(index));
        }
        for (int i = 0; i < 16; i++) {
            int index = (int)(NumericString.length() * Math.random());
            sb.append(NumericString.charAt(index));
        }
        return "AZ"+sb.toString();
    }

    public static boolean randomBoolean(){
        int[] a = {1,0,0,0,0,0,0,0,0,0,1,0,0,0};
        int index = (int)(a.length * Math.random());
        if(a[index]==1){
            return true;
        }
        return false;
    }

    public static String calculateMainVacationDays(String mainVacationDays, boolean disability, boolean specialistOrManager){
        if(disability){
            mainVacationDays = "43";
        }
        if(specialistOrManager && !disability){
            mainVacationDays = "30";
        }
        return mainVacationDays;
    }

    public static String calculateAdditionalVacationDays(String additionalVacationDays, Date contractStartDate, String previousWorkExperience, boolean disability){
        Date today = new Date();
        int experience = Integer.parseInt(previousWorkExperience) + today.getYear()-contractStartDate.getYear();
        if(experience>=15){
            additionalVacationDays = "6";
        } else if(experience>=10){
            additionalVacationDays = "4";
        } else if(experience>=5){
            additionalVacationDays = "2";
        }
        if(disability){
            additionalVacationDays = "0";
        }
        return additionalVacationDays;
    }
}
