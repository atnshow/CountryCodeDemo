package com.ec.module.countrycodemodule;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.ec.module.countrycodemodule.adapter.CountryCodeAdapter;
import com.ec.module.countrycodemodule.entity.CountryCode;
import com.ec.module.countrycodemodule.utils.CountryUtils;

import java.util.List;

/**
 * @author EC
 *         Date on 2016/10/18.
 */

public class CountryComponent extends BaseComponent {

    private RecyclerView mRv1;
    private EditText mSearchEt;
    private TextView mNoResultTV;
    //
    private CountryCodeAdapter mDataAdapter;
    //
    private CountryCodeAdapter.OnItemClickListener mListener;

    public CountryComponent(Activity activity) {
        super(activity);
        //
        setContentView(R.layout.ec_cc_layout_picker_dialog);
        //
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mRv1 = (RecyclerView) findViewById(R.id.rv1);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv1.setLayoutManager(layoutManager);
        //
        mSearchEt = (EditText) findViewById(R.id.search_et);
        mNoResultTV = (TextView) findViewById(R.id.no_result_tv);
    }

    private void initListener() {

    }

    private void initData() {
        new QueryPhoneContactsAsync().execute();
    }


    private class QueryPhoneContactsAsync extends AsyncTask<Void, Void, List<CountryCode>> {

        @Override
        protected List<CountryCode> doInBackground(Void... params) {
            return CountryUtils.getCountries(getActivity());
        }

        @Override
        protected void onPostExecute(List<CountryCode> result) {
            mDataAdapter = new CountryCodeAdapter(getActivity(), mSearchEt, mNoResultTV, result);

            mDataAdapter.setOnItemClickListener(mListener);

            mRv1.setAdapter(mDataAdapter);
        }
    }

    public void setOnItemClickListener(CountryCodeAdapter.OnItemClickListener listener) {
        this.mListener = listener;
        //
        if (mDataAdapter != null) {
            mDataAdapter.setOnItemClickListener(listener);
        }
    }

}
