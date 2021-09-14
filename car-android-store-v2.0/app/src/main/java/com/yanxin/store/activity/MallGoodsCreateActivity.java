package com.yanxin.store.activity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.MallCreateMaterialsAdapter;
import com.yanxin.store.adapter.rvadapter.PicAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CityBean;
import com.yanxin.store.bean.CreateMaterialsBean;
import com.yanxin.store.bean.FiltrateBean;
import com.yanxin.store.bean.GoodsDetailBean;
import com.yanxin.store.bean.GoodsSubstanceBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.contract.CreateGoodsContract;
import com.yanxin.store.entity.PicEntity;
import com.yanxin.store.event.MallGoodsEvent;
import com.yanxin.store.presenter.CreateGoodsPresenter;
import com.yanxin.store.req.CreateMallReq;
import com.yanxin.store.ui.FiltrateWindow;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasePOPWindow;
import com.yanxin.store.utils.BasicUtils;
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

import static com.yanxin.store.commont.Constant.AppConfig.BRAND_PARENT;
import static com.yanxin.store.commont.Constant.AppConfig.TECHNICIAN_TYPE;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_BRAND;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_MODEL;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_CAMERA;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE;

@XmlLayoutResId(contentId = R.layout.activity_mall_goods_create)
public class MallGoodsCreateActivity extends BaseMvpActivity<CreateGoodsPresenter> implements CreateGoodsContract.CreateGoodsView, EasyPermissions.PermissionCallbacks {
    private OptionsPickerView mTechnicianPickerView;
    private OptionsPickerView mCityPickerView;
    private OptionsPickerView mGoodsListOnePickView;
    private OptionsPickerView mGoodsListTwoPickView;

    private ArrayList<FiltrateBean> mBrandBean;
    private ArrayList<FiltrateBean> mModelBean;
    private ArrayList<CreateMaterialsBean> materialsBeans;
    private ArrayList<PicEntity> mPicEntity;

    private TextView mGoodsCreateTitle;
    private NestedScrollView mMallCreateGoodsLayout;
    private TextView mCreateTitleTv;
    private TextView mCreateOneType;
    private TextView mCreateTwoType;
    private TextView mCreateGoodsType;
    private TextView mCreateBrand;
    private TextView mCreateModel;
    private RecyclerView mWuliaoRv;
    private OptionalEditLayout mGoodsCity;
    private EditText mGoodsAddress;
    private EditText mMallServiceAmount;
    private EditText mMallAmCount;
    private EditText mMallPmCount;
    private EditText mMallNmCount;
    private RadioGroup mServiceWay;
    private EditText mGoodsName;
    private RadioButton mServiceInStore;
    private RadioButton mServiceInHome;
    private LinearLayout mMallKuCunLayout;
    private EditText mMallKuCun;
    private LinearLayout mMallSmfLayout;
    private EditText mMallSmf;
    private EditText mSiteFault;
    private RecyclerView mSiteFaultImg;
    private RecyclerView mShangPinRv;
    private ImageView mZhuPic;
    private String mZhuPath;
    private ImageView mZhuPicClear;
    private LinearLayout mMallTabListTips;
    private RadioGroup mMallType;
    private RadioButton mMallTypeShangJia;
    private RadioButton mMallTypeKuCun;
    private Button mMallCreateGoodsSubmit;

    private String companyAddressCity;
    private String companyAddressCounty;
    private String companyAddressProvince;
    private String mMallTypeUuid;  //商品类型
    private String mGoodsOneUuid;
    private String mGoodsTwoUuid;
    private String queryBrandUuid;
    private String queryModelUuid;
    private FiltrateWindow mFiltrateWindow;
    private File cameraFile;
    private PopupWindow mPopWindow;
    private PicEntity mCurrentPicEntity;
    private int mCurrentPosition;
    private boolean isPicDefault;
    private Dialog loadDialogView;

    private MallCreateMaterialsAdapter createMaterialsAdapter;
    private PicAdapter mPicAdapter;
    private int PIC_TYPE;  //0=商品主图,1=商品其他
    private boolean isEditor;
    private String mallUuid;
    private String storeUuid;


