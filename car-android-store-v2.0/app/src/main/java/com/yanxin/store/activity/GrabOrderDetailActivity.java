package com.yanxin.store.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.SrcAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.SceneInfoBean;
import com.yanxin.store.contract.GrabOrderContract;
import com.yanxin.store.event.GrabEvent;
import com.yanxin.store.event.SupportEvent;
import com.yanxin.store.presenter.GrabOrderPresenter;
import com.yanxin.store.req.SceneOrderConfirmReq;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasicUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.yanxin.store.commont.Constant.PermissionStatus.PER_CALL_PHONE;

@XmlLayoutResId(contentId = R.layout.activity_grab_order_detail)
public class GrabOrderDetailActivity extends BaseMvpActivity<GrabOrderPresenter> implements GrabOrderContract.GrabOrderView, EasyPermissions.PermissionCallbacks {
    private String mGrabUuid;
    private TextView mGrabStatusTitle;
    private ImageView mGrabStatusIcon;
    private ImageView mGrabAvatar;
    private TextView mGrabName;
    private OptionalEditLayout mGrabServingTime;
    private OptionalEditLayout mGrabServingAddress;
    private OptionalEditLayout mGrabBrand;
    private OptionalEditLayout mGrabModel;
    private OptionalEditLayout mGrabYear;
    private OptionalEditLayout mGrabVin;
    private OptionalEditLayout mGrabTransmission;
    private OptionalEditLayout mGrabDriving;
    private OptionalEditLayout mGrabBooster;
    private OptionalEditLayout mGrabWarrantyStatus;
    private OptionalEditLayout mGrabOtherStatus;
    private TextView mGrabFaultDesc;
    private RecyclerView mGrabFaultImg;
    private OptionalEditLayout mGrabMaintainType;
    private TextView mGrabInspectionProcess;
    private ImageView mGrabShangMenHF;
    private ImageView mGrabWeiXiuHF;
    private LinearLayout mGrabTechnicianLayout;
    private LinearLayout mGrabSceneOrderLayout;
    private TextView mGrabDtcDesc;
    private RecyclerView mGrabDtcImg;
    private OptionalEditLayout mGrabMaintainAmount;
    private OptionalEditLayout mGrabMaintainServiceAmount;
    private OptionalEditLayout mGrabCreateTime;
    private OptionalEditLayout mGrabOrderTime;
    private OptionalEditLayout mGrabPayTime;
    private OptionalEditLayout mGrabPayType;
    private OptionalEditLayout mGrabPayAmount;
    private OptionalEditLayout mGrabDaoDaTime;
    private OptionalEditLayout mGrabQrDaoDaTime;
    private OptionalEditLayout mGrabWeiXiuTime;
    private OptionalEditLayout mGrabQrWeiXiuTime;
    private OptionalEditLayout mGrabWangGongTime;
    private OptionalEditLayout mGrabQrWangGongTime;
    private TextView mGrabFaultTechnicianDesc;
    private TextView mGrabFaultTechnicianSolve;
    private TextView mGrabInspectDesc;
    private OptionalEditLayout mGrabBasicAmount;
    private OptionalEditLayout mGrabRepairAmount;
    private OptionalEditLayout mGrabOtherAmount;
    private OptionalEditLayout mGrabAllAmount;
    private Button mGrabSubmit;
    private Button mGrabTwo;
    private SrcAdapter mDescSrcAdapter;
    private ArrayList<String> mDescList;
    private SrcAdapter mDTCSrcAdapter;
    private ArrayList<String> mDTCList;
    private SrcAdapter mTechnicianSrcAdapter;
    private ArrayList<String> mTechnicianList;
    private SrcAdapter mWeiXiuSrcAdapter;
    private ArrayList<String> mWeiXiuList;
    private SrcAdapter mGrabSrcAdapter;
    private ArrayList<String> mGrabList;
    private String technicianMobile;
    private LinearLayout mGrabTechnicianMaintenanceLayout;
    private LinearLayout mGrabConfirmLayout;
    private TextView mGrabConfirmContent;
    private RecyclerView mGrabConfirmRv;
    private TextView mGrabTechnicianContent;
    private RecyclerView mGrabTechnicianRv;
    private RecyclerView mGrabInspectRvAmount;

