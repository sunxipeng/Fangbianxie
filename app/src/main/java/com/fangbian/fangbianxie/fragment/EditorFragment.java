package com.fangbian.fangbianxie.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fangbian.fangbianxie.MainActivity;
import com.fangbian.fangbianxie.R;
import com.fangbian.fangbianxie.browser.RichEditor;

import java.util.List;

/**
 * Created by sunxipeng on 2016/10/12.
 */
public class EditorFragment extends BaseFragment implements View.OnClickListener {

    //android内核浏览器
    private RichEditor mEditor;
    private InputMethodManager imm;//软键盘管理器
    private RelativeLayout rl_layout_editor;
    private ImageButton action_undo, action_redo, action_font, action_add;
    private LinearLayout ll_layout_add, ll_layout_font;//添加布局，字体布局
    private ImageButton ib_Bold, ib_Italic, ib_StrikeThough, ib_BlockQuote, ib_H1, ib_H2, ib_H3, ib_H4;
    private boolean flag1, flag2, flag3, flag4, flag5, flag6, flag7, flag8;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_editor;
    }
    boolean isclick = true;
    boolean isItalic;//是否斜体
    boolean isBold;//是否加粗
    boolean isStrikeThrough;//是否有删除线
    public final static int RICH_IMAGE_CODE = 0x33;
    @Override
    protected void initView(View view) {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mEditor = (RichEditor) view.findViewById(R.id.editor);

        mEditor.setEditorFontSize(20);

        test();


        //设置编辑字体颜色
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPadding(0, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");


        rl_layout_editor = (RelativeLayout) view.findViewById(R.id.rl_layout_editor);
        ll_layout_add = (LinearLayout) view.findViewById(R.id.ll_layout_add);
        ll_layout_font = (LinearLayout) view.findViewById(R.id.ll_layout_font);

        action_undo = (ImageButton) view.findViewById(R.id.action_undo);
        action_redo = (ImageButton) view.findViewById(R.id.action_redo);
        action_font = (ImageButton) view.findViewById(R.id.action_font);
        action_add = (ImageButton) view.findViewById(R.id.action_add);

        //字体布局
        ib_Bold = (ImageButton) view.findViewById(R.id.action_bold);
        ib_Italic = (ImageButton) view.findViewById(R.id.action_italic);
        ib_StrikeThough = (ImageButton) view.findViewById(R.id.action_strikethrough);
        ib_BlockQuote = (ImageButton) view.findViewById(R.id.action_blockquote);
        ib_H1 = (ImageButton) view.findViewById(R.id.action_heading1);
        ib_H2 = (ImageButton) view.findViewById(R.id.action_heading2);
        ib_H3 = (ImageButton) view.findViewById(R.id.action_heading3);
        ib_H4 = (ImageButton) view.findViewById(R.id.action_heading4);

        action_add.setOnClickListener(this);
        action_font.setOnClickListener(this);
        action_redo.setOnClickListener(this);
        action_undo.setOnClickListener(this);

        ib_Bold.setOnClickListener(this);
        ib_Italic.setOnClickListener(this);
        ib_StrikeThough.setOnClickListener(this);
        ib_BlockQuote.setOnClickListener(this);
        ib_H1.setOnClickListener(this);
        ib_H2.setOnClickListener(this);
        ib_H3.setOnClickListener(this);
        ib_H4.setOnClickListener(this);

        mEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    rl_layout_editor.setVisibility(View.VISIBLE);
//                    clickableType = 1;
                } else {
                    //imm.hideSoftInputFromWindow(mEditor.getWindowToken(), 0); //强制隐藏键盘
                    rl_layout_editor.setVisibility(View.GONE);
                }
            }
        });


        /**
         * 插入图片
         */
        view.findViewById(R.id.action_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, RICH_IMAGE_CODE);

            }
        });

        /**
         * 插入链接
         */
        view.findViewById(R.id.action_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInsertLinkDialog();
            }
        });


        /**
         * 插入分割线
         */
        view.findViewById(R.id.action_split).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertHr();
            }
        });

        /**
         *获取点击出文本的标签类型
         */
        mEditor.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String text, List<RichEditor.Type> types) {

                if (types.contains(RichEditor.Type.BOLD)) {
                    ib_Bold.setImageResource(R.mipmap.bold_l);
                    flag1 = true;
                    isBold = true;
                } else {
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                }

                if (types.contains(RichEditor.Type.ITALIC)) {
                    ib_Italic.setImageResource(R.mipmap.italic_l);
                    flag2 = true;
                    isItalic = true;
                } else {
                    ib_Italic.setImageResource(R.mipmap.italic_d);
                    flag2 = false;
                    isItalic = false;
                }

                if (types.contains(RichEditor.Type.STRIKETHROUGH)) {
                    ib_StrikeThough.setImageResource(R.mipmap.strikethrough_l);
                    flag3 = true;
                    isStrikeThrough = true;
                } else {
                    ib_StrikeThough.setImageResource(R.mipmap.strikethrough_d);
                    flag3 = false;
                    isStrikeThrough = false;
                }

                //块引用
                if (types.contains(RichEditor.Type.BLOCKQUOTE)) {
                    flag4 = true;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_l);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                } else {
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    flag4 = false;
                    isclick = false;
                }


                if (types.contains(RichEditor.Type.H1)) {
                    flag4 = false;
                    flag5 = true;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;

                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_l);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                } else {
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    flag5 = false;
                    isclick = false;
                }

                if (types.contains(RichEditor.Type.H2)) {
                    flag4 = false;
                    flag5 = false;
                    flag6 = true;
                    flag7 = false;
                    flag8 = false;

                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_l);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                } else {
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    flag6 = false;
                    isclick = false;
                }

                if (types.contains(RichEditor.Type.H3)) {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = true;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_l);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                } else {
                    ib_H4.setImageResource(R.mipmap.h3_d);
                    flag7 = false;
                    isclick = false;
                }

                if (types.contains(RichEditor.Type.H4)) {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = true;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_l);
                } else {
                    ib_H4.setImageResource(R.mipmap.h4_d);
                    flag8 = false;
                    isclick = false;
                }
            }
        });
       /* //监控输入的情况自动生成html
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });*/

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

