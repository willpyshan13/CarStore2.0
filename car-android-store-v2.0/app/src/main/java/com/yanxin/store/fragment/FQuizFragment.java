package com.yanxin.store.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.AskEveryoneActivity;
import com.yanxin.store.adapter.MainFragmentPagerAdapter;
import com.yanxin.store.adapter.rvadapter.QuizAnswerAdapter;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.api.ApiStore;
import com.yanxin.store.base.BaseFragment;
import com.yanxin.store.bean.FiltrateBean;
import com.yanxin.store.bean.TechnicianAnswerBean;
import com.yanxin.store.bean.TechnicianTypeBean;
import com.yanxin.store.req.DefaultReq;
import com.yanxin.store.req.TechnicianAnswerReq;
import com.yanxin.store.ui.FiltrateWindow;
import com.yanxin.store.ui.NoScrollViewPager;
import com.yanxin.store.utils.RetrofitUtils;
import com.yanxin.store.utils.RxHelper;

import java.util.ArrayList;
import java.util.HashSet;

import static com.yanxin.store.commont.Constant.AppConfig.TECHNICIAN_TYPE;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.FILTRATE_TYPE_SYS;
import static com.yanxin.store.commont.Constant.AsynchronousStatus.USER_TYPE_TECHNICIAN;

/**
 * 发现页-知识问答
 */
@XmlLayoutResId(contentId = R.layout.fragment_f_quiz)
public class FQuizFragment extends BaseFragment {
    private NoScrollViewPager mMainViewPager;
    private RadioGroup mMainGroup;
    private MainFragmentPagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragments;
    private ImageView mToJishi;
    private ImageView mToZhuanjia;
    private ArrayList<TechnicianAnswerBean.DataBean> mTechnicianAnswerBeans;
    private QuizAnswerAdapter mQuizAnswerAdapter;
    private ArrayList<FiltrateBean> mTypeBean;
    private FiltrateWindow mFiltrateWindow;
    private String mTechnicianTypeUuid;

    @Override
    protected void initData() {
        FQRushFragment mFqRushFragment = new FQRushFragment();
        FQMineRushParentFragment mFqMineRushParentFragment = new FQMineRushParentFragment();
        FQAskMeRushParentFragment mAskMeRushFragment = new FQAskMeRushParentFragment();
        FQSquareFragment mFqSquareFragment = new FQSquareFragment();
        mFragments.add(mFqRushFragment);
        mFragments.add(mFqMineRushParentFragment);
        if (MyApplication.getUserType() == USER_TYPE_TECHNICIAN) { //只有技师才有向我提问的页面
            mFragments.add(mAskMeRushFragment);
        }
        mFragments.add(mFqSquareFragment);
        mPagerAdapter.notifyDataSetChanged();
        mToJishi.setOnClickListener(v -> startActivity(new Intent(mActivity, AskEveryoneActivity.class)));
        mToZhuanjia.setOnClickListener(v -> {
            if (mTechnicianAnswerBeans.size() == 0) {
                ToastUtils.showShort("当前没有可询问的专家");
            } else {
                createExpertDialog();
            }
        });
        queryTechnicianAnswer();
        queryTechnicianType();
    }

