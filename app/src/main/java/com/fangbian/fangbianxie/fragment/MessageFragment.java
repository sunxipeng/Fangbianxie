package com.fangbian.fangbianxie.fragment;

import android.view.View;
import android.widget.ListView;

import com.fangbian.fangbianxie.R;
import com.fangbian.fangbianxie.adapter.MessageAdapter;

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

        ListView listview = (ListView) view.findViewById(R.id.listview);
        listview.setAdapter(new MessageAdapter(getActivity()));

    }

    @Override
    protected void loadData() {

    }
}
