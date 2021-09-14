package com.yanxin.store.mvvm.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.will.habit.base.BaseActivity
import com.yanxin.store.R
import com.yanxin.store.BR
import com.yanxin.store.MyApplication
import com.yanxin.store.activity.BrandActivity
import com.yanxin.store.adapter.rvadapter.POPRecyclerViewAdapter
import com.yanxin.store.bean.DepositBankBean
import com.yanxin.store.commont.Constant
import com.yanxin.store.commont.Constant.AsynchronousStatus
import com.yanxin.store.databinding.ActivityMineInfoTechBinding
import com.yanxin.store.mvvm.viewmodel.MineInfoTechViewModel
import com.yanxin.store.utils.BasePOPWindow
import com.yanxin.store.utils.RealPathFromUriUtils
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MineInfoTechActivity : BaseActivity<ActivityMineInfoTechBinding, MineInfoTechViewModel>() {
    private var mPopWindow: PopupWindow? = null
    private var mPopAdapter: POPRecyclerViewAdapter? = null
    private var mOptionsPickerView: OptionsPickerView<Any>? = null

    private var mPopPictureWindow: PopupWindow? = null
    private var cameraFile: File? = null
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_mine_info_tech
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.showArea.observe(this) {
            KeyboardUtils.hideSoftInput(binding.mineInfoCompany10Value)
            if (mOptionsPickerView == null) {
                mOptionsPickerView = OptionsPickerBuilder(this) { options1: Int, options2: Int, options3: Int, v: View? ->
                    val cityAddress = String.format(
                            "%s%s%s",
                            viewModel.cityBean[options1].areaName,
                            viewModel.areaBean[options1][options2].areaName,
                            viewModel.provinceBean[options1][options2][options3].areaName
                    )
                    viewModel.storeAddress.set(cityAddress)
                    viewModel.detailInfo.get()?.addressProvince = viewModel.cityBean[options1].uuid
                    viewModel.detailInfo.get()?.addressCity = viewModel.areaBean[options1][options2].uuid
                    viewModel.detailInfo.get()?.addressCounty = viewModel.provinceBean[options1][options2][options3].uuid
                }.build<Any>()
                mOptionsPickerView?.setPicker(viewModel.cityBean.toList(), viewModel.areaBean.toList(), viewModel.provinceBean.toList())
            }

            mOptionsPickerView?.show()
        }

        viewModel.showBank.observe(this) {
            showBank()
        }

        viewModel.showBrand.observe(this) {
            if (viewModel.mBrandList == null) {
                ToastUtils.showShort("没有获取到品牌数据")
            } else {
                val intent = Intent(baseContext, BrandActivity::class.java)
                intent.putExtra("brand_class", viewModel.mBrandList)
                startActivityForResult(intent, AsynchronousStatus.REGISTER_BRAND_CODE)
//                viewModel.mBrandList?.let {
//                }
            }
        }

        viewModel.showHeaderPick.observe(this){
            createPopWindow()
        }

        viewModel.showPick.observe(this){
        }
    }

    private fun showBank() {
        if (mPopWindow == null || !mPopWindow!!.isShowing) {
            mPopAdapter = POPRecyclerViewAdapter(R.layout.item_pop_recycler, viewModel.mBankList)
            mPopAdapter!!.setOnItemChildClickListener { adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int ->
                val dataBean: DepositBankBean.DataBean? = viewModel.mBankList?.get(position)
                viewModel.setBankInfo(dataBean?.lableDesc)
                mPopWindow!!.dismiss()
            }
            mPopWindow = BasePOPWindow.createRecyclerViewPop(
                    MineInfoTechActivity@ this
            ) { popView: View? -> mPopWindow!!.dismiss() }
            val mPopRecycler: RecyclerView = mPopWindow!!.contentView.findViewById(R.id.rv_item)
            mPopRecycler.adapter = mPopAdapter
            mPopAdapter?.notifyDataSetChanged()
            mPopWindow!!.animationStyle = R.style.PopWindowAnimStyle
            mPopWindow!!.showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0)
        }
    }

    private fun createPopWindow() {
        if (mPopPictureWindow == null || !mPopPictureWindow!!.isShowing) {
            mPopPictureWindow =
                BasePOPWindow.createPopWindow(MineInfoTechActivity@this) { popView: View ->
                    if (popView.id == R.id.photo_camera) {
                        /**
                         * 拍照选取图片
                         */
                        checkPermission(Constant.PermissionStatus.PER_CAMERA, Manifest.permission.CAMERA)
                    } else if (popView.id == R.id.photo_picker) {
                        /**
                         * 相册选取图片
                         */
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            checkPermission(
                                Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_MEDIA_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        } else {
                            checkPermission(
                                Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        }
                    }
                    mPopPictureWindow!!.dismiss()
                }
            mPopPictureWindow!!.setAnimationStyle(R.style.PopWindowAnimStyle)
            mPopPictureWindow!!.showAtLocation(findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0)
        }
    }

    /**
     * 检查权限
     *
     * @param perStatus
     * @param permission
     */
    private fun checkPermission(perStatus: Int, vararg permission: String) {
        if (perStatus == Constant.PermissionStatus.PER_CAMERA) {
            if (EasyPermissions.hasPermissions(baseContext, *permission)) {
                startCamera()
            } else {
                EasyPermissions.requestPermissions(
                    PermissionRequest.Builder(
                        this,
                        Constant.PermissionStatus.PER_CAMERA,
                        *permission
                    ).build()
                )
            }
        } else if (perStatus == Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE) {
            if (EasyPermissions.hasPermissions(baseContext, *permission)) {
                startPicker()
            } else {
                EasyPermissions.requestPermissions(
                    PermissionRequest.Builder(
                        this,
                        Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE,
                        *permission
                    ).build()
                )
            }
        }
    }

    private fun startPicker() {
        val pickIntent = Intent(Intent.ACTION_GET_CONTENT)
        pickIntent.type = "image/*"
        startActivityForResult(pickIntent, Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE)
    }

    private fun startCamera() {
        //创建文件夹
        val file: File?
        file = getExternalFilesDir("apk")
        if (!file!!.parentFile.exists()) {
            file.mkdir()
        }
        //用于保存调用相机拍照后所生成的文件
        cameraFile = File(
            file.absoluteFile.toString() + "/car_" + SimpleDateFormat("yyyy-MM-dd").format(
                Date()
            ) + ".jpg"
        )
        //跳转到调用系统相机
        val imageIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果在Android7.0以上,使用FileProvider获取Uri
            imageIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            //***代表的是
            val contentUri = FileProvider.getUriForFile(
                baseContext,
                MyApplication.getApplication().packageName + ".fileProvider",
                cameraFile!!
            )
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
        } else {
            //否则使用Uri.fromFile(file)方法获取Uri
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile))
        }
        startActivityForResult(imageIntent, Constant.PermissionStatus.PER_CAMERA)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.PermissionStatus.PER_CAMERA) {
            if (resultCode == RESULT_OK && cameraFile != null) {
                viewModel.uploadImage(cameraFile!!)
                showDialog("上传中")
//                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "上传中...")
            }
        } else if (requestCode == Constant.PermissionStatus.PER_WRITE_EXTERNAL_STORAGE) {
            if (resultCode == RESULT_OK && data != null) {
                cameraFile = File(RealPathFromUriUtils.getRealPathFromUri(baseContext, data.data))
//                mPresenter.uploadFile(cameraFile)
                viewModel.uploadImage(cameraFile!!)
                showDialog("上传中")
//                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "上传中...")
            }
        } else if (requestCode == AsynchronousStatus.REGISTER_BRAND_CODE && data != null) {
            viewModel.mSelectBrandList.clear()
            val brandName = data!!.getStringExtra("brandName")
            val brandUUID = data!!.getStringExtra("brandUUID")
            val brandUUIDs = listOf(*brandUUID!!.split("/").toTypedArray())
            viewModel.setSelectBrand(brandName, brandUUIDs)

        }
    }

}