    @SuppressLint("CheckResult")
    private void queryTechnicianAnswer() {
        TechnicianAnswerReq req = new TechnicianAnswerReq();
        req.setPageSize(50);
        req.setPageNum(1);
        req.setTechnologyTypeUuid(mTechnicianTypeUuid);
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryTechnicianAnswerList(MyApplication.getUserToken(), req)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(technicianAnswerBean -> {
                    if (technicianAnswerBean.isSuccess()) {
                        mTechnicianAnswerBeans.clear();
                        mTechnicianAnswerBeans.addAll(technicianAnswerBean.getData());
                        mQuizAnswerAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShort(technicianAnswerBean.getMsg());
                    }
                }, throwable -> ToastUtils.showShort(throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    private void queryTechnicianType() {
        RetrofitUtils.client(MyApplication.BASE_URL, ApiStore.class)
                .queryTechnicianTypeBean(TECHNICIAN_TYPE)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(technicianTypeBean -> {
                    if (technicianTypeBean.isSuccess()) {
                        ArrayList<TechnicianTypeBean.DataBean> data = technicianTypeBean.getData();
                        for (TechnicianTypeBean.DataBean dataBean : data) {
                            FiltrateBean filtrateBean = new FiltrateBean();
                            filtrateBean.setName(dataBean.getLableDesc());
                            filtrateBean.setUuid(dataBean.getUuid());
                            filtrateBean.setType(FILTRATE_TYPE_SYS);
                            mTypeBean.add(filtrateBean);
                        }
                        if (mTypeBean.size() > 0) {
                            FiltrateBean filtrateBean = new FiltrateBean();
                            filtrateBean.setName("全部技师");
                            filtrateBean.setUuid(null);
                            filtrateBean.setType(FILTRATE_TYPE_SYS);
                            mTypeBean.add(0, filtrateBean);
                        }
                    } else {
                        ToastUtils.showShort(technicianTypeBean.getMsg());
                    }
                }, throwable -> ToastUtils.showShort(throwable.getMessage()));
//        mTechnicianTypeUuid
    }

    /**
     * 创建并展示专家列表
     */
    private void createExpertDialog() {
        LinearLayout mProtocolLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_quiz_answer, null);
        RecyclerView mRv = mProtocolLayout.findViewById(R.id.dialog_rv);
        ImageView mClose = mProtocolLayout.findViewById(R.id.dialog_close);
        TextView mMoreType = mProtocolLayout.findViewById(R.id.dialog_type);
        mRv.setAdapter(mQuizAnswerAdapter);
        mQuizAnswerAdapter.notifyDataSetChanged();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        AlertDialog alertDialog = builder.setView(mProtocolLayout).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        mClose.setOnClickListener(v -> alertDialog.dismiss());
        mMoreType.setOnClickListener(v -> showPOPWindow(mMoreType, mTypeBean, content -> {
            mMoreType.setText(content);
            queryTechnicianAnswer();
        }));
        alertDialog.show();
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mTypeBean = new ArrayList<>();
        mTechnicianAnswerBeans = new ArrayList<>();
        mMainViewPager = findViewById(R.id.quiz_vp);
        mPagerAdapter = new MainFragmentPagerAdapter(getChildFragmentManager(), mFragments);
        mQuizAnswerAdapter = new QuizAnswerAdapter(R.layout.item_quiz_answer, mTechnicianAnswerBeans);
        mMainViewPager.setAdapter(mPagerAdapter);
        mToJishi = findViewById(R.id.to_jishi);
        mToZhuanjia = findViewById(R.id.to_zhuanjia);
        mQuizAnswerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Intent intent = new Intent(mActivity, AskEveryoneActivity.class);
            intent.putExtra("is_zhuanjia", true);
            intent.putExtra("data", mTechnicianAnswerBeans.get(position));
            startActivity(intent);
        });
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.mMainGroup = radioGroup;
        for (int i = 0; i < mMainGroup.getChildCount(); i++) {
            int finalI = i;
            mMainGroup.getChildAt(i).setOnClickListener(v -> {
                if (mFragments.size() != 0 && mMainViewPager != null) {
                    mMainViewPager.setCurrentItem(finalI);
                }
            });
        }
    }

    private void showPOPWindow(View view, ArrayList<FiltrateBean> beans, WindowCallBack windowCallBack) {
        mFiltrateWindow = FiltrateWindow.newBuilder()
                .setWidth((int) getResources().getDimension(R.dimen.quiz_dialog_filtrate_width))
                .setHeight((int) getResources().getDimension(R.dimen.quiz_dialog_filtrate_height))
                .setOnArithmeticList(beans)
                .setOnDismissListener(() -> mFiltrateWindow = null)
                .setOnSelectFiltrateListener(filtrateBean -> {
                    mTechnicianTypeUuid = filtrateBean.getUuid();
                    windowCallBack.value(filtrateBean.getName());
                })
                .build(mContext);
        mFiltrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }

    interface WindowCallBack {
        void value(String content);
    }
}
