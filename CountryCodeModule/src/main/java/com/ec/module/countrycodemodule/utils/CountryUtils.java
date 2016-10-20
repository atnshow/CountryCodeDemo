package com.ec.module.countrycodemodule.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.ec.module.countrycodemodule.entity.CountryCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author EC
 *         Date on 2016/10/18.
 */

public class CountryUtils {

    /**
     * 获取所有国家
     *
     * @param context
     * @return
     */
    public static List<CountryCode> getCountries(Context context) {
        context = context.getApplicationContext();

        final JSONObject jsonObject = getCountriesJSON(context);

        if (jsonObject == null) {
            return null;
        }

        return parseCountries(context, jsonObject);
    }

    private static JSONObject getCountriesJSON(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("ec_countries_code.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputStream != null) {
            try {
                return new JSONObject(IOUtils.readStreamAsString(inputStream, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static List<CountryCode> parseCountries(Context context, JSONObject jsonCountries) {
        List<CountryCode> countries = new ArrayList<>();
        Iterator<String> iter = jsonCountries.keys();
        //
        String language = null;
        if (Build.VERSION.SDK_INT >= 24) {
            language = context.getResources().getConfiguration().getLocales().get(0).getLanguage();
        } else {
            language = context.getResources().getConfiguration().locale.getLanguage();
        }
        //
        while (iter.hasNext()) {
            String nameCode = iter.next();
            try {
                String phoneCode = (String) jsonCountries.get(nameCode);
                //
                String countryName = new Locale(language, nameCode).getDisplayCountry();

                countries.add(new CountryCode(nameCode, phoneCode, countryName));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return countries;
    }

    public static boolean isEligibleForQuery(CountryCode countryCode, String query) {
        if (countryCode == null || TextUtils.isEmpty(query)) {
            return false;
        }
        query = query.toLowerCase();

        final String countryName = countryCode.getCountryName();
        final String nameCode = countryCode.getNameCode();
        final String phoneCode = countryCode.getPhoneCode();

        if (!TextUtils.isEmpty(countryName)) {
            if (countryName.toLowerCase().contains(query)) {
                return true;
            }
        }

        if (!TextUtils.isEmpty(nameCode)) {
            if (nameCode.toLowerCase().contains(query)) {
                return true;
            }
        }

        if (!TextUtils.isEmpty(phoneCode)) {
            if (phoneCode.toLowerCase().contains(query)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取当前语言环境的国家
     *
     * @param context
     * @return
     */
    public static CountryCode getCountry(Context context) {
        String country = null;
        if (Build.VERSION.SDK_INT >= 24) {
            country = context.getResources().getConfiguration().getLocales().get(0).getCountry();
        } else {
            country = context.getResources().getConfiguration().locale.getCountry();
        }

        return getCountry(context, country);
    }

    /**
     * 获取单个国家
     *
     * @param context
     * @param query   必须完全匹配，但不区分大小写。例如 "86" 或 "CN" 或 "cn"，都可以得到中国。
     * @return
     */
    public static CountryCode getCountry(Context context, String query) {
        if (TextUtils.isEmpty(query)) {
            return null;
        }
        //
        query = query.toLowerCase();
        //
        context = context.getApplicationContext();

        final JSONObject jsonCountries = getCountriesJSON(context);

        if (jsonCountries == null) {
            return null;
        }

        Iterator<String> iter = jsonCountries.keys();
        //
        String language = null;
        if (Build.VERSION.SDK_INT >= 24) {
            language = context.getResources().getConfiguration().getLocales().get(0).getLanguage();
        } else {
            language = context.getResources().getConfiguration().locale.getLanguage();
        }
        //
        while (iter.hasNext()) {
            String nameCode = iter.next();
            try {
                String phoneCode = (String) jsonCountries.get(nameCode);

                if (TextUtils.equals(phoneCode.toLowerCase(), query)) {
                    String countryName = new Locale(language, nameCode).getDisplayCountry();

                    return new CountryCode(nameCode, phoneCode, countryName);
                }

                if (TextUtils.equals(nameCode.toLowerCase(), query)) {
                    String countryName = new Locale(language, nameCode).getDisplayCountry();

                    return new CountryCode(nameCode, phoneCode, countryName);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
