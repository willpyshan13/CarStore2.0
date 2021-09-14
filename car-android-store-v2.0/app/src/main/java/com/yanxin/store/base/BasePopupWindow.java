package com.yanxin.store.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

public abstract class BasePopupWindow {
    protected Context mContext;
    protected View container;
    protected PopupWindow mPopup;

    protected int width;
    protected int height;

    public BasePopupWindow(Context context) {
        this.mContext = context;
    }

    protected void createWindow(int layoutRes, int w, int h) {
        this.width = w;
        this.height = h;
        container = LayoutInflater.from(mContext).inflate(layoutRes, null, false);
        onViewCreated(container);
        mPopup = new PopupWindow(container, width, height, true);
        onPopupWindowCreated(mPopup);
        mPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopup.setOutsideTouchable(true);
        mPopup.setTouchable(true);
    }

    /**
     * 显示在瞄点view的位置
     *
     * @param anchor        瞄点view
     * @param layoutGravity 与瞄点view的位置
     * @param xMerge        与layoutGravity的x偏移位置相叠加
     * @param yMerge        与layoutGravity的y偏移位置相叠加
     */
    public void showBashOfAnchor(View anchor, LayoutGravity layoutGravity, int xMerge, int yMerge) {
        int[] offset = layoutGravity.getOffset(anchor, mPopup);
        mPopup.showAsDropDown(anchor, offset[0] + xMerge, offset[1] + yMerge);
    }

    /**
     * 显示在瞄点view的位置
     *
     * @param anchor        瞄点view
     * @param layoutGravity 与瞄点view的位置
     */
    public void showBashOfAnchor(View anchor, LayoutGravity layoutGravity) {
        int[] offset = layoutGravity.getOffset(anchor, mPopup);
        mPopup.showAsDropDown(anchor, offset[0], offset[1]);
    }

    /**
     * 显示在瞄点view的位置
     *
     * @param anchor 瞄点view
     * @param xOff   x偏移位置
     * @param yOff   y偏移位置
     */
    public void showAsDropDown(View anchor, int xOff, int yOff) {
        mPopup.showAsDropDown(anchor, xOff, yOff);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        mPopup.showAtLocation(parent, gravity, x, y);
    }

    public void dismiss() {
        if (mPopup != null) {
            mPopup.dismiss();
        }
    }

    /**
     * view创建之后
     *
     * @param container 内容布局
     */
    protected abstract void onViewCreated(View container);

    /**
     * PopupWindow创建之后
     *
     * @param popupWindow PopupWindow对象
     */
    protected abstract void onPopupWindowCreated(PopupWindow popupWindow);


    public static class LayoutGravity {
        private int layoutGravity;
        public static final int ALIGN_LEFT = 0x1;
        public static final int ALIGN_ABOVE = 0x2;
        public static final int ALIGN_RIGHT = 0x4;
        public static final int ALIGN_BOTTOM = 0x8;
        public static final int TO_LEFT = 0x10;
        public static final int TO_ABOVE = 0x20;
        public static final int TO_RIGHT = 0x40;
        public static final int TO_BOTTOM = 0x80;
        public static final int CENTER_HORIZONTAL = 0x100;
        public static final int CENTER_VERTICAL = 0x200;

        public LayoutGravity(int gravity) {
            layoutGravity = gravity;
        }

        public int getLayoutGravity() {
            return layoutGravity;
        }

        public void setLayoutGravity(int gravity) {
            layoutGravity = gravity;
        }

        public void setHorizontalGravity(int gravity) {
            layoutGravity &= (ALIGN_ABOVE + ALIGN_BOTTOM + TO_ABOVE + TO_BOTTOM + CENTER_VERTICAL);
            layoutGravity |= gravity;
        }

        public void setVerticalGravity(int gravity) {
            layoutGravity &= (ALIGN_LEFT + ALIGN_RIGHT + TO_LEFT + TO_RIGHT + CENTER_HORIZONTAL);
            layoutGravity |= gravity;
        }

        public boolean isParamFit(int param) {
            return (layoutGravity & param) > 0;
        }

        public int getHorizontalParams() {
            for (int i = ALIGN_LEFT; i <= CENTER_HORIZONTAL; i = i << 2) {
                if (isParamFit(i)) {
                    return i;
                }
            }
            return ALIGN_LEFT;
        }

        public int getVerticalParam() {
            for (int i = ALIGN_ABOVE; i <= CENTER_VERTICAL; i = i << 2) {
                if (isParamFit(i)) {
                    return i;
                }
            }
            return TO_BOTTOM;
        }

        public int[] getOffset(View anchor, PopupWindow window) {
            int anchorWidth = anchor.getWidth();
            int anchorHeight = anchor.getHeight();

            int winWidth = window.getWidth();
            int winHeight = window.getHeight();
            View view = window.getContentView();
            if (winWidth <= 0) {
                winWidth = view.getWidth();
            }
            if (winHeight <= 0) {
                winHeight = view.getHeight();
            }

            int xOff = 0;
            int yOff = 0;

            switch (getHorizontalParams()) {
                case ALIGN_LEFT:
                    xOff = 0;
                    break;
                case ALIGN_RIGHT:
                    xOff = anchorWidth - winWidth;
                    break;
                case TO_LEFT:
                    xOff = -winWidth;
                    break;
                case TO_RIGHT:
                    xOff = anchorWidth;
                    break;
                case CENTER_HORIZONTAL:
                    xOff = (anchorWidth - winWidth) / 2;
                    break;
                default:
                    break;
            }
            switch (getVerticalParam()) {
                case ALIGN_ABOVE:
                    yOff = -anchorHeight;
                    break;
                case ALIGN_BOTTOM:
                    yOff = -winHeight;
                    break;
                case TO_ABOVE:
                    yOff = -anchorHeight - winHeight;
                    break;
                case TO_BOTTOM:
                    yOff = 0;
                    break;
                case CENTER_VERTICAL:
                    yOff = (-winHeight - anchorHeight) / 2;
                    break;
                default:
                    break;
            }
            return new int[]{xOff, yOff};
        }
    }
}