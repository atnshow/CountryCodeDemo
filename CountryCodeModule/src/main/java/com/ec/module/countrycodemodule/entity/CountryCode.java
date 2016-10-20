package com.ec.module.countrycodemodule.entity;

public class CountryCode {
    private String nameCode;
    private String phoneCode;
    private String countryName;

    public CountryCode(String nameCode, String phoneCode, String countryName) {
        this.nameCode = nameCode;
        this.phoneCode = phoneCode;
        this.countryName = countryName;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}