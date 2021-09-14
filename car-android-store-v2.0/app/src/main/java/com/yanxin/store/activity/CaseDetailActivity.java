package com.yanxin.store.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.bean.CaseDetailBean;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasicUtils;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.yanxin.store.commont.Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE;

@XmlLayoutResId(contentId = R.layout.activity_case_detai)
public class CaseDetailActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private String caseUuid;
    private OptionalEditLayout mDetailName;
    private OptionalEditLayout mDetailYear;
    private OptionalEditLayout mDetailPushTime;
    private TextView mDetailTitle;
    private TextView mDetailBrandName;
    private TextView mDetailModelName;
    private TextView mDetailPowerInfo;
    private TextView mDetailDistance;
    private TextView mDetailManufactureTime;
    private TextView mDetailVin;
    private TextView mDetailFailureContent;
    private TextView mDetailFailureSystem;
    private TextView mDetailProcessPdf;
    private ImageView mDetailView;
    private TextView mDetailSummarize;
    private TextView mDetailContent;
    private TextView mDetailScoreNum;
    private LinearLayout mDetailScoreStar;
    private RadioGroup mDetailScoreReviews;
    private TextView mDetailPrice;
    private TextView mDetailBuy;
    private String pdfPath;
    private String pdfName;
    private ProgressBar mDownProcess;
    private File mPDFParentPath = new File(Environment.getExternalStorageDirectory() + File.separator + "carPdf" + File.separator);
    private File mPDFPath;
    private LinearLayout mCasePushLayout;
    private OptionalEditLayout mCaseBuyNum;
    private LinearLayout mBuyLayout;

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryCaseDetail(MyApplication.getUserToken(), caseUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(caseDetailBean -> {
                            if (caseDetailBean.isSuccess()) {
                                initCaseDetail(caseDetailBean.getData());
                            } else {
                                ToastUtils.showShort(caseDetailBean.getMsg());
                            }
                        }
                        , throwable -> {
                            finish();
                            ToastUtils.showLong("该案例未检测到诊断思路与过程文档, 请查看其它案例");
                        });
        mDetailBuy.setOnClickListener(v -> buyCase(caseUuid));
    }

    @SuppressLint("CheckResult")
    private void buyCase(String caseUuid) {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .buyCase(MyApplication.getUserToken(), caseUuid)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(caseDetailBean -> {
                            if (caseDetailBean.isSuccess()) {
                                MyApplication.setPayOrderUuid(caseUuid);
                                MyApplication.setPayStatusType(0);
                                Intent intent = new Intent(getBaseContext(), PayActivity.class);
                                intent.putExtra("order_uuid", caseDetailBean.getData());
                                startActivity(intent);
                            }
                        }
                        , throwable -> ToastUtils.showLong(throwable.getMessage()));
    }

    /**
     * 获取详情填充数据
     *
     * @param caseDetailBean
     */
    private void initCaseDetail(CaseDetailBean.DataBean caseDetailBean) {
        if (caseDetailBean.getTechnicianType() == 3) {
            mDetailName.setDescTextView("店铺名称：");
            mDetailYear.setDescTextView("店铺类型：");
            mDetailName.setContentTextView(caseDetailBean.getStoreName());
            mDetailYear.setContentTextView(caseDetailBean.getStoreType().equals("102") ? "4S店" : "其它");
        } else {
            mDetailName.setDescTextView("发布技师：");
            mDetailYear.setDescTextView("技师工龄：");
            mDetailName.setContentTextView(caseDetailBean.getUserName());
            mDetailYear.setContentTextView(caseDetailBean.getWorkingYear() + "年");
        }
        mDetailTitle.setText(caseDetailBean.getTitle());
        mDetailPushTime.setContentTextView(caseDetailBean.getCreatedTime());
        mDetailBrandName.setText(caseDetailBean.getBrandName());
        mDetailModelName.setText(caseDetailBean.getModelName());
        mDetailPowerInfo.setText(caseDetailBean.getPowerInfo());
        mDetailDistance.setText(caseDetailBean.getMileage() + "km");
        mDetailManufactureTime.setText(caseDetailBean.getMadeTime());
        String vin = caseDetailBean.getVin();
        mDetailVin.setText(vin.length() < 8 ? vin : BasicUtils.setStringValue(vin.length() - 8, vin, '*'));
        mDetailFailureContent.setText(caseDetailBean.getFaultDesc());
        mDetailFailureSystem.setText(caseDetailBean.getAttachSysName());
        mDetailPrice.setText("案例价格：¥" + caseDetailBean.getAmt());
        pdfPath = caseDetailBean.getCaseImgList().get(0);
        pdfName = pdfPath.substring(pdfPath.lastIndexOf("/") + 1);
        mPDFPath = new File(mPDFParentPath.getPath(), pdfName);
        mCaseBuyNum.setContentTextView(caseDetailBean.getSalesVolume() + "次");
        if (caseDetailBean.getTechnicianUuid().equals(MyApplication.getUserUuid())) {
            mCasePushLayout.setVisibility(View.GONE);
            mDetailProcessPdf.setText("点击查看诊断思路与过程PDF文档");
            mDetailProcessPdf.setOnClickListener(v -> {
                startReader(pdfPath);
            });
            /*checkPermission(PER_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)*/
            mDetailContent.setVisibility(View.VISIBLE);
            mDetailSummarize.setVisibility(View.INVISIBLE);
//            mDetailContent.setText(caseDetailBean.getSummary());
            mBuyLayout.setVisibility(View.GONE);
        } else {
            if (caseDetailBean.getPurchase() == 1) {
                mCasePushLayout.setVisibility(View.GONE);
                mDetailProcessPdf.setText("点击查看诊断思路与过程PDF文档");
//                mDetailProcessPdf.setOnClickListener(v -> checkPermission(PER_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE));
                mDetailProcessPdf.setOnClickListener(v -> /*checkPermission(PER_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)*/
                        startReader(pdfPath));
                mDetailContent.setVisibility(View.VISIBLE);
                mDetailSummarize.setVisibility(View.INVISIBLE);
//                mDetailContent.setText(caseDetailBean.getSummary());
                mBuyLayout.setVisibility(View.GONE);
            } else {
                mDetailProcessPdf.setText("购买后可查看");
                mCasePushLayout.setVisibility(View.VISIBLE);
                mDetailProcessPdf.setOnClickListener(v -> ToastUtils.showShort("请先购买"));
            }
        }

    }

    private void startReader(String path) {
        String dex = BasicUtils.getFileFormat(path);
        if (dex.equals("pdf")) {
            startActivity(new Intent(getBaseContext(), WebActivity.class)
                    .putExtra("pdf_path", path));
//            downPdfFile();
//            checkPermission(PER_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            Intent intent = new Intent(getBaseContext(), ImageActivity.class);
            intent.putExtra("url", path);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                    CaseDetailActivity.this, mDetailView, "bigImg")
                    .toBundle());
        }
    }

    @Override
    protected void initView() {
        caseUuid = getIntent().getStringExtra("case_uuid");
        mDetailName = findViewById(R.id.detail_name);
        mDetailYear = findViewById(R.id.detail_year);
        mDetailPushTime = findViewById(R.id.detail_push_time);
        mDetailTitle = findViewById(R.id.detail_title);
        mDetailBrandName = findViewById(R.id.detail_brand_name);
        mDetailModelName = findViewById(R.id.detail_model_name);
        mDetailPowerInfo = findViewById(R.id.detail_power_info);
        mDetailDistance = findViewById(R.id.detail_distance);
        mDetailManufactureTime = findViewById(R.id.detail_manufacture_time);
        mDetailVin = findViewById(R.id.detail_vin);
        mDetailFailureContent = findViewById(R.id.detail_failure_content);
        mDetailFailureSystem = findViewById(R.id.detail_failure_system);
        mDetailProcessPdf = findViewById(R.id.detail_process_pdf);
        mDetailView = findViewById(R.id.case_view);
        mDetailSummarize = findViewById(R.id.detail_summarize);
        mDetailContent = findViewById(R.id.detail_summary_content);
        mDetailScoreNum = findViewById(R.id.detail_score_num);
        mDetailScoreStar = findViewById(R.id.detail_score_star);
        mDetailScoreReviews = findViewById(R.id.detail_score_reviews);
        mDetailPrice = findViewById(R.id.detail_price);
        mDetailBuy = findViewById(R.id.detail_buy);
        mDownProcess = findViewById(R.id.down_process);
        mCasePushLayout = findViewById(R.id.case_push_layout);
        mCaseBuyNum = findViewById(R.id.detail_buy_num);
        mBuyLayout = findViewById(R.id.buy_layout);

    }

    private void checkPermission(int perStatus, String writeExternalStorage) {
        if (perStatus == PER_WRITE_EXTERNAL_STORAGE) {
            if (EasyPermissions.hasPermissions(getBaseContext(), writeExternalStorage)) {
                QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
                    @Override
                    public void onViewInitFinished(boolean arg0) {
                        // TODO Auto-generated method stub
                        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                        LogUtils.e(" onViewInitFinished is " + arg0);
                    }

                    @Override
                    public void onCoreInitFinished() {
                        // TODO Auto-generated method stub
                    }
                };
                //x5内核初始化接口
                QbSdk.initX5Environment(getApplicationContext(), cb);
                downPdfFile();
            } else {
                EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, PER_WRITE_EXTERNAL_STORAGE, writeExternalStorage).build());
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PER_WRITE_EXTERNAL_STORAGE) {
            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
                @Override
                public void onViewInitFinished(boolean arg0) {
                    // TODO Auto-generated method stub
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    LogUtils.e(" onViewInitFinished is " + arg0);
                }

                @Override
                public void onCoreInitFinished() {
                    // TODO Auto-generated method stub
                }
            };
            //x5内核初始化接口
            QbSdk.initX5Environment(getApplicationContext(), cb);
