package com.yanxin.store.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.PicAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.entity.PicEntity;
import com.yanxin.store.event.GrabEvent;
import com.yanxin.store.req.InDoorSceneOrderReq;
import com.yanxin.store.utils.BasePOPWindow;
import com.yanxin.store.utils.GlideEngine;
import com.yanxin.store.utils.RealPathFromUriUtils;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;
import com.yanxin.store.utils.WeiboDialogUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.yanxin.store.commont.Constant.PermissionStatus.PER_CAMERA;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE;

@XmlLayoutResId(contentId = R.layout.activity_goto_confirm)
public class GotoConfirmActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private EditText mGtContent;
    private TextView mGtTitle;
    private RecyclerView mGtRv;
    private Button mGtSubmit;
    private File cameraFile;
    private Dialog loadDialogView;
    private PicEntity mCurrentPicEntity;
    private int mCurrentPosition;
    private PopupWindow mPopWindow;
    private ArrayList<PicEntity> mPicEntity;
    private PicAdapter mPicAdapter;
    private boolean isPicDefault;
    private int mGrabType;


    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        mPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            KeyboardUtils.hideSoftInput(this);
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
        addDefaultStorePic();
        mGtSubmit.setOnClickListener(v -> {
            InDoorSceneOrderReq inDoorSceneOrderReq = new InDoorSceneOrderReq();
            ArrayList<String> list = new ArrayList<>();
            for (PicEntity picEntity : mPicEntity) {
                if (picEntity.getPath() != null && !picEntity.getPath().isEmpty()) {
                    list.add(picEntity.getPath());
                }
            }
            inDoorSceneOrderReq.setImageList(list);
            inDoorSceneOrderReq.setType(mGrabType);
            if (mGrabType==1) {
                inDoorSceneOrderReq.setDescribe(mGtContent.getText().toString().trim());
            }else{
                inDoorSceneOrderReq.setRepairSummary(mGtContent.getText().toString().trim());
            }
            if (list.size() == 0) {
                ToastUtils.showShort("?????????????????????");
                return;
            }
            inDoorSceneOrderReq.setSceneOrderUuid(getIntent().getStringExtra("uuid"));
            RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                    .sceneOrderDescribe(MyApplication.getUserToken(), inDoorSceneOrderReq)
                    .compose(RxHelper.rxSchedulerHelper())
                    .subscribe(defaultBean -> {
                        if (defaultBean.isSuccess()) {
                            EventBus.getDefault().post(new GrabEvent());
                            finish();
                        }
                    }, throwable -> ToastUtils.showShort(throwable.getMessage()));
        });
    }

    @Override
    protected void initView() {
        mPicEntity = new ArrayList<>();
        mGtContent = findViewById(R.id.gt_content);
        mGtTitle = findViewById(R.id.gt_title);
        mGtRv = findViewById(R.id.gt_rv);
        mGtSubmit = findViewById(R.id.gt_submit);
        mGrabType = getIntent().getIntExtra("grab_type", 1);
        mPicAdapter = new PicAdapter(R.layout.include_store_register_pic, mPicEntity);
        mGtTitle.setText(mGrabType == 1 ? "????????????????????????" : "??????????????????");
        mGtContent.setHint(mGrabType == 1 ? "??????????????????????????????????????????????????????????????????????????????????????????????????????" : "??????????????????......");
        mGtRv.setAdapter(mPicAdapter);
    }

    private void addDefaultStorePic() {
        PicEntity picEntity = new PicEntity();
        picEntity.setDefault(true);
        mPicEntity.add(picEntity);
        isPicDefault = true;
    }

    private void createPopWindow() {
        if (mPopWindow == null || !mPopWindow.isShowing()) {
            mPopWindow = BasePOPWindow.createPopWindow(GotoConfirmActivity.this, popView -> {
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
        EasyPhotos.createAlbum(this, false, false, GlideEngine.getInstance())
                .setFileProviderAuthority(MyApplication.getApplication().getPackageName() + ".fileProvider")
                .complexSelector(true, 1, 1)//?????????????????????????????????????????????????????????????????????
                .setVideoMaxSecond(10)
                .start(PER_WRITE_EXTERNAL_STORAGE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PER_CAMERA) {
            if (resultCode == Activity.RESULT_OK && cameraFile != null) {
                addPicFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
            }
        } else if (requestCode == PER_WRITE_EXTERNAL_STORAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                Photo photo = resultPhotos.get(0);
                if (photo.size > 1024 * 1000 * 6) {
                    ToastUtils.showShort("????????????,????????????");
                    return;
                }
                cameraFile = new File(photo.path);
                ImageUtils.compressByQuality(BitmapFactory.decodeFile(photo.path), 50);
                addPicFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
            }
        }
    }

    @SuppressLint("CheckResult")
    private void addPicFile(File mFile) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", mFile.getName(), requestFile);
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .uploadFile(part, "other")
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(uploadFileBean -> {
                    String path = uploadFileBean.getData();
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
                    WeiboDialogUtils.closeDialog(loadDialogView);
                }, throwable -> ToastUtils.showShort(throwable.getMessage()));
    }
}
