package com.yanxin.store.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.bean.CaseDetailBean;
import com.yanxin.store.bean.DictBean;
import com.yanxin.store.bean.FiltrateBean;
import com.yanxin.store.commont.Constant;
import com.yanxin.store.contract.PushCaseContract;
import com.yanxin.store.presenter.PushCasePresenter;
import com.yanxin.store.req.PushCaseReq;
import com.yanxin.store.ui.FiltrateWindow;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasicUtils;
import com.yanxin.store.utils.RealPathFromUriUtils;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;
import com.yanxin.store.utils.WeiboDialogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.yanxin.store.commont.Constant.AppConfig.ATTACH_SYS;
import static com.yanxin.store.commont.Constant.AppConfig.BRAND_PARENT;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_BRAND;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_MODEL;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_SYS;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_DOWN_READ_EXTERNAL_STORAGE;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_READ_EXTERNAL_STORAGE;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE;

@XmlLayoutResId(contentId = R.layout.activity_push_case)
public class PushCaseActivity extends BaseMvpActivity<PushCasePresenter> implements PushCaseContract.PushCaseView, EasyPermissions.PermissionCallbacks {
    private File pdfFile;
    private TextView mCaseTitle;
    private OptionalEditLayout mDescTitle;
    private TextView mCaseBrand;
    private TextView mCaseModel;
    private EditText mCaseEngine;
    private EditText mCaseGearbox;
    private EditText mCaseMileage;
    private OptionalEditLayout mCaseManufactureDay;
    private OptionalEditLayout mCaseVin;
    private EditText mCaseErrorContent;
    private TextView mCaseGearboxType;
    private TextView mCasePdf;
    private TextView mCaseDocument;
    //    private EditText mCaseSummary;
    private TextView mCasePrice;
    private Button mCaseSubmit;
    private LinearLayout mRemarksLayout;
    private TextView mRemarks;
    private PushCaseReq mPushCaseReq;
    private ArrayList<FiltrateBean> mBrandBean;
    private ArrayList<FiltrateBean> mModelBean;
    private ArrayList<FiltrateBean> mSystemBean;
    private FiltrateWindow mFiltrateWindow;
    private String queryBrandUuid;
    private String queryModelUuid;
    private String queryAttachSys;
    private Dialog loadDialogView;
    private String mPdfPath;
    private File mPDFParentPath = new File(Environment.getExternalStorageDirectory() + File.separator + "carPdf" + File.separator);
    private File mPDFPath = new File(mPDFParentPath.getPath(), "document.pdf");
    private boolean isRemarks;
    private String mPdfName;

    @Override
    protected void initMVPData() {
        mPresenter.queryAllBrand(BRAND_PARENT);
        mPresenter.queryAllSystem(ATTACH_SYS);
        mCaseSubmit.setOnClickListener(v -> {
            mPushCaseReq.setAmt(Integer.valueOf(mCasePrice.getText().toString()));
            mPushCaseReq.setAttachSys(queryAttachSys);
            mPushCaseReq.setBrandUuid(queryBrandUuid);
            mPushCaseReq.setModel(queryModelUuid);
            String caseErrorValue = BasicUtils.getEditValue(mCaseErrorContent);
            String mileage = BasicUtils.getEditValue(mCaseMileage);
            if (caseErrorValue.isEmpty()) {
                ToastUtils.showShort("?????????????????????");
                return;
            }
            if (mileage.isEmpty()) {
                ToastUtils.showShort("??????????????????");
                return;
            }
            mPushCaseReq.setFaultDesc(caseErrorValue);
            mPushCaseReq.setPowerInfo(BasicUtils.getEditValue(mCaseEngine) + "+" + BasicUtils.getEditValue(mCaseGearbox));
            mPushCaseReq.setMileage(Integer.parseInt(mileage));
            String vinValue = mCaseVin.getContentEditValue();
            if (!vinValue.isEmpty() && vinValue.length() != 17) {
                ToastUtils.showShort("???????????????????????????");
                return;
            }
            mPushCaseReq.setVin(vinValue);
            if (mPdfPath == null || mPdfPath.isEmpty()) {
                ToastUtils.showShort("?????????????????????????????????PDF");
                return;
            }
            if (queryModelUuid == null) {
                ToastUtils.showShort("?????????????????????");
                return;
            }
            ArrayList<String> pathList = new ArrayList<>();
            pathList.add(mPdfPath);
            mPushCaseReq.setCaseImgList(pathList);
            mPushCaseReq.setMadeTime(mCaseManufactureDay.getContentEditValue());
            mPushCaseReq.setTitle(mDescTitle.getContentEditValue());
            mPushCaseReq.setPafName(mPdfName);
            mPresenter.submitCase(mPushCaseReq);
        });
        mCaseBrand.setOnClickListener(v -> {
            if (mBrandBean.size() == 0) {
                ToastUtils.showShort("????????????????????????????????????");
            } else {
                showPOPWindow(FILTRATE_TYPE_BRAND, mCaseBrand, mBrandBean);
            }
        });
        mCaseModel.setOnClickListener(v -> {
            if (queryBrandUuid == null || queryBrandUuid.isEmpty()) {
                ToastUtils.showShort("????????????????????????");
            } else {
                showPOPWindow(FILTRATE_TYPE_MODEL, mCaseModel, mModelBean);
            }
        });
        mCaseGearboxType.setOnClickListener(v -> {
            if (mSystemBean.size() == 0) {
                ToastUtils.showShort("?????????????????????????????????");
            } else {
                showPOPWindow(FILTRATE_TYPE_SYS, mCaseGearboxType, mSystemBean);
            }
        });
        mCasePdf.setOnClickListener(v -> checkPermission(PER_READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE));

        mCaseDocument.setOnClickListener(v -> checkPermission(PER_DOWN_READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE));
        if (isRemarks) {
            mRemarksLayout.setVisibility(View.VISIBLE);
            mRemarks.setText(getIntent().getStringExtra("remarks"));
            initRemarks(getIntent().getStringExtra("case_uuid"));
        }
    }

