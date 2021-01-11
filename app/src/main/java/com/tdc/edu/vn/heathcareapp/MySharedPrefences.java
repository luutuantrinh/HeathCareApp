package com.tdc.edu.vn.heathcareapp;

import android.content.SharedPreferences;
import android.content.Context;

public class MySharedPrefences {
    private static final String My_SP = "My_Shared_Prefenecs";
    private Context mContext;

    public MySharedPrefences(Context mContext) {
        this.mContext = mContext;
    }
   public void putBooleanValue(String key, Boolean value){
       SharedPreferences SharedPreferences = mContext.getSharedPreferences(My_SP, 0);
       android.content.SharedPreferences.Editor editor = SharedPreferences.edit();
       editor.putBoolean(key, value);
       editor.apply();
   }

   public boolean getBooleanValue(String key){
       SharedPreferences SharedPreferences = mContext.getSharedPreferences(My_SP, 0);
       return SharedPreferences.getBoolean(key, false);
   }
}