    @Override
    protected void initMVPData() {
        mPresenter.queryOneTypeBean("-1");
        mPresenter.queryAllBrand(BRAND_PARENT);
        mPresenter.queryCity();
        mPresenter.queryTechnicianTypeBean(TECHNICIAN_TYPE);
        mCreateBrand.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            if (mBrandBean.size() == 0) {
                ToastUtils.showShort("正在获取品牌数据，请稍后");
            } else {
                showPOPWindow(FILTRATE_TYPE_BRAND, mCreateBrand, mBrandBean);
            }
        });
        mCreateModel.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            if (queryBrandUuid == null || queryBrandUuid.isEmpty()) {
                ToastUtils.showShort("请先选择车辆品牌");
            } else {
                showPOPWindow(FILTRATE_TYPE_MODEL, mCreateModel, mModelBean);
            }
        });
        mCreateOneType.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            if (mGoodsListOnePickView != null) {
                mGoodsListOnePickView.show();
            }
        });
        mCreateTwoType.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            if (mGoodsListTwoPickView != null) {
                mGoodsListTwoPickView.show();
            } else {
                ToastUtils.showShort("请先选择一级分类");
            }
        });
        mCreateGoodsType.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            if (mTechnicianPickerView != null) {
                mTechnicianPickerView.show();
            }
        });
        mGoodsCity.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            if (mCityPickerView != null) {
                mCityPickerView.show();
            }
        });
        mServiceInStore.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mMallKuCunLayout.setVisibility(View.VISIBLE);
                mMallSmfLayout.setVisibility(View.GONE);
            } else {
                mMallKuCunLayout.setVisibility(View.GONE);
                mMallSmfLayout.setVisibility(View.VISIBLE);
            }
        });
        mServiceInHome.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mMallKuCunLayout.setVisibility(View.GONE);
                mMallSmfLayout.setVisibility(View.VISIBLE);
            } else {
                mMallKuCunLayout.setVisibility(View.VISIBLE);
                mMallSmfLayout.setVisibility(View.GONE);
            }
        });
        mPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            KeyboardUtils.hideSoftInput(this);
            PIC_TYPE = 1;
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
        createMaterialsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (materialsBeans.get(position).isDefault()) {
                addDefaultWuLiao(false);
            } else {
                materialsBeans.remove(position);
                createMaterialsAdapter.notifyDataSetChanged();
            }
        });
        createMaterialsAdapter.setValueCall(new MallCreateMaterialsAdapter.ValueCall() {
            @Override
            public void nameValue(int position, String str) {
                materialsBeans.get(position).setName(str);
            }

            @Override
            public void amountValue(int position, String str) {
                materialsBeans.get(position).setAmount(str);
            }
        });
        mZhuPic.setOnClickListener(v -> {
            PIC_TYPE = 0;
            createPopWindow();
        });
        addDefaultWuLiao(true);
        if (!isEditor) {
            addDefaultStorePic();
        }
        mMallCreateGoodsSubmit.setOnClickListener(v -> {
            CreateMallReq createMallReq = new CreateMallReq();
            createMallReq.setGoodsName(BasicUtils.getEditValue(mGoodsName));
            createMallReq.setGoodsType(mMallTypeUuid);
            createMallReq.setParentType(mGoodsOneUuid);
            createMallReq.setSubType(mGoodsTwoUuid);
            createMallReq.setVehicleBrand(queryBrandUuid);
            createMallReq.setVehicleModel(queryModelUuid);
            ArrayList<CreateMallReq.DetailListBean> listBeans = new ArrayList<>();
            for (CreateMaterialsBean materialsBean : materialsBeans) {
                CreateMallReq.DetailListBean detailListBean = new CreateMallReq.DetailListBean();
                detailListBean.setActAmt(Float.parseFloat(materialsBean.getAmount()));
                detailListBean.setName(materialsBean.getName());
                listBeans.add(detailListBean);
            }
            createMallReq.setDetailList(listBeans);
            createMallReq.setAddr(mGoodsCity.getContentTextValue() + "," + BasicUtils.getEditValue(mGoodsAddress));
            createMallReq.setReceiveMethod(mServiceInStore.isChecked() ? 1 : 2);
            if (mServiceInStore.isChecked()) {
                createMallReq.setSurplusNum(Integer.parseInt(BasicUtils.getEditValue(mMallKuCun)));
            } else {
                createMallReq.setVisitFee(Float.parseFloat(BasicUtils.getEditValue(mMallSmf)));
            }
            createMallReq.setGoodsDescribe(BasicUtils.getEditValue(mSiteFault));
            ArrayList<CreateMallReq.GoodsImgListReqBean> imgPoBean = new ArrayList<>();
            CreateMallReq.GoodsImgListReqBean goodsImgListReqBean = new CreateMallReq.GoodsImgListReqBean();
            goodsImgListReqBean.setImgType(0);
            goodsImgListReqBean.setImgPath(mZhuPath);
            imgPoBean.add(goodsImgListReqBean);
            for (PicEntity picEntity : mPicEntity) {
                if (picEntity.getPath() != null && !picEntity.getPath().isEmpty()) {
                    CreateMallReq.GoodsImgListReqBean neImgListReqBean = new CreateMallReq.GoodsImgListReqBean();
                    neImgListReqBean.setImgType(1);
                    neImgListReqBean.setImgPath(picEntity.getPath());
                    imgPoBean.add(neImgListReqBean);
                }
            }
            createMallReq.setGoodsImgListReq(imgPoBean);
            createMallReq.setAmServeNum(Integer.parseInt(BasicUtils.getEditValue(mMallAmCount)));
            createMallReq.setPmServeNum(Integer.parseInt(BasicUtils.getEditValue(mMallPmCount)));
            createMallReq.setNmServeNum(Integer.parseInt(BasicUtils.getEditValue(mMallNmCount)));
            createMallReq.setSellSts(mMallTypeShangJia.isChecked() ? 1 : 0);
            if (isEditor) {
                createMallReq.setUuid(mallUuid);
                createMallReq.setStoreUuid(storeUuid);
                mPresenter.updateMallGoods(createMallReq);
            } else {
                mPresenter.addMallGoods(createMallReq);
            }
        });
        if (isEditor) {
            mPresenter.queryGoodsDetail(mallUuid);
        }

    }

    private void addDefaultWuLiao(boolean isDefault) {
        CreateMaterialsBean createMaterialsBean = new CreateMaterialsBean();
        createMaterialsBean.setDefault(isDefault);
        materialsBeans.add(createMaterialsBean);
        createMaterialsAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initMVPView() {
        mBrandBean = new ArrayList<>();
        mModelBean = new ArrayList<>();
        materialsBeans = new ArrayList<>();
        mPicEntity = new ArrayList<>();
        isEditor = getIntent().getBooleanExtra("is_editor", false);
        mallUuid = getIntent().getStringExtra("mall_uuid");
        mGoodsCreateTitle = findViewById(R.id.goods_create_title);
        mMallCreateGoodsLayout = findViewById(R.id.mall_create_goods_layout);
        mCreateTitleTv = findViewById(R.id.create_title_tv);
        mCreateOneType = findViewById(R.id.create_one_type);
        mCreateTwoType = findViewById(R.id.create_two_type);
        mCreateGoodsType = findViewById(R.id.create_goods_type);
        mCreateBrand = findViewById(R.id.create_brand);
        mCreateModel = findViewById(R.id.create_model);
        mWuliaoRv = findViewById(R.id.wuliao_rv);
        mGoodsCity = findViewById(R.id.goods_city);
        mGoodsAddress = findViewById(R.id.goods_address);
        mMallServiceAmount = findViewById(R.id.mall_service_amount);
        mServiceWay = findViewById(R.id.service_way);
        mGoodsName = findViewById(R.id.mall_goods_name);
        mServiceInStore = findViewById(R.id.service_in_store);
        mServiceInHome = findViewById(R.id.service_in_home);
        mMallKuCunLayout = findViewById(R.id.mall_kucun_layout);
        mMallKuCun = findViewById(R.id.mall_kucun);
        mMallSmfLayout = findViewById(R.id.mall_smf_layout);
        mMallSmf = findViewById(R.id.mall_smf);
        mSiteFault = findViewById(R.id.site_fault);
        mSiteFaultImg = findViewById(R.id.site_fault_img);
        mShangPinRv = findViewById(R.id.shangpin_rv);
        mZhuPic = findViewById(R.id.sp_zhu_pic);
        mZhuPicClear = findViewById(R.id.sp_zhu_clear);
        mMallTabListTips = findViewById(R.id.mall_tab_list_tips);
        mMallType = findViewById(R.id.mall_type);
        mMallAmCount = findViewById(R.id.am_count);
        mMallPmCount = findViewById(R.id.pm_count);
        mMallNmCount = findViewById(R.id.nm_count);
        mMallTypeShangJia = findViewById(R.id.mall_type_shangjia);
        mMallTypeKuCun = findViewById(R.id.mall_type_kucun);
        mMallCreateGoodsSubmit = findViewById(R.id.mall_create_goods_submit);
        createMaterialsAdapter = new MallCreateMaterialsAdapter(R.layout.item_goods_create_materials_name, materialsBeans);
        mPicAdapter = new PicAdapter(R.layout.include_store_register_pic, mPicEntity);
        mWuliaoRv.setAdapter(createMaterialsAdapter);
        mShangPinRv.setAdapter(mPicAdapter);
    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return CreateGoodsPresenter.newInstance();
    }

    /**
     * 跳到隐私政策等协议页面
     *
     * @param agreeType
     */
    private void startAgreeActivity(int agreeType) {
        startActivity(new Intent(getBaseContext(), AgreementActivity.class).putExtra("agree_tag", agreeType));
    }


    @Override
    public void failed(String errorMsg) {
        WeiboDialogUtils.closeDialog(loadDialogView);
        ToastUtils.showShort(errorMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void queryGoodsDetail(GoodsDetailBean.DataBean goodsBean) {
        storeUuid = goodsBean.getStoreUuid();
        String[] addSplit = goodsBean.getAddr().split(",");
        mGoodsName.setText(goodsBean.getGoodsName());
        mGoodsOneUuid = goodsBean.getLevelOneUuid();
        mCreateOneType.setText(goodsBean.getLevelOne());
        mGoodsTwoUuid = goodsBean.getLevelTwoUuid();
        mCreateTwoType.setText(goodsBean.getLevelTwo());
        mCreateGoodsType.setText(goodsBean.getGoodsTypeName());
        mCreateBrand.setText(goodsBean.getVehicleBrandName());
        queryBrandUuid = goodsBean.getVehicleBrand();
        mCreateModel.setText(goodsBean.getVehicleModelName());
        queryModelUuid = goodsBean.getVehicleModel();
        ArrayList<GoodsDetailBean.DataBean.DetailListBean> detailList = goodsBean.getDetailList();
        materialsBeans.clear();
        for (int i = 0; i < detailList.size(); i++) {
            CreateMaterialsBean createMaterialsBean = new CreateMaterialsBean();
            createMaterialsBean.setName(detailList.get(i).getName());
            if (i == 0) {
                createMaterialsBean.setDefault(true);
            } else {
                createMaterialsBean.setDefault(false);
            }
            createMaterialsBean.setAmount(detailList.get(i).getActAmt() + "");
            materialsBeans.add(createMaterialsBean);
        }
        createMaterialsAdapter.notifyDataSetChanged();
        mGoodsCity.setContentTextView(addSplit[0]);
        mGoodsAddress.setText(addSplit[1]);
        if (goodsBean.getReceiveMethod() == 1) {
            mMallKuCunLayout.setVisibility(View.VISIBLE);
            mMallSmfLayout.setVisibility(View.GONE);
            mServiceInStore.setChecked(true);
            mServiceInHome.setChecked(false);
            mMallKuCun.setText(goodsBean.getSurplusNum() + "");
        } else {
            mMallSmfLayout.setVisibility(View.VISIBLE);
            mMallKuCunLayout.setVisibility(View.GONE);
            mServiceInHome.setChecked(true);
            mServiceInStore.setChecked(false);
            mMallSmf.setText(goodsBean.getVisitFee() + "");
        }
        mSiteFault.setText(goodsBean.getGoodsDescribe());
        ArrayList<GoodsDetailBean.DataBean.ImgListBean> imgList = goodsBean.getImgList();
        for (GoodsDetailBean.DataBean.ImgListBean imgListBean : imgList) {
            if (imgListBean.getImgType() == 0) {
                mZhuPath = imgListBean.getImgPath();
                mZhuPicClear.setVisibility(View.VISIBLE);
                Glide.with(getBaseContext())
                        .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_upload_pic))
                        .load(mZhuPath).into(mZhuPic);
            } else {
                PicEntity picEntity = new PicEntity();
                picEntity.setNetWork(true);
                picEntity.setPath(imgListBean.getImgPath());
                mPicEntity.add(picEntity);
            }
        }
        mPicAdapter.notifyDataSetChanged();
        mMallAmCount.setText(goodsBean.getAmServeNum() + "");
        mMallPmCount.setText(goodsBean.getPmServeNum() + "");
        mMallNmCount.setText(goodsBean.getNmServeNum() + "");
    }

    @Override
    public void uploadSuccess(String path) {
        WeiboDialogUtils.closeDialog(loadDialogView);
        if (PIC_TYPE == 0) {
            this.mZhuPath = path;
            mZhuPicClear.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.pic_card_upload_positive))
                    .load(cameraFile).into(mZhuPic);
        } else {
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
        }
        WeiboDialogUtils.closeDialog(loadDialogView);
    }

    @Override
    public void addMallGoods(String uuid) {
        startActivity(new Intent(getBaseContext(), CreateStatusActivity.class));
    }

    @Override
    public void updateMallGoods(GoodsDetailBean.DataBean goodsBean) {
        EventBus.getDefault().post(new MallGoodsEvent());
        ToastUtils.showShort("修改成功");
        finish();
    }

    @Override
    public void queryTechnicianTypeSuccess(ArrayList<TechnicianTypeBean.DataBean> dataBeans) {
        mTechnicianPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String engineValue = dataBeans.get(options1).getLableDesc();
            mCreateGoodsType.setText(engineValue);
            mMallTypeUuid = dataBeans.get(options1).getUuid();
        }).build();
        mTechnicianPickerView.setPicker(dataBeans);
    }

    @Override
    public void queryCitySuccess(ArrayList<CityBean.DataBean> cityBean, ArrayList<ArrayList<CityBean.DataBean>> areaBean, ArrayList<ArrayList<ArrayList<CityBean.DataBean>>> addressBean) {
        mCityPickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String cityAddress = String.format("%s%s%s", cityBean.get(options1).getAreaName()
                    , areaBean.get(options1).get(options2).getAreaName()
                    , addressBean.get(options1).get(options2).get(options3).getAreaName());
            mGoodsCity.setContentTextView(cityAddress);
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
    public void queryOneTypeBean(ArrayList<GoodsSubstanceBean.DataBean> subBean) {
        mGoodsListOnePickView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            GoodsSubstanceBean.DataBean dataBean = subBean.get(options1);
            mCreateOneType.setText(dataBean.getGroupName());
            mGoodsOneUuid = dataBean.getUuid();
            mPresenter.queryTwoTypeBean(mGoodsOneUuid);
        }).build();
        mGoodsListOnePickView.setPicker(subBean);
    }

    @Override
    public void queryTwoTypeBean(ArrayList<GoodsSubstanceBean.DataBean> subBean) {
        mGoodsListTwoPickView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            GoodsSubstanceBean.DataBean dataBean = subBean.get(options1);
            mCreateTwoType.setText(dataBean.getGroupName());
            mGoodsTwoUuid = dataBean.getUuid();
        }).build();
        mGoodsListTwoPickView.setPicker(subBean);
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
                        mCreateBrand.setText(filtrateBean.getName());
                        mCreateModel.setText("");
                    } else if (tag == FILTRATE_TYPE_MODEL) {
                        queryModelUuid = filtrateBean.getUuid();
                        mCreateModel.setText(filtrateBean.getName());
                    }
                })
                .build(this);
        mFiltrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
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

    private void createPopWindow() {
        if (mPopWindow == null || !mPopWindow.isShowing()) {
            mPopWindow = BasePOPWindow.createPopWindow(MallGoodsCreateActivity.this, popView -> {
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
                cameraFile = new File(RealPathFromUriUtils.getRealPathFromUri(getBaseContext(), data.getData()));
                mPresenter.uploadFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "上传中...");
            }
        }
    }

    private void addDefaultStorePic() {
        PicEntity picEntity = new PicEntity();
        picEntity.setDefault(true);
        mPicEntity.add(picEntity);
        isPicDefault = true;
        mPicAdapter.notifyDataSetChanged();
    }
}
