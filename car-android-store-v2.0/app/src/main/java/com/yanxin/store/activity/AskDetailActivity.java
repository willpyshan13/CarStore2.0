package com.yanxin.store.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.PicAdapter;
import com.yanxin.store.adapter.rvadapter.SrcAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.AskOrderDetailBean;
import com.yanxin.store.bean.RushOrderDetailBean;
import com.yanxin.store.contract.AskDetailContract;
import com.yanxin.store.entity.PicEntity;
import com.yanxin.store.event.FQEvent;
import com.yanxin.store.presenter.AskDetailPresenter;
import com.yanxin.store.req.ReplyRushReq;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasePOPWindow;
import com.yanxin.store.utils.BasicUtils;
import com.yanxin.store.utils.GlideEngine;
import com.yanxin.store.utils.RealPathFromUriUtils;
import com.yanxin.store.utils.WeiboDialogUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.yanxin.store.commont.Constant.PermissionStatus.PER_CAMERA;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE;


@XmlLayoutResId(contentId = R.layout.activity_ask_detail)
public class AskDetailActivity extends BaseMvpActivity<AskDetailPresenter> implements AskDetailContract.AskDetailView, EasyPermissions.PermissionCallbacks {
    private int enterColor = Color.parseColor("#0091FF");
    private int processColor = Color.parseColor("#F7B500");
    private int cancelColor = Color.parseColor("#999999");
    private int waitColor = Color.parseColor("#FDC920");
    private int errorColor = Color.parseColor("#FF0000");
    private TextView mAskStatus;
    private ImageView mAskMore;
    private TextView mAskTitle;
    private TextView mAskPrice;
    private TextView mAskHint;
    private TextView mAskContent;
    private RecyclerView mAskRv;
    private OptionalEditLayout mAskOrderNum;
    private OptionalEditLayout mAskCreateTime;
    private OptionalEditLayout mAskOrderPrice;
    private OptionalEditLayout mAskPayType;
    private OptionalEditLayout mAskUpdateTime;
    private OptionalEditLayout mAskUserName;
    private TextView mAskUserNameLayout;
    private TextView mAskSubmit;
    private TextView mAskCancel;
    private SrcAdapter mSrcAdapter;
    private ArrayList<String> mSrcList;
    private ArrayList<PicEntity> mPicEntity;
    private PicAdapter mPicAdapter;
    private boolean isPicDefault;
    private PicEntity mCurrentPicEntity;
    private int mCurrentPosition;
    private PopupWindow mPopWindow;
    private File cameraFile;
    private Dialog loadDialogView;
    private AlertDialog alertDialog;
    private LinearLayout mProtocolLayout;
    private String orderUuid;
    private String rushUuid;
    private boolean isOrder;
    private boolean isGrab;  //是不是抢的
    private boolean isBegMe;  //是不是向我提问的
    private boolean isPosted;  //是不是我发布的


