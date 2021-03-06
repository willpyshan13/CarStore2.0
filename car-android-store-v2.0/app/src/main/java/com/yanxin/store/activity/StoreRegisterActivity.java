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
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.yanxin.store.adapter.rvadapter.PersonAdapter;
import com.yanxin.store.adapter.rvadapter.PersonTypeAdapter;
import com.yanxin.store.adapter.rvadapter.PicAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DepositBankBean;
import com.yanxin.store.bean.PersonBean;
import com.yanxin.store.bean.PersonTypeBean;
import com.yanxin.store.bean.StoreDetailBean;
import com.yanxin.store.contract.StoreContract;
import com.yanxin.store.entity.PicEntity;
import com.yanxin.store.presenter.StorePresenter;
import com.yanxin.store.req.StoreRegisterReq;
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

@XmlLayoutResId(contentId = R.layout.activity_store_register)
public class StoreRegisterActivity extends BaseMvpActivity<StorePresenter> implements StoreContract.StoreView, EasyPermissions.PermissionCallbacks {
    private OptionalEditLayout mLoginMobile;        //???????????????
    private OptionalEditLayout mLoginNickname;      //?????????
    private OptionalEditLayout mRepairBrand;        //????????????
    private OptionalEditLayout mCompanyName;        //????????????
    private OptionalEditLayout mCompanyCity;        //????????????
    private EditText mCompanyAddress;               //??????????????????
    private OptionalEditLayout mBankAccount;        //????????????
    private OptionalEditLayout mReceivingAccount;   //????????????
    private OptionalEditLayout mWxAccount;          //??????????????????
    private OptionalEditLayout mAlipayAccount;      //?????????????????????
    private ImageView mBusinessPic;                 //????????????
    private ImageView mBusinessPicClear;            //??????????????????
    private RecyclerView mStorePic;                 //??????????????????
    private ImageView mPicCardPositive;             //???????????????
    private ImageView mPicCardPositiveClear;        //?????????????????????
    private ImageView mPicCardNegative;             //???????????????
    private ImageView mPicCardNegativeClear;        //?????????????????????
    private TextView mAddContact;                   //???????????????
    private RecyclerView mContactGroup;             //???????????????
    private Button mSubmit;                    //????????????
    private LinearLayout mAuditLayout;                    //?????????????????????
    private LinearLayout mAuditErrorInfoLayout;                    //???????????????????????????????????????????????????
    private TextView mAuditErrorContent;                    //????????????
    private ImageView mAuditStatusIcon;                    //????????????
    private TextView mAuditStatusHint;                    //??????????????????
    private PopupWindow mPopWindow;
    private ArrayList<PicEntity> mPicEntity;
    private ArrayList<PersonTypeBean.DataBean> mPersonTypeBean;
    private ArrayList<DepositBankBean.DataBean> mBankBean;
    private List<StoreRegisterReq.StoreUserReqBean> storeUserReqBeans;
    private ArrayList<PersonBean> mPersonBean;
    private File cameraFile;
    private PicAdapter mPicAdapter;
    private POPRecyclerViewAdapter mPopAdapter;
    private PersonTypeAdapter mTypeAdapter;
    private PersonAdapter mPersonAdapter;
    private Dialog loadDialogView;
    private OptionsPickerView mOptionsPickerView;
    private boolean isLoadCitySuccess;
    private boolean isBankSuccess;
    private boolean isPersonTypeSuccess;
    private StoreRegisterReq storeRegisterReq;
    private String companyAddressCity;
    private String companyAddressCounty;
    private String companyAddressProvince;
    private String companyAddressDetail;
    private boolean isLogin;  //????????????????????????????????????

    /**
     * ?????????????????????????????????
     */
    private PicEntity mCurrentPicEntity;
    private int mCurrentPosition;
    private boolean isPicDefault;
    private int PIC_FLAG;   //1:??????????????????  2:????????????????????????  3:????????????????????????  4:????????????????????????
    private String businessPath;
    private String picCardPositivePath;
    private String picCardNegativePath;
    private ArrayList<String> mBrandStrList;
    private ArrayList<BrandBean.DataBean> mBrandList;
    private List<String> mBrandDetailList;
    private String storeUuid;

