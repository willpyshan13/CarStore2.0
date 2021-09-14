package com.yanxin.store.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.yanxin.store.R;
import com.yanxin.store.adapter.rvadapter.RegBrandAdapter;
import com.yanxin.store.adapter.rvadapter.RegChosenBrandAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.bean.BrandBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.ArrayList;

import static com.yanxin.store.commont.Constant.AsynchronousStatus.REGISTER_BRAND_CODE;

@XmlLayoutResId(contentId = R.layout.activity_brand)
public class BrandActivity extends BaseActivity {

    private EditText mFindBrand;
    private RecyclerView mChosenBrand;
    private RecyclerView mAllBrand;
    private ImageView mSearchView;
    private Button mSubmit;
    private ArrayList<BrandBean.DataBean> brandBean;
    private ArrayList<BrandBean.DataBean> searchBean;
    private ArrayList<BrandBean.DataBean> chosen;
    private ArrayList<BrandBean.DataBean> saveAllBean;
    private RegBrandAdapter mRegBrandAdapter;
    private RegChosenBrandAdapter mRegChosenBrandAdapter;
    private boolean isSearch;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        chosen = new ArrayList<>();
        saveAllBean = new ArrayList<>();
        searchBean = new ArrayList<>();
        brandBean = (ArrayList<BrandBean.DataBean>) getIntent().getSerializableExtra("brand_class");
        saveAllBean.addAll(brandBean);
        for (BrandBean.DataBean dataBean : brandBean) {
            if (dataBean.isCheck()) {
                chosen.add(dataBean);
            }
        }
        mRegChosenBrandAdapter = new RegChosenBrandAdapter(R.layout.item_reg_chosen_brand, chosen);
        mRegBrandAdapter = new RegBrandAdapter(R.layout.item_reg_all_brand, brandBean);
        mFindBrand = findViewById(R.id.find_brand);
        mChosenBrand = findViewById(R.id.chosen_brand);
        mAllBrand = findViewById(R.id.all_brand);
        mSearchView = findViewById(R.id.brand_search);
        mSubmit = findViewById(R.id.brand_submit);
        mChosenBrand.setAdapter(mRegChosenBrandAdapter);
        mAllBrand.setAdapter(mRegBrandAdapter);
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mChosenBrand.setLayoutManager(manager);
        mRegBrandAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            BrandBean.DataBean dataBean = brandBean.get(position);
            if (isSearch) {
                if (dataBean.isCheck()) {
                    dataBean.setCheck(false);
                    for (BrandBean.DataBean bean : saveAllBean) {
                        if (dataBean.getUuid().equals(bean.getUuid())) {
                            bean.setCheck(false);
                            break;
                        }
                    }
                    chosen.remove(dataBean);
                } else {
                    if (chosen.size() >= 5) {
                        ToastUtils.showShort("最多选择5个品牌");
                        return;
                    }
                    dataBean.setCheck(true);
                    for (BrandBean.DataBean bean : saveAllBean) {
                        if (dataBean.getUuid().equals(bean.getUuid())) {
                            bean.setCheck(true);
                            break;
                        }
                    }
                    chosen.add(dataBean);
                }
            } else {
                if (dataBean.isCheck()) {
                    dataBean.setCheck(false);
                    saveAllBean.get(position).setCheck(false);
                    chosen.remove(dataBean);
                } else {
                    if (chosen.size() >= 5) {
                        ToastUtils.showShort("最多选择5个品牌");
                        return;
                    }
                    dataBean.setCheck(true);
                    saveAllBean.get(position).setCheck(true);
                    chosen.add(dataBean);
                }
            }

            mRegBrandAdapter.notifyDataSetChanged();
            mRegChosenBrandAdapter.notifyDataSetChanged();
        });
        mRegChosenBrandAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            String uuid = chosen.get(position).getUuid();
            if (isSearch) {
                for (BrandBean.DataBean dataBean : saveAllBean) {
                    if (dataBean.getUuid().equals(uuid)) {
                        dataBean.setCheck(false);
                        return;
                    }
                }
            } else {
                for (BrandBean.DataBean dataBean : brandBean) {
                    if (uuid.equals(dataBean.getUuid())) {
                        dataBean.setCheck(false);
                        saveAllBean.get(position).setCheck(false);
                        break;
                    }
                }
            }
            chosen.remove(position);
            mRegBrandAdapter.notifyDataSetChanged();
            mRegChosenBrandAdapter.notifyDataSetChanged();
        });
        mSearchView.setOnClickListener(v -> {
            String searchValue = BasicUtils.getEditValue(mFindBrand);
            if (searchValue.isEmpty()) {
                brandBean.clear();
                brandBean.addAll(saveAllBean);
            } else {
                searchBean.clear();
                for (BrandBean.DataBean dataBean : saveAllBean) {
                    if (dataBean.getConfigName().contains(searchValue)) {
                        searchBean.add(dataBean);
                    }
                }
                brandBean.clear();
                brandBean.addAll(searchBean);
            }
            mRegBrandAdapter.notifyDataSetChanged();
        });
        mSubmit.setOnClickListener(v -> {
            StringBuffer nameSB = new StringBuffer();
            StringBuffer uuidSB = new StringBuffer();
            for (BrandBean.DataBean dataBean : chosen) {
                nameSB.append(dataBean.getConfigName() + "/");
                uuidSB.append(dataBean.getUuid() + "/");
            }
            String brandName = nameSB.deleteCharAt(nameSB.length() - 1).toString();
            String brandUUID = uuidSB.deleteCharAt(uuidSB.length() - 1).toString();
            Intent intent = new Intent();
            intent.putExtra("brand_class", saveAllBean);
            intent.putExtra("brandName", brandName);
            intent.putExtra("brandUUID", brandUUID);
            setResult(REGISTER_BRAND_CODE, intent);
            finish();
        });
    }
}
