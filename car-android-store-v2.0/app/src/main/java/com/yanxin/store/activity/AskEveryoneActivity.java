package com.yanxin.store.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.PicAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.FiltrateBean;
import com.yanxin.store.bean.TechnicianAnswerBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.contract.AskEveryoneContract;
import com.yanxin.store.entity.PicEntity;
import com.yanxin.store.presenter.AskEveryonePresenter;
import com.yanxin.store.req.AskExpertReq;
import com.yanxin.store.ui.FiltrateWindow;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasePOPWindow;
import com.yanxin.store.utils.BasicUtils;
import com.yanxin.store.utils.GlideEngine;
import com.yanxin.store.utils.WeiboDialogUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.yanxin.store.commont.Constant.AppConfig.BRAND_PARENT;
import static com.yanxin.store.commont.Constant.AppConfig.TECHNICIAN_TYPE;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_BRAND;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_MODEL;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_TECHNICIAN;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_CAMERA;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE;


@XmlLayoutResId(contentId = R.layout.activity_ask_everyone)
public class AskEveryoneActivity extends BaseMvpActivity<AskEveryonePresenter> implements AskEveryoneContract.AskEveryoneView, EasyPermissions.PermissionCallbacks {
    private boolean isZhuanJia;  //是否是专家页面，是的话上面要展示专家信息
    private TechnicianAnswerBean.DataBean mDataBean;
    private TextView mAskTitle;
    private RelativeLayout mExpertInfoLayout;
    private android.widget.ImageView mExpertAvatar;
    private TextView mExpertName;
    private TextView mExpertScore;
    private TextView mExpertScoreNum;
    private TextView mExpertPrice;
    private TextView mExpertType;
    private TextView mExpertGt;
    private TextView mAskHint;
    private LinearLayout mAskLayout;
    private TextView mAskType;
    private TextView mAskBrand;
    private TextView mAskModel;
    private Button mSubmit;
    private OptionalEditLayout mDescTitle;
    private EditText mDescContent;
    private PicAdapter mPicAdapter;
    private RecyclerView mDescSrcRv;
    private File cameraFile;
    private PopupWindow mPopWindow;
    private ArrayList<PicEntity> mPicEntity;
    private ArrayList<FiltrateBean> mBrandBean;
    private ArrayList<FiltrateBean> mModelBean;
    private ArrayList<FiltrateBean> mTypeBean;
    private boolean isPicDefault;
    private Dialog loadDialogView;
    private PicEntity mCurrentPicEntity;
    private int mCurrentPosition;
    private AskExpertReq mAskExpertReq;
    private FiltrateWindow mFiltrateWindow;
    private String queryBrandUuid;
    private String queryModelUuid;
    private String technicalTypeUuid;

    @Override
    protected void initMVPData() {
        addDefaultStorePic();
        mPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            KeyboardUtils.hideSoftInput(this);
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
        mAskBrand.setOnClickListener(v -> {
            if (mBrandBean.size() == 0) {
                ToastUtils.showShort("正在获取品牌数据，请稍后");
            } else {
                showPOPWindow(FILTRATE_TYPE_BRAND, mAskBrand, mBrandBean);
            }
        });
        mAskModel.setOnClickListener(v -> {
            if (queryBrandUuid == null || queryBrandUuid.isEmpty()) {
                ToastUtils.showShort("请先选择车辆品牌");
            } else {
                showPOPWindow(FILTRATE_TYPE_MODEL, mAskModel, mModelBean);
            }
        });
        mAskType.setOnClickListener(v -> {
            if (mTypeBean.size() == 0) {
                ToastUtils.showShort("没有获取到技术数据");
            } else {
                showPOPWindow(mAskType, mTypeBean);
            }
        });
        mSubmit.setOnClickListener(v -> {
            mAskExpertReq.setConsultContent(BasicUtils.getEditValue(mDescContent));
            mAskExpertReq.setVehicleBrand(queryBrandUuid);
            if (queryModelUuid == null) {
                ToastUtils.showShort("请选择车型");
                return;
            }
            mAskExpertReq.setVehicleModel(queryModelUuid);
            ArrayList<String> imgs = new ArrayList<>();
            for (PicEntity picEntity : mPicEntity) {
                String path = picEntity.getPath();
                if (path != null && !path.isEmpty()) {
                    imgs.add(picEntity.getPath());
                }
            }
            mAskExpertReq.setConsultImgList(imgs);
            if (isZhuanJia) {
                mAskExpertReq.setTechnicianUuid(mDataBean.getUuid());
                mAskExpertReq.setTechnicalTypeUuid(mDataBean.getTechnicalTypeUuid());
            } else {
                if (technicalTypeUuid == null) {
                    ToastUtils.showShort("请选择技术类型");
                    return;
                } else {
                    mAskExpertReq.setTechnicalTypeUuid(technicalTypeUuid);
                }
            }
            mAskExpertReq.setConsultTitle(mDescTitle.getContentEditValue());
            mPresenter.submit(mAskExpertReq);
        });
        mPresenter.queryAllBrand(BRAND_PARENT);
        mPresenter.queryTechnicianTypeBean(TECHNICIAN_TYPE);
    }

