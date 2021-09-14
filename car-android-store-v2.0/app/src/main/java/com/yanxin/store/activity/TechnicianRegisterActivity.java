package com.yanxin.store.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.POPRecyclerViewAdapter;
import com.yanxin.store.adapter.rvadapter.TechnicianTypeAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DepositBankBean;
import com.yanxin.store.bean.TechnicianDetailBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.contract.TechnicianContract;
import com.yanxin.store.presenter.TechnicianPresenter;
import com.yanxin.store.req.TechnicianRegisterReq;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasePOPWindow;
import com.yanxin.store.utils.BasicUtils;
import com.yanxin.store.utils.RealPathFromUriUtils;
import com.yanxin.store.utils.WeiboDialogUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.yanxin.store.commont.Constant.AppConfig.BRAND_PARENT;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.REGISTER_BRAND_CODE;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_CAMERA;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE;

@XmlLayoutResId(contentId = R.layout.activity_tecgnician_register)
public class TechnicianRegisterActivity extends BaseMvpActivity<TechnicianPresenter> implements TechnicianContract.TechnicianView, EasyPermissions.PermissionCallbacks {
    private LinearLayout mAuditLayout;
    private LinearLayout mAuditErrorLayout;
    private TextView mAuditErrorHint;
    private ImageView mAuditStatusIcon;
    private TextView mAuditStatusContent;
    private OptionalEditLayout mRegName;
    private OptionalEditLayout mRegMobile;
    private OptionalEditLayout mRegCardType;
    private EditText mRegCardNum;
    private ImageView mRegCardPositive;
    private ImageView mRegCardPositiveClear;
    private ImageView mPicCardNegative;
    private ImageView mPicCardNegativeClear;
    private OptionalEditLayout mRegCity;
    private EditText mRegAddress;
    private OptionalEditLayout mRegUrgentMobile;
    private OptionalEditLayout mRegYear;
    private OptionalEditLayout mRepairBrand;
    private OptionalEditLayout mTechnologyType;
    private ImageView mSkillsPic;
    private ImageView mSkillsPicClear;
    private ImageView mOemPic;
    private ImageView mOemPicClear;
    private ImageView mDriverPositivePic;
    private ImageView mDriverPositivePicClear;
    private ImageView mDriverNegativePic;
    private ImageView mDriverNegativePicClear;
    private ImageView mDianGongPositive;
    private ImageView mDianGongPositiveClear;
    private ImageView mDianGongNegative;
    private ImageView mDianGongNegativeClear;
    private String mCardPositivePath, mCardNegativePath, mSkillsPath, mOemPath, mDriverPositivePath, mDriverNegativePath, mDianGongPositivePath, mDianGongNegativePath;
    private OptionalEditLayout mReceivingAccount;
    private OptionalEditLayout mBankAccount;
    private OptionalEditLayout mWxAccount;
    private OptionalEditLayout mAlipayAccount;
    private Button mSubmit;
    private File cameraFile;
    private Dialog loadDialogView;
    private TechnicianRegisterReq mTechnicianRegisterReq;
    private String mTechnicianType;
    private PopupWindow mPopWindow;
    private boolean isLoadCitySuccess;
    private boolean isTechnicianSuccess;
    private TechnicianTypeAdapter mTypeAdapter;
    private POPRecyclerViewAdapter mPopAdapter;
    private OptionsPickerView mOptionsPickerView;
    private String companyAddressCity;
    private String companyAddressCounty;
    private String companyAddressProvince;
    private ArrayList<TechnicianTypeBean.DataBean> mTechnician;
    private boolean isBankSuccess;
    private boolean isLogin;  //是否登录未审核通过进来的
    private List<String> mBrandDetailList = new ArrayList<>();
    private String tUuid;
    private String electricianCertificateUrl;
    private String electricianCertificateBackUrl;

    private ArrayList<BrandBean.DataBean> mBrandList;
    private ArrayList<DepositBankBean.DataBean> mBankBean;