    @Override
    protected void initMVPData() {
        initViewOnClick();
        initAdapterItemOnClick();
        storeRegisterReq = new StoreRegisterReq();
        mPresenter.getDepositBank();
        mPresenter.queryCity();
        mPresenter.getPersonType();
        if (!isLogin) {
            mPresenter.queryBrand(BRAND_PARENT);
        }
    }

    private void initAdapterItemOnClick() {
        mPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            KeyboardUtils.hideSoftInput(this);
            PIC_FLAG = 2;
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
        mPersonAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.contact_type:
                    if (isPersonTypeSuccess) {
                        if (mPopWindow == null || !mPopWindow.isShowing()) {
                            mPopWindow = BasePOPWindow.createRecyclerViewPop(StoreRegisterActivity.this, popView -> {
                                mPopWindow.dismiss();
                            });
                            RecyclerView mPopRecycler = mPopWindow.getContentView().findViewById(R.id.rv_item);
                            mPopRecycler.setAdapter(mTypeAdapter);
                            mTypeAdapter.notifyDataSetChanged();
                            mPopWindow.setAnimationStyle(R.style.PopWindowAnimStyle);
                            mPopWindow.showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0);
                            PersonBean personBean = mPersonBean.get(position);
                            mTypeAdapter.setOnItemChildClickListener((adapter1, view1, position1) -> {
                                mPopWindow.dismiss();
                                personBean.setRenType(mPersonTypeBean.get(position1).getLableDesc());
                                personBean.setUuid(mPersonTypeBean.get(position1).getUuid());
                                personBean.setPersonType(mPersonTypeBean.get(position1).getUuid());
                                mPersonAdapter.notifyDataSetChanged();
                            });
                        }
                    } else {
                        ToastUtils.showShort("???????????????????????????");
                    }
                    break;
                case R.id.contact_del:
                    mPersonBean.remove(position);
                    mPersonAdapter.notifyDataSetChanged();
                    break;
            }
        });
        mPopAdapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            mPopWindow.dismiss();
            mBankAccount.setContentTextView(mBankBean.get(position).getLableDesc());
        });
    }

    private void initViewOnClick() {
        mBusinessPic.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            PIC_FLAG = 1;
            createPopWindow();
        });
        mBusinessPicClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            mBusinessPicClear.setVisibility(View.INVISIBLE);
            mBusinessPic.setImageResource(R.drawable.pic_upload_pic);
        });
        mPicCardPositive.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            PIC_FLAG = 3;
            createPopWindow();
        });
        mPicCardPositiveClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            mPicCardPositiveClear.setVisibility(View.INVISIBLE);
            mPicCardPositive.setImageResource(R.drawable.pic_card_upload_positive);
        });
        mPicCardNegative.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            PIC_FLAG = 4;
            createPopWindow();
        });
        mPicCardNegativeClear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            mPicCardNegativeClear.setVisibility(View.INVISIBLE);
            mPicCardNegative.setImageResource(R.drawable.pic_card_upload_negative);
        });
        mRepairBrand.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (mBrandList.size() == 0) {
                ToastUtils.showShort("???????????????????????????");
            } else {
                Intent intent = new Intent(getBaseContext(), BrandActivity.class);
                intent.putExtra("brand_class", mBrandList);
                startActivityForResult(intent, REGISTER_BRAND_CODE);
            }
        });
        mCompanyCity.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isLoadCitySuccess) {
                mOptionsPickerView.show();
            } else {
                ToastUtils.showShort("????????????????????????????????????...");
            }
        });
        mBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(v);
                if (isBankSuccess) {
                    if (mPopWindow == null || !mPopWindow.isShowing()) {
                        mPopWindow = BasePOPWindow.createRecyclerViewPop(StoreRegisterActivity.this, popView -> {
                            mPopWindow.dismiss();
                        });
                        RecyclerView mPopRecycler = mPopWindow.getContentView().findViewById(R.id.rv_item);
                        mPopRecycler.setAdapter(mPopAdapter);
                        mPopAdapter.notifyDataSetChanged();
                        mPopWindow.setAnimationStyle(R.style.PopWindowAnimStyle);
                        mPopWindow.showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0);
                    }
                } else {
                    ToastUtils.showShort("????????????????????????");
                }
            }
        });
        mAddContact.setOnClickListener(v -> addPersonInformation(false));
        mSubmit.setOnClickListener(view -> {
            KeyboardUtils.hideSoftInput(view);
            if (checkValueIsEmpty()) {
                ArrayList<String> businessImgList = new ArrayList<>();
                businessImgList.add(businessPath);
                storeRegisterReq.setGlyMobile(mLoginMobile.getContentEditValue());
                storeRegisterReq.setBusinessImgList(businessImgList);
                storeRegisterReq.setCompanyAddressCity(companyAddressCity);
                storeRegisterReq.setCompanyAddressCounty(companyAddressCounty);
                storeRegisterReq.setCompanyAddressProvince(companyAddressProvince);
                storeRegisterReq.setCompanyAddressDetail(mCompanyAddress.getText().toString().trim());
                storeRegisterReq.setShopImgList(mBrandStrList);
                storeRegisterReq.setStoreType("101"); //??????????????????
                storeRegisterReq.setStoreName(mLoginNickname.getContentEditValue()); //??????????????????
                storeRegisterReq.setCompanyName(mCompanyName.getContentEditValue());
                storeRegisterReq.setLegalPersonFront(picCardPositivePath);
                storeRegisterReq.setLegalPersonReverse(picCardNegativePath);
                StoreRegisterReq.StoreAccountReqBean storeAccountReqBean = new StoreRegisterReq.StoreAccountReqBean();
                storeAccountReqBean.setDepositBank(mBankAccount.getContentTextValue());
                storeAccountReqBean.setCardNumbers(mReceivingAccount.getContentEditValue());
                storeAccountReqBean.setWeChatAccount(mWxAccount.getContentEditValue());
                storeAccountReqBean.setAlipayAccount(mAlipayAccount.getContentEditValue());
                storeRegisterReq.setStoreAccountReq(storeAccountReqBean);
//                storeRegisterReq.setStoreName(mCompanyName.getContentEditValue());
                storeUserReqBeans.clear();
                for (PersonBean personBean : mPersonBean) {
                    if (!RegexUtils.isMobileExact(personBean.getMobile())) {
                        ToastUtils.showShort("????????????????????????????????????");
                        return;
                    }
                    StoreRegisterReq.StoreUserReqBean storeUserReqBean = new StoreRegisterReq.StoreUserReqBean();
                    storeUserReqBean.setMobile(personBean.getMobile());
                    storeUserReqBean.setPersonType(personBean.getUuid());
                    storeUserReqBean.setUserName(personBean.getUserName());
                    storeUserReqBean.setRenType(personBean.getRenType());
                    storeUserReqBean.setUuid(personBean.getUuid());
                    storeUserReqBeans.add(storeUserReqBean);
                }
                storeRegisterReq.setStoreUserReq(storeUserReqBeans);
                if (isLogin) {
                    storeRegisterReq.setUuid(storeUuid);
                    mPresenter.updateStore(getIntent().getStringExtra("user_token"), storeRegisterReq);
                } else {
                    mPresenter.addStore(storeRegisterReq);
                }
            }
        });
    }

    private void createPopWindow() {
        if (mPopWindow == null || !mPopWindow.isShowing()) {
            mPopWindow = BasePOPWindow.createPopWindow(StoreRegisterActivity.this, popView -> {
                if (popView.getId() == R.id.photo_camera) {
                    /**
                     * ??????????????????
                     */
                    checkPermission(PER_CAMERA, Manifest.permission.CAMERA);
                } else if (popView.getId() == R.id.photo_picker) {
                    /**
                     * ??????????????????
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
     * ????????????
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
        //???????????????
        File file;
        file = getExternalFilesDir("apk");
        if (!file.getParentFile().exists()) {
            file.mkdir();
        }
        //???????????????????????????????????????????????????
        cameraFile = new File(file.getAbsoluteFile() + "/car_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".jpg");
        //???????????????????????????
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //?????????Android7.0??????,??????FileProvider??????Uri
            imageIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //***????????????
            Uri contentUri = FileProvider.getUriForFile(getBaseContext(), MyApplication.getApplication().getPackageName() + ".fileProvider", cameraFile);
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            //????????????Uri.fromFile(file)????????????Uri
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        }
        startActivityForResult(imageIntent, PER_CAMERA);
    }

    private boolean checkValueIsEmpty() {
        String mobileContentValue = mLoginMobile.getContentEditValue();
        String nameContentValue = mLoginNickname.getContentEditValue();
        String companyNameContentValue = mCompanyName.getContentEditValue();
        String backAccountContentValue = mBankAccount.getContentTextValue();
        String receivingAccountValue = mReceivingAccount.getContentEditValue();
        String brandContentTextValue = mRepairBrand.getContentTextValue();
        if (!RegexUtils.isMobileExact(mobileContentValue)) {
            ToastUtils.showShort("???????????????????????????");
            return false;
        }
        if (BasicUtils.strIsEmpty(nameContentValue)) {
            ToastUtils.showShort("???????????????");
            return false;
        }
        if (BasicUtils.strIsEmpty(brandContentTextValue)) {
            ToastUtils.showShort("?????????????????????");
            return false;
        }
        if (BasicUtils.strIsEmpty(companyNameContentValue)) {
            ToastUtils.showShort("?????????????????????");
            return false;
        }
        if (BasicUtils.strIsEmpty(backAccountContentValue)) {
            ToastUtils.showShort("?????????????????????");
            return false;
        }
        if (BasicUtils.strIsEmpty(receivingAccountValue)) {
            ToastUtils.showShort("?????????????????????");
            return false;
        }
        return checkSelectValue();
    }

    private boolean checkSelectValue() {
        if (BasicUtils.strIsEmpty(businessPath)) {
            ToastUtils.showShort("?????????????????????");
            return false;
        }
        mBrandStrList.clear();
        for (PicEntity picEntity : mPicEntity) {
            if (picEntity.getPath() != null && !picEntity.getPath().isEmpty()) {
                mBrandStrList.add(picEntity.getPath());
            }
        }
        if (mBrandStrList.size() == 0) {
            ToastUtils.showShort("?????????????????????");
            return false;
        }
        if (mPersonBean.size() == 0) {
            ToastUtils.showShort("??????????????????");
            return false;
        }
        return true;
    }

    @Override
    protected void initMVPView() {
        Intent intent = getIntent();
        isLogin = intent.getBooleanExtra("is_login", false);
        initFindView();
        initArrayList();
        initAdapter();
        if (isLogin) {
            mSubmit.setText("??????????????????");
            mPresenter.getStoreDetail(getIntent().getStringExtra("user_token"));
        } else {
            mAuditLayout.setVisibility(View.GONE);
            //???????????????????????????????????????
            addDefaultStorePic();
            //??????????????????????????????
            addPersonInformation(true);
        }
    }

    private void initAdapter() {
        mPicAdapter = new PicAdapter(R.layout.include_store_register_pic, mPicEntity);
        mPopAdapter = new POPRecyclerViewAdapter(R.layout.item_pop_recycler, mBankBean);
        mTypeAdapter = new PersonTypeAdapter(R.layout.item_pop_recycler, mPersonTypeBean);
        mPersonAdapter = new PersonAdapter(R.layout.item_store_register_contact_information, mPersonBean);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mContactGroup.setLayoutManager(linearLayoutManager);
        mContactGroup.setAdapter(mPersonAdapter);
        mStorePic.setAdapter(mPicAdapter);
    }

    private void initArrayList() {
        mPicEntity = new ArrayList<>();
        mPersonTypeBean = new ArrayList<>();
        mBankBean = new ArrayList<>();
        mPersonBean = new ArrayList<>();
        mBrandStrList = new ArrayList<>();
        mBrandList = new ArrayList<>();
        storeUserReqBeans = new ArrayList<>();
    }

    private void initFindView() {
        mLoginMobile = findViewById(R.id.login_mobile);
        mLoginNickname = findViewById(R.id.login_nickname);
        mRepairBrand = findViewById(R.id.repair_brand);
        mCompanyName = findViewById(R.id.company_name);
        mCompanyCity = findViewById(R.id.company_city);
        mCompanyAddress = findViewById(R.id.company_address);
        mBankAccount = findViewById(R.id.bank_account);
        mReceivingAccount = findViewById(R.id.receiving_account);
        mWxAccount = findViewById(R.id.wx_account);
        mAlipayAccount = findViewById(R.id.alipay_account);
        mBusinessPic = findViewById(R.id.business_pic);
        mBusinessPicClear = findViewById(R.id.business_pic_clear);
        mStorePic = findViewById(R.id.store_pic);
        mPicCardPositive = findViewById(R.id.pic_card_positive);
        mPicCardPositiveClear = findViewById(R.id.pic_card_positive_clear);
        mPicCardNegative = findViewById(R.id.pic_card_negative);
        mPicCardNegativeClear = findViewById(R.id.pic_card_negative_clear);
        mAddContact = findViewById(R.id.add_contact);
        mContactGroup = findViewById(R.id.contact_group);
        mSubmit = findViewById(R.id.login_submit);
        mAuditLayout = findViewById(R.id.audit_layout);
        mAuditErrorInfoLayout = findViewById(R.id.audit_error_layout);
        mAuditErrorContent = findViewById(R.id.audit_error_hint);
        mAuditStatusIcon = findViewById(R.id.audit_status_icon);
        mAuditStatusHint = findViewById(R.id.audit_status_content);
    }

    private void addPersonInformation(boolean isDefault) {
        PersonBean personBean = new PersonBean();
        personBean.setDefaultPerson(isDefault);
        mPersonBean.add(personBean);
        mPersonAdapter.notifyDataSetChanged();
    }

    private void addDefaultStorePic() {
        PicEntity picEntity = new PicEntity();
        picEntity.setDefault(true);
        mPicEntity.add(picEntity);
        isPicDefault = true;
    }

    @Override
    public BasePresenter initPresenter() {
        return StorePresenter.newInstance();
    }

    @Override
    public void startActivity() {

    }


    @Override
    public void getStoreDetail(StoreDetailBean.DataBean dataBean) {
        storeUuid = dataBean.getUuid();
        mAuditLayout.setVisibility(View.VISIBLE);
        int auditType = dataBean.getCheckSts();
        if (auditType == 0) {
            mAuditStatusIcon.setImageResource(R.drawable.wait_icon);
            mAuditStatusHint.setText("?????????");
            mAuditErrorInfoLayout.setVisibility(View.GONE);
        } else if (auditType == 2) {
            mAuditStatusIcon.setImageResource(R.drawable.audit_error);
            mAuditStatusHint.setText("???????????????");
            mAuditErrorInfoLayout.setVisibility(View.VISIBLE);
            mAuditErrorContent.setText(dataBean.getRejectDetail());
        }
        mBrandDetailList = dataBean.getBrandUuidList();
        storeRegisterReq.setBrandUuidList(mBrandDetailList);
        mRepairBrand.setContentTextView(dataBean.getBrandName());
        mPresenter.queryBrand(BRAND_PARENT);
        mLoginMobile.setContentEditView(dataBean.getGlyMobile());
        mLoginNickname.setContentEditView(dataBean.getStoreName());
        mCompanyName.setContentEditView(dataBean.getCompanyName());
        companyAddressCity = dataBean.getCompanyAddressCity();
        companyAddressCounty = dataBean.getCompanyAddressCounty();
        companyAddressProvince = dataBean.getCompanyAddressProvince();
        mCompanyCity.setContentTextView(dataBean.getAddressProvinceName() + dataBean.getAddressCityName() + dataBean.getAddressCountyName());
        mCompanyAddress.setText(dataBean.getCompanyAddressDetail());
        String businessImg = dataBean.getBusinessImgList().get(0);
        businessPath = businessImg;
        Glide.with(getBaseContext())
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                .load(businessImg).into(mBusinessPic);
        mBusinessPicClear.setVisibility(View.VISIBLE);
        if (dataBean.getLegalPersonFront() != null && !dataBean.getLegalPersonFront().isEmpty()) {
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_card_upload_positive))
                    .load(dataBean.getLegalPersonFront()).into(mPicCardPositive);
            mPicCardPositiveClear.setVisibility(View.VISIBLE);
        }
        if (dataBean.getLegalPersonReverse() != null && !dataBean.getLegalPersonReverse().isEmpty()) {
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_card_upload_negative))
                    .load(dataBean.getLegalPersonReverse()).into(mPicCardNegative);
            mPicCardNegativeClear.setVisibility(View.VISIBLE);
        }
        List<String> shopImgList = dataBean.getShopImgList();
        int maxSize = Math.min(6, shopImgList.size());
        for (int i = 0; i < maxSize; i++) {
            PicEntity picEntity = new PicEntity();
            picEntity.setPath(shopImgList.get(i));
            picEntity.setNetWork(true);
            picEntity.setDefault(false);
            mPicEntity.add(picEntity);
        }
        if (mPicEntity.size() < 6) {
            addDefaultStorePic();
        }
        StoreDetailBean.DataBean.StoreAccountReqBean storeAccountRes = dataBean.getStoreAccountRes();
        mBankAccount.setContentTextView(storeAccountRes.getDepositBank());
        mReceivingAccount.setContentEditView(storeAccountRes.getCardNumbers());
        mWxAccount.setContentEditView(storeAccountRes.getWeChatAccount());
        mAlipayAccount.setContentEditView(storeAccountRes.getAlipayAccount());
        mPicAdapter.notifyDataSetChanged();
        List<StoreDetailBean.DataBean.StoreUserResListBean> storeUserResList = dataBean.getStoreUserResList();
        for (StoreDetailBean.DataBean.StoreUserResListBean storeUserResListBean : storeUserResList) {
            PersonBean personBean = new PersonBean();
            personBean.setMobile(storeUserResListBean.getMobile());
            personBean.setUserName(storeUserResListBean.getUserName());
            personBean.setRenType(storeUserResListBean.getPosition());
            personBean.setUuid(storeUserResListBean.getUuid());
            mPersonBean.add(personBean);
        }
        mPersonBean.get(0).setDefaultPerson(true);
        mPersonAdapter.notifyDataSetChanged();

    }

    @Override
    public void addStoreSuccess(String dataStr) {
        ToastUtils.showShort("????????????, ?????????????????????");
        finish();
    }

    @Override
    public void updateStoreSuccess(String dataStr) {

    }

    @Override
    public void queryBrandSuccess(ArrayList<BrandBean.DataBean> dataBeans) {
        StringBuffer brandName = new StringBuffer();
        if (mBrandDetailList != null) {
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
                businessPath = path;
                mBusinessPicClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                        .load(cameraFile).into(mBusinessPic);
                break;
            case 2:
                //?????????????????????
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
                    //????????????
                    PicEntity picEntity = new PicEntity();
                    picEntity.setDefault(false);
                    picEntity.setPath(path);
                    picEntity.setPicFile(cameraFile);
                    mPicEntity.add(mPicEntity.size() - 1, picEntity);
                }
                mPicAdapter.notifyDataSetChanged();
                break;
            case 3:
                picCardPositivePath = path;
                mPicCardPositiveClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_card_upload_negative))
                        .load(cameraFile).into(mPicCardPositive);
                break;
            case 4:
                picCardNegativePath = path;
                mPicCardNegativeClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_card_upload_negative))
                        .load(cameraFile).into(mPicCardNegative);
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
            mCompanyCity.setContentTextView(cityAddress);
            companyAddressProvince = cityBean.get(options1).getUuid();
            companyAddressCity = areaBean.get(options1).get(options2).getUuid();
            companyAddressCounty = addressBean.get(options1).get(options2).get(options3).getUuid();
        }).build();
        mOptionsPickerView.setPicker(cityBean, areaBean, addressBean);
    }

    @Override
    public void queryCityFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void uploadFileFailed(String msg) {
        ToastUtils.showShort(msg);
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
    public void personTypeSuccess(ArrayList<PersonTypeBean.DataBean> dataBeans) {
        isPersonTypeSuccess = true;
        mPersonTypeBean.addAll(dataBeans);
    }

    @Override
    public void personTypeFailed(String msg) {

    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
        WeiboDialogUtils.closeDialog(loadDialogView);
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
        ToastUtils.showShort("???????????????????????????????????????");
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
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
            }
        } else if (requestCode == PER_WRITE_EXTERNAL_STORAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                cameraFile = new File(RealPathFromUriUtils.getRealPathFromUri(getBaseContext(), data.getData()));
                mPresenter.uploadFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
            }
        } else if (requestCode == REGISTER_BRAND_CODE && data != null) {
            mBrandList.clear();
            mBrandList.addAll((ArrayList<BrandBean.DataBean>) data.getSerializableExtra("brand_class"));
            String brandName = data.getStringExtra("brandName");
            String brandUUID = data.getStringExtra("brandUUID");
            mRepairBrand.setContentTextView(brandName);
            List<String> brandUUIDs = Arrays.asList(brandUUID.split("/"));
            storeRegisterReq.setBrandUuidList(brandUUIDs);
        }
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
                .setTitle("??????")
                .setMessage("??????????????????????????????, ?????????????")
                .setPositiveButton("???", (dialog, which) -> finish())
                .setNegativeButton("???", (dialog, which) -> dialog.dismiss()).create();
        alertDialog.show();
    }
}

