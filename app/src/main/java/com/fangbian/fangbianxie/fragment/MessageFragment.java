package com.fangbian.fangbianxie.fragment;

import android.view.View;

import com.fangbian.fangbianxie.R;
import com.fangbian.fangbianxie.adapter.MessageAdapter;
import com.fangbian.fangbianxie.view.XListView;

/**
 * Created by sunxipeng on 2016/10/12.
 */
public class MessageFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View view) {

        XListView listview = (XListView) view.findViewById(R.id.listview);
        listview.setPullRefreshEnable(true);
        listview.setPullLoadEnable(true);
        listview.setAdapter(new MessageAdapter(getActivity()));

    }

    @Override
    protected void loadData() {

    }
}
