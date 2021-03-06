package com.yanxin.store.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.PicAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseMvpActivity;
import com.yanxin.store.base.BasePresenter;
import com.yanxin.store.contract.MaintenanceProposalContract;
import com.yanxin.store.entity.PicEntity;
import com.yanxin.store.event.GrabEvent;
import com.yanxin.store.presenter.MaintenanceProposalPresenter;
import com.yanxin.store.req.AddSceneOrderServiceReq;
import com.yanxin.store.ui.OptionalEditLayout;
import com.yanxin.store.utils.BasePOPWindow;
import com.yanxin.store.utils.BasicUtils;
import com.yanxin.store.utils.GlideEngine;
import com.yanxin.store.utils.WeiboDialogUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.yanxin.store.commont.Constant.AppConfig.BASE_SERVING_FEE;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_CAMERA;
import static com.yanxin.store.commont.Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE;

@XmlLayoutResId(contentId = R.layout.activity_create_maintenance)
public class CreateMaintenanceProposalActivity extends BaseMvpActivity<MaintenanceProposalPresenter> implements MaintenanceProposalContract.MaintenanceProposalView, EasyPermissions.PermissionCallbacks {
    private AddSceneOrderServiceReq addSceneOrderServiceReq;

    private Button mMtSubmit;
    private float allAmount;
    private EditText mMtFaultDesc;
    private EditText mMtInspectDesc;
    private androidx.recyclerview.widget.RecyclerView mMtRv;
    private EditText mMtSolutionDesc;
    private OptionalEditLayout mMtBasicInspectAmount;
    private OptionalEditLayout mMtRepairAmount;
    private EditText mMtOtherAmount;
    private TextView mMtOtherHint;
    private OptionalEditLayout mAllAmount;
    private File cameraFile;
    private ArrayList<PicEntity> mPicEntity;
    private PicEntity mCurrentPicEntity;
    private int mCurrentPicPosition;
    private PicAdapter mPicAdapter;
    private Dialog loadDialogView;
    private PopupWindow mPopWindow;
    private boolean isPicDefault;

