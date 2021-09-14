package com.yanxin.store.activity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;

@XmlLayoutResId(contentId = R.layout.activity_img)
public class ImageActivity extends BaseActivity {
    private ImageView mImgSrc;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mImgSrc = (ImageView) findViewById(R.id.img_src);
        Glide.with(getBaseContext()).load(getIntent().getStringExtra("url")).into(mImgSrc);
    }
}
