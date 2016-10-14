package com.fangbian.fangbianxie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fangbian.fangbianxie.R;

/**
 * Created by sunxipeng on 2016/10/12.
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;

    public MessageAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.item,null);
    }
}
