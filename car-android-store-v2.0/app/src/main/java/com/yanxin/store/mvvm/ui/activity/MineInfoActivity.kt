package com.yanxin.store.mvvm.ui.activity

import android.Manifest
import android.app.Activity
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
import com.will.habit.widget.dialog.ChoiceDialog
import com.yanxin.store.R
import com.yanxin.store.BR
import com.yanxin.store.MyApplication
import com.yanxin.store.activity.BrandActivity
import com.yanxin.store.adapter.rvadapter.POPRecyclerViewAdapter
import com.yanxin.store.bean.DepositBankBean
import com.yanxin.store.commont.Constant
import com.yanxin.store.commont.Constant.PermissionStatus
import com.yanxin.store.databinding.ActivityMineInfoBinding
import com.yanxin.store.listener.IPopClick
import com.yanxin.store.mvvm.viewmodel.MineInfoViewModel
import com.yanxin.store.utils.BasePOPWindow
import com.yanxin.store.utils.RealPathFromUriUtils
import com.yanxin.store.utils.WeiboDialogUtils
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MineInfoActivity : BaseActivity<ActivityMineInfoBinding, MineInfoViewModel>() {
    private var mPopWindow: PopupWindow? = null
    private var mPopAdapter: POPRecyclerViewAdapter? = null
    private var mPopPictureWindow: PopupWindow? = null
    private var cameraFile: File? = null
    private var mOptionsPickerView: OptionsPickerView<Any>? = null
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_mine_info
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.showDialog.observe(this) {
            viewModel.personTypeListName?.let { data ->
                ChoiceDialog(this, true)
                        .setItems(data)
                        .setOnItemClickListener { onClickView, position ->
                            viewModel.setRoleText(position)
                        }
                        .setDialogGravity(Gravity.CENTER)
                        .create()
                        .show()
            }
        }

        viewModel.showBank.observe(this) {
            showBank()
        }

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
        viewModel.showBrand.observe(this) {
            if (viewModel.mBrandList == null) {
                ToastUtils.showShort("???????????????????????????")
            } else {
                viewModel.mBrandList?.let {
                    val intent = Intent(baseContext, BrandActivity::class.java)
                    intent.putExtra("brand_class", viewModel.mBrandList)
                    startActivityForResult(intent, Constant.AsynchronousStatus.REGISTER_BRAND_CODE)
                }
            }
        }

        viewModel.showPick.observe(this){
            createPopWindow()
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
                    MineInfoActivity@ this
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
                BasePOPWindow.createPopWindow(MineInfoActivity@this) { popView: View ->
                    if (popView.id == R.id.photo_camera) {
                        /**
                         * ??????????????????
                         */
                        checkPermission(PermissionStatus.PER_CAMERA, Manifest.permission.CAMERA)
                    } else if (popView.id == R.id.photo_picker) {
                        /**
                         * ??????????????????
                         */
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            checkPermission(
                                PermissionStatus.PER_WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_MEDIA_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        } else {
                            checkPermission(
                                PermissionStatus.PER_WRITE_EXTERNAL_STORAGE,
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
     * ????????????
     *
     * @param perStatus
     * @param permission
     */
    private fun checkPermission(perStatus: Int, vararg permission: String) {
        if (perStatus == PermissionStatus.PER_CAMERA) {
            if (EasyPermissions.hasPermissions(baseContext, *permission)) {
                startCamera()
            } else {
                EasyPermissions.requestPermissions(
                    PermissionRequest.Builder(
                        this,
                        PermissionStatus.PER_CAMERA,
                        *permission
                    ).build()
                )
            }
        } else if (perStatus == PermissionStatus.PER_WRITE_EXTERNAL_STORAGE) {
            if (EasyPermissions.hasPermissions(baseContext, *permission)) {
                startPicker()
            } else {
                EasyPermissions.requestPermissions(
                    PermissionRequest.Builder(
                        this,
                        PermissionStatus.PER_WRITE_EXTERNAL_STORAGE,
                        *permission
                    ).build()
                )
            }
        }
    }

    private fun startPicker() {
        val pickIntent = Intent(Intent.ACTION_GET_CONTENT)
        pickIntent.type = "image/*"
        startActivityForResult(pickIntent, PermissionStatus.PER_WRITE_EXTERNAL_STORAGE)
    }

    private fun startCamera() {
        //???????????????
        val file: File?
        file = getExternalFilesDir("apk")
        if (!file!!.parentFile.exists()) {
            file.mkdir()
        }
        //???????????????????????????????????????????????????
        cameraFile = File(
            file.absoluteFile.toString() + "/car_" + SimpleDateFormat("yyyy-MM-dd").format(
                Date()
            ) + ".jpg"
        )
        //???????????????????????????
        val imageIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //?????????Android7.0??????,??????FileProvider??????Uri
            imageIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            //***????????????
            val contentUri = FileProvider.getUriForFile(
                baseContext,
                MyApplication.getApplication().packageName + ".fileProvider",
                cameraFile!!
            )
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
        } else {
            //????????????Uri.fromFile(file)????????????Uri
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile))
        }
        startActivityForResult(imageIntent, PermissionStatus.PER_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PermissionStatus.PER_CAMERA) {
            if (resultCode == RESULT_OK && cameraFile != null) {
                viewModel.uploadImage(cameraFile!!)
                showDialog("?????????")
//                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "?????????...")
            }
        } else if (requestCode == PermissionStatus.PER_WRITE_EXTERNAL_STORAGE) {
            if (resultCode == RESULT_OK && data != null) {
                cameraFile = File(RealPathFromUriUtils.getRealPathFromUri(baseContext, data.data))
//                mPresenter.uploadFile(cameraFile)
                viewModel.uploadImage(cameraFile!!)
                showDialog("?????????")
//                loadDialogView = WeiboDialogUtils.createLoadingDialog(this, "?????????...")
            }
        } else
        if (requestCode == Constant.AsynchronousStatus.REGISTER_BRAND_CODE && data != null) {
            viewModel.mSelectBrandList.clear()
            val brandName = data!!.getStringExtra("brandName")
            val brandUUID = data!!.getStringExtra("brandUUID")
            val brandUUIDs = listOf(*brandUUID!!.split("/").toTypedArray())
            viewModel.setSelectBrand(brandName, brandUUIDs)

        }
    }


}