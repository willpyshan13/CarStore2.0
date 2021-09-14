package com.yanxin.store.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.yanxin.store.R;

public class OptionalEditLayout extends LinearLayout {
    private boolean isMandatory; //是否必选，默认是
    private boolean isMoreShow; //是否显示右边按钮，默认是
    private boolean isSingleLine; //是否显示右边按钮，默认是
    private int isInputType; // 输入类型   0表示TextView, 1表示EditView
    private int mEditInputType; // 输入类型   0表示number, 1表示text, 2表示password, 3表示phone
    private float mDescWeight;  //标题权重
    private int mInputTypeText = 0;
    private int mInputTypeEdit = 1;
    private float mDescTextViewSize = 1;
    private float mContentViewSize = 1;
    private boolean mDescGravity;
    private boolean mContentGravity;
    private boolean mDescBoldStyle;
    private int mContentViewColor;
    private int mDescViewColor;


    /**
     * 保存的三个View
     */
    private TextView mTagMandatoryView; //必选的红色标志
    private TextView mContentTextView;
    private TextView mDescriptionView; //最左边的描述
    private EditText mContentEditView;
    private ImageView mItemMoreView;    //more图标
    private String contentText;
    private String contentHint;

    public OptionalEditLayout(Context context) {
        this(context, null);
    }

    public OptionalEditLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionalEditLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrsSet(context, attrs);
    }

    private void initAttrsSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OptionalEditLayout);
        isMandatory = typedArray.getBoolean(R.styleable.OptionalEditLayout_is_mandatory, true);
        isMoreShow = typedArray.getBoolean(R.styleable.OptionalEditLayout_is_more_show, true);
        isSingleLine = typedArray.getBoolean(R.styleable.OptionalEditLayout_singleLine, false);
        isInputType = typedArray.getInt(R.styleable.OptionalEditLayout_is_input_type, 0);
        mEditInputType = typedArray.getInt(R.styleable.OptionalEditLayout_input_type, 1);
        mDescTextViewSize = typedArray.getDimension(R.styleable.OptionalEditLayout_desc_tv_size, 14);
        mContentViewSize = typedArray.getDimension(R.styleable.OptionalEditLayout_content_hint_tv_size, 14);
        mDescGravity = typedArray.getBoolean(R.styleable.OptionalEditLayout_is_desc_center_gravity, true);
        mContentGravity = typedArray.getBoolean(R.styleable.OptionalEditLayout_is_content_center_gravity, false);
        mDescBoldStyle = typedArray.getBoolean(R.styleable.OptionalEditLayout_is_desc_bold, false);
        mContentViewColor = typedArray.getColor(R.styleable.OptionalEditLayout_content_tv_color, Color.parseColor("#424242"));
        mDescViewColor = typedArray.getColor(R.styleable.OptionalEditLayout_desc_tv_color, Color.parseColor("#424242"));
        mDescWeight = typedArray.getFloat(R.styleable.OptionalEditLayout_desc_weight, 0);

//        if (mDescWeight > maxWeight) {
//            mDescWeight = maxWeight / 2;
//        }
        String descStr = typedArray.getString(R.styleable.OptionalEditLayout_desc_content);
        contentText = typedArray.getString(R.styleable.OptionalEditLayout_content_text);

        contentHint = typedArray.getString(R.styleable.OptionalEditLayout_content_hint);
        /**
         * 只支持横向
         */
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        addView();
        mDescriptionView.setText(descStr);
        if (isInputType == mInputTypeText) {
            mContentTextView.setHint(contentHint);
            mContentTextView.setText(contentText);
        } else {
            mContentEditView.setHint(contentHint);
            mContentEditView.setText(contentText);
        }
        if (!isMandatory) {
            mTagMandatoryView.setVisibility(INVISIBLE);
        }
        if (!isMoreShow) {
            mItemMoreView.setVisibility(INVISIBLE);
        }
    }

    /**
     * 添加基础的三个View
     */
    private void addView() {
        mTagMandatoryView = createTagMandatoryView();
        addView(mTagMandatoryView);
        mDescriptionView = createDescriptionTextView();
        if (mDescBoldStyle) {
            mDescriptionView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        addView(mDescriptionView);
        mContentTextView = createContentTextView();
        mContentEditView = createContentEditView();
        if (isInputType == mInputTypeText) {
            addView(mContentTextView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            mContentTextView.setSingleLine(isSingleLine);
        } else {
            addView(mContentEditView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            switch (mEditInputType) {
                case 0:
                    mContentEditView.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                    break;
                case 1:
                    mContentEditView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                    break;
                case 2:
                    mContentEditView.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
                case 3:
                    mContentEditView.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                    break;
            }
            mContentEditView.setSingleLine(isSingleLine);
        }
        mItemMoreView = createItemMoreView();
        addView(mItemMoreView);
    }

    private TextView createDescriptionTextView() {
        TextView mDescView = new TextView(getContext());
        mDescView.setTextColor(Color.BLACK);
        mDescView.setTextSize(mDescTextViewSize);
        mDescView.setTextColor(mDescViewColor);
        if (!mDescGravity) {
            mDescView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }
        if (mDescWeight != 0) {
            mDescView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, mDescWeight));
        }
        return mDescView;
    }

    private ImageView createItemMoreView() {
        ImageView mMoreIcon = new ImageView(getContext());
        mMoreIcon.setImageResource(R.drawable.register_more);
        mMoreIcon.setPadding(20, 10, 20, 10);
        return mMoreIcon;
    }

    private EditText createContentEditView() {
        EditText mContentView = new EditText(getContext());
        mContentView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mContentView.setBackground(null);
        mContentView.setTextSize(mContentViewSize);
        mContentView.setTextColor(mContentViewColor);
        mContentView.setPadding(30, 0, 5, 0);
        if (!mContentGravity) {
            mContentView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }
//        if (mDescWeight != 0) {
//            mContentView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, maxWeight - mDescWeight));
//        }
        return mContentView;
    }

    private TextView createContentTextView() {
        TextView mContentView = new TextView(getContext());
        mContentView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mContentView.setTextSize(mContentViewSize);
        mContentView.setTextColor(mContentViewColor);
        mContentView.setPadding(30, 0, 0, 0);
        if (!mContentGravity) {
            mContentView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }
//        if (mDescWeight != 0) {
//            mContentView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, maxWeight - mDescWeight));
//        }
        return mContentView;
    }

    /**
     * 固定的  展示出来表示是必填
     * @return
     */
    private TextView createTagMandatoryView() {
        TextView mTagView = new TextView(getContext());
        mTagView.setTextColor(Color.RED);
        mTagView.setText("*");
        mTagView.setPadding(20, 10, 20, 10);
        return mTagView;
    }

    /**
     * 交互方法
     *
     * @return
     */
    public void setDescTextView(String content) {
        mDescriptionView.setText(content);
    }

    public void setContentTextView(String content) {
        mContentTextView.setText(content);
    }

    public void setContentEditView(String content) {
        mContentEditView.setText(content);
    }

    public String getContentTextValue() {
        return mContentTextView.getText().toString().trim();
    }

    public String getContentEditValue() {
        return mContentEditView.getText().toString().trim();
    }

    public EditText getEditValueView() {
        return mContentEditView;
    }

    public TextView getTextValueView() {
        return mContentTextView;
    }

    public static boolean isEmpty(TextView mContentView) {
        return mContentView.getText().toString().trim().isEmpty();
    }

    public static boolean isEmpty(EditText mContentView) {
        return mContentView.getText().toString().trim().isEmpty();
    }

}
