package com.yanxin.store.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yanxin.store.R;
import com.yanxin.store.listener.IPopClick;

/**
 * PopWindow工具类，先写死，等空下来在扩展
 */
public class BasePOPWindow {

    public static PopupWindow createPopWindow(Context context, IPopClick iPopClick) {
        View popView = LayoutInflater.from(context).inflate(R.layout.layout_photo_select, null);
        PopupWindow mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        popView.setOnClickListener(iPopClick::callBack);
        popView.findViewById(R.id.photo_camera).setOnClickListener(iPopClick::callBack);
        popView.findViewById(R.id.photo_picker).setOnClickListener(iPopClick::callBack);
        popView.findViewById(R.id.photo_cancel).setOnClickListener(iPopClick::callBack);
        popView.findViewById(R.id.photo_dismiss).setOnClickListener(iPopClick::callBack);
        return mPopupWindow;
    }

    public static PopupWindow createCenterPopWindow(Context context, IPopClick iPopClick) {
        View popView = LayoutInflater.from(context).inflate(R.layout.layout_photo_center_select, null);
        PopupWindow mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        popView.setOnClickListener(iPopClick::callBack);
        popView.findViewById(R.id.photo_camera).setOnClickListener(iPopClick::callBack);
        popView.findViewById(R.id.photo_picker).setOnClickListener(iPopClick::callBack);
        popView.findViewById(R.id.photo_cancel).setOnClickListener(iPopClick::callBack);
        popView.findViewById(R.id.photo_dismiss).setOnClickListener(iPopClick::callBack);
        return mPopupWindow;
    }

    /**
     * 带动态数据的PopWindow
     * @param context
     * @param iPopClick
     * @return
     */
    public static PopupWindow createRecyclerViewPop(Context context,IPopClick iPopClick) {
        View popView = LayoutInflater.from(context).inflate(R.layout.layout_recyc_pop, null);
        PopupWindow mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        popView.setOnClickListener(iPopClick::callBack);
        return mPopupWindow;
    }

}
