package com.yanxin.store.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.MallGroupAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.GroupCreateBean;
import com.yanxin.store.contract.CreateGroupContract;
import com.yanxin.store.presenter.CreateGroupPresenter;
import com.yanxin.store.req.GroupBuyReq;

import java.util.ArrayList;

@XmlLayoutResId(contentId = R.layout.activity_create_group)
public class MallGroupCreateActivity extends BaseMvpActivity<CreateGroupPresenter> implements CreateGroupContract.CreateGroupView {
    private TextView mGroupCount;
    private RecyclerView mGroupRv;
    private Button mGroupSubmit;
    private MallGroupAdapter mGroupAdapter;
    private ArrayList<GroupCreateBean.DataBean> groupBean;
    private GroupCreateBean.DataBean mCreateBean;

    @Override
    protected void initMVPData() {
        GroupBuyReq groupBuyReq = new GroupBuyReq();
        groupBuyReq.setPageNum(1);
        groupBuyReq.setPageSize(10000);
        mPresenter.queryAllGroupMall(groupBuyReq);
        mGroupSubmit.setOnClickListener(v -> {
            if (mCreateBean == null) {
                ToastUtils.showShort("请先选择拼团商品");
                return;
            }
            Intent intent = new Intent(getBaseContext(), MallGroupDetailCreateActivity.class);
            intent.putExtra("bean", mCreateBean);
            startActivity(intent);
        });
        mGroupAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (GroupCreateBean.DataBean dataBean : groupBean) {
                    dataBean.setCheck(false);
                }
                GroupCreateBean.DataBean dataBean = groupBean.get(position);
                mCreateBean = dataBean;
                boolean check = dataBean.isCheck();
                dataBean.setCheck(!check);
                mGroupAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initMVPView() {
        groupBean = new ArrayList<>();
        mGroupCount = findViewById(R.id.group_count);
        mGroupRv = findViewById(R.id.group_rv);
        mGroupSubmit = findViewById(R.id.group_submit);
        mGroupAdapter = new MallGroupAdapter(R.layout.item_create_group_mall, groupBean);
        mGroupRv.setAdapter(mGroupAdapter);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public BasePresenter initPresenter() {
        return CreateGroupPresenter.newInstance();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void queryAllGroupMall(ArrayList<GroupCreateBean.DataBean> groupBean) {
        this.groupBean.addAll(groupBean);
        mGroupCount.setText("已自动筛选支持创建拼团活动可选择的商品，总计" + groupBean.size() + "个");
        mGroupAdapter.notifyDataSetChanged();
    }
}
