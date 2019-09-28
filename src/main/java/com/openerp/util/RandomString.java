package com.openerp.util;

public class RandomString {

    public static String getAlphaNumeric(int n){
        String AlphaNumericString = "AZNUSDERGBPTL01234567890123456789";
        String NumericString = "0123456789";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n-4; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        for (int i = n-4; i < n; i++) {
            int index = (int)(NumericString.length() * Math.random());
            sb.append(NumericString.charAt(index));
        }
        return sb.toString(); 
    }

    public static void main(String[] args) {
        System.out.println(getAlphaNumeric(16));
    }
} 