    @Override
    protected void initMVPView() {
        mPicEntity = new ArrayList<>();
        mBrandBean = new ArrayList<>();
        mModelBean = new ArrayList<>();
        mTypeBean = new ArrayList<>();
        mAskExpertReq = new AskExpertReq();
        mAskTitle = findViewById(R.id.ask_title);
        mExpertInfoLayout = findViewById(R.id.expert_info_layout);
        mExpertAvatar = findViewById(R.id.expert_avatar);
        mExpertName = findViewById(R.id.expert_name);
        mExpertScore = findViewById(R.id.expert_score);
        mExpertScoreNum = findViewById(R.id.expert_score_num);
        mExpertPrice = findViewById(R.id.expert_price);
        mExpertType = findViewById(R.id.expert_type);
        mExpertGt = findViewById(R.id.expert_gt);
        mAskType = findViewById(R.id.ask_type);
        mAskBrand = findViewById(R.id.ask_brand);
        mAskModel = findViewById(R.id.ask_model);
        mDescTitle = findViewById(R.id.desc_title);
        mDescContent = findViewById(R.id.desc_content);
        mDescSrcRv = findViewById(R.id.desc_src_rv);
        mAskHint = findViewById(R.id.ask_hint);
        mAskLayout = findViewById(R.id.ask_type_layout);
        mSubmit = findViewById(R.id.submit);
        mPicAdapter = new PicAdapter(R.layout.include_store_register_pic, mPicEntity);
        isZhuanJia = getIntent().getBooleanExtra("is_zhuanjia", false);
        if (isZhuanJia) {
            mDataBean = (TechnicianAnswerBean.DataBean) getIntent().getSerializableExtra("data");
            mExpertInfoLayout.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.case_default_icon))
                    .load(mDataBean.getPhotoImgUrl()).into(mExpertAvatar);

