package com.yanxin.store.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

public class TouchLayout extends LinearLayout {
    private int lastY, screenHeight;

    public TouchLayout(Context context) {
        this(context, null);
    }

    public TouchLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels - 500;//减去下边的高度
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //设定移动的初始位置相对位置
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE://移动
                //event.getRawX()事件点距离屏幕左上角的距离
                int dy = (int) event.getRawY() - lastY;
                int top = this.getTop() + dy;
                int bottom = this.getBottom() + dy;
                if (top < 0) {  //最上边
                    top = 0;
                    bottom = top + this.getHeight();
                }
                if (bottom > screenHeight) {//最下边
                    bottom = screenHeight;
                    top = bottom - this.getHeight();
                }
                this.layout(getLeft(), top, getRight(), bottom);//设置控件的新位置
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
