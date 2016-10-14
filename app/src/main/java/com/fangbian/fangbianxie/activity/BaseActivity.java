package com.fangbian.fangbianxie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by sunxipeng on 2016/10/12.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        loadData();
    }


    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void loadData();
}
