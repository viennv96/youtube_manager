package com.example.youtubermanager;

import java.text.DecimalFormat;

public class Validate {

    public String validateNumber(int number){
        DecimalFormat df = new DecimalFormat("#.##");
        if(number < 10000){
            return String.valueOf(number);
        }else if(number > 9999 && number < 1000000){
            return df.format((float)number/1000) + "K";
        }else if (number > 999999 && number < 1000000000){
            return df.format((float)number/1000000) + "M";
        }else {
            return df.format((float)number/1000000000) + "B";
        }
    }
}
