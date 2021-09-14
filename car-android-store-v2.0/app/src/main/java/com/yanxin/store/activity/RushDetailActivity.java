package com.yanxin.store.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SpanUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.ImageAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.RushDetailBean;
import com.yanxin.store.bean.RushOrderDetailBean;
import com.yanxin.store.bean.TechnicianAnswerBean;
import com.yanxin.store.contract.RushDetailContract;
import com.yanxin.store.presenter.RushDetailPresenter;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

@XmlLayoutResId(contentId = R.layout.activity_rush_detail)
public class RushDetailActivity extends BaseMvpActivity<RushDetailPresenter> implements RushDetailContract.RushDetailView {
    private String orderUuid;
    private boolean isOrder;
    private boolean isPT;
    private ImageAdapter imageAdapter;
    private ArrayList<String> mImg;
    private ImageAdapter mReplyAdapter;
    private ArrayList<String> mReplyImg;
    private RelativeLayout mZhuanjiaLayout;
    private CircleImageView mRushZhuanjiaImg;
    private TextView mRushZhuanjiaName;
    private TextView mRushZhuanjiaType;
    private TextView mRushZhuanjiaBrand;
    private RelativeLayout mTiwenLayout;
    private LinearLayout mBuyLayout;
    private OptionalEditLayout mBuyTime;
    private OptionalEditLayout mBuyAmount;
    private CircleImageView mRushImg;
    private TextView mRushName;
    private TextView mRushTime;
    private TextView mDetailTitle;
    private TextView mDetailType;
    private TextView mDetailBrand;
    private TextView mDetailModel;
    private TextView mRushZhuanJiaTo;
    private TextView mRushFailureContent;
    private RecyclerView mRushFailureImg;
    private RecyclerView mRushReplyImg;
    private LinearLayout mRushReplyLayout;
    private TextView mRushReplyContent;


