package com.yanxin.store.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanxin.store.MyApplication;
import com.yanxin.store.R;
import com.yanxin.store.activity.subactivity.SubAccountActivity;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;

import static com.yanxin.store.commont.Constant.AsynchronousStatus.USER_TYPE_STORE;

@XmlLayoutResId(contentId = R.layout.activity_pay_status)
public class PayStatusActivity extends BaseActivity {
    private boolean success;
    private ImageView mPayStatusIcon;
    private TextView mPayStatusTitle;
    private Button mReturnHome;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        success = getIntent().getBooleanExtra("is_success", false);
        mPayStatusIcon = findViewById(R.id.pay_status_icon);
        mPayStatusTitle = findViewById(R.id.pay_status_title);
        mReturnHome = findViewById(R.id.return_home);
        if (success) {
            mPayStatusIcon.setImageResource(R.drawable.success_icon);
            mPayStatusTitle.setText("支付成功");
        } else {
            mPayStatusIcon.setImageResource(R.drawable.failed_icon);
            mPayStatusTitle.setText("支付失败");
        }
        mReturnHome.setOnClickListener(v -> startOrderDetail());
    }

    private void startOrderDetail() {
        if (MyApplication.getStoreAccount() == 0) {
            int payStatusType = MyApplication.getPayStatusType();
            String payOrderUuid = MyApplication.getPayOrderUuid();
            Intent homeIntent = new Intent(getBaseContext(), MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Intent[] intent = new Intent[2];
            Intent orderIntent = new Intent();
            intent[0] = homeIntent;
            if (payStatusType == 0) {
                orderIntent.setClass(getBaseContext(), CaseDetailActivity.class);
                orderIntent.putExtra("case_uuid", payOrderUuid);
                orderIntent.putExtra("is_order", false);
                intent[1] = orderIntent;
                startActivities(intent);
            } else if (payStatusType == 1) {
                orderIntent.setClass(getBaseContext(), AskDetailActivity.class);
                orderIntent.putExtra("order_uuid", payOrderUuid);
                orderIntent.putExtra("is_order", true);
                orderIntent.putExtra("is_posted", true);
                intent[1] = orderIntent;
                startActivities(intent);
            } else if (payStatusType == 2) {
                orderIntent.setClass(getBaseContext(), RushDetailActivity.class);
                orderIntent.putExtra("order_uuid", payOrderUuid);
                orderIntent.putExtra("is_order", true);
                orderIntent.putExtra("is_pt", true);
                intent[1] = orderIntent;
                startActivities(intent);
            } else if (payStatusType == 3) {
                orderIntent.setClass(getBaseContext(), GrabOrderDetailActivity.class);
                orderIntent.putExtra("grab_uuid", payOrderUuid);
                intent[1] = orderIntent;
                startActivities(intent);
            } else if (payStatusType == 4) {
                orderIntent.setClass(getBaseContext(), DtcMyBuyActivity.class);
                intent[1] = orderIntent;
                startActivities(intent);
            } else {
                startTaskHomeActivity(0);
            }
        } else {
            int payStatusType = MyApplication.getPayStatusType();
            if (payStatusType == 3) {
                String payOrderUuid = MyApplication.getPayOrderUuid();
                Intent subIntent = new Intent(this, SubAccountActivity.class);
                subIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Intent[] intent = new Intent[2];
                Intent grabIntent = new Intent(this, GrabOrderDetailActivity.class);
                grabIntent.putExtra("grab_uuid", payOrderUuid);
                intent[0] = subIntent;
                intent[1] = grabIntent;
                startActivities(intent);
            } else {
                startTaskSubActivity();
            }
        }
    }

    @Override
    public void onBackFinish(View view) {
        startOrderDetail();
    }

    @Override
    public void onBackPressed() {
        startOrderDetail();
    }
}
