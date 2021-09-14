package com.yanxin.store.base;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: fengzeyuan
 * @Date: 2021/7/25 7:08 下午
 */
public abstract class BasePageDataAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private List<T> mDataList = new ArrayList<>();

    public BasePageDataAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BasePageDataAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BasePageDataAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void setNewData(@Nullable List<T> data) {
        if (data != null && !data.isEmpty()) {
            mDataList.clear();
            mDataList.addAll(data);
            super.setNewData(mDataList);
        }
    }

    public void loadMoreDate(@Nullable List<T> data, boolean hasMore) {
        if (data != null) {
            mDataList.addAll(data);
            notifyDataSetChanged();
            loadMoreComplete();
            if (!hasMore || data.isEmpty()) {
                loadMoreEnd();
            }
        } else {
            loadMoreFail();
        }

    }
}
