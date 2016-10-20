package com.fangbian.fangbianxie.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.fangbian.fangbianxie.R;
import com.fangbian.fangbianxie.browser.RichEditor;
import com.fangbian.fangbianxie.util.FileUtil;
import com.fangbian.fangbianxie.view.PictureAndTextEditorView;

/**
 * Created by sunxipeng on 2016/10/12.
 */
public class EditorFragment extends BaseFragment implements View.OnClickListener {

    //private TextView tv_addphoto;
    //android内核浏览器
    private RichEditor mEditor;
    private TextView mPreview;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_editor;
    }

    @Override
    protected void initView(View view) {

        /*tv_addphoto = (TextView) view.findViewById(R.id.tv_addphoto);
        tv_addphoto.setOnClickListener(this);*/

        mEditor = (RichEditor) view.findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        //设置编辑字体颜色
        mEditor.setEditorFontColor(Color.RED);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");
        mPreview = (TextView) view.findViewById(R.id.preview);

        //监控输入的情况自动生成html
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });
        view.findViewById(R.id.action_undo).setOnClickListener(this);
        view.findViewById(R.id.action_redo).setOnClickListener(this);
        view.findViewById(R.id.action_bold).setOnClickListener(this);
        /*view.findViewById(R.id.action_italic).setOnClickListener(this);*/
        /*view.findViewById(R.id.action_strikethrough).setOnClickListener(this);*/
        view.findViewById(R.id.action_underline).setOnClickListener(this);
        view.findViewById(R.id.action_heading1).setOnClickListener(this);
        view.findViewById(R.id.action_heading2).setOnClickListener(this);
        view.findViewById(R.id.action_heading3).setOnClickListener(this);
        view.findViewById(R.id.action_heading4).setOnClickListener(this);
        /*view.findViewById(R.id.action_heading5).setOnClickListener(this);*/
        view.findViewById(R.id.action_blockquote).setOnClickListener(this);
        view.findViewById(R.id.action_insert_numbers).setOnClickListener(this);
        //插入图片按钮设置点击事件
        view.findViewById(R.id.action_insert_image).setOnClickListener(this);
        view.findViewById(R.id.action_insert_link).setOnClickListener(this);

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            /*//获取系统相册
            case R.id.tv_addphoto:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/jpeg");
                startActivityForResult(intent, 888);
                break;*/
            case R.id.action_undo:
                mEditor.undo();
                break;
            case R.id.action_redo:
                mEditor.redo();
                break;
            case R.id.action_bold:
                mEditor.setBold();
                break;
           /* case R.id.action_italic:
                mEditor.setItalic();
                break;*/
            /*case R.id.action_strikethrough:
                break;*/
            case R.id.action_underline:
                mEditor.setUnderline();
                break;
            case R.id.action_heading1:
                mEditor.setHeading(1);
                break;
            case R.id.action_heading2:
                mEditor.setHeading(2);
                break;
            case R.id.action_heading3:
                mEditor.setHeading(3);
                break;
            case R.id.action_heading4:
                mEditor.setHeading(4);
                break;
           /* case R.id.action_heading5:
                mEditor.setHeading(5);
                break;*/
            case R.id.action_blockquote:
                mEditor.setBlockquote();
                break;
            case R.id.action_insert_numbers:
                mEditor.setNumbers();
                break;
            case R.id.action_insert_image:
                //此处需要处理插入本地图片(目前是插入网络图片),(处理方法为向服务端传送图片，然后服务端返回图片网络路径)
                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
                break;
            case R.id.action_insert_link:
                //插入超链接
                mEditor.insertLink("https://github.com/wasabeef", "我是超链接");
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
                        //获取选择照片的存储路径
                        String imageurl = FileUtil.getImageAbsolutePath(getActivity(), selectedImage);

                    }

            }
        }
    }
}
