package com.yanxin.store.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.SrcAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.util.ArrayList;


@XmlLayoutResId(contentId = R.layout.activity_mall_detail)
public class MallDetailActivity extends BaseActivity {
    private String mallUuid;
    private TextView mMallId;
    private TextView mMallType;
    private TextView mMallTime;
    private TextView mMallRejectTitle;
    private TextView mMallRejectContent;
    private ImageView mMallStatusIcon;
    private TextView mMallStatusContent;
    private OptionalEditLayout mMallTitle;
    private OptionalEditLayout mMallOneType;
    private OptionalEditLayout mMallTwoType;
    private OptionalEditLayout mMallBrand;
    private OptionalEditLayout mMallModel;
    private OptionalEditLayout mMallWuliao;
    private OptionalEditLayout mMallServiceAmount;
    private OptionalEditLayout mMallPeisongType;
    private OptionalEditLayout mMallAddress;
    private OptionalEditLayout mMallKucun;
    private OptionalEditLayout mMallShangmenAmount;
    private TextView mMallDesc;
    private ImageView mMallZhutu;
    private RecyclerView mMallPicRv;
    private OptionalEditLayout mMallAmCount;
    private OptionalEditLayout mMallPmCount;
    private OptionalEditLayout mMallNmCount;
    private TextView mMallEdit;
    private TextView mMallSubmit;
    private SrcAdapter mSrcAdapter;
    private ArrayList<String> mImg;


    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryGoodsDetail(MyApplication.getUserToken(), mallUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(goodsDetailBean -> {
                    if (goodsDetailBean.isSuccess()) {
                        initGoodsDetail(goodsDetailBean.getData());
                    } else {
                        ToastUtils.showShort(goodsDetailBean.getMsg());
                        finish();
                    }
                }, throwable -> {
                    ToastUtils.showShort(throwable.getMessage());
                    finish();
                });
        mMallEdit.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), MallGoodsCreateActivity.class)
                .putExtra("is_editor", true)
                .putExtra("mall_uuid", mallUuid)));
    }

    @SuppressLint("SetTextI18n")
    private void initGoodsDetail(GoodsDetailBean.DataBean detailBean) {
        String[] addSplit = detailBean.getAddr().split(",");
        mMallId.setText("商品ID: " + detailBean.getUuid());
        mMallType.setText("类型: " + detailBean.getGoodsTypeName());
        mMallTime.setText("创建时间: " + detailBean.getCreatedTime());
        if (detailBean.getCheckSts() == 2) {
            mMallRejectTitle.setVisibility(View.VISIBLE);
            mMallRejectContent.setVisibility(View.VISIBLE);
            mMallRejectContent.setText("");
            mMallStatusIcon.setImageResource(R.drawable.failed_icon);
            mMallStatusContent.setText("已驳回");
        } else if (detailBean.getCheckSts() == 1) {
            mMallStatusIcon.setImageResource(R.drawable.success_icon);
            mMallStatusContent.setText("已上架");
        } else if (detailBean.getCheckSts() == 0) {
            mMallStatusIcon.setImageResource(R.drawable.wait_icon);
            mMallStatusContent.setText("待审核");
        } else if (detailBean.getSellSts() == 0) {
            mMallStatusIcon.setImageResource(R.drawable.failed_icon);
            mMallStatusContent.setText("已下架");
        }
        mMallTitle.setContentTextView(detailBean.getGoodsName());
        mMallOneType.setContentTextView(detailBean.getLevelOne());
        mMallTwoType.setContentTextView(detailBean.getLevelTwo());
        mMallBrand.setContentTextView(detailBean.getVehicleBrandName());
        mMallModel.setContentTextView(detailBean.getVehicleModelName());
        StringBuffer sb = new StringBuffer();
        ArrayList<GoodsDetailBean.DataBean.DetailListBean> detailList = detailBean.getDetailList();
        for (GoodsDetailBean.DataBean.DetailListBean detailListBean : detailList) {
            sb.append(detailListBean.getName() + ":" + detailListBean.getActAmt() + "元" + "/");
        }
        sb.deleteCharAt(sb.length() - 1);
        mMallWuliao.setContentTextView(sb.toString());
        mMallServiceAmount.setContentTextView(detailBean.getPlatformServiceMoney() + "元");
        if (detailBean.getReceiveMethod() == 1) {
            mMallPeisongType.setContentTextView("到店服务");
            mMallKucun.setVisibility(View.VISIBLE);
            mMallAddress.setVisibility(View.VISIBLE);
            mMallAddress.setContentTextView(addSplit[0] + " " + addSplit[1]);
            mMallKucun.setContentTextView(detailBean.getSurplusNum() + "");
        } else {
            mMallPeisongType.setContentTextView("上门服务");
            mMallShangmenAmount.setVisibility(View.VISIBLE);
            mMallShangmenAmount.setContentTextView(detailBean.getVisitFee() + "元");
        }
        mMallDesc.setText(detailBean.getGoodsDescribe());
        ArrayList<GoodsDetailBean.DataBean.ImgListBean> imgList = detailBean.getImgList();
        String poPath = null;
        ArrayList<String> nePath = new ArrayList<>();
        ;
        for (GoodsDetailBean.DataBean.ImgListBean imgListBean : imgList) {
            if (imgListBean.getImgType() == 1) {
                poPath = imgListBean.getImgPath();
            } else {
                nePath.add(imgListBean.getImgPath());
            }
        }
        Glide.with(getBaseContext())
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                .load(poPath).into(mMallZhutu);
        mImg.addAll(nePath);
        mSrcAdapter.notifyDataSetChanged();
        mMallAmCount.setContentTextView(detailBean.getAmServeNum() + "人");
        mMallPmCount.setContentTextView(detailBean.getPmServeNum() + "人");
        mMallNmCount.setContentTextView(detailBean.getNmServeNum() + "人");
    }

    @Override
    protected void initView() {
        mallUuid = getIntent().getStringExtra("mall_uuid");
        mImg = new ArrayList<>();
        mMallId = findViewById(R.id.mall_id);
        mMallType = findViewById(R.id.mall_type);
        mMallTime = findViewById(R.id.mall_time);
        mMallRejectTitle = findViewById(R.id.mall_reject_title);
        mMallRejectContent = findViewById(R.id.mall_reject_content);
        mMallStatusIcon = findViewById(R.id.mall_status_icon);
        mMallStatusContent = findViewById(R.id.mall_status_content);
        mMallTitle = findViewById(R.id.mall_title);
        mMallOneType = findViewById(R.id.mall_one_type);
        mMallTwoType = findViewById(R.id.mall_two_type);
        mMallBrand = findViewById(R.id.mall_brand);
        mMallModel = findViewById(R.id.mall_model);
        mMallWuliao = findViewById(R.id.mall_wuliao);
        mMallServiceAmount = findViewById(R.id.mall_service_amount);
        mMallPeisongType = findViewById(R.id.mall_peisong_type);
        mMallAddress = findViewById(R.id.mall_address);
        mMallKucun = findViewById(R.id.mall_kucun);
        mMallShangmenAmount = findViewById(R.id.mall_shangmen_amount);
        mMallDesc = findViewById(R.id.mall_desc);
        mMallZhutu = findViewById(R.id.mall_zhutu);
        mMallPicRv = findViewById(R.id.mall_pic_rv);
        mMallAmCount = findViewById(R.id.mall_am_count);
        mMallPmCount = findViewById(R.id.mall_pm_count);
        mMallNmCount = findViewById(R.id.mall_nm_count);
        mMallEdit = findViewById(R.id.mall_edit);
        mMallSubmit = findViewById(R.id.mall_submit);
        mSrcAdapter = new SrcAdapter(R.layout.item_content_desc_img, mImg);
        mMallPicRv.setAdapter(mSrcAdapter);
    }
}
