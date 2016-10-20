package com.ec.android.test.countrycodedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ec.module.countrycodemodule.CountryComponent;
import com.ec.module.countrycodemodule.adapter.CountryCodeAdapter;
import com.ec.module.countrycodemodule.entity.CountryCode;
import com.ec.module.countrycodemodule.utils.CountryUtils;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        final CountryComponent component = new CountryComponent(this);

        final View rootView = component.getRootView();

        setContentView(rootView);
        //
        component.setOnItemClickListener(new CountryCodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, CountryCode countryCode, int position) {
                final StringBuilder stringBuilder = new StringBuilder();

                stringBuilder
                        .append("你点击的是:")
                        .append(countryCode.getCountryName())
                        .append("  ")
                        .append(countryCode.getNameCode())
                        .append("  ")
                        .append(countryCode.getPhoneCode());

                Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // 获取当前语言环境的国家
        final CountryCode countryCodeCurrent = CountryUtils.getCountry(getApplicationContext());

        if (countryCodeCurrent != null) {
            Log.d(TAG, "countryCodeCurrent : " + countryCodeCurrent.getCountryName());
        }
        // 获取某个国家
        final CountryCode countryCodeSome = CountryUtils.getCountry(getApplicationContext(), "de");

        if (countryCodeSome != null) {
            Log.d(TAG, "countryCodeSome : " + countryCodeSome.getCountryName());
        }

    }
}
