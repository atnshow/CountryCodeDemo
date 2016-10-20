package com.ec.module.countrycodemodule;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * @author EC
 *         Date on 2016/8/25.
 */
public class BaseComponent {
    private Activity mActivity;
    private View mRootView;

    public BaseComponent(Activity activity) {
        this.mActivity = activity;
    }

    public void setContentView(View rootView) {
        this.mRootView = rootView;
    }

    public void setContentView(@LayoutRes int resId) {
        this.mRootView = View.inflate(mActivity, resId, null);
    }

    public View findViewById(int id) {
        if (mRootView != null) {
            return mRootView.findViewById(id);
        } else {
            throw new RuntimeException("rootView is not attach");
        }
    }

    public final String getString(@StringRes int resId) {
        return mActivity.getResources().getString(resId);
    }

    public View getRootView() {
        return mRootView;
    }

    public Activity getActivity() {
        return mActivity;
    }
}