    @Override
    protected void initMVPData() {
        mPresenter.getSceneOrderDetail(mGrabUuid);
        mGrabSrcAdapter.setOnItemChildClickListener((adapter, view, position) -> ActivityOptionsMakeSceneTransitionAnimation(view, mGrabList.get(position)));
        mTechnicianSrcAdapter.setOnItemChildClickListener((adapter, view, position) -> ActivityOptionsMakeSceneTransitionAnimation(view, mTechnicianList.get(position)));
        mDTCSrcAdapter.setOnItemChildClickListener((adapter, view, position) -> ActivityOptionsMakeSceneTransitionAnimation(view, mDTCList.get(position)));
        mDescSrcAdapter.setOnItemChildClickListener((adapter, view, position) -> ActivityOptionsMakeSceneTransitionAnimation(view, mDescList.get(position)));
        mWeiXiuSrcAdapter.setOnItemChildClickListener((adapter, view, position) -> ActivityOptionsMakeSceneTransitionAnimation(view, mWeiXiuList.get(position)));
    }

    private void ActivityOptionsMakeSceneTransitionAnimation(View view, String mPath) {
        String fileFormat = BasicUtils.getFileFormat(mPath);
        if (fileFormat.equals("mp4")) {
            startActivity(new Intent(getBaseContext(), VideoActivity.class).putExtra("video_url", mPath));
        } else {
            Intent intent = new Intent(getBaseContext(), ImageActivity.class);
            intent.putExtra("url", mPath);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                    GrabOrderDetailActivity.this, view, "bigImg")
                    .toBundle());
        }
    }

    @Override
    protected void initMVPView() {
        EventBus.getDefault().register(this);
        mGrabUuid = getIntent().getStringExtra("grab_uuid");
        mGrabStatusTitle = findViewById(R.id.grab_status_title);
        mGrabStatusIcon = findViewById(R.id.grab_status_icon);
        mGrabAvatar = findViewById(R.id.grab_avatar);
        mGrabName = findViewById(R.id.grab_name);
        mGrabServingTime = findViewById(R.id.grab_serving_time);
        mGrabServingAddress = findViewById(R.id.grab_serving_address);
        mGrabBrand = findViewById(R.id.grab_brand);
        mGrabModel = findViewById(R.id.grab_model);
        mGrabYear = findViewById(R.id.grab_year);
        mGrabVin = findViewById(R.id.grab_vin);
        mGrabTransmission = findViewById(R.id.grab_transmission);
        mGrabDriving = findViewById(R.id.grab_driving);
        mGrabBooster = findViewById(R.id.grab_booster);
        mGrabWarrantyStatus = findViewById(R.id.grab_warranty_status);
        mGrabOtherStatus = findViewById(R.id.grab_other_status);
        mGrabFaultDesc = findViewById(R.id.grab_fault_desc);
        mGrabFaultImg = findViewById(R.id.grab_fault_img);
        mGrabMaintainType = findViewById(R.id.grab_maintain_type);
        mGrabInspectionProcess = findViewById(R.id.grab_inspection_process);
        mGrabShangMenHF = findViewById(R.id.grab_shangmen_hf);
        mGrabWeiXiuHF = findViewById(R.id.grab_weixiu_hf);
        mGrabTechnicianLayout = findViewById(R.id.grab_basic_amount_layout);
        mGrabSceneOrderLayout = findViewById(R.id.grab_scene_order_layout);
        mGrabDtcDesc = findViewById(R.id.grab_dtc_desc);
        mGrabDtcImg = findViewById(R.id.grab_dtc_img);
        mGrabMaintainAmount = findViewById(R.id.grab_maintain_amount);
        mGrabMaintainServiceAmount = findViewById(R.id.grab_maintain_service_amount);
        mGrabCreateTime = findViewById(R.id.grab_create_time);
        mGrabOrderTime = findViewById(R.id.grab_order_time);
        mGrabPayTime = findViewById(R.id.grab_pay_time);
        mGrabPayType = findViewById(R.id.grab_pay_type);
        mGrabTechnicianMaintenanceLayout = findViewById(R.id.grab_technician_maintenance_layout);
        mGrabTechnicianContent = findViewById(R.id.grab_technician_content);
        mGrabTechnicianRv = findViewById(R.id.grab_technician_rv);
        mGrabConfirmLayout = findViewById(R.id.grab_confirm_layout);
        mGrabConfirmContent = findViewById(R.id.grab_confirm_content);
        mGrabConfirmRv = findViewById(R.id.grab_confirm_rv);
        mGrabPayAmount = findViewById(R.id.grab_pay_amount);
        mGrabDaoDaTime = findViewById(R.id.grab_daoda_time);
        mGrabWeiXiuTime = findViewById(R.id.grab_weixiu_time);
        mGrabWangGongTime = findViewById(R.id.grab_wangong_time);
        mGrabQrWangGongTime = findViewById(R.id.grab_qr_wangong_time);
        mGrabQrDaoDaTime = findViewById(R.id.grab_qr_daoda_time);
        mGrabQrWeiXiuTime = findViewById(R.id.grab_qr_weixiu_time);
        mGrabFaultTechnicianDesc = findViewById(R.id.grab_fault_technician_desc);
        mGrabFaultTechnicianSolve = findViewById(R.id.grab_fault_technician_solve);
        mGrabInspectDesc = findViewById(R.id.grab_inspect_desc);
        mGrabBasicAmount = findViewById(R.id.grab_basic_amount);
        mGrabInspectRvAmount = findViewById(R.id.grab_inspect_rv);
        mGrabRepairAmount = findViewById(R.id.grab_repair_amount);
        mGrabOtherAmount = findViewById(R.id.grab_other_amount);
        mGrabAllAmount = findViewById(R.id.grab_all_amount);
        mGrabSubmit = findViewById(R.id.grab_submit);

        mGrabTwo = findViewById(R.id.grab_cancel);
        mDescList = new ArrayList<>();
        mDTCList = new ArrayList<>();
        mWeiXiuList = new ArrayList<>();
        mTechnicianList = new ArrayList<>();
        mGrabList = new ArrayList<>();
        mDescSrcAdapter = new SrcAdapter(R.layout.item_content_desc_img, mDescList);
        mGrabFaultImg.setAdapter(mDescSrcAdapter);
        mDTCSrcAdapter = new SrcAdapter(R.layout.item_content_desc_img, mDTCList);
        mGrabDtcImg.setAdapter(mDTCSrcAdapter);
        mTechnicianSrcAdapter = new SrcAdapter(R.layout.item_content_desc_img, mTechnicianList);
        mGrabTechnicianRv.setAdapter(mTechnicianSrcAdapter);
        mGrabSrcAdapter = new SrcAdapter(R.layout.item_content_desc_img, mGrabList);
        mGrabConfirmRv.setAdapter(mGrabSrcAdapter);
        mWeiXiuSrcAdapter = new SrcAdapter(R.layout.item_content_desc_img, mWeiXiuList);
        mGrabInspectRvAmount.setAdapter(mWeiXiuSrcAdapter);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);

    }

    @Override
    public BasePresenter initPresenter() {
        return GrabOrderPresenter.newInstance();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void reminderOrders(String msg) {
        ToastUtils.showShort("催单成功");
    }

    @Override
    public void getSceneOrderDetail(SceneInfoBean.DataBean dataBean) {
        mDTCList.clear();
        mDescList.clear();
        mGrabList.clear();
        mWeiXiuList.clear();
        mGrabTwo.setVisibility(View.GONE);
        mGrabSubmit.setVisibility(View.GONE);
        mGrabServingTime.setContentTextView(dataBean.getServiceDate());
        mGrabServingAddress.setContentTextView(dataBean.getDetailedAddr());
        mGrabBrand.setContentTextView(dataBean.getBrandName());
        mGrabModel.setContentTextView(dataBean.getCarModelName());
        mGrabYear.setContentTextView(dataBean.getCarStyle());
        mGrabVin.setContentTextView(dataBean.getVinCode());
        mGrabTransmission.setContentTextView(dataBean.getTransmissionOneLevel() + "/" + dataBean.getTransmissionTwoLevel());
        mGrabDriving.setContentTextView(dataBean.getDrivingMode());
        mGrabBooster.setContentTextView(dataBean.getBoosterSystem());
        mGrabWarrantyStatus.setContentTextView(dataBean.getWarrantySts() == 0 ? "保修" : "不保修");
        mGrabOtherStatus.setContentTextView(dataBean.getOtherSts());
        mGrabFaultDesc.setText(dataBean.getFaultDesc());
        mDescList.addAll(dataBean.getFaultImageList());
        mGrabMaintainType.setContentTextView(dataBean.getRepairType());
        mGrabInspectionProcess.setText(dataBean.getAlreadyInspect());
        mGrabDtcDesc.setText(dataBean.getDtcCode());
        mDTCList.addAll(dataBean.getDtcImageList());
        mGrabMaintainAmount.setContentTextView("¥" + dataBean.getBasicDoorAmount() + "");
        mGrabMaintainServiceAmount.setContentTextView("¥" + dataBean.getOrderServiceAmount() + "");
        mGrabCreateTime.setContentTextView(dataBean.getCreatedTime());
        int orderSts = dataBean.getOrderSts();
        if (dataBean.getSceneOrderTechnicianUuid() == null) {
            mGrabTechnicianMaintenanceLayout.setVisibility(View.GONE);
            mGrabShangMenHF.setVisibility(View.GONE);
        } else {
            mGrabTechnicianMaintenanceLayout.setVisibility(View.VISIBLE);
            mGrabShangMenHF.setVisibility(View.VISIBLE);
        }
        if (orderSts < 2) {
            mGrabTechnicianLayout.setVisibility(View.GONE);
        } else {
            mGrabTechnicianLayout.setVisibility(View.VISIBLE);
        }
        if (orderSts < 4) {
            mGrabSceneOrderLayout.setVisibility(View.GONE);
            mGrabWeiXiuHF.setVisibility(View.GONE);
        } else {
            mGrabSceneOrderLayout.setVisibility(View.VISIBLE);
            mGrabWeiXiuHF.setVisibility(View.VISIBLE);
        }
        if (orderSts < 6) {
            mGrabConfirmLayout.setVisibility(View.GONE);
        } else {
            mGrabConfirmLayout.setVisibility(View.VISIBLE);
        }
        if (dataBean.getGrabbingOrdersSts() == 1) {
            mGrabOrderTime.setVisibility(View.VISIBLE);
            initGrabOrderStatus(dataBean);
//            mGrabOrderTime.setContentTextView();
        } else {
            mGrabOrderTime.setVisibility(View.GONE);
            mGrabName.setText(dataBean.getIssuerName());
            if (dataBean.getIssuerUuid().equals(MyApplication.getUserUuid())) {
                mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                mGrabStatusTitle.setText("待抢单");
                mGrabTwo.setVisibility(View.GONE);
                mGrabSubmit.setVisibility(View.VISIBLE);
                mGrabSubmit.setText("取消订单");
                mGrabSubmit.setOnClickListener(v -> createAlertDialog("您确认要取消订单吗?", () -> mPresenter.grabCancelOrder(mGrabUuid, 1)));
            } else {
                mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                mGrabStatusTitle.setText("待抢单");
                mGrabSubmit.setVisibility(View.VISIBLE);
                mGrabSubmit.setText("立即抢单");
                mGrabSubmit.setOnClickListener(v -> mPresenter.grabBingOrder(mGrabUuid));
            }
//            mGrabOrderTime.setContentTextView();
        }
        mGrabBrand.setContentTextView(dataBean.getBrandName() + "/" + dataBean.getCarModelName());
        mDescSrcAdapter.notifyDataSetChanged();
        mDTCSrcAdapter.notifyDataSetChanged();
    }

    @Override
    public void sceneOrderConfirm(String dataBean) {
        EventBus.getDefault().post(new SupportEvent());
        mPresenter.getSceneOrderDetail(mGrabUuid);
    }

    @Override
    public void grabBingOrder(String msg) {
        EventBus.getDefault().post(new SupportEvent());
        ToastUtils.showShort("恭喜,抢到订单");
        mPresenter.getSceneOrderDetail(mGrabUuid);
    }

    @Override
    public void grabCancelOrder(String msg) {
        mPresenter.getSceneOrderDetail(mGrabUuid);
        EventBus.getDefault().post(new SupportEvent());
        ToastUtils.showShort("订单已取消");
    }

    /**
     * @param dataBean
     */
    private void initGrabOrderStatus(SceneInfoBean.DataBean dataBean) {
        mGrabOrderTime.setContentTextView(dataBean.getGrabbingDate());

        if (dataBean.getIssuerUuid().equals(MyApplication.getUserUuid())) {
            mGrabName.setText(dataBean.getBuyerName());
            //本人发布
            switch (dataBean.getOrderSts()) {
                case 1:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("已接单, 待支付");
                    mGrabSubmit.setVisibility(View.VISIBLE);
                    mGrabTwo.setVisibility(View.VISIBLE);
                    mGrabTwo.setText("去支付");
                    mGrabSubmit.setText("取消订单");
                    mGrabTwo.setOnClickListener(v -> {
                        MyApplication.setPayOrderUuid(mGrabUuid);
                        MyApplication.setPayStatusType(3);
                        Intent intent = new Intent(getBaseContext(), PayActivity.class);
                        intent.putExtra("order_uuid", mGrabUuid);
                        startActivity(intent);
                    });
                    mGrabSubmit.setOnClickListener(v -> createAlertDialog("您确认要取消订单吗?", () -> mPresenter.grabCancelOrder(mGrabUuid, 2)));
                    break;
                case 2:
                    if (dataBean.getSceneOrderTechnicianUuid() != null) {
                        mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                        mGrabStatusTitle.setText("技师已上门待我确认");
                        mGrabTwo.setVisibility(View.VISIBLE);
                        mGrabTwo.setText("确认到达");
                        mGrabSubmit.setVisibility(View.VISIBLE);
                        mGrabSubmit.setText("联系技师");
                        mGrabTwo.setOnClickListener(v -> {
                            SceneOrderConfirmReq sceneOrderConfirmReq = new SceneOrderConfirmReq();
                            sceneOrderConfirmReq.setSceneOrderUuid(mGrabUuid);
                            sceneOrderConfirmReq.setType(1);
                            mPresenter.sceneOrderConfirm(sceneOrderConfirmReq);
                        });
                        mGrabSubmit.setOnClickListener(v -> {
                            technicianMobile = dataBean.getSceneOrderTechnicianRes().getTechnicianMobile();
                            createAlertDialog("点击确定拨打技师电话号码\n" + technicianMobile, () ->
                                    checkPermission(PER_CALL_PHONE, Manifest.permission.CALL_PHONE));
                        });
                    } else {
                        mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                        mGrabStatusTitle.setText("已付款, 待服务");
                        mGrabSubmit.setVisibility(View.VISIBLE);
                        mGrabSubmit.setText("我要催单");
                        mGrabSubmit.setOnClickListener(v -> mPresenter.reminderOrders(mGrabUuid));
                    }
                    break;
                case 3:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("已确认上门, 等待提交建议维修方案");
                    break;
                case 4:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("已收到建议维修方案，等待付款");
                    mGrabSubmit.setVisibility(View.VISIBLE);
                    mGrabTwo.setVisibility(View.VISIBLE);
                    mGrabSubmit.setText("放弃维修自行处理");
                    mGrabTwo.setText("确认并支付费用");
                    mGrabSubmit.setOnClickListener(v -> {
                        createAlertDialog("您确认要取消订单吗?", () -> mPresenter.grabCancelOrder(mGrabUuid, 3));
                    });
                    mGrabTwo.setOnClickListener(v -> {
                        MyApplication.setPayOrderUuid(mGrabUuid);
                        MyApplication.setPayStatusType(3);
                        String uuidX = dataBean.getSceneOrderServiceRes().getUuidX();
                        Intent intent = new Intent(getBaseContext(), PayActivity.class);
                        intent.putExtra("order_uuid", uuidX);
                        startActivity(intent);
                    });
                    break;
                case 5:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("已支付建议维修方案费用，开始服务");
                    break;
                case 6:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("技师提交完成服务，等待确认");
                    mGrabTwo.setVisibility(View.VISIBLE);
                    mGrabTwo.setText("确认完成");
                    mGrabTwo.setOnClickListener(v -> {
                        createAlertDialog("经您确认并完全认可，本次服务已完成。是否确认?", () -> {
                            SceneOrderConfirmReq sceneOrderConfirmReq = new SceneOrderConfirmReq();
                            sceneOrderConfirmReq.setSceneOrderUuid(mGrabUuid);
                            sceneOrderConfirmReq.setType(2);
                            mPresenter.sceneOrderConfirm(sceneOrderConfirmReq);
                        });

                    });
                    break;
                case 7:
//                    mGrabStatusTitle.setText("已确认，等待评分");
                    mGrabStatusIcon.setImageResource(R.drawable.success_icon);
                    mGrabStatusTitle.setText("订单已完成");
                    break;
                case 8:
                    mGrabStatusIcon.setImageResource(R.drawable.success_icon);
                    mGrabStatusTitle.setText("订单已完成");
                case 11:
                case 12:
                case 13:
                    mGrabStatusIcon.setImageResource(R.drawable.cancel_icon);
                    mGrabStatusTitle.setText("已取消");
                    break;
            }
        } else {
            mGrabName.setText(dataBean.getIssuerName());
            switch (dataBean.getOrderSts()) {
                case 1:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("抢到了, 等待对方支付上门费");
                    mGrabSubmit.setVisibility(View.GONE);
                    mGrabTwo.setVisibility(View.GONE);
                    break;
                case 2:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    if (dataBean.getSceneOrderTechnicianUuid() != null) {
                        mGrabStatusTitle.setText("已提交, 等待对方确认上门");
                        mGrabSubmit.setVisibility(View.GONE);
                    } else {
                        mGrabStatusTitle.setText("对方已支付上门费, 等待上门");
                        mGrabSubmit.setVisibility(View.VISIBLE);
                        mGrabSubmit.setText("我已到达");
                        mGrabSubmit.setOnClickListener(v ->
                                startActivity(new Intent(getBaseContext(), GotoConfirmActivity.class)
                                        .putExtra("uuid", mGrabUuid)
                                        .putExtra("grab_type", 1)));
                    }
                    break;
                case 3:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("已确认上门, 等待提交建议维修方案");
                    mGrabSubmit.setVisibility(View.VISIBLE);
                    mGrabSubmit.setText("提交现场检查数据");
                    mGrabSubmit.setOnClickListener(v -> {
                        startActivity(new Intent(getBaseContext(), CreateMaintenanceProposalActivity.class).putExtra("uuid", mGrabUuid));
                    });
                    break;
                case 4:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("已提交建议维修方案, 等待付款");
                    break;
                case 5:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("已支付建议维修方案费用，开始服务");
                    mGrabSubmit.setVisibility(View.VISIBLE);
                    mGrabSubmit.setText("提交完成服务");
                    mGrabSubmit.setOnClickListener(v ->
                            startActivity(new Intent(getBaseContext(), GotoConfirmActivity.class)
                                    .putExtra("uuid", mGrabUuid)
                                    .putExtra("grab_type", 2)));
                    break;
                case 6:
                    mGrabStatusIcon.setImageResource(R.drawable.wait_icon);
                    mGrabStatusTitle.setText("已提交完成服务, 等待对方确认");
                    break;
                case 7:
                    mGrabStatusIcon.setImageResource(R.drawable.success_icon);
                    mGrabStatusTitle.setText("对方已确认, 订单已完成");
                    break;
                case 8:
                    mGrabStatusIcon.setImageResource(R.drawable.success_icon);
                    mGrabStatusTitle.setText("对方已确认, 订单已完成");
                    break;
                case 11:
                case 12:
                case 13:
                    mGrabStatusIcon.setImageResource(R.drawable.cancel_icon);
                    mGrabStatusTitle.setText("已取消");
                    break;
            }
        }
        int orderSts = dataBean.getOrderSts();
        SceneInfoBean.DataBean.SceneOrderServiceResBean sceneOrderServiceRes = dataBean.getSceneOrderServiceRes();
        if (orderSts == 11) {
            mGrabTechnicianLayout.setVisibility(View.GONE);
            mGrabSceneOrderLayout.setVisibility(View.GONE);
            mGrabConfirmLayout.setVisibility(View.GONE);
            mGrabShangMenHF.setVisibility(View.GONE);
            mGrabWeiXiuHF.setVisibility(View.GONE);
        } else if (orderSts == 12) {
            mGrabOrderTime.setContentTextView(dataBean.getLastUpdatedTime());
            mGrabOrderTime.setDescTextView("取消时间");
            mGrabTechnicianLayout.setVisibility(View.GONE);
            mGrabSceneOrderLayout.setVisibility(View.GONE);
            mGrabConfirmLayout.setVisibility(View.GONE);
            mGrabShangMenHF.setVisibility(View.GONE);
            mGrabWeiXiuHF.setVisibility(View.GONE);
            mGrabSubmit.setVisibility(View.GONE);
            mGrabTwo.setVisibility(View.GONE);
        } else if (orderSts == 13) {
            mTechnicianList.clear();
            mGrabTechnicianContent.setText(dataBean.getSceneOrderTechnicianRes().getDescribes());
            mTechnicianList.addAll(dataBean.getSceneOrderTechnicianRes().getDoorImageList());
            mGrabDaoDaTime.setVisibility(View.VISIBLE);
            mGrabDaoDaTime.setContentTextView(dataBean.getSceneOrderTechnicianRes().getCreatedTime());
            mTechnicianSrcAdapter.notifyDataSetChanged();
            mGrabPayTime.setContentTextView(dataBean.getPayDate());
            mGrabPayAmount.setContentTextView("¥" + dataBean.getTotalAmount());
            mGrabQrDaoDaTime.setVisibility(View.VISIBLE);
            mGrabQrDaoDaTime.setContentTextView(dataBean.getGrabUpdateTime());
            mGrabPayType.setContentTextView(dataBean.getPayType() == 0 ? "微信" : "支付宝");
            mGrabFaultTechnicianDesc.setText(sceneOrderServiceRes.getFaultDescX());
            mGrabFaultTechnicianSolve.setText(sceneOrderServiceRes.getSolution());
            ArrayList<String> imageList = sceneOrderServiceRes.getImageList();
            mWeiXiuList.addAll(imageList);
            mWeiXiuSrcAdapter.notifyDataSetChanged();
            mGrabInspectDesc.setText(sceneOrderServiceRes.getCheckData());
            mGrabWeiXiuTime.setVisibility(View.VISIBLE);
            mGrabWeiXiuTime.setContentTextView(sceneOrderServiceRes.getCreatedTimeX());
            mGrabBasicAmount.setContentTextView("¥" + sceneOrderServiceRes.getBasicInspectAmount());
            mGrabRepairAmount.setContentTextView("¥" + sceneOrderServiceRes.getRepairAmount());
            mGrabOtherAmount.setContentTextView("¥" + sceneOrderServiceRes.getOtherAmount());
            mGrabAllAmount.setContentTextView("¥" + sceneOrderServiceRes.getTotalAmountX());
            mGrabQrWeiXiuTime.setVisibility(View.VISIBLE);
            mGrabQrWeiXiuTime.setContentTextView(dataBean.getLastUpdatedTime());
            mGrabQrWeiXiuTime.setDescTextView("取消维修方案时间");
            mGrabConfirmLayout.setVisibility(View.GONE);
        } else {
            if (orderSts >= 2 && dataBean.getSceneOrderTechnicianUuid() != null) {
                mTechnicianList.clear();
                mGrabTechnicianContent.setText(dataBean.getSceneOrderTechnicianRes().getDescribes());
                mTechnicianList.addAll(dataBean.getSceneOrderTechnicianRes().getDoorImageList());
                mGrabDaoDaTime.setVisibility(View.VISIBLE);
                mGrabPayTime.setContentTextView(dataBean.getPayDate());
                mGrabPayAmount.setContentTextView("¥" + dataBean.getTotalAmount());
                mGrabPayType.setContentTextView(dataBean.getPayType() == 0 ? "微信" : "支付宝");
                mGrabDaoDaTime.setContentTextView(dataBean.getSceneOrderTechnicianRes().getCreatedTime());
                mTechnicianSrcAdapter.notifyDataSetChanged();
            }
            if (orderSts >= 3) {
                mGrabQrDaoDaTime.setVisibility(View.VISIBLE);
                mGrabQrDaoDaTime.setContentTextView(dataBean.getGrabUpdateTime());
            }
            if (orderSts >= 4) {
                mGrabFaultTechnicianDesc.setText(sceneOrderServiceRes.getFaultDescX());
                mGrabFaultTechnicianSolve.setText(sceneOrderServiceRes.getSolution());
                ArrayList<String> imageList = sceneOrderServiceRes.getImageList();
                mWeiXiuList.addAll(imageList);
                mWeiXiuSrcAdapter.notifyDataSetChanged();
                mGrabInspectDesc.setText(sceneOrderServiceRes.getCheckData());
                mGrabWeiXiuTime.setVisibility(View.VISIBLE);
                mGrabWeiXiuTime.setContentTextView(sceneOrderServiceRes.getCreatedTimeX());
                mGrabBasicAmount.setContentTextView("¥" + sceneOrderServiceRes.getBasicInspectAmount());
                mGrabRepairAmount.setContentTextView("¥" + sceneOrderServiceRes.getRepairAmount());
                mGrabOtherAmount.setContentTextView("¥" + sceneOrderServiceRes.getOtherAmount());
                mGrabAllAmount.setContentTextView("¥" + sceneOrderServiceRes.getTotalAmountX());
            }
            if (orderSts >= 5) {
                mGrabQrWeiXiuTime.setVisibility(View.VISIBLE);
                mGrabQrWeiXiuTime.setContentTextView(sceneOrderServiceRes.getLastUpdatedTime());
            }
            if (orderSts >= 6) {
                mGrabConfirmContent.setText(dataBean.getSceneOrderTechnicianRes().getRepairSummary());
                mGrabList.addAll(dataBean.getSceneOrderTechnicianRes().getEndImageList());
                mGrabSrcAdapter.notifyDataSetChanged();
                mGrabWangGongTime.setVisibility(View.VISIBLE);
                mGrabWangGongTime.setContentTextView(dataBean.getSceneOrderTechnicianRes().getLastUpdatedTime());
            }
            if (orderSts >= 7) {
                mGrabQrWangGongTime.setVisibility(View.VISIBLE);
                mGrabQrWangGongTime.setContentTextView(dataBean.getLastUpdatedTime());
            }
        }
    }

    private void telTechnician(String technicianMobile) {
        Intent intent = new Intent();
        //设置动作
        intent.setAction(Intent.ACTION_CALL);
        //设置数据
        intent.setData(Uri.parse("tel:" + technicianMobile));
        startActivity(intent);
    }

    @Subscribe
    public void eventRefresh(GrabEvent grabEvent) {
        EventBus.getDefault().post(new SupportEvent());
        mPresenter.getSceneOrderDetail(mGrabUuid);
    }

    private void createAlertDialog(String msg, AlertCallBack alertCallBack) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(msg)
                .setPositiveButton("确认", (dialog, which) -> alertCallBack.callBack())
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 检查权限
     *
     * @param perStatus
     * @param permission
     */
    private void checkPermission(int perStatus, String... permission) {
        if (perStatus == PER_CALL_PHONE) {
            if (EasyPermissions.hasPermissions(getBaseContext(), permission)) {
                telTechnician(technicianMobile);
            } else {
                EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, PER_CALL_PHONE, permission).build());
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PER_CALL_PHONE) {
            telTechnician(technicianMobile);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        ToastUtils.showShort("请给予权限");
    }

    interface AlertCallBack {
        void callBack();
    }
}
