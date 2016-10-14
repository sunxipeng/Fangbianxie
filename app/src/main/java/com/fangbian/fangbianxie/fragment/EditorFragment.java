package com.fangbian.fangbianxie.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.fangbian.fangbianxie.R;
import com.fangbian.fangbianxie.util.FileUtil;
import com.fangbian.fangbianxie.view.PictureAndTextEditorView;

/**
 * Created by sunxipeng on 2016/10/12.
 */
public class EditorFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_addphoto;
    private PictureAndTextEditorView mEditText;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_editor;
    }

    @Override
    protected void initView(View view) {

        tv_addphoto = (TextView) view.findViewById(R.id.tv_addphoto);
        tv_addphoto.setOnClickListener(this);
        mEditText = (PictureAndTextEditorView) view.findViewById(R.id.et_fragment);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_addphoto:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/jpeg");
                startActivityForResult(intent, 888);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 888:
                    if (data != null) {

                        Uri selectedImage = data.getData();

                        String imageurl = FileUtil.getImageAbsolutePath(getActivity(), selectedImage);
                        mEditText.insertBitmap(imageurl);

                    }

            }
        }
    }
}