//            downPdfFile();
        }
    }

    @SuppressLint("CheckResult")
    private void downPdfFile() {
        mDetailProcessPdf.setVisibility(View.INVISIBLE);
        mDownProcess.setVisibility(View.VISIBLE);
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .downPdfFile(pdfPath)
                .subscribeOn(Schedulers.io())
                .subscribe(responseBody -> {
                    downProcess(responseBody);
                }, throwable -> ToastUtils.showShort(throwable.getMessage()));
    }

    private void downProcess(ResponseBody body) throws IOException {
        if (!mPDFPath.exists()) {
            mPDFPath.getParentFile().mkdirs();
        }
        InputStream inputStream;
        OutputStream outputStream;
        byte[] fileReader = new byte[4096];
        long fileSize = body.contentLength();
        mDownProcess.setMax((int) (fileSize / 100));
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
            mDownProcess.setProgress((int) (fileSizeDownloaded / 100));
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        runOnUiThread(() -> {
            ToastUtils.showShort("下载完成");
            mDownProcess.setVisibility(View.GONE);
            mDetailProcessPdf.setVisibility(View.VISIBLE);
            mDetailProcessPdf.setText("点击查看诊断思路与过程PDF文档");
            mDetailProcessPdf.setOnClickListener(v -> {
                String path = mPDFPath.getAbsolutePath();
                String dex = BasicUtils.getFileFormat(path);
                if (dex.equals("pdf")) {
                    startActivity(new Intent(getBaseContext(), PDFActivity.class)
                            .putExtra("pdf_path", mPDFPath.getAbsolutePath()));
                }
            });
            mDetailProcessPdf.performClick();
        });
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
