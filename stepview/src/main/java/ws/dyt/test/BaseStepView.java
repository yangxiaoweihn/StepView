package ws.dyt.test;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ws.dyt.test.R;

/**
 */
public class BaseStepView extends LinearLayout {
    private static final int DEFAULT_PADDING = 10;
    private static final int DEFAULT_FONT_SIZE = 14;
    protected int defaultPadding = -1;
    protected int defaultFontSize = -1;
    protected RelativeLayout mVgMinus;
    protected RelativeLayout mVgPlus;
    protected EditText mEtInput;

    private int stepPaddingLeft, stepPaddingRight, stepPaddingTop, stepPaddingBottom;
    private int stepMinusBackgroundSelector, stepPlusBackgroundSelector;

    private int inputPaddingLeft, inputPaddingRight, inputPaddingTop, inputPaddingBottom;
    private float inputFontSize;
    private boolean inputIsInput = true;


    public BaseStepView(Context context) {
        super(context);
        Log.e("DEBUG", "BaseStepView(*)");
        init(context, null, 0);
    }

    public BaseStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("DEBUG", "BaseStepView(*, *)");
        init(context, attrs, 0);
    }

    public BaseStepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.e("DEBUG", "BaseStepView(*, *, *)");
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        if (-1 == defaultFontSize){
            defaultFontSize = sp2px(DEFAULT_FONT_SIZE);
        }
        if (-1 == defaultPadding){
            defaultPadding = dp2px(DEFAULT_PADDING);
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );
        this.addView(LayoutInflater.from(context).inflate(R.layout.sample_base_step_view, null, false), lp);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseStepView, defStyle, 0);
        stepPaddingLeft = (int) a.getDimension(R.styleable.BaseStepView_step_padding_left, defaultPadding);
        stepPaddingTop = (int) a.getDimension(R.styleable.BaseStepView_step_padding_top, defaultPadding);
        stepPaddingRight = (int) a.getDimension(R.styleable.BaseStepView_step_padding_right, defaultPadding);
        stepPaddingBottom = (int) a.getDimension(R.styleable.BaseStepView_step_padding_bottom, defaultPadding);
        Log.e("DEBUG", "stepPaddingBottom: "+stepPaddingBottom+" , DEFAULT_PADDING: "+DEFAULT_PADDING+" , defaultPadding: "+defaultPadding);
        Log.e("DEBUG", "stepPaddingLeft: "+stepPaddingLeft);
        Log.e("DEBUG", "stepPaddingTop: "+stepPaddingTop);
        Log.e("DEBUG", "stepPaddingRight: "+stepPaddingRight);
        Log.e("DEBUG", "stepPaddingBottom: "+stepPaddingBottom);


        stepMinusBackgroundSelector = a.getResourceId(R.styleable.BaseStepView_step_minus_background_selector, 0);
        stepPlusBackgroundSelector = a.getResourceId(R.styleable.BaseStepView_step_plus_background_selector, 0);

        inputIsInput = a.getBoolean(R.styleable.BaseStepView_input_is_input, true);
        inputFontSize = px2sp(a.getDimension(R.styleable.BaseStepView_input_font_size, defaultFontSize));
        Log.e("DEBUG", "inputFontSize: "+inputFontSize+" , "+px2sp(inputFontSize)+" , DEFAULT_FONT_SIZE: "+DEFAULT_FONT_SIZE+" , defaultFontSize: "+defaultFontSize);

        inputPaddingLeft = (int) a.getDimension(R.styleable.BaseStepView_input_padding_left, defaultPadding);
        inputPaddingTop = (int) a.getDimension(R.styleable.BaseStepView_input_padding_top, defaultPadding);
        inputPaddingRight = (int) a.getDimension(R.styleable.BaseStepView_input_padding_right, defaultPadding);
        inputPaddingBottom = (int) a.getDimension(R.styleable.BaseStepView_input_padding_bottom, defaultPadding);


        a.recycle();

        this.initView();
    }

    public void setStepPaddingRight(float paddingSp){
        mVgMinus.setPadding(stepPaddingLeft, stepPaddingTop, mVgMinus.getPaddingRight() + dp2px(paddingSp), stepPaddingBottom);
    }
    private void initView(){
        mVgMinus = (RelativeLayout) findViewById(R.id.vg_minus);
        mVgPlus = (RelativeLayout) findViewById(R.id.vg_plus);
        mEtInput = (EditText) findViewById(R.id.et_input);

        mVgMinus.setBackgroundResource(stepMinusBackgroundSelector);
        mVgMinus.setPadding(stepPaddingLeft, stepPaddingTop, stepPaddingRight, stepPaddingBottom);
        mVgMinus.invalidate();

        mVgPlus.setPadding(stepPaddingLeft, stepPaddingTop, stepPaddingRight, stepPaddingBottom);
        mVgPlus.setBackgroundResource(stepPlusBackgroundSelector);

        mEtInput.setPadding(inputPaddingLeft, inputPaddingTop, inputPaddingRight, inputPaddingBottom);
        mEtInput.setTextSize(inputFontSize);

        this.postInvalidate();
        if (!inputIsInput){
            mEtInput.setFocusable(false);
            mEtInput.setEnabled(false);
        }else {
            mEtInput.addTextChangedListener(tw);
        }

        mVgMinus.setOnClickListener(onClickListener);
        mVgPlus.setOnClickListener(onClickListener);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.vg_minus == v.getId()) {

            }else if (R.id.vg_plus == v.getId()){

            }
        }
    };

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    protected int px2dp(final float px) {
        return DisplayUtil.px2dip(getContext(), px);
    }

    protected int dp2px(final float dp) {
        return DisplayUtil.dip2px(getContext(), dp);
    }

    protected int sp2px(final  float sp){
        return DisplayUtil.sp2px(getContext(), sp);
    }

    protected int px2sp(final  float px){
        return DisplayUtil.px2sp(getContext(), px);
    }
}
