package com.yanxin.store.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.PicAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.DrivingBean;
import com.yanxin.store.bean.EngineDisplacementBean;
import com.yanxin.store.bean.FiltrateBean;
import com.yanxin.store.bean.SiteFeeBean;
import com.yanxin.store.bean.SuperchargingBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.bean.TransmissionBean;
import com.yanxin.store.bean.TransmissonChildBean;
import com.yanxin.store.contract.AddSiteOrderContract;
import com.yanxin.store.entity.PicEntity;
import com.yanxin.store.event.SupportEvent;
import com.yanxin.store.presenter.AddSiteOrderPresenter;
import com.yanxin.store.req.AddSiteReq;
import com.yanxin.store.ui.FiltrateWindow;
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

import static com.yanxin.store.commont.Constant.AppConfig.BASE_FEE;
import static com.yanxin.store.commont.Constant.AppConfig.BASE_SERVING_FEE;
import static com.yanxin.store.commont.Constant.AppConfig.BRAND_PARENT;
import static com.yanxin.store.commont.Constant.AppConfig.DRIVING_MODE;
import static com.yanxin.store.commont.Constant.AppConfig.ENGINE_DISPLACEMENT;
import static com.yanxin.store.commont.Constant.AppConfig.SUPERCHARGING_SYSTEM;
import static com.yanxin.store.commont.Constant.AppConfig.TECHNICIAN_TYPE;
import static com.yanxin.store.commont.Constant.AppConfig.TRANSMISSION_TYPE;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_BRAND;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_MODEL;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_CAMERA;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_FIND_LOCATION;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_READ_EXTERNAL_STORAGE;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE;

@XmlLayoutResId(contentId = R.layout.activity_add_site_order)
public class AddSiteOrderActivity extends BaseMvpActivity<AddSiteOrderPresenter> implements AddSiteOrderContract.AddSiteOrderView, EasyPermissions.PermissionCallbacks {
    private ArrayList<FiltrateBean> mBrandBean;
    private ArrayList<FiltrateBean> mModeBean;
    private TextView mSiteTime;
    private TextView mSiteCity;
    private EditText mSiteAddress;
    private TextView mSiteYear;
    private TextView mSiteBrand;
    private TextView mSiteEngine;
    private TextView mSiteMode;
    private TextView mSiteDriving;
    private TextView mSiteSupercharging;
    private TextView mSiteWarrantyStatus;
    private TextView mSiteTransmission;
    private TextView mSiteTransmissionChild;
    private TextView mSiteTechnician;
    private OptionalEditLayout mSiteYam;
    private OptionalEditLayout mSiteVin;
    private EditText mSiteStatusContent;
    private EditText mSiteFault;
    private EditText mSiteInspectionProcess;
    private EditText mSiteDtcAll;
    private RecyclerView mSiteDtcImgs;
    private RecyclerView mSiteFaultImgs;
    private OptionalEditLayout mSiteBaseFee;
    private OptionalEditLayout mSiteBaseServiceFee;
    private Button mSiteSubmit;
    private ImageView mMapView;
    private PopupWindow mPopWindow;
    private Dialog loadDialogView;
    private FiltrateWindow mFiltrateWindow;
    private PicAdapter mDtcPicAdapter;
    private PicAdapter mFaultPicAdapter;
    private ArrayList<PicEntity> mDTCEntity;
    private ArrayList<PicEntity> mFaultEntity;
    private PicEntity mCurrentDtcEntity;
    private PicEntity mCurrentFaultEntity;
    private int mCurrentDtcPosition;
    private int mCurrentFaultPosition;
    private boolean isDtcPicDefault;
    private boolean isFaultPicDefault;

    private boolean isLoadCitySuccess;
    private OptionsPickerView mCityPickerView;
    private boolean isLoadEngineSuccess;
    private OptionsPickerView mEnginePickerView;
    private boolean isLoadTransSuccess;
    private OptionsPickerView mTransPickerView;
    private boolean isLoadTransChildSuccess;
    private OptionsPickerView mTransChildPickerView;
    private boolean isLoadSuperSuccess;
    private OptionsPickerView mSuperPickerView;
    private boolean isLoadDrivingSuccess;
    private OptionsPickerView mDrivingPickerView;
    private boolean isLoadTechnicianSuccess;
    private OptionsPickerView mTechnicianPickerView;
    private OptionsPickerView mWarrantyPickView;

