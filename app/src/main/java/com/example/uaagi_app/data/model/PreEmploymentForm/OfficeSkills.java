package com.example.uaagi_app.data.model.PreEmploymentForm;

public class OfficeSkills {
    private String msword;
    private String msexcel;
    private String msppt;
    private String msoutlook;

    public OfficeSkills(String msword, String msexcel, String msppt, String msoutlook) {
        this.msword = msword != null ? msword : "";
        this.msexcel = msexcel != null ? msexcel : "";
        this.msppt = msppt != null ? msppt : "";
        this.msoutlook = msoutlook != null ? msoutlook : "";
    }
    public OfficeSkills() {
        this.msword = "";
        this.msexcel = "";
        this.msppt = "";
        this.msoutlook = "";
    }

    public String getMsword() {
        return msword;
    }

    public void setMsword(String msword) {
        this.msword = msword;
    }

    public String getMsexcel() {
        return msexcel;
    }

    public void setMsexcel(String msexcel) {
        this.msexcel = msexcel;
    }

    public String getMsppt() {
        return msppt;
    }

    public void setMsppt(String msppt) {
        this.msppt = msppt;
    }

    public String getMsoutlook() {
        return msoutlook;
    }

    public void setMsoutlook(String msoutlook) {
        this.msoutlook = msoutlook;
    }
}