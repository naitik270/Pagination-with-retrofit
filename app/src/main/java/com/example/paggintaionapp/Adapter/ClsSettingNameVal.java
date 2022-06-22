package com.example.paggintaionapp.Adapter;

public class ClsSettingNameVal {


    public String getSettingName() {
        return SettingName;
    }

    public void setSettingName(String settingName) {
        SettingName = settingName;
    }

    private String SettingName ="";

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position = 0;

    public ClsSettingNameVal(String setting_profile,
                             int position) {
        this.SettingName = setting_profile;
        this.position = position;
    }


}