    @Override
    protected void initMVPData() {
        mPresenter.queryRushDetail(isGrab ? rushUuid : orderUuid);
        mAskMore.setOnClickListener(v ->
                startActivity(new Intent(getBaseContext(), RushDetailActivity.class)
                        .putExtra("order_uuid", isGrab ? rushUuid : orderUuid)
                        .putExtra("is_order", isOrder)));
        mPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            KeyboardUtils.hideSoftInput(view);
            mCurrentPicEntity = mPicEntity.get(position);
            mCurrentPosition = position;
            switch (view.getId()) {
                case R.id.pic_layout:
                    createPopWindow();
                    break;
                case R.id.business_pic_clear:
                    mPicEntity.remove(position);
                    if (!isPicDefault) {
                        addDefaultStorePic();
                    }
                    mPicAdapter.notifyDataSetChanged();
                    break;
            }
        });
    }

    @Override
    protected void initMVPView() {
        orderUuid = getIntent().getStringExtra("order_uuid");
        rushUuid = getIntent().getStringExtra("rush_uuid");
        isGrab = getIntent().getBooleanExtra("is_grab", false);
        isBegMe = getIntent().getBooleanExtra("is_beg_me", false);
        isPosted = getIntent().getBooleanExtra("is_posted", false);
        isOrder = getIntent().getBooleanExtra("is_order", false);
        mSrcList = new ArrayList<>();
        mPicEntity = new ArrayList<>();
        addDefaultStorePic();
        mAskStatus = findViewById(R.id.ask_status);
        mAskMore = findViewById(R.id.ask_more);
        mAskTitle = findViewById(R.id.ask_title);
        mAskPrice = findViewById(R.id.ask_price);
        mAskContent = findViewById(R.id.ask_content);
        mAskRv = findViewById(R.id.ask_rv);
        mAskOrderNum = findViewById(R.id.ask_order_num);
        mAskCreateTime = findViewById(R.id.ask_create_time);
        mAskHint = findViewById(R.id.ask_failed_hint);
        mAskOrderPrice = findViewById(R.id.ask_order_price);
        mAskPayType = findViewById(R.id.ask_pay_type);
        mAskUpdateTime = findViewById(R.id.ask_update_time);
        mAskUserName = findViewById(R.id.ask_user_name);
        mAskUserNameLayout = findViewById(R.id.ask_user_name_layout);
        mAskSubmit = findViewById(R.id.ask_submit);
        mAskCancel = findViewById(R.id.ask_cancel);
        mSrcAdapter = new SrcAdapter(R.layout.item_content_desc_img, mSrcList);
        mPicAdapter = new PicAdapter(R.layout.include_store_register_pic, mPicEntity);
        mAskRv.setAdapter(mSrcAdapter);
    }

    private void addDefaultStorePic() {
        PicEntity picEntity = new PicEntity();
        picEntity.setDefault(true);
        mPicEntity.add(picEntity);
        isPicDefault = true;
    }

    private void createReplyDialog() {
        mProtocolLayout = (LinearLayout) LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_reply_rush, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertDialog = builder.setView(mProtocolLayout).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        if (isGrab) {
            mProtocolLayout.findViewById(R.id.hint_layout).setVisibility(View.VISIBLE);
        } else {
            mProtocolLayout.findViewById(R.id.hint_layout).setVisibility(View.GONE);
        }
        mProtocolLayout.findViewById(R.id.dialog_dismiss).setOnClickListener(v -> alertDialog.dismiss());
        RecyclerView mRv = mProtocolLayout.findViewById(R.id.layout_rv);
        EditText mEditView = mProtocolLayout.findViewById(R.id.layout_content);
        Button mReplySubmit = mProtocolLayout.findViewById(R.id.layout_reply);
        mRv.setAdapter(mPicAdapter);
        mPicAdapter.notifyDataSetChanged();
        mReplySubmit.setOnClickListener(v -> {
                    ReplyRushReq replyRushReq = new ReplyRushReq();
                    if (isGrab) {
                        replyRushReq.setOrderUuid(rushUuid);
                    } else {
                        replyRushReq.setOrderUuid(orderUuid);
                    }
                    replyRushReq.setAnswerContent(BasicUtils.getEditValue(mEditView));
                    ArrayList<String> mPicList = new ArrayList<>();
                    for (PicEntity picEntity : mPicEntity) {
                        if (!picEntity.isDefault()) {
                            mPicList.add(picEntity.getPath());
                        }
                    }
                    replyRushReq.setAnswerImgList(mPicList);
                    mPresenter.replyRush(replyRushReq);
                }
        );
        alertDialog.show();
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
        return AskDetailPresenter.newInstance();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void queryAskDetail(AskOrderDetailBean.DataBean dataBean) {
        String technicianUuid = dataBean.getTechnicianUuid();
        if (technicianUuid != null && technicianUuid.equals(MyApplication.getUserUuid())) {
            switch (dataBean.getOrderSts()) {
                case 1:
                    if (dataBean.getAnswerSts() == 0) {
                        mAskStatus.setTextColor(waitColor);
                        mAskStatus.setText("待答复");
                        mAskSubmit.setText("回答");
                        mAskCancel.setText("拒绝");
                        mAskSubmit.setOnClickListener(v -> createReplyDialog());
                        mAskCancel.setOnClickListener(v -> {
                            cancelOrderAlert(dataBean.getConsultUuid());
                        });
                        if (!isBegMe) {
                            mAskCancel.setVisibility(View.GONE);
                        }
                    } else {
                        if (dataBean.getAnswerCheckSts() == 0) {
                            mAskStatus.setTextColor(waitColor);
                            mAskStatus.setText("回答待审核");
                        } else {
                            mAskStatus.setTextColor(enterColor);
                            mAskStatus.setText("已答复");
                        }
                    }
                    break;
                case 2:
                    mAskStatus.setTextColor(cancelColor);
                    mAskStatus.setText("已取消");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
                case 3:
                    mAskStatus.setTextColor(waitColor);
                    mAskStatus.setText("退款中");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
                case 4:
                    mAskStatus.setTextColor(enterColor);
                    mAskStatus.setText("退款成功");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
                case 5:
                    mAskStatus.setTextColor(errorColor);
                    mAskStatus.setText("退款失败");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
                case 6:
                    mAskStatus.setTextColor(enterColor);
                    mAskStatus.setText("已完成");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
            }
            mAskUserName.setVisibility(View.VISIBLE);
            mAskUserName.setContentTextView(dataBean.getTechnicianName());
            mAskUserNameLayout.setVisibility(View.VISIBLE);
        } else {
            switch (dataBean.getOrderSts()) {
                case 0:
                    if (isPosted) {
                        mAskStatus.setText("待支付");
                        mAskSubmit.setVisibility(View.VISIBLE);
                        mAskSubmit.setText("去支付");
                        mAskSubmit.setOnClickListener(v -> {
                            MyApplication.setPayOrderUuid(dataBean.getUuid());
                            MyApplication.setPayStatusType(1);
                            String uuidX = dataBean.getUuid();
                            Intent intent = new Intent(getBaseContext(), PayActivity.class);
                            intent.putExtra("order_uuid", uuidX);
                            startActivity(intent);
                        });
                        mAskCancel.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    switch (dataBean.getConsultCheckSts()) {
                        case 0:
                            mAskStatus.setText("待审核");
                            mAskCancel.setVisibility(View.GONE);
                            mAskSubmit.setVisibility(View.GONE);
                            break;
                        case 1:
                            if (technicianUuid == null) {
                                mAskStatus.setText("待抢单");
                                mAskSubmit.setText("抢答");
                                mAskCancel.setText("拒绝");
                                mAskStatus.setTextColor(waitColor);
                                mAskSubmit.setOnClickListener(v -> mPresenter.grabRushOrder(dataBean.getConsultUuid()));
                                mAskCancel.setVisibility(View.GONE);
                            } else {
                                if (dataBean.getAnswerSts() == 0) {
                                    mAskStatus.setTextColor(waitColor);
                                    mAskStatus.setText("待答复");
//                                    mAskSubmit.setText("回答");
//                                    mAskCancel.setText("拒绝");
//                                    mAskSubmit.setOnClickListener(v -> createReplyDialog());
//                                    mAskCancel.setOnClickListener(v -> {
//                                        cancelOrderAlert(dataBean.getConsultUuid());
//                                    });
                                } else {
                                    if (dataBean.getAnswerCheckSts() == 0) {
                                        mAskStatus.setTextColor(waitColor);
                                        mAskStatus.setText("待答复");
                                    } else {
                                        mAskStatus.setTextColor(enterColor);
                                        mAskStatus.setText("已答复");
                                    }
                                }
                                mAskCancel.setVisibility(View.GONE);
                                mAskSubmit.setVisibility(View.GONE);
                            }
                            break;
                        case 2:
                            mAskStatus.setText("审核驳回");
                            mAskCancel.setVisibility(View.GONE);
                            mAskSubmit.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case 2:
                    mAskStatus.setTextColor(cancelColor);
                    mAskStatus.setText("已取消");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
                case 3:
                    mAskStatus.setTextColor(waitColor);
                    mAskStatus.setText("退款中");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
                case 4:
                    mAskStatus.setTextColor(enterColor);
                    mAskStatus.setText("退款成功");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
                case 5:
                    mAskStatus.setTextColor(errorColor);
                    mAskStatus.setText("退款失败");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
                case 6:
                    mAskStatus.setTextColor(enterColor);
                    mAskStatus.setText("已完成");
                    mAskCancel.setVisibility(View.GONE);
                    mAskSubmit.setVisibility(View.GONE);
                    break;
            }
        }
//        if (dataBean.getOrderSts() == 1) {
//            if (technicianUuid != null && technicianUuid.equals(MyApplication.getUserUuid())) {//我的单子
//                mAskStatus.setText("待接单");
//                if (consultRes.getAnswerSts() == 0) {
//                    if (consultRes.getConsultType() == 1) {
//                        mAskStatus.setText("待回答");
//                        mAskSubmit.setText("回答");
//                        mAskCancel.setText("拒绝");
//                        mAskSubmit.setOnClickListener(v -> createReplyDialog());
//                        mAskCancel.setOnClickListener(v -> {
//                            cancelOrderAlert(consultRes.getUuid());
//                        });
//                    } else {
//                        mAskStatus.setText("待答复");
//                        mAskSubmit.setText("回答");
//                        mAskCancel.setVisibility(View.GONE);
//                        if (isPush) {
//                            mAskSubmit.setVisibility(View.GONE);
//                        }
//                        mAskSubmit.setOnClickListener(v -> createReplyDialog());
//                    }
//
//                } else {
//                    mAskSubmit.setVisibility(View.GONE);
//                    mAskCancel.setVisibility(View.GONE);
//                    switch (dataBean.getAnswerCheckSts()) {
//                        case 0:
//                            mAskStatus.setText("回答待审核");
//                            mAskStatus.setTextColor(processColor);
//                            break;
//                        case 1:
//                            mAskStatus.setText("问答订单完成");
//                            mAskStatus.setTextColor(enterColor);
//                            break;
//                        case 2:
//                            mAskStatus.setText("审核驳回, 已取消");
//                            mAskStatus.setTextColor(errorColor);
//                            mAskHint.setText("驳回理由:");
//                            break;
//                    }
//                }
//            } else {
//                if (technicianUuid == null) {
//                    mAskStatus.setText("待抢答");
//                    mAskCancel.setVisibility(View.GONE);
//                    mAskSubmit.setVisibility(View.VISIBLE);
//                    mAskSubmit.setText("抢答");
//                    if (isPush) {
//                        mAskSubmit.setVisibility(View.GONE);
//                    }
//                    mAskSubmit.setOnClickListener(v -> mPresenter.grabRushOrder(dataBean.getUuid()));
//                } else {
//                    mAskStatus.setText("待答复");
//                    mAskStatus.setTextColor(processColor);
//                    mAskSubmit.setVisibility(View.GONE);
//                    mAskCancel.setVisibility(View.GONE);
//                }
//            }
//        } else if (dataBean.getOrderSts() == 2) {
//            errOrderText("已取消");
//        } else if (dataBean.getOrderSts() == 3) {
//            errOrderText("退款中");
//        } else if (dataBean.getOrderSts() == 4) {
//            errOrderText("退款成功");
//        } else if (dataBean.getOrderSts() == 5) {
//            errOrderText("退款失败");
//        } else if (dataBean.getOrderSts() == 6) {
//            mAskSubmit.setVisibility(View.GONE);
//            mAskCancel.setVisibility(View.GONE);
//            mAskStatus.setText("订单完成");
//            mAskStatus.setTextColor(enterColor);
//        }
        mAskTitle.setText(dataBean.getTitle());
        mAskPrice.setText("¥" + dataBean.getOrderAmount());
        mAskContent.setText(dataBean.getConsultDesc());
        mSrcList.clear();
        ArrayList<String> consultImgList = dataBean.getConsultImgUrlList();
        if (consultImgList != null) {
            int size = Math.min(consultImgList.size(), 3);
            for (int i = 0; i < size; i++) {
                mSrcList.add(consultImgList.get(i));
            }
        }
        mSrcAdapter.notifyDataSetChanged();
        mAskOrderNum.setContentTextView(dataBean.getOrderNum());
        mAskCreateTime.setContentTextView(dataBean.getCreatedTime());
        mAskOrderPrice.setContentTextView("¥" + dataBean.getOrderAmount());
        mAskPayType.setContentTextView(dataBean.getPayType() == 0 ? "微信支付" : "支付宝支付");
        if (dataBean.getOrderSts() == 2) {
            mAskUpdateTime.setVisibility(View.VISIBLE);
            mAskUpdateTime.setDescTextView("取消时间:");

            mAskSubmit.setVisibility(View.GONE);
            mAskCancel.setVisibility(View.GONE);
        } else if (dataBean.getAnswerSts() == 1) {
            mAskUpdateTime.setVisibility(View.VISIBLE);
            mAskUpdateTime.setDescTextView("回答时间:");
            mAskUpdateTime.setContentTextView(dataBean.getAnswerTime());
        } else {
            mAskUpdateTime.setVisibility(View.GONE);
        }
    }

    private void errOrderText(String errText) {
        mAskSubmit.setVisibility(View.GONE);
        mAskCancel.setVisibility(View.GONE);
        mAskStatus.setText(errText);
        mAskStatus.setTextColor(errorColor);
    }

    private void cancelOrderAlert(String orderUuid) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定要拒绝这个提问吗?")
                .setPositiveButton("确定", (dialog, which) -> mPresenter.putAskOrderCancel(orderUuid))
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
    }

    @Override
    public void grabRushOrder(String uuid) {
        EventBus.getDefault().post(new FQEvent());
        mPresenter.queryRushDetail(isGrab ? rushUuid : orderUuid);
        createReplyDialog();
    }

    @Override
    public void putAskOrderCancel(String uuid) {
        ToastUtils.showShort("已拒绝");
        mPresenter.queryRushDetail(isGrab ? rushUuid : orderUuid);
    }

    @Override
    public void uploadImgFile(String path) {
        //最后一张的时候
        if (mCurrentPicEntity.isDefault() && mCurrentPosition >= 5) {
            mCurrentPicEntity.setDefault(false);
            mCurrentPicEntity.setPath(path);
            mCurrentPicEntity.setPicFile(cameraFile);
            isPicDefault = false;
        } else if (!mCurrentPicEntity.isDefault()) {
            mCurrentPicEntity.setDefault(false);
            mCurrentPicEntity.setPath(path);
            mCurrentPicEntity.setPicFile(cameraFile);
        } else {
            //正常添加
            PicEntity picEntity = new PicEntity();
            picEntity.setDefault(false);
            picEntity.setPath(path);
            picEntity.setPicFile(cameraFile);
            mPicEntity.add(mPicEntity.size() - 1, picEntity);
        }
        mPicAdapter.notifyDataSetChanged();
        WeiboDialogUtils.closeDialog(loadDialogView);
    }

    @Override
    public void replyRush(String msg) {
        ToastUtils.showShort("回答成功~");
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        mPresenter.queryRushDetail(isGrab ? rushUuid : orderUuid);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PER_CAMERA) {
            startCamera();
        } else if (requestCode == PER_WRITE_EXTERNAL_STORAGE) {
            startPicker();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }


    private void createPopWindow() {
        if (mPopWindow == null || !mPopWindow.isShowing()) {
            mPopWindow = BasePOPWindow.createCenterPopWindow(AskDetailActivity.this, popView -> {
                if (popView.getId() == R.id.photo_camera) {
                    /**
                     * 拍照选取图片
                     */
                    checkPermission(PER_CAMERA, Manifest.permission.CAMERA);
                } else if (popView.getId() == R.id.photo_picker) {
                    /**
                     * 相册选取图片
                     */
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        checkPermission(PER_WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                    } else {
                        checkPermission(PER_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }
                mPopWindow.dismiss();
            });
            mPopWindow.setAnimationStyle(R.style.PopWindowAnimStyle);
            mPopWindow.showAtLocation(mProtocolLayout, Gravity.CENTER, 0, 0);
        }
    }

    private void checkPermission(int perStatus, String... permission) {
        if (perStatus == PER_CAMERA) {
            if (EasyPermissions.hasPermissions(getBaseContext(), permission)) {
                startCamera();
            } else {
                EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, PER_CAMERA, permission).build());
            }
        } else if (perStatus == PER_WRITE_EXTERNAL_STORAGE) {
            if (EasyPermissions.hasPermissions(getBaseContext(), permission)) {
                startPicker();
            } else {
                EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, PER_WRITE_EXTERNAL_STORAGE, permission).build());
            }
        }
    }

    private void startPicker() {
        EasyPhotos.createAlbum(this, false, false, GlideEngine.getInstance())
                .setFileProviderAuthority(MyApplication.getApplication().getPackageName() + ".fileProvider")
                .complexSelector(true, 1, 1)//参数说明：是否只能选择单类型，视频数，图片数。
                .setVideoMaxSecond(10)
                .start(PER_WRITE_EXTERNAL_STORAGE);
    }

    private void startCamera() {
        //创建文件夹
        File file;
        file = getExternalFilesDir("apk");
        if (!file.getParentFile().exists()) {
            file.mkdir();
        }
        //用于保存调用相机拍照后所生成的文件
        cameraFile = new File(file.getAbsoluteFile() + "/car_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".jpg");
        //跳转到调用系统相机
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果在Android7.0以上,使用FileProvider获取Uri
            imageIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //***代表的是
            Uri contentUri = FileProvider.getUriForFile(getBaseContext(), MyApplication.getApplication().getPackageName() + ".fileProvider", cameraFile);
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            //否则使用Uri.fromFile(file)方法获取Uri
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        }
        startActivityForResult(imageIntent, PER_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PER_CAMERA) {
            if (resultCode == Activity.RESULT_OK && cameraFile != null) {
                mPresenter.uploadImgFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "上传中...");
            }
        } else if (requestCode == PER_WRITE_EXTERNAL_STORAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                Photo photo = resultPhotos.get(0);
                if (photo.size > 1024 * 1000 * 6) {
                    ToastUtils.showShort("文件过大,无法上传");
                    return;
                }
                cameraFile = new File(photo.path);
                ImageUtils.compressByQuality(BitmapFactory.decodeFile(photo.path), 50);
                mPresenter.uploadImgFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "上传中...");
            }
        }
    }
}
