package com.yanxin.store.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.BrandAdapter;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.base.BasePopupWindow;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.FiltrateBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/18
 */
public class FiltrateWindow extends BasePopupWindow {

    private PopupWindow.OnDismissListener onDismissListener;
    private ArrayList<FiltrateBean> list;
    private ArrayList<FiltrateBean> allList = new ArrayList<>();
    private ArrayList<FiltrateBean> searchList = new ArrayList<>();
    private OnSelectFiltrateListener onSelectFiltrateListener;

    private RecyclerView rv;
    private EditText mSearchView;
    private ImageView mSearchIconView;
    private BrandAdapter adapter;
    private BaseActivity activity;

    public FiltrateWindow(Context context, Builder builder) {
        super(context);
        activity = (BaseActivity) context;
        onDismissListener = builder.onDismissListener;
        list = builder.list;
        allList.addAll(list);
        onSelectFiltrateListener = builder.onSelectFiltrateListener;
        createWindow(R.layout.popup_filtrate, builder.width,
                builder.height);
    }

    @Override
    protected void onViewCreated(View container) {
        rv = container.findViewById(R.id.popup_filtrate_rv);
        mSearchView = container.findViewById(R.id.popup_search);
        mSearchIconView = container.findViewById(R.id.popup_search_icon);
        if (list == null) {
            list = new ArrayList<>();
        }
        adapter = new BrandAdapter(R.layout.item_pop_recycler, list);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (!list.isEmpty() && list.size() > position) {
                FiltrateBean bean = list.get(position);
                if (onSelectFiltrateListener != null) {
                    onSelectFiltrateListener.onSelectFiltrate(bean);
                }
            }
            dismiss();
        });
        mSearchIconView.setOnClickListener(v -> {
            String searchValue = BasicUtils.getEditValue(mSearchView);
            if (searchValue.isEmpty()) {
                list.clear();
                list.addAll(allList);
            } else {
                searchList.clear();
                list.clear();
                for (FiltrateBean filtrateBean : allList) {
                    if (filtrateBean.getName().contains(searchValue)) {
                        FiltrateBean searchBean = new FiltrateBean();
                        searchBean.setUuid(filtrateBean.getUuid());
                        searchBean.setName(filtrateBean.getName());
                        searchBean.setType(filtrateBean.getType());
                        searchList.add(searchBean);
                    }
                }
                list.addAll(searchList);
            }
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onPopupWindowCreated(PopupWindow popupWindow) {
        popupWindow.setOnDismissListener(() -> {
            if (onDismissListener != null) {
                mSearchView.setText("");
                list.clear();
                list.addAll(allList);
                onDismissListener.onDismiss();
            }
            darkenBackground(activity, 1f);
        });
        darkenBackground(activity, 0.4f);
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Activity activity, Float bgColor) {
        if (activity != null) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = bgColor;
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            activity.getWindow().setAttributes(lp);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private PopupWindow.OnDismissListener onDismissListener;
        private ArrayList<FiltrateBean> list;
        private int width;
        private int height;
        private OnSelectFiltrateListener onSelectFiltrateListener;


        public Builder setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnArithmeticList(ArrayList<FiltrateBean> list) {
            this.list = list;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setOnSelectFiltrateListener(OnSelectFiltrateListener onSelectFiltrateListener) {
            this.onSelectFiltrateListener = onSelectFiltrateListener;
            return this;
        }

        public FiltrateWindow build(Context context) {
            return new FiltrateWindow(context, this);
        }
    }

    public interface OnSelectFiltrateListener {
        /**
         * 选中item
         *
         * @param filtrateBean
         */
        void onSelectFiltrate(FiltrateBean filtrateBean);
    }
}
