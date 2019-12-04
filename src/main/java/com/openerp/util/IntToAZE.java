package com.openerp.util;

import org.apache.log4j.Logger;

public class IntToAZE {
    static String[] to_19 = { "sıfır",  "bir",   "iki",  "üç", "dörd",   "beş",   "altı",
        "yeddi", "səkkiz", "doqquz", "on",   "on bir", "on iki", "on üç",
        "on dörd", "on beş", "on altı", "on yeddi", "on səkkiz", "on doqquz" };
    static String[] tens  = { "iyirmi", "otuz", "qırx", "əlli", "altmış", "yetmiş", "səksən", "doxsan"};
    static String[] denom = { "",
        "min",     "milyon",         "milyard",       "trilyon",       "quadrillion",
        "quintillion",  "sextillion",      "septillion",    "octillion",      "nonillion",
        "decillion",    "undecillion",     "duodecillion",  "tredecillion",   "quattuordecillion",
        "sexdecillion", "septendecillion", "octodecillion", "novemdecillion", "vigintillion" };

    private String convert_nn(int val) throws Exception {
        if (val < 20)
            return to_19[val];
        for (int v = 0; v < tens.length; v++) {
            String dcap = tens[v];
            int dval = 20 + 10 * v;
            if (dval + 10 > val) {
                if ((val % 10) != 0)
                    return dcap + " " + to_19[val % 10];
                return dcap;
            }        
        }
        throw new Exception("Should never get here, less than 100 failure");
    }
    private String convert_nnn(int val) throws Exception {
        String word = "";
        int rem = val / 100;
        int mod = val % 100;
        if (rem > 0) {
            word = to_19[rem] + " yüz";
            if (mod > 0) {
                word = word + " ";
            }
        }
        if (mod > 0) {
            word = word + convert_nn(mod);
        }
        return word;
    }
    public String aze(int val) throws Exception {
        if (val < 100) {
            return convert_nn(val);
        }
        if (val < 1000) {
            return convert_nnn(val);
        }
        for (int v = 0; v < denom.length; v++) {
            int didx = v - 1;
            int dval = new Double(Math.pow(1000, v)).intValue();
            if (dval > val) {
                int mod = new Double(Math.pow(1000, didx)).intValue();
                int l = val / mod;
                int r = val - (l * mod);
                String ret = convert_nnn(l) + " " + denom[didx];
                if (r > 0) {
                    ret = ret + " " + aze(r);
                }
                return ret;
            }
        }
        throw new Exception("Should never get here, bottomed out in english_number");
    }
}