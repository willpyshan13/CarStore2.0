package com.yanxin.store.activity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;

import java.util.HashMap;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

@XmlLayoutResId(contentId = R.layout.activity_video)
public class VideoActivity extends BaseActivity {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        JzvdStd jzvdStd = (JzvdStd) findViewById(R.id.jz_video);
        findViewById(R.id.jz_close).setOnClickListener(v -> finish());
        String video_url = getIntent().getStringExtra("video_url");
        jzvdStd.setUp(video_url, "");
        jzvdStd.posterImageView.setImageBitmap(getImageView(video_url));
        jzvdStd.startVideo();
    }

    private Bitmap getImageView(String videoUrl) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