    private TimePickerView mTimePickerView;
    private TimePickerView mYearView;
    private String companyAddressCity;
    private String companyAddressCounty;
    private String companyAddressProvince;
    private String engineUuid;
    private String transUuid;
    private String transChildUuid;
    private String superUuid;
    private String drivingUuid;
    private String technicianUuid;
    private String queryBrandUuid;
    private String queryModelUuid;
    private String feeUuid = BASE_FEE;
    private String orderServiceFeeUuid = BASE_SERVING_FEE;
    private int warrantyStatus;
    private int entityType = 0; //0 dtc 图片  1 fault 图片
    private File cameraFile;
    private ArrayList<String> status;

    @Override
    protected void initMVPData() {
        addDefaultDtcPic();
        addDefaultFaultPic();
        initNerWorkData();
        status.add("保修");
        status.add("不保修");
        mDtcPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            KeyboardUtils.hideSoftInput(this);
            entityType = 0;
            mCurrentDtcEntity = mDTCEntity.get(position);
            mCurrentDtcPosition = position;
            switch (view.getId()) {
                case R.id.pic_layout:
                    createPopWindow();
                    break;
                case R.id.business_pic_clear:
                    mDTCEntity.remove(position);
                    if (!isDtcPicDefault) {
                        addDefaultDtcPic();
                    }
                    mDtcPicAdapter.notifyDataSetChanged();
                    break;
            }
        });
        mFaultPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            KeyboardUtils.hideSoftInput(this);
            entityType = 1;
            mCurrentFaultEntity = mFaultEntity.get(position);
            mCurrentFaultPosition = position;
            switch (view.getId()) {
                case R.id.pic_layout:
                    createPopWindow();
                    break;
                case R.id.business_pic_clear:
                    mFaultEntity.remove(position);
                    if (!isFaultPicDefault) {
                        addDefaultFaultPic();
                    }
                    mFaultPicAdapter.notifyDataSetChanged();
                    break;
            }
        });
        mSiteTechnician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(v);
                if (isLoadTechnicianSuccess) {
                    mTechnicianPickerView.show();
                } else {
                    ToastUtils.showShort("维修类型正在加载，请稍后...");
                }
            }
        });
        mSiteTime.setOnClickListener(v -> {
//            Calendar startNow = Calendar.getInstance();
//            int startYear = startNow.get(Calendar.YEAR);
//            int startMonth = startNow.get(Calendar.MONTH);
//            int startDay = startNow.get(Calendar.DAY_OF_MONTH);
//            startNow.set(startYear, startMonth, startDay);
            KeyboardUtils.hideSoftInput(v);
            mTimePickerView = new TimePickerBuilder(AddSiteOrderActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    mSiteTime.setText(new SimpleDateFormat("yyyy-MM-dd HH").format(date) + ":00");
                }
            }).setType(new boolean[]{true, true, true, true, false, false})
                    .setLabel("年", "月", "日", "时", "分", "秒")
                    .build();
            mTimePickerView.show();
        });
        mSiteYear.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mYearView = new TimePickerBuilder(AddSiteOrderActivity.this, (date, v1) ->
                    mSiteYear.setText(new SimpleDateFormat("yyyy").format(date)))
                    .setType(new boolean[]{true, false, false, false, false, false})
                    .setLabel("年", "月", "日", "时", "分", "秒")
                    .build();
            mYearView.show();
        });
        mSiteCity.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isLoadCitySuccess) {
                mCityPickerView.show();
            } else {
                ToastUtils.showShort("地图数据正在加载，请稍后...");
            }
        });
        mSiteBrand.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (mBrandBean.size() == 0) {
                ToastUtils.showShort("正在获取品牌数据，请稍后");
            } else {
                showPOPWindow(FILTRATE_TYPE_BRAND, mSiteBrand, mBrandBean);
            }
        });
        mSiteMode.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (queryBrandUuid == null || queryBrandUuid.isEmpty()) {
                ToastUtils.showShort("请先选择车辆品牌");
            } else {
                showPOPWindow(FILTRATE_TYPE_MODEL, mSiteMode, mModeBean);
            }
        });
        mSiteEngine.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isLoadEngineSuccess) {
                mEnginePickerView.show();
            } else {
                ToastUtils.showShort("发动机排量数据正在获取，请稍后...");
            }
        });
        mSiteDriving.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isLoadDrivingSuccess) {
                mDrivingPickerView.show();
            } else {
                ToastUtils.showShort("驱动方式数据正在获取，请稍后...");
            }
        });
        mSiteSupercharging.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isLoadSuperSuccess) {
                mSuperPickerView.show();
            } else {
                ToastUtils.showShort("增压系统数据正在获取，请稍后...");
            }
        });
        mSiteWarrantyStatus.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            mWarrantyPickView = new OptionsPickerBuilder(AddSiteOrderActivity.this
                    , (options1, options2, options3, pickerView) -> {
                String engineValue = status.get(options1);
                mSiteWarrantyStatus.setText(engineValue);
                warrantyStatus = options1;
            }).build();
            mWarrantyPickView.setPicker(status);
            mWarrantyPickView.show();
        });
        mSiteTransmission.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isLoadTransSuccess) {
                mTransPickerView.show();
            } else {
                ToastUtils.showShort("变数器正在获取，请稍后...");
            }
        });
        mSiteTransmissionChild.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(v);
            if (isLoadTransChildSuccess) {
                mTransChildPickerView.show();
            } else {
                ToastUtils.showShort("变数器正在获取，请稍后...");
            }
        });
        mMapView.setVisibility(View.GONE);
        mMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkPermission(PER_FIND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        });
        mSiteSubmit.setOnClickListener(v -> {
            AddSiteReq addSiteReq = new AddSiteReq();
            addSiteReq.setAlreadyInspect(BasicUtils.getEditValue(mSiteInspectionProcess));
            addSiteReq.setBoosterSystemUuid(superUuid);
            addSiteReq.setBrandUuid(queryBrandUuid);
            addSiteReq.setCarModelUuid(queryModelUuid);
            addSiteReq.setServiceDate(mSiteTime.getText().toString());
            addSiteReq.setAddressCity(companyAddressCity);
            addSiteReq.setAddressCounty(companyAddressCounty);
            addSiteReq.setAddressProvince(companyAddressProvince);
            addSiteReq.setLatitude(MyApplication.getLatitude());
            addSiteReq.setLongitude(MyApplication.getLongitude());
            addSiteReq.setDetailedAddr(mSiteCity.getText().toString() + mSiteAddress.getText().toString());
            addSiteReq.setCarStyle(mSiteYear.getText().toString());
            addSiteReq.setDetailedAddr(BasicUtils.getEditValue(mSiteAddress));
            addSiteReq.setDrivingModeUuid(drivingUuid);
            addSiteReq.setDtcCode(BasicUtils.getEditValue(mSiteDtcAll));
            ArrayList<String> dtcImgs = new ArrayList<>();
            for (PicEntity picEntity : mDTCEntity) {
                if (picEntity.getPath() != null && !picEntity.getPath().isEmpty()) {
                    dtcImgs.add(picEntity.getPath());
                }
            }
            addSiteReq.setDtcImageList(dtcImgs);
            addSiteReq.setBasicDoorAmountUuid(feeUuid);
            addSiteReq.setOrderServiceAmountUuid(orderServiceFeeUuid);
            addSiteReq.setEngineDisplacementUuid(engineUuid);
            addSiteReq.setFaultDesc(BasicUtils.getEditValue(mSiteFault));
            ArrayList<String> faultImgs = new ArrayList<>();
            for (PicEntity picEntity : mFaultEntity) {
                if (picEntity.getPath() != null && !picEntity.getPath().isEmpty()) {
                    faultImgs.add(picEntity.getPath());
                }
            }
            addSiteReq.setFaultDescImageList(faultImgs);
            addSiteReq.setOtherSts(BasicUtils.getEditValue(mSiteStatusContent));
            addSiteReq.setRepairTypeUuid(technicianUuid);
            addSiteReq.setTransmissionOneLevelUuid(transUuid);
            addSiteReq.setTransmissionTwoLevelUuid(transChildUuid);
            String vinValue = mSiteVin.getContentEditValue();
            if (!vinValue.isEmpty() && vinValue.length() != 17) {
                ToastUtils.showShort("请输入正确的车架号");
                return;
            }
            addSiteReq.setVinCode(vinValue);
            addSiteReq.setWarrantySts(warrantyStatus);
//            addSiteReq.setOrderServiceAmountUuid(BASE_SERVING_FEE);
            mPresenter.addSite(addSiteReq);
        });
    }

    private void initNerWorkData() {
        mPresenter.queryAllBrand(BRAND_PARENT);
        mPresenter.queryDriving(DRIVING_MODE);
        mPresenter.queryCity();
        mPresenter.queryEngineDisplacement(ENGINE_DISPLACEMENT);
        mPresenter.querySupercharging(SUPERCHARGING_SYSTEM);
        mPresenter.queryTechnicianTypeBean(TECHNICIAN_TYPE);
        mPresenter.queryTransmission(TRANSMISSION_TYPE);
        mPresenter.queryBaseFee(BASE_FEE);
        mPresenter.queryBaseServiceFee(BASE_SERVING_FEE);
    }

    @Override
    protected void initMVPView() {
        initFindView();
        initArrayList();
        initAdapter();
    }

    private void initAdapter() {
        mDtcPicAdapter = new PicAdapter(R.layout.include_store_register_pic, mDTCEntity);
        mFaultPicAdapter = new PicAdapter(R.layout.include_store_register_pic, mFaultEntity);
        mSiteDtcImgs.setAdapter(mDtcPicAdapter);
        mSiteFaultImgs.setAdapter(mFaultPicAdapter);
    }

    private void initArrayList() {
        mBrandBean = new ArrayList<>();
        mModeBean = new ArrayList<>();
        mDTCEntity = new ArrayList<>();
        mFaultEntity = new ArrayList<>();
        status = new ArrayList<>();
    }

    private void initFindView() {
        mSiteTime = findViewById(R.id.site_time);
        mSiteCity = findViewById(R.id.site_city);
        mSiteAddress = findViewById(R.id.site_address);
        mSiteYear = findViewById(R.id.site_year);
        mSiteBrand = findViewById(R.id.site_brand);
        mSiteMode = findViewById(R.id.site_mode);
        mSiteEngine = findViewById(R.id.site_engine);
        mSiteDriving = findViewById(R.id.site_driving);
        mSiteSupercharging = findViewById(R.id.site_supercharging);
        mSiteWarrantyStatus = findViewById(R.id.site_warranty_status);
        mSiteTransmission = findViewById(R.id.site_transmission);
        mSiteTransmissionChild = findViewById(R.id.site_transmission_child);
        mSiteTechnician = findViewById(R.id.site_technician_type);
        mSiteYam = findViewById(R.id.site_yam);
        mSiteVin = findViewById(R.id.site_vin);
        mSiteFaultImgs = findViewById(R.id.site_fault_img);
        mSiteFault = findViewById(R.id.site_fault);
        mSiteStatusContent = findViewById(R.id.site_status_content);
        mSiteInspectionProcess = findViewById(R.id.site_inspection_process);
        mSiteDtcAll = findViewById(R.id.site_dtc_all);
        mSiteDtcImgs = findViewById(R.id.site_dtc_imgs);
        mSiteBaseFee = findViewById(R.id.site_base_fee);
        mSiteBaseServiceFee = findViewById(R.id.site_base_service_fee);
        mMapView = findViewById(R.id.map);
        mSiteSubmit = findViewById(R.id.site_submit);
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
        return AddSiteOrderPresenter.newInstance();
    }


    @Override
    public void addSuccess(String data) {
        ToastUtils.showShort("发布成功");
//        Intent intent = new Intent(getBaseContext(),PayActivity.class);
//        intent.putExtra("order_uuid", data);
//        startActivity(intent);
        EventBus.getDefault().post(new SupportEvent());
        finish();
    }

    @Override
    public void uploadSuccess(String path) {
        if (entityType == 0) {
            //最后一张的时候
            if (mCurrentDtcEntity.isDefault() && mCurrentDtcPosition >= 5) {
                mCurrentDtcEntity.setDefault(false);
                mCurrentDtcEntity.setPath(path);
                mCurrentDtcEntity.setPicFile(cameraFile);
                isDtcPicDefault = false;
            } else if (!mCurrentDtcEntity.isDefault()) {
                mCurrentDtcEntity.setDefault(false);
                mCurrentDtcEntity.setPath(path);
                mCurrentDtcEntity.setPicFile(cameraFile);
            } else {
                //正常添加
                PicEntity picEntity = new PicEntity();
                picEntity.setDefault(false);
                picEntity.setPath(path);
                picEntity.setPicFile(cameraFile);
                mDTCEntity.add(mDTCEntity.size() - 1, picEntity);
            }
            mDtcPicAdapter.notifyDataSetChanged();
        } else {
            //最后一张的时候
            if (mCurrentFaultEntity.isDefault() && mCurrentFaultPosition >= 5) {
                mCurrentFaultEntity.setDefault(false);
                mCurrentFaultEntity.setPath(path);
                mCurrentFaultEntity.setPicFile(cameraFile);
                isFaultPicDefault = false;
            } else if (!mCurrentFaultEntity.isDefault()) {
                mCurrentFaultEntity.setDefault(false);
                mCurrentFaultEntity.setPath(path);
                mCurrentFaultEntity.setPicFile(cameraFile);
            } else {
                //正常添加
                PicEntity picEntity = new PicEntity();
                picEntity.setDefault(false);
                picEntity.setPath(path);
                picEntity.setPicFile(cameraFile);
                mFaultEntity.add(mFaultEntity.size() - 1, picEntity);
            }
            mFaultPicAdapter.notifyDataSetChanged();
        }
        WeiboDialogUtils.closeDialog(loadDialogView);
    }

    @Override
    public void queryTransmission(ArrayList<TransmissionBean.DataBean> transBean) {
        isLoadTransSuccess = true;
        mTransPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String engineValue = transBean.get(options1).getLableDesc();
            mSiteTransmission.setText(engineValue);
            transUuid = transBean.get(options1).getUuid();
            mPresenter.queryManualTransmission(transBean.get(options1).getLableCode());
        }).build();
        mTransPickerView.setPicker(transBean);
    }

    @Override
    public void queryBaseFee(SiteFeeBean.DataBean feeBean) {
        mSiteBaseFee.setContentTextView("¥" + feeBean.getLableValue());
        feeUuid = feeBean.getUuid();
    }

    @Override
    public void queryBaseServiceFee(SiteFeeBean.DataBean siteFeeBean) {
        mSiteBaseServiceFee.setContentTextView("¥" + siteFeeBean.getLableValue());
    }

    @Override
    public void querySupercharging(ArrayList<SuperchargingBean.DataBean> superBean) {
        isLoadSuperSuccess = true;
        mSuperPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String engineValue = superBean.get(options1).getLableDesc();
            mSiteSupercharging.setText(engineValue);
            superUuid = superBean.get(options1).getUuid();
        }).build();
        mSuperPickerView.setPicker(superBean);
    }

    @Override
    public void queryManualTransmission(ArrayList<TransmissonChildBean.DataBean> transChildBean) {
        isLoadTransChildSuccess = true;
        mTransChildPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String engineValue = transChildBean.get(options1).getLableDesc();
            mSiteTransmissionChild.setText(engineValue);
            transChildUuid = transChildBean.get(options1).getUuid();
        }).build();
        mTransChildPickerView.setPicker(transChildBean);
    }

    @Override
    public void queryEngineDisplacement(ArrayList<EngineDisplacementBean.DataBean> engineBean) {
        isLoadEngineSuccess = true;
        mEnginePickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String engineValue = engineBean.get(options1).getLableDesc();
            mSiteEngine.setText(engineValue);
            engineUuid = engineBean.get(options1).getUuid();
        }).build();
        mEnginePickerView.setPicker(engineBean);
    }

    @Override
    public void queryDriving(ArrayList<DrivingBean.DataBean> drivingBean) {
        isLoadDrivingSuccess = true;
        mDrivingPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String engineValue = drivingBean.get(options1).getLableDesc();
            mSiteDriving.setText(engineValue);
            drivingUuid = drivingBean.get(options1).getUuid();
        }).build();
        mDrivingPickerView.setPicker(drivingBean);
    }

    @Override
    public void queryTechnicianTypeSuccess(ArrayList<TechnicianTypeBean.DataBean> technicianBean) {
        isLoadTechnicianSuccess = true;
        mTechnicianPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String engineValue = technicianBean.get(options1).getLableDesc();
            mSiteTechnician.setText(engineValue);
            technicianUuid = technicianBean.get(options1).getUuid();
        }).build();
        mTechnicianPickerView.setPicker(technicianBean);
    }

    @Override
    public void queryCitySuccess(ArrayList<CityBean.DataBean> cityBean, ArrayList<ArrayList<CityBean.DataBean>> areaBean, ArrayList<ArrayList<ArrayList<CityBean.DataBean>>> addressBean) {
        isLoadCitySuccess = true;
        mCityPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String cityAddress = String.format("%s%s%s", cityBean.get(options1).getAreaName()
                    , areaBean.get(options1).get(options2).getAreaName()
                    , addressBean.get(options1).get(options2).get(options3).getAreaName());
            mSiteCity.setText(cityAddress);
            companyAddressProvince = cityBean.get(options1).getUuid();
            companyAddressCity = areaBean.get(options1).get(options2).getUuid();
            companyAddressCounty = addressBean.get(options1).get(options2).get(options3).getUuid();
        }).build();
        mCityPickerView.setPicker(cityBean, areaBean, addressBean);
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
    public void queryAllModelSuccess(ArrayList<BrandBean.DataBean> brandBean) {
        mModeBean.clear();
        for (BrandBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getConfigName());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_MODEL);
            mModeBean.add(filtrateBean);
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
        } else if (perStatus == PER_FIND_LOCATION) {
            if (EasyPermissions.hasPermissions(getBaseContext(), permission)) {
                startToMapView();
            } else {
                EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, PER_FIND_LOCATION, permission).build());
            }
        }
    }

    private void startToMapView() {
        startActivityForResult(new Intent(getBaseContext(), MapActivity.class), PER_FIND_LOCATION);
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

    private void createPopWindow() {
        if (mPopWindow == null || !mPopWindow.isShowing()) {
            mPopWindow = BasePOPWindow.createPopWindow(AddSiteOrderActivity.this, popView -> {
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

    private void showPOPWindow(int tag, View view, ArrayList<FiltrateBean> beans) {
        mFiltrateWindow = FiltrateWindow.newBuilder()
                .setWidth((int) getResources().getDimension(R.dimen.popup_window_filtrate_width))
                .setHeight((int) getResources().getDimension(R.dimen.popup_window_filtrate_height))
                .setOnArithmeticList(beans)
                .setOnDismissListener(() -> mFiltrateWindow = null)
                .setOnSelectFiltrateListener(filtrateBean -> {
                    if (tag == FILTRATE_TYPE_BRAND) {
                        queryBrandUuid = filtrateBean.getUuid();
                        queryModelUuid = null;
                        mPresenter.queryAllModel(queryBrandUuid);
                        mSiteBrand.setText(filtrateBean.getName());
                        mSiteMode.setText("请选择故障车辆型号");
                    } else if (tag == FILTRATE_TYPE_MODEL) {
                        queryModelUuid = filtrateBean.getUuid();
                        mSiteMode.setText(filtrateBean.getName());
                    }
                })
                .build(this);
        mFiltrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }

    private void addDefaultDtcPic() {
        PicEntity picEntity = new PicEntity();
        picEntity.setDefault(true);
        mDTCEntity.add(picEntity);
        isDtcPicDefault = true;
    }

    private void addDefaultFaultPic() {
        PicEntity picEntity = new PicEntity();
        picEntity.setDefault(true);
        mFaultEntity.add(picEntity);
        isFaultPicDefault = true;
    }
}