    @SuppressLint("CheckResult")
    private void initRemarks(String caseUuid) {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryCaseDetail(MyApplication.getUserToken(), caseUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(caseDetailBean -> {
                            if (caseDetailBean.isSuccess()) {
                                intiValue(caseDetailBean.getData());
                            } else {
                                ToastUtils.showShort(caseDetailBean.getMsg());
                            }
                        }
                        , throwable -> {
                            ToastUtils.showShort(throwable.getMessage());
                        });

    }

    @SuppressLint("SetTextI18n")
    private void intiValue(CaseDetailBean.DataBean data) {
        mDescTitle.setContentEditView(data.getTitle());
        mCaseBrand.setText(data.getBrandName());
        queryBrandUuid = data.getBrandUuid();
        mCaseModel.setText(data.getModelName());
        queryModelUuid = data.getModel();
        String sp = "[+]";
        String[] split = data.getPowerInfo().split(sp);
        mCaseEngine.setText(split[0]);
        mCaseGearbox.setText(split[1]);
        mCaseMileage.setText(data.getMileage() + "");
        mCaseManufactureDay.setContentEditView(data.getMadeTime());
        mCaseVin.setContentEditView(data.getVin());
        mCaseErrorContent.setText(data.getFaultDesc());
        mCaseGearboxType.setText(data.getAttachSysName());
        queryAttachSys = data.getAttachSys();
        mCasePdf.setText(data.getPdfName());
    }

    private void showPOPWindow(int tag, View view, ArrayList<FiltrateBean> beans) {
        mFiltrateWindow = FiltrateWindow.newBuilder()
                .setWidth((int) getResources().getDimension(R.dimen.popup_window_filtrate_width))
                .setHeight((int) getResources().getDimension(R.dimen.popup_window_filtrate_height))
                .setOnArithmeticList(beans)
                .setOnDismissListener(() -> mFiltrateWindow = null)
                .setOnSelectFiltrateListener(filtrateBean -> {
                    if (tag == FILTRATE_TYPE_BRAND) {
                        if (filtrateBean.getName().equals("????????????")) {
                            queryBrandUuid = null;
                            queryModelUuid = null;
                            mCaseBrand.setText("????????????????????????");
                        } else {
                            queryBrandUuid = filtrateBean.getUuid();
                            mPresenter.queryAllModel(queryBrandUuid);
                            mCaseBrand.setText(filtrateBean.getName());
                        }
                        mCaseModel.setText("????????????????????????");
                    } else if (tag == FILTRATE_TYPE_MODEL) {
                        if (filtrateBean.getName().equals("????????????")) {
                            queryModelUuid = null;
                            mCaseModel.setText("????????????????????????");
                        } else {
                            queryModelUuid = filtrateBean.getUuid();
                            mCaseModel.setText(filtrateBean.getName());
                        }
                    } else if (tag == FILTRATE_TYPE_SYS) {
                        if (filtrateBean.getName().equals("????????????")) {
                            queryAttachSys = null;
                            mCaseGearboxType.setText("??????????????????????????????");
                        } else {
                            queryAttachSys = filtrateBean.getUuid();
                            mCaseGearboxType.setText(filtrateBean.getName());
                        }
                    }
                })
                .build(PushCaseActivity.this);
        mFiltrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }

    @Override
    protected void initMVPView() {
        mBrandBean = new ArrayList<>();
        mModelBean = new ArrayList<>();
        mSystemBean = new ArrayList<>();
        mPushCaseReq = new PushCaseReq();
        mCaseTitle = findViewById(R.id.case_title);
        mDescTitle = findViewById(R.id.desc_title);
        mCaseBrand = findViewById(R.id.case_brand);
        mCaseModel = findViewById(R.id.case_model);
        mCaseEngine = findViewById(R.id.case_engine);
        mCaseGearbox = findViewById(R.id.case_gearbox);
        mCaseMileage = findViewById(R.id.case_mileage);
        mCaseManufactureDay = findViewById(R.id.case_manufacture_day);
        mCaseVin = findViewById(R.id.case_vin);
        mCaseErrorContent = findViewById(R.id.case_error_content);
        mCaseGearboxType = findViewById(R.id.case_gearbox_type);
        mCasePdf = findViewById(R.id.case_pdf);
        mCaseDocument = findViewById(R.id.case_document);
//        mCaseSummary = findViewById(R.id.case_summary);
        mCasePrice = findViewById(R.id.case_price);
        mCaseSubmit = findViewById(R.id.case_submit);
        mRemarksLayout = findViewById(R.id.remarks_layout);
        mRemarks = findViewById(R.id.remarks);
        isRemarks = getIntent().getBooleanExtra("is_remarks", false);
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
        return PushCasePresenter.newInstance();
    }

    /**
     * ????????????
     *
     * @param perStatus
     * @param permission
     */
    private void checkPermission(int perStatus, String... permission) {
        if (perStatus == PER_WRITE_EXTERNAL_STORAGE) {
            if (EasyPermissions.hasPermissions(getBaseContext(), permission)) {
                startPdfActivity();
            } else {
                EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, perStatus, permission).build());
            }
        } else if (perStatus == PER_DOWN_READ_EXTERNAL_STORAGE) {
            if (EasyPermissions.hasPermissions(getBaseContext(), permission)) {
                startActivity(new Intent(getBaseContext(), WebActivity.class)
                        .putExtra("pdf_path", Constant.AppConfig.CASE_DOCUMENT));
            } else {
                EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, perStatus, permission).build());
            }
        }
    }

    @SuppressLint("CheckResult")
    private void downPdf(String caseDocument) {
        loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "????????????...");
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .downPdfFile(caseDocument)
                .subscribeOn(Schedulers.io())
                .subscribe(responseBody -> downProcess(responseBody)
                        , throwable -> WeiboDialogUtils.closeDialog(loadDialogView));
    }

    private void downProcess(ResponseBody body) throws IOException {
        if (!mPDFPath.exists()) {
            mPDFPath.getParentFile().mkdirs();
        }
        InputStream inputStream;
        OutputStream outputStream;
        byte[] fileReader = new byte[4096];
        long fileSize = body.contentLength();
        long fileSizeDownloaded = 0;
        inputStream = body.byteStream();
        outputStream = new FileOutputStream(mPDFPath);
        while (true) {
            int read = inputStream.read(fileReader);
            if (read == -1) {
                break;
            }
            outputStream.write(fileReader, 0, read);
            fileSizeDownloaded += read;
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        runOnUiThread(() -> {
            WeiboDialogUtils.closeDialog(loadDialogView);
            startActivity(new Intent(getBaseContext(), PDFActivity.class)
                    .putExtra("pdf_path", mPDFPath.getAbsolutePath()));
        });
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PER_WRITE_EXTERNAL_STORAGE) {
            startPdfActivity();
        } else if (requestCode == PER_DOWN_READ_EXTERNAL_STORAGE) {
            startActivity(new Intent(getBaseContext(), WebActivity.class)
                    .putExtra("pdf_path", Constant.AppConfig.CASE_DOCUMENT));
        }
    }

    private void startPdfActivity() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PER_WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        ToastUtils.showShort("???????????????????????????");
    }

    @Override
    public void queryAllSystemSuccess(ArrayList<DictBean.DataBean> brandBean) {
        for (DictBean.DataBean dataBean : brandBean) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName(dataBean.getLableDesc());
            filtrateBean.setUuid(dataBean.getUuid());
            filtrateBean.setType(FILTRATE_TYPE_SYS);
            mSystemBean.add(filtrateBean);
        }
        if (mSystemBean.size() != 0) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName("????????????");
            mSystemBean.add(0, filtrateBean);
        }
    }

    @Override
    public void queryAllSystemFailed(String msg) {

    }

    @Override
    public void submitSuccess(String msg) {
        ToastUtils.showShort("????????????, ?????????????????????");
        finish();
    }

    @Override
    public void uploadFileFailed(String msg) {
        WeiboDialogUtils.closeDialog(loadDialogView);
    }

    @Override
    public void uploadFileSuccess(String path) {
        WeiboDialogUtils.closeDialog(loadDialogView);
        this.mPdfPath = path;
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
        if (mBrandBean.size() != 0) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName("????????????");
            mBrandBean.add(0, filtrateBean);
        }
    }

    @Override
    public void queryAllBrandFailed(String msg) {

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
        if (mModelBean.size() != 0) {
            FiltrateBean filtrateBean = new FiltrateBean();
            filtrateBean.setName("????????????");
            mModelBean.add(0, filtrateBean);
        }
    }

    @Override
    public void queryAllModelFailed(String msg) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PER_WRITE_EXTERNAL_STORAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                pdfFile = new File(RealPathFromUriUtils.getRealPathFromUri(getBaseContext(), data.getData()));
                mPdfName = pdfFile.getName();
                mPresenter.uploadFile(pdfFile);
                mCasePdf.setText(pdfFile.getName());
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
            }
        }
    }
}