    @Override
    protected void initMVPData() {
        if (isOrder) {
            mPresenter.queryRushOrderDetail(orderUuid);
        } else {
            mPresenter.queryRushDetail(orderUuid);
        }
        imageAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            String mPath = mImg.get(position);
            String fileFormat = BasicUtils.getFileFormat(mPath);
            if (fileFormat.equals("mp4")) {
                startActivity(new Intent(getBaseContext(), VideoActivity.class).putExtra("video_url", mPath));
            } else {
                Intent intent = new Intent(getBaseContext(), ImageActivity.class);
                intent.putExtra("url", mPath);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                        RushDetailActivity.this, view, "bigImg")
                        .toBundle());
            }
        });

        mReplyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            String mPath = mReplyImg.get(position);
            String fileFormat = BasicUtils.getFileFormat(mPath);
            if (fileFormat.equals("mp4")) {
                startActivity(new Intent(getBaseContext(), VideoActivity.class).putExtra("video_url", mPath));
            } else {
                Intent intent = new Intent(getBaseContext(), ImageActivity.class);
                intent.putExtra("url", mPath);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                        RushDetailActivity.this, view, "bigImg")
                        .toBundle());
            }
        });
    }


    @Override
    protected void initMVPView() {
        mImg = new ArrayList<>();
        mReplyImg = new ArrayList<>();
        orderUuid = getIntent().getStringExtra("order_uuid");
        isOrder = getIntent().getBooleanExtra("is_order", false);
        isPT = getIntent().getBooleanExtra("is_pt", false);
        mZhuanjiaLayout = findViewById(R.id.zhuanjia_layout);
        mRushZhuanjiaImg = findViewById(R.id.rush_zhuanjia_img);
        mRushZhuanjiaName = findViewById(R.id.rush_zhuanjia_name);
        mRushZhuanjiaType = findViewById(R.id.rush_zhuanjia_type);
        mRushZhuanjiaBrand = findViewById(R.id.rush_zhuanjia_brand);
        mTiwenLayout = findViewById(R.id.tiwen_layout);
        mBuyLayout = findViewById(R.id.buy_layout);
        mBuyTime = findViewById(R.id.buy_time);
        mBuyAmount = findViewById(R.id.buy_amount);
        mRushImg = findViewById(R.id.rush_img);
        mRushName = findViewById(R.id.rush_name);
        mRushTime = findViewById(R.id.rush_time);
        mDetailTitle = findViewById(R.id.detail_title);
        mDetailType = findViewById(R.id.detail_type);
        mDetailBrand = findViewById(R.id.detail_brand);
        mDetailModel = findViewById(R.id.detail_model);
        mRushZhuanJiaTo = findViewById(R.id.rush_zhuanjia_to);
        mRushFailureContent = findViewById(R.id.rush_failure_content);
        mRushFailureImg = findViewById(R.id.rush_failure_img);
        mRushReplyLayout = findViewById(R.id.rush_reply_layout);
        mRushReplyContent = findViewById(R.id.rush_reply_content);
        mRushReplyImg = findViewById(R.id.rush_reply_img);
        mRushReplyContent = findViewById(R.id.rush_reply_content);
        mRushReplyLayout = findViewById(R.id.rush_reply_layout);
        imageAdapter = new ImageAdapter(R.layout.item_content_desc_img, mImg);
        mReplyAdapter = new ImageAdapter(R.layout.item_content_desc_img, mReplyImg);
        mRushFailureImg.setAdapter(imageAdapter);
        mRushReplyImg.setAdapter(mReplyAdapter);
    }


    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public BasePresenter initPresenter() {
        return RushDetailPresenter.newInstance();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void queryRushOrderDetail(RushOrderDetailBean.DataBean rushDetailBean) {
        RushOrderDetailBean.DataBean.ConsultResBean consultRes = rushDetailBean.getConsultRes();
        if (isPT) {
            mZhuanjiaLayout.setVisibility(View.VISIBLE);
            mTiwenLayout.setVisibility(View.VISIBLE);
            mBuyLayout.setVisibility(View.VISIBLE);
            mBuyTime.setContentTextView(rushDetailBean.getCreatedTime());
            mBuyAmount.setContentTextView("¥" + rushDetailBean.getOrderAmount());
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                    .load(consultRes.getTechnicianImgUrl()).into(mRushZhuanjiaImg);
            SpanUtils.with(mRushZhuanjiaName).append(consultRes.getTechnicianName()).append("  ")
                    .append(rushDetailBean.getByConsultNumber() + "次咨询").append("  ")
                    .append("5.0")
                    .create();
            SpanUtils.with(mRushZhuanjiaType).append("技术类型: ").append(consultRes.getTechnicalTypeName()).create();
            ArrayList<String> brandList = rushDetailBean.getBrandList();
            StringBuffer stringBuffer = new StringBuffer();
            for (String brandStr : brandList) {
                stringBuffer.append(brandStr + ", ");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            SpanUtils.with(mRushZhuanjiaBrand).append("擅长: ").append(stringBuffer.toString()).create();
            mRushZhuanJiaTo.setText("¥" + consultRes.getConsultAmt() + " 发起咨询");
            mRushZhuanJiaTo.setOnClickListener(v -> {
                TechnicianAnswerBean.DataBean dataBean = new TechnicianAnswerBean.DataBean();
                dataBean.setPhotoImgUrl(consultRes.getTechnicianImgUrl());
                dataBean.setName(consultRes.getTechnicianName());
//                dataBean.setScore(rushDetailBean.getTechnicianScore() + "");
                dataBean.setTechnologyType(consultRes.getTechnicalTypeName());
                ArrayList<TechnicianAnswerBean.DataBean.BrandResListBean> brandResArrayList = new ArrayList<>();
                for (String s : brandList) {
                    TechnicianAnswerBean.DataBean.BrandResListBean brandResListBean = new TechnicianAnswerBean.DataBean.BrandResListBean();
                    brandResListBean.setBrandName(s);
                    brandResArrayList.add(brandResListBean);
                }
                dataBean.setBrandResList(brandResArrayList);
                dataBean.setAnswerAmt(rushDetailBean.getReceivableAmount());
                dataBean.setQaCount(rushDetailBean.getByConsultNumber());
                Intent intent = new Intent(getBaseContext(), AskEveryoneActivity.class);
                intent.putExtra("is_zhuanjia", true);
                intent.putExtra("data", dataBean);
                startActivity(intent);
            });
            //提问者显示
            mTiwenLayout.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                    .load(consultRes.getTechnicianImgUrl()).into(mRushImg);
            mRushName.setText(consultRes.getCarOwnerName());
            mRushTime.setText("提问时间: " + rushDetailBean.getCreatedTime());
        }
        //如果技师UUID和当前登录用户一致,   就是当前用户的单子
        if (consultRes.getTechnicianUuid() != null && consultRes.getTechnicianUuid().equals(MyApplication.getUserUuid())) {
            if (consultRes.getAnswerCheckSts() == 1) {
                mRushReplyLayout.setVisibility(View.VISIBLE);
                mRushReplyContent.setText(consultRes.getAnswerDesc());
                mReplyImg.addAll(consultRes.getAnswerImgList());
                mReplyAdapter.notifyDataSetChanged();
            }
        } else if (consultRes.getAnswerSts() == 1 && consultRes.getAnswerCheckSts() == 1) {
            mRushReplyLayout.setVisibility(View.VISIBLE);
            mRushReplyContent.setText(consultRes.getAnswerDesc());
            mReplyImg.addAll(consultRes.getAnswerImgList());
            mReplyAdapter.notifyDataSetChanged();
        }
        Glide.with(getBaseContext())
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                .load(rushDetailBean.getCarOwnerImgUrl()).into(mRushImg);
//        mRushName.setText(rushDetailBean.getCarOwnerName());
//        mRushTime.setText(rushDetailBean.getCreatedTime());
        mDetailTitle.setText(rushDetailBean.getConsultRes().getTitle());
        mRushFailureContent.setText(rushDetailBean.getConsultRes().getConsultDesc());
        mDetailType.setText(rushDetailBean.getConsultRes().getTechnicalTypeName());
        mDetailBrand.setText(rushDetailBean.getBrandName());
        mDetailModel.setText(rushDetailBean.getModelName());
        mImg.addAll(rushDetailBean.getConsultRes().getConsultImgList());
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryRushDetail(RushDetailBean.DataBean rushDetailBean) {
        //如果技师UUID和当前登录用户一致,   就是当前用户的单子
        if (rushDetailBean.getTechnicianUuid() != null && rushDetailBean.getTechnicianUuid().equals(MyApplication.getUserUuid())) {
            if (rushDetailBean.getAnswerCheckSts() == 1) {
                mRushReplyLayout.setVisibility(View.VISIBLE);
                mRushReplyContent.setText(rushDetailBean.getAnswerDesc());
                mReplyImg.addAll(rushDetailBean.getAnswerImgList());
                mReplyAdapter.notifyDataSetChanged();
            }
        } else if (rushDetailBean.getAnswerSts() == 1 && rushDetailBean.getAnswerCheckSts() == 1) {
            mRushReplyLayout.setVisibility(View.VISIBLE);
            mRushReplyContent.setText(rushDetailBean.getAnswerDesc());
            mReplyImg.addAll(rushDetailBean.getAnswerImgList());
            mReplyAdapter.notifyDataSetChanged();
        }
        Glide.with(getBaseContext())
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                .load(rushDetailBean.getCarOwnerImgUrl()).into(mRushImg);
//        mRushName.setText(rushDetailBean.getCarOwnerName());
//        mRushTime.setText(rushDetailBean.getCreatedTime());
        mDetailType.setText(rushDetailBean.getTechnicalTypeName());
        mDetailBrand.setText(rushDetailBean.getVehicleBrandName());
        mDetailModel.setText(rushDetailBean.getVehicleModelName());
        mDetailTitle.setText(rushDetailBean.getTitle());
        mRushFailureContent.setText(rushDetailBean.getConsultDesc());
        mImg.addAll(rushDetailBean.getConsultImgList());
        imageAdapter.notifyDataSetChanged();
    }
}