    @Override
    protected void initMVPData() {
        mMtSubmit.setOnClickListener(v -> {
            addSceneOrderServiceReq.setFaultDesc(BasicUtils.getEditValue(mMtFaultDesc));
            addSceneOrderServiceReq.setCheckData(BasicUtils.getEditValue(mMtInspectDesc));
            addSceneOrderServiceReq.setSolution(BasicUtils.getEditValue(mMtSolutionDesc));
            addSceneOrderServiceReq.setSceneOrderUuid(getIntent().getStringExtra("uuid"));
            ArrayList<String> list = new ArrayList<>();
            for (PicEntity picEntity : mPicEntity) {
                if (picEntity.getPath() != null && !picEntity.getPath().isEmpty()) {
                    list.add(picEntity.getPath());
                }
            }
            addSceneOrderServiceReq.setImageList(list);
            addSceneOrderServiceReq.setOrderServiceAmountUuid(BASE_SERVING_FEE);
            addSceneOrderServiceReq.setBasicInspectAmount(getAmount(mMtBasicInspectAmount));
            addSceneOrderServiceReq.setOtherAmount(getAmountEdit(mMtOtherAmount));
            addSceneOrderServiceReq.setRepairAmount(getAmount(mMtRepairAmount));
            addSceneOrderServiceReq.setTotalAmount(getAmount(mMtBasicInspectAmount) + getAmountEdit(mMtOtherAmount) + getAmount(mMtRepairAmount));
            if (BasicUtils.getEditValue(mMtFaultDesc).isEmpty()) {
                ToastUtils.showShort("?????????????????????????????????");
                return;
            }
            if (BasicUtils.getEditValue(mMtInspectDesc).isEmpty()) {
                ToastUtils.showShort("?????????????????????");
                return;
            }
            if (BasicUtils.getEditValue(mMtSolutionDesc).isEmpty()) {
                ToastUtils.showShort("???????????????????????????");
                return;
            }
            if (list.size() == 0) {
                ToastUtils.showShort("?????????????????????");
                return;
            }
            mPresenter.submitProposal(addSceneOrderServiceReq);
        });
        mMtBasicInspectAmount.getEditValueView().addTextChangedListener(new AmountCallBack() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accumulateAmount();
            }
        });
        mMtRepairAmount.getEditValueView().addTextChangedListener(new AmountCallBack() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accumulateAmount();
            }
        });
        mMtOtherAmount.addTextChangedListener(new AmountCallBack() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                accumulateAmount();
            }
        });
        mPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            KeyboardUtils.hideSoftInput(this);
            mCurrentPicEntity = mPicEntity.get(position);
            mCurrentPicPosition = position;
            switch (view.getId()) {
                case R.id.pic_layout:
                    createPopWindow();
                    break;
                case R.id.business_pic_clear:
                    mPicEntity.remove(position);
                    if (!isPicDefault) {
                        addDefaultDtcPic();
                    }
                    mPicAdapter.notifyDataSetChanged();
                    break;
            }
        });
        mMtOtherHint.setOnClickListener(v -> createAlertDialog("????????????????????????????????????????????????......????????????????????????????????????????????????????????????????????????????????????????????????????????????"));
        addDefaultDtcPic();
        mPicAdapter.notifyDataSetChanged();
    }

    private void createAlertDialog(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("??????")
                .setMessage(msg)
                .setNegativeButton("????????????", (dialog, which) -> dialog.dismiss()).create();
        alertDialog.show();
    }

    private void addDefaultDtcPic() {
        PicEntity picEntity = new PicEntity();
        picEntity.setDefault(true);
        mPicEntity.add(picEntity);
        isPicDefault = true;
    }

    private void createPopWindow() {
        if (mPopWindow == null || !mPopWindow.isShowing()) {
            mPopWindow = BasePOPWindow.createPopWindow(CreateMaintenanceProposalActivity.this, popView -> {
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

    @Override
    protected void initMVPView() {
        mPicEntity = new ArrayList<>();
        mMtFaultDesc = findViewById(R.id.mt_fault_desc);
        mMtInspectDesc = findViewById(R.id.mt_inspect_desc);
        mMtRv = findViewById(R.id.mt_rv);
        mMtSolutionDesc = findViewById(R.id.mt_solution_desc);
        mMtBasicInspectAmount = findViewById(R.id.mt_inspect_amount);
        mMtRepairAmount = findViewById(R.id.mt_repair_amount);
        mMtOtherAmount = findViewById(R.id.mt_other_amount);
        mMtOtherHint = findViewById(R.id.mt_other_hint);
        mAllAmount = findViewById(R.id.all_amount);
        mMtSubmit = findViewById(R.id.mt_submit);
        mPicAdapter = new PicAdapter(R.layout.include_store_register_pic, mPicEntity);
        mMtRv.setAdapter(mPicAdapter);
        addSceneOrderServiceReq = new AddSceneOrderServiceReq();
    }

    @Override
    public void startActivity() {

    }


    @Override
    public void uploadSuccess(String mPath) {
        //?????????????????????
        if (mCurrentPicEntity.isDefault() && mCurrentPicPosition >= 5) {
            mCurrentPicEntity.setDefault(false);
            mCurrentPicEntity.setPath(mPath);
            mCurrentPicEntity.setPicFile(cameraFile);
            isPicDefault = false;
        } else if (!mCurrentPicEntity.isDefault()) {
            mCurrentPicEntity.setDefault(false);
            mCurrentPicEntity.setPath(mPath);
            mCurrentPicEntity.setPicFile(cameraFile);
        } else {
            //????????????
            PicEntity picEntity = new PicEntity();
            picEntity.setDefault(false);
            picEntity.setPath(mPath);
            picEntity.setPicFile(cameraFile);
            mPicEntity.add(mPicEntity.size() - 1, picEntity);
        }
        mPicAdapter.notifyDataSetChanged();
        WeiboDialogUtils.closeDialog(loadDialogView);
    }

    @Override
    public void submitProposal(String msg) {
        ToastUtils.showShort("????????????, ??????????????????");
        EventBus.getDefault().post(new GrabEvent());
        finish();
    }

    private void accumulateAmount() {
        allAmount = getAmount(mMtBasicInspectAmount)
                + getAmount(mMtRepairAmount)
                + getAmountEdit(mMtOtherAmount);
        addSceneOrderServiceReq.setTotalAmount(allAmount);
        mAllAmount.setContentTextView("??" + allAmount);
    }

    private float getAmountEdit(EditText mMtOtherAmount) {
        String amountValue = mMtOtherAmount.getText().toString().trim();
        return Float.parseFloat(amountValue.isEmpty() || amountValue.equals(".") ? "0" : amountValue);
    }

    private float getAmount(OptionalEditLayout mAmountLayout) {
        String amountValue = mAmountLayout.getContentEditValue();
        return Float.parseFloat(amountValue.isEmpty() || amountValue.equals(".") ? "0" : amountValue);
    }

    @Override
    public void failed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public BasePresenter initPresenter() {
        return MaintenanceProposalPresenter.newInstance();
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
        ToastUtils.showShort("?????????????????????????????????");
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
                //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                Photo photo = resultPhotos.get(0);
                if (photo.size > 1024 * 1000 * 6) {
                    ToastUtils.showShort("????????????,????????????");
                    return;
                }
                cameraFile = new File(photo.path);
                ImageUtils.compressByQuality(BitmapFactory.decodeFile(photo.path), 50);
                mPresenter.uploadFile(cameraFile);
                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
            }
        }
    }


    class AmountCallBack implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
