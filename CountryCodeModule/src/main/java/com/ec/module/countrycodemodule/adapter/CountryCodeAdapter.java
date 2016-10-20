package com.ec.module.countrycodemodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ec.module.countrycodemodule.R;
import com.ec.module.countrycodemodule.entity.CountryCode;
import com.ec.module.countrycodemodule.utils.CountryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hbb20 on 11/1/16.
 */
public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.CountryCodeViewHolder> {
    private Context mContext;
    //
    private TextView mNoResultTv;
    private EditText mSearchEt;
    //
    private List<CountryCode> filteredCountries = null, masterCountries = null;
    //
    private OnItemClickListener mListener;

    public CountryCodeAdapter(Context context, EditText searchEt, TextView noResultTv, List<CountryCode> countries) {
        this.mContext = context;
        //
        this.mSearchEt = searchEt;
        this.mNoResultTv = noResultTv;
        //
        this.masterCountries = countries;
        //
        setTextWatcher();
        this.filteredCountries = getFilteredCountries("");
    }

    private void setTextWatcher() {
        if (this.mSearchEt != null) {
            this.mSearchEt.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s == null) {
                        applyQuery(null);
                    } else {
                        applyQuery(s.toString());
                    }
                }
            });

            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

    private void applyQuery(String query) {
        if (query == null) {
            return;
        }
        mNoResultTv.setVisibility(View.GONE);
        query = query.toLowerCase();

        //if query started from "+" ignore it
        if (query.length() > 0 && query.charAt(0) == '+') {
            query = query.substring(1);
        }

        filteredCountries = getFilteredCountries(query);

        if (filteredCountries.size() == 0) {
            mNoResultTv.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    private List<CountryCode> getFilteredCountries(String query) {
        if (TextUtils.isEmpty(query)) {
            return masterCountries;
        }
        List<CountryCode> tempCountryList = new ArrayList<>();

        for (CountryCode country : masterCountries) {
            if (CountryUtils.isEligibleForQuery(country, query)) {
                tempCountryList.add(country);
            }
        }
        return tempCountryList;
    }

    @Override
    public CountryCodeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = View.inflate(mContext, R.layout.ec_cc_layout_recycler_country_item, null);

        CountryCodeViewHolder viewHolder = new CountryCodeViewHolder(rootView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CountryCodeViewHolder countryCodeViewHolder, final int i) {
        final CountryCode countryCode = filteredCountries.get(i);

        countryCodeViewHolder.setCountry(countryCode);

        countryCodeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(view, countryCode, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredCountries.size();
    }

    class CountryCodeViewHolder extends RecyclerView.ViewHolder {

        TextView countryNameTv, phoneCodeTv;
        View dividerView;

        public CountryCodeViewHolder(View itemView) {
            super(itemView);
            countryNameTv = (TextView) itemView.findViewById(R.id.country_name_tv);
            phoneCodeTv = (TextView) itemView.findViewById(R.id.phone_code_tv);
            dividerView = itemView.findViewById(R.id.divider_view);
        }

        public void setCountry(CountryCode country) {
            if (country != null) {
                dividerView.setVisibility(View.GONE);
                countryNameTv.setVisibility(View.VISIBLE);
                phoneCodeTv.setVisibility(View.VISIBLE);
                //
                countryNameTv.setText(country.getCountryName() + " (" + country.getNameCode().toUpperCase() + ")");

                final String s = "+" + country.getPhoneCode();
                phoneCodeTv.setText(s);
            } else {
                dividerView.setVisibility(View.VISIBLE);
                countryNameTv.setVisibility(View.GONE);
                phoneCodeTv.setVisibility(View.GONE);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, CountryCode countryCode, int position);
    }
}