    private int PIC_FLAG;   //1:身份证正面  2:身份证反面  3:国家技能鉴定  4:主机厂  5:驾驶证正面  6：驾驶证反面, 7:电工证正面 8:电工证反面

    @Override
    protected void initMVPData() {
        initViewClickData();
    }

    private void initViewClickData() {
        mRepairBrand.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (mBrandList.size() == 0) {
                ToastUtils.showShort("没有获取到品牌数据");
            } else {

                Intent intent = new Intent(getBaseContext(), BrandActivity.class);
                intent.putExtra("brand_class", mBrandList);
                startActivityForResult(intent, REGISTER_BRAND_CODE);
            }
        });
        mRegCardPositive.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            PIC_FLAG = 1;
            createPopWindow();
        });
        mRegCardPositiveClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mCardPositivePath = null;
            mRegCardPositiveClear.setVisibility(View.INVISIBLE);
            mRegCardPositive.setImageResource(R.drawable.pic_card_upload_positive);
        });
        mPicCardNegative.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            PIC_FLAG = 2;
            createPopWindow();
        });
        mPicCardNegativeClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mCardNegativePath = null;
            mPicCardNegativeClear.setVisibility(View.INVISIBLE);
            mPicCardNegative.setImageResource(R.drawable.pic_card_upload_negative);
        });
        mSkillsPic.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            PIC_FLAG = 3;
            createPopWindow();
        });
        mSkillsPicClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mSkillsPath = null;
            mSkillsPicClear.setVisibility(View.INVISIBLE);
            mSkillsPic.setImageResource(R.drawable.pic_upload_pic);
        });
        mOemPic.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            PIC_FLAG = 4;
            createPopWindow();
        });
        mOemPicClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mOemPath = null;
            mOemPicClear.setVisibility(View.INVISIBLE);
            mOemPic.setImageResource(R.drawable.pic_upload_pic);
        });
        mDriverPositivePic.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            PIC_FLAG = 5;
            createPopWindow();
        });
        mDriverPositivePicClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mDriverPositivePicClear.setVisibility(View.INVISIBLE);
            mDriverPositivePic.setImageResource(R.drawable.pic_card_upload_positive);
        });
        mDriverNegativePic.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            PIC_FLAG = 6;
            createPopWindow();
        });
        mDriverNegativePicClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mDriverNegativePicClear.setVisibility(View.INVISIBLE);
            mDriverNegativePic.setImageResource(R.drawable.pic_card_upload_negative);
        });
        mDianGongPositive.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            PIC_FLAG = 7;
            createPopWindow();
        });
        mDianGongPositiveClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mDianGongPositiveClear.setVisibility(View.INVISIBLE);
            mDianGongPositive.setImageResource(R.drawable.pic_card_upload_positive);
        });
        mDianGongNegative.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            PIC_FLAG = 8;
            createPopWindow();
        });
        mDianGongNegativeClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mDianGongNegativeClear.setVisibility(View.INVISIBLE);
            mDianGongNegative.setImageResource(R.drawable.pic_card_upload_negative);
        });
        mRegCity.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isLoadCitySuccess) {
                mOptionsPickerView.show();
            } else {
                ToastUtils.showShort("地图数据正在加载，请稍后...");
            }
        });
        mTechnologyType.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isTechnicianSuccess) {
                if (mPopWindow == null || !mPopWindow.isShowing()) {
                    mPopWindow = BasePOPWindow.createRecyclerViewPop(TechnicianRegisterActivity.this, popView -> {
                        mPopWindow.dismiss();
                    });
                    RecyclerView mPopRecycler = mPopWindow.getContentView().findViewById(R.id.rv_item);
                    mPopRecycler.setAdapter(mTypeAdapter);
                    mTypeAdapter.notifyDataSetChanged();
                    mPopWindow.setAnimationStyle(R.style.PopWindowAnimStyle);
                    mPopWindow.showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0);
                }
            } else {
                ToastUtils.showShort("正在获取技术类型数据");
            }
        });
        mTypeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TechnicianTypeBean.DataBean dataBean = mTechnician.get(position);
            mTechnologyType.setContentTextView(dataBean.getLableDesc());
            mTechnicianType = dataBean.getUuid();
            mPopWindow.dismiss();
        });
        mPopAdapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            mPopWindow.dismiss();
            mBankAccount.setContentTextView(mBankBean.get(position).getLableDesc());
        });
        mBankAccount.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isBankSuccess) {
                if (mPopWindow == null || !mPopWindow.isShowing()) {
                    mPopWindow = BasePOPWindow.createRecyclerViewPop(TechnicianRegisterActivity.this, popView -> {
                        mPopWindow.dismiss();
                    });
                    RecyclerView mPopRecycler = mPopWindow.getContentView().findViewById(R.id.rv_item);
                    mPopRecycler.setAdapter(mPopAdapter);
                    mPopAdapter.notifyDataSetChanged();
                    mPopWindow.setAnimationStyle(R.style.PopWindowAnimStyle);
                    mPopWindow.showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0);
                }
            } else {
                ToastUtils.showShort("正在加载银行数据");
            }
        });
        mSubmit.setOnClickListener(v -> {
            if (checkValueIsEmpty()) {
                mTechnicianRegisterReq.setAddressCity(companyAddressCity);
                mTechnicianRegisterReq.setAddressCounty(companyAddressCounty);
                mTechnicianRegisterReq.setAddressProvince(companyAddressProvince);
                mTechnicianRegisterReq.setAddressDetail(mRegAddress.getText().toString().trim());
                mTechnicianRegisterReq.setDriverLicenseBackUrl(mDriverNegativePath);
                mTechnicianRegisterReq.setDriverLicenseUrl(mDriverPositivePath);
                ArrayList<String> hostList = new ArrayList<>();
                hostList.add(mSkillsPath);
                mTechnicianRegisterReq.setStateImgList(hostList);
                ArrayList<String> oemList = new ArrayList<>();
                oemList.add(mOemPath);
                mTechnicianRegisterReq.setUuid(tUuid);
                mTechnicianRegisterReq.setHostImgList(oemList);
                mTechnicianRegisterReq.setIdentityCardUrl(mCardPositivePath);
                mTechnicianRegisterReq.setIdentityCardBackUrl(mCardNegativePath);
                mTechnicianRegisterReq.setMobile(mRegMobile.getContentEditValue());
                mTechnicianRegisterReq.setUserName(mRegName.getContentEditValue());
                TechnicianRegisterReq.TechnicianAccountBean technicianAccountBean = new TechnicianRegisterReq.TechnicianAccountBean();
                technicianAccountBean.setWeChatAccount(mWxAccount.getContentEditValue());
                technicianAccountBean.setAlipayAccount(mAlipayAccount.getContentEditValue());
                technicianAccountBean.setCardNumbers(mReceivingAccount.getContentEditValue());
                technicianAccountBean.setDepositBank(mBankAccount.getContentTextValue());
                mTechnicianRegisterReq.setElectricianCertificateUrl(mDianGongPositivePath);
                mTechnicianRegisterReq.setElectricianCertificateBackUrl(mDianGongNegativePath);
                mTechnicianRegisterReq.setCertificateType("706");
                mTechnicianRegisterReq.setCertificateNum(BasicUtils.getEditValue(mRegCardNum));
                mTechnicianRegisterReq.setTechnicianAccount(technicianAccountBean);
                mTechnicianRegisterReq.setWorkingYear(Integer.parseInt(mRegYear.getContentEditValue()));
                mTechnicianRegisterReq.setTechnologyType(mTechnicianType);
                if(isLogin){
                    mPresenter.updateTechnician(getIntent().getStringExtra("user_token"),mTechnicianRegisterReq);
                }else{
                    mPresenter.registerTechnician(mTechnicianRegisterReq);
                }
            }
        });
    }

    private void createPopWindow() {
        if (mPopWindow == null || !mPopWindow.isShowing()) {
            mPopWindow = BasePOPWindow.createPopWindow(TechnicianRegisterActivity.this, popView -> {
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
        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, PER_WRITE_EXTERNAL_STORAGE);
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
    protected void initMVPView() {
        isLogin = getIntent().getBooleanExtra("is_login", false);
        initFindView();
        initArrayList();
        mTypeAdapter = new TechnicianTypeAdapter(R.layout.item_pop_recycler, mTechnician);
        mPopAdapter = new POPRecyclerViewAdapter(R.layout.item_pop_recycler, mBankBean);
        mPresenter.getDepositBank();
        mPresenter.queryCity();
        mPresenter.queryTechnicianType();
        mTechnicianRegisterReq = new TechnicianRegisterReq();
        if (isLogin) {
            mSubmit.setText("重新提交审核");
            mPresenter.getTechnicianDetail(getIntent().getStringExtra("user_token"));
        } else {
            mAuditLayout.setVisibility(View.GONE);
            mPresenter.queryBrand(BRAND_PARENT);
        }
    }

    private void initFindView() {
        mAuditLayout = findViewById(R.id.audit_layout);
        mAuditErrorLayout = findViewById(R.id.audit_error_layout);
        mAuditErrorHint = findViewById(R.id.audit_error_hint);
        mAuditStatusIcon = findViewById(R.id.audit_status_icon);
        mAuditStatusContent = findViewById(R.id.audit_status_content);
        mRegName = findViewById(R.id.reg_name);
        mRegCardType = findViewById(R.id.reg_card_type);
        mRegMobile = findViewById(R.id.reg_mobile);
        mRegCardNum = findViewById(R.id.reg_card_num);
        mRegCardPositive = findViewById(R.id.reg_card_positive);
        mRegCardPositiveClear = findViewById(R.id.reg_card_positive_clear);
        mPicCardNegative = findViewById(R.id.pic_card_negative);
        mPicCardNegativeClear = findViewById(R.id.pic_card_negative_clear);
        mRegCity = findViewById(R.id.reg_city);
        mRegAddress = findViewById(R.id.reg_address);
        mRegUrgentMobile = findViewById(R.id.reg_urgent_mobile);
        mRegYear = findViewById(R.id.reg_year);
        mRepairBrand = findViewById(R.id.repair_brand);
        mTechnologyType = findViewById(R.id.technology_type);
        mSkillsPic = findViewById(R.id.skills_pic);
        mSkillsPicClear = findViewById(R.id.skills_pic_clear);
        mOemPic = findViewById(R.id.oem_pic);
        mOemPicClear = findViewById(R.id.oem_pic_clear);
        mDriverPositivePic = findViewById(R.id.driver_positive_pic);
        mDriverPositivePicClear = findViewById(R.id.driver_positive_pic_clear);
        mDriverNegativePic = findViewById(R.id.driver_negative_pic);
        mDriverNegativePicClear = findViewById(R.id.driver_negative_pic_clear);
        mDianGongPositive = findViewById(R.id.diangong_positive_pic);
        mDianGongPositiveClear = findViewById(R.id.diangong_positive_pic_clear);
        mDianGongNegative = findViewById(R.id.diangong_negative_pic);
        mDianGongNegativeClear = findViewById(R.id.diangong_negative_pic_clear);
        mReceivingAccount = findViewById(R.id.receiving_account);
        mBankAccount = findViewById(R.id.bank_account);
        mWxAccount = findViewById(R.id.wx_account);
        mAlipayAccount = findViewById(R.id.alipay_account);
        mSubmit = findViewById(R.id.submit);
    }

    private boolean checkValueIsEmpty() {
        String mobileContentValue = mRegMobile.getContentEditValue();
        String nameContentValue = mRegName.getContentEditValue();
        String mRegCityValue = mRegCity.getContentTextValue();
        String mYearValue = mRegYear.getContentEditValue();
        String brandContentTextValue = mRepairBrand.getContentTextValue();
        String technologyContentTextValue = mTechnologyType.getContentTextValue();
        String receivingAccountValue = mReceivingAccount.getContentEditValue();
        String backAccountContentValue = mBankAccount.getContentTextValue();
        if (!RegexUtils.isMobileExact(mobileContentValue)) {
            ToastUtils.showShort("请输入正确的手机号");
            return false;
        }
        if (BasicUtils.strIsEmpty(nameContentValue)) {
            ToastUtils.showShort("请输入姓名");
            return false;
        }
        if (BasicUtils.strIsEmpty(mRegCityValue)) {
            ToastUtils.showShort("请选择所在地区");
            return false;
        }
        if (BasicUtils.strIsEmpty(mYearValue)) {
            ToastUtils.showShort("请输入工龄");
            return false;
        }
        if (BasicUtils.strIsEmpty(brandContentTextValue)) {
            ToastUtils.showShort("请选择维修品牌");
            return false;
        }
        if (BasicUtils.strIsEmpty(technologyContentTextValue)) {
            ToastUtils.showShort("请选择技术类型");
            return false;
        }
        if (BasicUtils.strIsEmpty(receivingAccountValue)) {
            ToastUtils.showShort("请输入收款账号");
            return false;
        }
        if (BasicUtils.strIsEmpty(backAccountContentValue)) {
            ToastUtils.showShort("请选择开户银行");
            return false;
        }
        return checkSelectValue();
    }

    private boolean checkSelectValue() {
        if (BasicUtils.strIsEmpty(mSkillsPath) && BasicUtils.strIsEmpty(mOemPath)) {
            ToastUtils.showShort("请上传国家资质鉴定证书或主机厂认证");
            return false;
        }
        return true;
    }

    private void initArrayList() {
        mBrandList = new ArrayList<>();
        mTechnician = new ArrayList<>();
        mBankBean = new ArrayList<>();
    }

    @Override
    public BasePresenter initPresenter() {
        return TechnicianPresenter.newInstance();
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
    public void getTechnicianDetail(TechnicianDetailBean.DataBean dataBean) {
        tUuid = dataBean.getUuid();
        mAuditLayout.setVisibility(View.VISIBLE);
        int auditType = dataBean.getCheckSts();
        if (auditType == 0) {
            mAuditStatusIcon.setImageResource(R.drawable.wait_icon);
            mAuditStatusContent.setText("待审核");
            mAuditErrorLayout.setVisibility(View.GONE);
        } else if (auditType == 2) {
            mAuditStatusIcon.setImageResource(R.drawable.audit_error);
            mAuditStatusContent.setText("审核未通过");
            mAuditErrorLayout.setVisibility(View.VISIBLE);
            mAuditErrorHint.setText(dataBean.getRejectDetail());
        }
        mRegName.setContentEditView(dataBean.getUserName());
        mRegMobile.setContentEditView(dataBean.getMobile());
        mRegCardNum.setText(dataBean.getCertificateNum());
        if (dataBean.getIdentityCardUrl() != null && !dataBean.getIdentityCardUrl().isEmpty()) {
            mCardPositivePath = dataBean.getIdentityCardUrl();
            mRegCardPositiveClear.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                    .load(mCardPositivePath).into(mRegCardPositive);
        }
        if (dataBean.getIdentityCardBackUrl() != null && !dataBean.getIdentityCardBackUrl().isEmpty()) {
            mCardNegativePath = dataBean.getIdentityCardBackUrl();
            mPicCardNegativeClear.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                    .load(mCardNegativePath).into(mPicCardNegative);
        }
        companyAddressCity = dataBean.getAddressCity();
        companyAddressCounty = dataBean.getAddressCounty();
        companyAddressProvince = dataBean.getAddressProvince();
        mRegCity.setContentTextView(dataBean.getAddressProvinceName() + dataBean.getAddressCityName() + dataBean.getAddressCountyName());
        mRegAddress.setText(dataBean.getAddressDetail());
//        mRepairBrand.setContentEditView(dataBean);
        mTechnologyType.setContentTextView(dataBean.getTechnologyType());
        if (dataBean.getHostImgList() != null) {
            mSkillsPath = dataBean.getHostImgList().get(0);
            mSkillsPicClear.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                    .load(mSkillsPath).into(mSkillsPic);
        }
        if (dataBean.getStateImgList() != null) {
            mOemPath = dataBean.getStateImgList().get(0);
            mOemPicClear.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                    .load(mOemPath).into(mOemPic);
        }
        if (dataBean.getDriverLicenseUrl() != null && !dataBean.getDriverLicenseUrl().isEmpty()) {
            mDriverPositivePath = dataBean.getDriverLicenseUrl();
            mDriverPositivePicClear.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                    .load(mDriverPositivePath).into(mDriverPositivePic);
        }
        if (dataBean.getIdentityCardUrl() != null && !dataBean.getIdentityCardUrl().isEmpty()) {
            mDriverNegativePath = dataBean.getIdentityCardUrl();
            mDriverNegativePicClear.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                    .load(mDriverNegativePath).into(mDriverNegativePic);
        }
        if (dataBean.getElectricianCertificateUrl() != null && !dataBean.getElectricianCertificateUrl().isEmpty()) {
            mDianGongPositivePath = dataBean.getIdentityCardUrl();
            mDianGongPositiveClear.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_card_upload_positive))
                    .load(mDianGongPositivePath).into(mDianGongPositive);
        }
        if (dataBean.getElectricianCertificateBackUrl() != null && !dataBean.getElectricianCertificateBackUrl().isEmpty()) {
            mDianGongNegativePath = dataBean.getIdentityCardUrl();
            mDianGongNegativeClear.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_card_upload_negative))
                    .load(mDianGongNegativePath).into(mDianGongNegative);
        }

        mRegYear.setContentEditView(dataBean.getWorkingYear() + "");
        TechnicianDetailBean.DataBean.TechnicianAccountBean technicianAccount = dataBean.getTechnicianAccount();
        mReceivingAccount.setContentEditView(technicianAccount.getCardNumbers());
        mBankAccount.setContentTextView(technicianAccount.getDepositBank());
        mWxAccount.setContentEditView(technicianAccount.getWeChatAccount());
        mAlipayAccount.setContentEditView(technicianAccount.getAlipayAccount());
        for (TechnicianDetailBean.DataBean.BrandListBean brandListBean : dataBean.getBrandList()) {
            mBrandDetailList.add(brandListBean.getBrandUuid());
        }
        mPresenter.queryBrand(BRAND_PARENT);
    }

    @Override
    public void registerTechnician(String token) {
        ToastUtils.showShort("注册成功,请等待审核");
        finish();
    }

    @Override
    public void updateTechnician(String token) {
        ToastUtils.showShort("修改成功,请等待审核");
        finish();
    }

    @Override
    public void queryTechnicianTypeSuccess(ArrayList<TechnicianTypeBean.DataBean> dataBeans) {
        isTechnicianSuccess = true;
        mTechnician.addAll(dataBeans);
    }

    @Override
    public void queryTechnicianTypeFailed(String msg) {

    }

    @Override
    public void queryBrandSuccess(ArrayList<BrandBean.DataBean> dataBeans) {
        StringBuffer brandName = new StringBuffer();
        if (mBrandDetailList.size() > 0) {
            for (BrandBean.DataBean dataBean : dataBeans) {
                for (int i = 0; i < mBrandDetailList.size(); i++) {
                    String s = mBrandDetailList.get(i);
                    if (dataBean.getUuid().equals(s)) {
                        dataBean.setCheck(true);
                        brandName.append(dataBean.getConfigName() + "/");
                        mBrandDetailList.remove(i);
                    }
                }
                mBrandList.add(dataBean);
            }
            mRepairBrand.setContentTextView(brandName.deleteCharAt(brandName.length() - 1).toString());
        } else {
            mBrandList.addAll(dataBeans);
        }
    }

    @Override
    public void queryBrandFailed(String msg) {

    }

    @Override
    public void uploadFileSuccess(String path) {
        switch (PIC_FLAG) {
            case 1:
                mCardPositivePath = path;
                mRegCardPositiveClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                        .load(cameraFile).into(mRegCardPositive);
                break;
            case 2:
                mCardNegativePath = path;
                mPicCardNegativeClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                        .load(cameraFile).into(mPicCardNegative);
                break;
            case 3:
                mSkillsPath = path;
                mSkillsPicClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                        .load(cameraFile).into(mSkillsPic);
                break;
            case 4:
                mOemPath = path;
                mOemPicClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                        .load(cameraFile).into(mOemPic);
                break;
            case 5:
                mDriverPositivePath = path;
                mDriverPositivePicClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                        .load(cameraFile).into(mDriverPositivePic);
                break;
            case 6:
                mDriverNegativePath = path;
                mDriverNegativePicClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                        .load(cameraFile).into(mDriverNegativePic);
                break;
            case 7:
                mDianGongPositivePath = path;
                mDianGongPositiveClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_card_upload_positive))
                        .load(cameraFile).into(mDianGongPositive);
                break;
            case 8:
                mDianGongNegativePath = path;
                mDianGongNegativeClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_card_upload_negative))
                        .load(cameraFile).into(mDianGongNegative);
                break;
        }
        WeiboDialogUtils.closeDialog(loadDialogView);
    }

    @Override
    public void queryCitySuccess(ArrayList<CityBean.DataBean> cityBean, ArrayList<ArrayList<CityBean.DataBean>> areaBean, ArrayList<ArrayList<ArrayList<CityBean.DataBean>>> addressBean) {
        isLoadCitySuccess = true;
        mOptionsPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String cityAddress = String.format("%s%s%s", cityBean.get(options1).getAreaName()
                    , areaBean.get(options1).get(options2).getAreaName()
                    , addressBean.get(options1).get(options2).get(options3).getAreaName());
            mRegCity.setContentTextView(cityAddress);
            companyAddressProvince = cityBean.get(options1).getUuid();
            companyAddressCity = areaBean.get(options1).get(options2).getUuid();
            companyAddressCounty = addressBean.get(options1).get(options2).get(options3).getUuid();
        }).build();
        mOptionsPickerView.setPicker(cityBean, areaBean, addressBean);
    }

    @Override
    public void queryCityFailed(String path) {

    }

    @Override
    public void uploadFileFailed(String msg) {
        WeiboDialogUtils.closeDialog(loadDialogView);
    }

    @Override
    public void depositBankSuccess(ArrayList<DepositBankBean.DataBean> dataBeans) {
        isBankSuccess = true;
        mBankBean.addAll(dataBeans);
    }

    @Override
    public void depositBankFailed(String msg) {

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
                mPresenter.uploadFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "上传中...");
            }
        } else if (requestCode == PER_WRITE_EXTERNAL_STORAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                cameraFile = new File(RealPathFromUriUtils.getRealPathFromUri(getBaseContext(), data.getData()));
                mPresenter.uploadFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "上传中...");
            }
        } else if (requestCode == REGISTER_BRAND_CODE && data != null) {
            mBrandList.clear();
            mBrandList.addAll((ArrayList<BrandBean.DataBean>) data.getSerializableExtra("brand_class"));
            String brandName = data.getStringExtra("brandName");
            String brandUUID = data.getStringExtra("brandUUID");
            mRepairBrand.setContentTextView(brandName);
            List<String> brandUUIDs = Arrays.asList(brandUUID.split("/"));
            mTechnicianRegisterReq.setBrandUuidArrayList(brandUUIDs);

        }
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
    public void onBackPressed() {
        isExit();
    }

    @Override
    public void onBackFinish(View view) {
        isExit();
    }

    private void isExit() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("退出将不保存任何编辑, 是否退出?")
                .setPositiveButton("是", (dialog, which) -> finish())
                .setNegativeButton("否", (dialog, which) -> dialog.dismiss()).create();
        alertDialog.show();
    }
}
