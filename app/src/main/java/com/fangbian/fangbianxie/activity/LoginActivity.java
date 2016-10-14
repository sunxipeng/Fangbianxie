package com.fangbian.fangbianxie.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.fangbian.fangbianxie.MainActivity;
import com.fangbian.fangbianxie.R;

/**
 * Created by sunxipeng on 2016/10/12.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        TextView tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_login:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
