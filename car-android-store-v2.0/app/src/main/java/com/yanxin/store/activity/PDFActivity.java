package com.yanxin.store.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsReaderView;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;

@XmlLayoutResId(contentId = R.layout.activity_pdf)
public class PDFActivity extends BaseActivity implements TbsReaderView.ReaderCallback {
    private TextView mPdfTitle;
    private ImageView mPdfRepair;
    private RelativeLayout mPdfLayout;
    private TbsReaderView mTbsReaderView;
    private String mPDFPath;

    @Override
    protected void initData() {
        mPdfRepair.setOnClickListener(v -> {
            if (MyApplication.isX5Success() && EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ToastUtils.showShort("当前X5内核正常, 无需修复");
            } else {
                QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
                    @Override
                    public void onViewInitFinished(boolean arg0) {
                        // TODO Auto-generated method stub
                        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                        ToastUtils.showShort("修复" + (arg0 ? "成功" : "失败") + ", 请重新进入本页面");
                    }

                    @Override
                    public void onCoreInitFinished() {
                        // TODO Auto-generated method stub
                        LogUtils.e("onCoreInitFinished");
                    }
                };
                //x5内核初始化接口
                QbSdk.initX5Environment(getApplicationContext(), cb);
            }
        });
    }

    @Override
    protected void initView() {
        mPDFPath = getIntent().getStringExtra("pdf_path");
        mPdfTitle = findViewById(R.id.pdf_title);
        mPdfLayout = findViewById(R.id.pdf_layout);
        mPdfRepair = findViewById(R.id.pdf_repair);
        mTbsReaderView = new TbsReaderView(this, this);
        mPdfLayout.addView(mTbsReaderView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        openFile();
    }

    private void openFile() {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", mPDFPath);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = mTbsReaderView.preOpen(mPDFPath.substring(mPDFPath.lastIndexOf(".") + 1), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
    }

}