            mExpertName.setText(mDataBean.getName());
            mExpertScoreNum.setText(mDataBean.getScore());
            mExpertPrice.setText("¥" + mDataBean.getAnswerAmt());
            SpanUtils.with(mExpertType)
                    .append(mDataBean.getTechnologyType())
                    .append("    ").append(mDataBean.getQaCount() + "")
                    .setForegroundColor(Color.parseColor("#0091FF"))
                    .append("次咨询").create();
            StringBuffer sb = new StringBuffer();
            ArrayList<TechnicianAnswerBean.DataBean.BrandResListBean> brandResArrayList = mDataBean.getBrandResList();
            for (TechnicianAnswerBean.DataBean.BrandResListBean dataBean : brandResArrayList) {
                sb.append(dataBean.getBrandName() + "/");
            }
            String brandName = sb.deleteCharAt(sb.length() - 1).toString();
            SpanUtils.with(mExpertGt)
                    .append("擅长：")
                    .append(brandName)
                    .create();
            mAskHint.setText("提问后等待技师回答，在“我的—我的提问”查看");
            mAskTitle.setText("提问让专家技师回答");
            mAskLayout.setVisibility(View.GONE);
        } else {
            mExpertInfoLayout.setVisibility(View.GONE);
            mAskHint.setText("提交提问后需要后台审核，审核通过后专家开始抢答作答，若未通过审核，将驳回，可重新编辑提交。");
            mAskTitle.setText("提问让全国技师抢答");
        }
        mDescSrcRv.setAdapter(mPicAdapter);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
        WeiboDialogUtils.closeDialog(loadDialogView);
    }

    @Override
    public BasePresenter initPresenter() {
        return AskEveryonePresenter.newInstance();
    }

    private void addDefaultStorePic() {
        PicEntity picEntity = new PicEntity();
        picEntity.setDefault(true);
        mPicEntity.add(picEntity);
        isPicDefault = true;
    }

    private void createPopWindow() {
        if (mPopWindow == null || !mPopWindow.isShowing()) {
            mPopWindow = BasePOPWindow.createPopWindow(AskEveryoneActivity.this, popView -> {
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
            mPopWindow.showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 检查权限
     *
     * @param perStatus
     * @param permission
     */
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
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PER_CAMERA) {
            startCamera();
        } else if (requestCode == PER_WRITE_EXTERNAL_STORAGE) {
            startPicker();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        ToastUtils.showShort("不给予权限无法进行图片上传");
    }


    @Override
    public void submitSuccess(String uuid) {
        MyApplication.setPayOrderUuid(uuid);
        MyApplication.setPayStatusType(1);
        Intent intent = new Intent(getBaseContext(), PayActivity.class);
        intent.putExtra("order_uuid", uuid);
        startActivity(intent);
    }

    @Override
    public void submitFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void uploadFileFailed(String msg) {
        WeiboDialogUtils.closeDialog(loadDialogView);
    }

    @Override
    public void uploadFileSuccess(String path) {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PER_CAMERA) {
            if (resultCode == Activity.RESULT_OK && cameraFile != null) {
                mPresenter.uploadFile(cameraFile);
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
                mPresenter.uploadFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "上传中...");
            }
        }
    }

    @Override
    public void queryTechnicianTypeSuccess(ArrayList<TechnicianTypeBean.DataBean> brandBean) {
        for (TechnicianTypeBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getLableDesc());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_TECHNICIAN);
            mTypeBean.add(filtrateBean);
        }
    }

    @Override
    public void queryTechnicianTypeFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void queryAllBrandSuccess(ArrayList<BrandBean.DataBean> brandBean) {
        for (BrandBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getConfigName());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_BRAND);
            mBrandBean.add(filtrateBean);
        }
    }

    @Override
    public void queryAllBrandFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean) {
        mModelBean.clear();
        for (BrandBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getConfigName());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_MODEL);
            mModelBean.add(filtrateBean);
        }
    }

    @Override
    public void queryAllModelFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void showPOPWindow(int tag, View view, ArrayList<FiltrateBean> beans) {
        mFiltrateWindow = FiltrateWindow.newBuilder()
                .setWidth((int) getResources().getDimension(R.dimen.popup_window_filtrate_width))
                .setHeight((int) getResources().getDimension(R.dimen.popup_window_filtrate_height))
                .setOnArithmeticList(beans)
                .setOnDismissListener(() -> mFiltrateWindow = null)
                .setOnSelectFiltrateListener(filtrateBean -> {
                    if (tag == FILTRATE_TYPE_BRAND) {
                        queryBrandUuid = filtrateBean.getUuid();
                        mModelBean.clear();
                        mPresenter.queryAllModel(queryBrandUuid);
                        queryModelUuid = null;
                        mAskBrand.setText(filtrateBean.getName());
                        mAskModel.setText("");
                    } else if (tag == FILTRATE_TYPE_MODEL) {
                        queryModelUuid = filtrateBean.getUuid();
                        mAskModel.setText(filtrateBean.getName());
                    }
                })
                .build(AskEveryoneActivity.this);
        mFiltrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }

    private void showPOPWindow(View view, ArrayList<FiltrateBean> beans) {
        mFiltrateWindow = FiltrateWindow.newBuilder()
                .setWidth((int) getResources().getDimension(R.dimen.quiz_dialog_filtrate_width))
                .setHeight((int) getResources().getDimension(R.dimen.quiz_dialog_filtrate_height))
                .setOnArithmeticList(beans)
                .setOnDismissListener(() -> {
                    mFiltrateWindow = null;
                })
                .setOnSelectFiltrateListener(filtrateBean -> {
                    technicalTypeUuid = filtrateBean.getUuid();
                    mAskType.setText(filtrateBean.getName());
                })
                .build(AskEveryoneActivity.this);
        mFiltrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }
}
