package com.fangbian.fangbianxie;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangbian.fangbianxie.fragment.BaseFragment;
import com.fangbian.fangbianxie.fragment.EditorFragment;
import com.fangbian.fangbianxie.fragment.MessageFragment;
import com.fangbian.fangbianxie.fragment.MineFragment;
import com.fangbian.fangbianxie.util.Commonutil;
import com.fangbian.fangbianxie.util.FragmentController;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private FragmentManager mFragmentManager = this.getSupportFragmentManager();
    private BaseFragment messBaseFragment;
    private BaseFragment editorFragment;
    private BaseFragment mineFragment;
    private TextView tv_message;
    private TextView tv_editor;
    private TextView tv_mine;
    private BaseFragment mOldFagment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {

        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_editor = (TextView) findViewById(R.id.tv_editor);
        tv_mine = (TextView) findViewById(R.id.tv_mine);
        tv_mine.setOnClickListener(this);
        tv_editor.setOnClickListener(this);
        tv_message.setOnClickListener(this);

        messBaseFragment = new MessageFragment();
        editorFragment = new EditorFragment();
        mineFragment = new MineFragment();

        RelativeLayout rl_fragment_container = (RelativeLayout) this.findViewById(R.id.rl_fragment_container);
        rl_fragment_container.removeAllViews();
        selectButton(R.id.tv_message);
        switchFragment(messBaseFragment, mOldFagment);

    }


    public void switchFragment(BaseFragment newFragment, BaseFragment oldFragment) {
        if (newFragment != oldFragment) {
            FragmentController.getInstance().addFragment2Container(R.id.rl_fragment_container, mFragmentManager, newFragment, oldFragment);
            this.mOldFagment = newFragment;
        } else {
            //LogUtils.i(TAG, "重复点击了tab");
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_message:
                selectButton(R.id.tv_message);
                switchFragment(messBaseFragment, mOldFagment);
                break;
            case R.id.tv_editor:
                selectButton(R.id.tv_editor);
                switchFragment(editorFragment, mOldFagment);
                break;
            case R.id.tv_mine:
                selectButton(R.id.tv_mine);
                switchFragment(mineFragment, mOldFagment);
                break;
        }

    }

    private void selectButton(int id) {

        tv_message.setTextSize(Commonutil.px2dip(this, 45));
        tv_editor.setTextSize(Commonutil.px2dip(this, 45));
        tv_mine.setTextSize(Commonutil.px2dip(this, 45));
        switch (id) {
            case R.id.tv_message:
                tv_message.setTextSize(Commonutil.px2dip(this, 55));
                break;
            case R.id.tv_editor:
                tv_editor.setTextSize(Commonutil.px2dip(this, 55));
                break;
            case R.id.tv_mine:
                tv_mine.setTextSize(Commonutil.px2dip(this, 55));
                break;
        }
    }
}
