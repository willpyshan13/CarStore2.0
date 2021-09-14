package com.yanxin.store.activity;

import android.widget.TextView;

import com.yanxin.store.R;
import com.yanxin.store.annoation.XmlLayoutResId;
import com.yanxin.store.base.BaseActivity;
import com.yanxin.store.commont.Constant.WebDocumentTag;

@XmlLayoutResId(contentId = R.layout.activity_agreement)
public class AgreementActivity extends BaseActivity {
    private int agreementType;
    private TextView mTitle;
    private TextView mContent;

    @Override
    protected void initData() {
        if (WebDocumentTag.TAG_PRIVATE_AGREE == agreementType) {
            mTitle.setText(getString(R.string.login_intimacy_title));
            mContent.setText(getString(R.string.login_intimacy_content));
        } else if (WebDocumentTag.TAG_SERVICE_AGREE == agreementType) {
            mTitle.setText(getString(R.string.login_agreement_title));
            mContent.setText(getString(R.string.login_agreement_content));
        } else {
            mTitle.setText(getString(R.string.login_privacy_policy_title));
            mContent.setText(getString(R.string.login_privacy_policy_content));
        }
    }

    @Override
    protected void initView() {
        mTitle = findViewById(R.id.agree_title);
        mContent = findViewById(R.id.agree_content);
        agreementType = getIntent().getIntExtra("agree_tag", WebDocumentTag.TAG_PRIVATE_AGREE);
    }
}