//            //上传图片
//            case R.id.img_uploading_pic:
//                PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
//                intent.setPhotoCount(6 - selectedPhotos.size());//可以添加6张图片
//                startActivityForResult(intent, REQUEST_CODE);
//                break;

            //撤回
            case R.id.action_undo:

                mEditor.undo();
                break;
            //复原
            case R.id.action_redo:

                mEditor.redo();
                break;
            //字体
            case R.id.action_font:

                if (ll_layout_font.getVisibility() == View.VISIBLE) {
                    ll_layout_font.setVisibility(View.GONE);
                } else {
                    if (ll_layout_add.getVisibility() == View.VISIBLE) {
                        ll_layout_add.setVisibility(View.GONE);
                    }
                    ll_layout_font.setVisibility(View.VISIBLE);
                    startAnimation(ll_layout_font);
                }
                break;


            //添加
            case R.id.action_add:

                if (ll_layout_add.getVisibility() == View.VISIBLE) {
                    ll_layout_add.setVisibility(View.GONE);
                } else {
                    if (ll_layout_font.getVisibility() == View.VISIBLE) {
                        ll_layout_font.setVisibility(View.GONE);
                    }
                    ll_layout_add.setVisibility(View.VISIBLE);
                    startAnimation(ll_layout_add);
                }
                break;
            /**
             *粗体
             */
            case R.id.action_bold:
                if (flag1) {
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    ib_Bold.setImageResource(R.mipmap.bold_l);
                    flag1 = true;
                    isBold = true;
                }
                mEditor.setBold();
                break;
            //斜体
            case R.id.action_italic:
                if (flag2) {
                    ib_Italic.setImageResource(R.mipmap.italic_d);
                    flag2 = false;
                    isItalic = false;
                } else {
                    ib_Italic.setImageResource(R.mipmap.italic_l);
                    flag2 = true;
                    isItalic = true;
                }
                mEditor.setItalic();
                break;
            //删除线
            case R.id.action_strikethrough:
                if (flag3) {
                    ib_StrikeThough.setImageResource(R.mipmap.strikethrough_d);
                    flag3 = false;
                    isStrikeThrough = false;
                } else {
                    ib_StrikeThough.setImageResource(R.mipmap.strikethrough_l);
                    flag3 = true;
                    isStrikeThrough = true;
                }
                mEditor.setStrikeThrough();
                break;
            //块引用
            case R.id.action_blockquote:
                if (flag4) {
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    flag4 = false;
                    isclick = false;
                } else {
                    flag4 = true;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_l);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                }
                Log.e("BlockQuote", "isItalic:" + isItalic + "，isBold：" + isBold + "，isStrikeThrough:" + isStrikeThrough);
                mEditor.setBlockquote(isclick, isItalic, isBold, isStrikeThrough);
                break;
            /**
             * H1-H4字体
             */
            case R.id.action_heading1:
                if (flag5) {
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    flag5 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = true;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_l);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                }
                mEditor.setHeading(1, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.action_heading2:
                if (flag6) {
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    flag6 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = false;
                    flag6 = true;
                    flag7 = false;
                    flag8 = false;

                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_l);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                }
                mEditor.setHeading(2, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.action_heading3:
                if (flag7) {
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    flag7 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = true;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_l);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                }
                mEditor.setHeading(3, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.action_heading4:
                if (flag8) {
                    ib_H4.setImageResource(R.mipmap.h4_d);
                    flag8 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = true;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_l);
                }
                mEditor.setHeading(4, isclick, isItalic, isBold, isStrikeThrough);
                break;
    }}
    // 执行动画效果
    public void startAnimation(View mView) {

        AlphaAnimation aa = new AlphaAnimation(0.4f, 1.0f); // 0完全透明 1 完全不透明
        // 以(0%,0.5%)为基准点，从0.5缩放至1
        ScaleAnimation sa = new ScaleAnimation(0.5f, 1, 0.5f, 1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.5f);

        // 添加至动画集合
        AnimationSet as = new AnimationSet(false);
        as.addAnimation(aa);
        as.addAnimation(sa);
        as.setDuration(500);
        // 执行动画
        mView.startAnimation(as);
    }


    //超链接dialog
    private void showDialog() {

        final AlertDialog myDialog = new AlertDialog.Builder(getActivity()).create();
        myDialog.show();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        myDialog.setContentView(view);
        myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        final EditText link_name = (EditText) view.findViewById(R.id.link_name);
        final EditText link_adress = (EditText) view.findViewById(R.id.link_adress);
        view.findViewById(R.id.bt_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = link_name.getText().toString().trim();
                String adress = link_adress.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(adress)) {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    mEditor.insertLink(adress, name);
                    myDialog.dismiss();
                }

            }
        });
        view.findViewById(R.id.bt_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();

            }
        });


    }
    /**
     * 插入链接Dialog
     */
    private AlertDialog linkDialog;
    private void showInsertLinkDialog() {

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        linkDialog = adb.create();

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_insertlink, null);

        final EditText et_link_address = (EditText) view.findViewById(R.id.et_link_address);
        final EditText et_link_title = (EditText) view.findViewById(R.id.et_link_title);

        Editable etext = et_link_address.getText();
        Selection.setSelection(etext, etext.length());

        //点击确实的监听
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String linkAddress = et_link_address.getText().toString();
                String linkTitle = et_link_title.getText().toString();

                if (linkAddress.endsWith("http://") || TextUtils.isEmpty(linkAddress)) {
                    Toast.makeText(getActivity(), "请输入超链接地址", Toast.LENGTH_SHORT);
                } else if (TextUtils.isEmpty(linkTitle)) {
                    Toast.makeText(getActivity(), "请输入超链接标题", Toast.LENGTH_SHORT);
                } else {
                    mEditor.insertLink(linkAddress, linkTitle);
                    linkDialog.dismiss();
                }
            }
        });
        //点击取消的监听
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkDialog.dismiss();
            }
        });
        linkDialog.setCancelable(false);
        linkDialog.setView(view, 0, 0, 0, 0); // 设置 view
        linkDialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RICH_IMAGE_CODE && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String path = getRealFilePath(getActivity(),selectedImage);
            mEditor.insertImage(path,"图片");}


    }
    public  String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域的底部
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    View mRoot;
    public void test(){
        mRoot = getActivity().getWindow().getDecorView();
        mRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                mRoot.getWindowVisibleDisplayFrame(rect);
                int rootInvisibleHeight = mRoot.getRootView().getHeight()-rect.bottom;
                if(rootInvisibleHeight>100){
                    ((MainActivity)getActivity()).footview.setVisibility(View.GONE);
                    //显示
                   // footerView.setVisibility(View.GONE);
                   /* int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    test1.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域的底部
                    int srollHeight = (location[1] + test1.getHeight()) - rect.bottom;
                    mRoot.scrollTo(0, srollHeight);*/
                }else{
                    ((MainActivity)getActivity()).footview.setVisibility(View.VISIBLE);
                    //mRoot.scrollTo(0,0);
                    //隐藏
                    //footerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
