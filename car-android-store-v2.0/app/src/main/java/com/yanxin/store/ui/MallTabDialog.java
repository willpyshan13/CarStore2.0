package com.yanxin.store.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yanxin.store.R;


/**
 * description:自定义dialog
 */

public class MallTabDialog extends Dialog {

    /**
     * 显示的消息
     */
    private TextView messageTv;

    /**
     * 确认和取消按钮
     */
    private TextView negtiveBn, positiveBn;


    public MallTabDialog(Context context) {
        super(context, R.style.MyDialogStyle);
    }

    /**
     * 都是内容数据
     */
    private String message;
    private String title;
    private String positive, negtive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mall_tab_online_tip);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        //如果用户自定了message
        if (!TextUtils.isEmpty(message)) {
            messageTv.setText(message);
        }
        //如果设置按钮的文字
        if (!TextUtils.isEmpty(positive)) {
            positiveBn.setText(positive);
        } else {
            positiveBn.setText("确定");
        }
        if (!TextUtils.isEmpty(negtive)) {
            negtiveBn.setText(negtive);
        } else {
            negtiveBn.setText("取消");
        }

    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        negtiveBn = findViewById(R.id.negtive);
        positiveBn = findViewById(R.id.positive);
        messageTv = (TextView) findViewById(R.id.message);
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;

    public MallTabDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        public void onPositiveClick();

        /**
         * 点击取消按钮事件
         */
        public void onNegtiveClick();
    }

    public String getMessage() {
        return message;
    }

    public MallTabDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MallTabDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPositive() {
        return positive;
    }

    public MallTabDialog setPositive(String positive) {
        this.positive = positive;
        return this;
    }

    public String getNegtive() {
        return negtive;
    }

    public MallTabDialog setNegtive(String negtive) {
        this.negtive = negtive;
        return this;
    }
    

}
