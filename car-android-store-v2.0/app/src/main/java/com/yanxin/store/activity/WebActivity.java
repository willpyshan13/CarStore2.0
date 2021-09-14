package com.yanxin.store.activity;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.LogUtils;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;

@XmlLayoutResId(contentId = R.layout.activity_web)
public class WebActivity extends BaseActivity {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        WebView mWeb = findViewById(R.id.car_webView);
        String mPDFPath = getIntent().getStringExtra("pdf_path");
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        mWeb.setWebChromeClient(new WebChromeClient() {
        });
        String s = "https://dev.store.dlvehicle.com/#/pdfhtml?pdfUrl=" + mPDFPath;
        LogUtils.e(s);
        mWeb.loadUrl(s);
    }
}
