package com.yanxin.store.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.will.habit.binding.command.BindingAction;
import com.will.habit.binding.command.BindingCommand;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Desc: 配合{@link SwipeLayout} 使用
 * 支持触摸时关闭已打开的item
 * <p>
 * Date:
 * Company: will
 * Updater:
 * Update Time:
 * Update Comments:
 *
 * @Author:
 */
public class SwipeRecyclerViewSupport extends RecyclerView {

    private HashSet<SwipeLayout> mOpenSwipeLayouts = new HashSet<>(2);
    private SwipeLayoutListener mSwipeLayoutListener = new SwipeLayoutListener();
    private BindingCommand<BindingAction> closeCommand;

    /**
     * 当前SwipeLayout是否左滑打开
     */
    private boolean isOpened = false;

    public SwipeRecyclerViewSupport(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child instanceof SwipeLayout) {
            ((SwipeLayout) child).addSwipeListener(mSwipeLayoutListener);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getPointerCount() > 1) {
            return super.onInterceptTouchEvent(e);
        }

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isOpened) {
                // 如果当前recycleView中的SwipeLayout都是关闭的，则通知关闭其他recycleView中的SwipeLayout
                closeOtherSwipeLayout();
            } else if (!mOpenSwipeLayouts.isEmpty()) {
                // 用户点击了当前有打开SwipeLayout的RecycleView中的item
                View view = findChildViewUnder(e.getX(), e.getY());
                closeSwipeLayoutWithout(view);
            }
        }
        return super.onInterceptTouchEvent(e);
    }

    /**
     * 设置关闭SwipeLayout监听
     *
     * <p>
     */
    public void setCloseCommand(BindingCommand<BindingAction> closeCommand) {
        this.closeCommand = closeCommand;
    }

    /**
     * 触发关闭SwipeLayout
     *
     * <p>
     */
    public void setTriggerClose(boolean close) {
        closeSwipeLayoutWithout();
    }

    /**
     * Desc: 通知关闭已打开的item
     * <p>
     */
    private void closeOtherSwipeLayout() {
        if (closeCommand != null) {
            closeCommand.execute();
        }
    }

    /**
     * Desc: 关闭除了触摸区域的item
     * <p>
     * @param childViewUnder 触摸的item
     */
    private void closeSwipeLayoutWithout(@Nullable View childViewUnder) {
        Iterator<SwipeLayout> iterator = mOpenSwipeLayouts.iterator();
        while (iterator.hasNext()) {
            SwipeLayout swipeLayout = iterator.next();
            if (swipeLayout != childViewUnder) {
                swipeLayout.close();
                iterator.remove();
            }
        }
    }

    /**
     * Desc: 关闭已打开的SwipeLayout，此方法用于嵌套RecycleView时调用
     * <p>
     */
    public void closeSwipeLayoutWithout() {
        Iterator<SwipeLayout> iterator = mOpenSwipeLayouts.iterator();
        while (iterator.hasNext()) {
            SwipeLayout swipeLayout = iterator.next();
            swipeLayout.close();
            iterator.remove();
        }
    }

    private class SwipeLayoutListener implements SwipeLayout.SwipeListener {

        @Override
        public void onStartOpen(SwipeLayout layout) {
            mOpenSwipeLayouts.add(layout);
        }

        @Override
        public void onOpen(SwipeLayout layout) {
            mOpenSwipeLayouts.add(layout);
            isOpened = true;
        }

        @Override
        public void onStartClose(SwipeLayout layout) {
        }

        @Override
        public void onClose(SwipeLayout layout) {
            if (layout.getOpenStatus() == SwipeLayout.Status.Close) {
                mOpenSwipeLayouts.remove(layout);
                isOpened = false;
            }
        }

        @Override
        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

        }

        @Override
        public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

        }
    }
}
