package ws.dyt.stepview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import ws.dyt.stepview.R;

import ws.dyt.stepview.util.DisplayUtil;

/**
 * Created by yangxiaowei on 15/10/9
 */
public abstract class BaseStepView extends LinearLayout {
    private static final int DEFAULT_FONT_SIZE = 14;
    protected int defaultFontSize = -1;
    protected RelativeLayout mVgMinus;
    protected RelativeLayout mVgPlus;
    protected EditText mEtInput;

    private float stepMinusWeight = 1.f, stepPlusWeight = 1.f;
    private int stepMinusBackgroundSelector, stepPlusBackgroundSelector;

    private float inputFontSize;
    private boolean inputIsInput = true;

    static class DataHolder{
        public int min = 0;
        public int max = 0;
        public int cur = 0;
    }
    private DataHolder dataHolder = new DataHolder();

    public BaseStepView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BaseStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BaseStepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        if (-1 == defaultFontSize){
            defaultFontSize = sp2px(DEFAULT_FONT_SIZE);
        }
        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );
        this.addView(LayoutInflater.from(context).inflate(R.layout.base_step_view, null, false), lp);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseStepView, defStyle, 0);

        stepMinusWeight = a.getFloat(R.styleable.BaseStepView_step_minus_weight, 1.f);
        stepPlusWeight = a.getFloat(R.styleable.BaseStepView_step_plus_weight, 1.f);
        stepMinusBackgroundSelector = a.getResourceId(R.styleable.BaseStepView_step_minus_background_selector, 0);
        stepPlusBackgroundSelector = a.getResourceId(R.styleable.BaseStepView_step_plus_background_selector, 0);

        inputIsInput = a.getBoolean(R.styleable.BaseStepView_input_is_input, true);
        inputFontSize = px2sp(a.getDimension(R.styleable.BaseStepView_input_font_size, defaultFontSize));

        dataHolder.min = a.getInt(R.styleable.BaseStepView_input_min, 0);
        dataHolder.max = a.getInt(R.styleable.BaseStepView_input_max, 0);
        a.recycle();

        this.initView();
    }

    private void initView(){
        mVgMinus = (RelativeLayout) findViewById(R.id.vg_minus);
        mVgPlus = (RelativeLayout) findViewById(R.id.vg_plus);
        mEtInput = (EditText) findViewById(R.id.et_input);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
                );
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mVgMinus.addView(setStepMinusView(), lp);
        mVgPlus.addView(setStepPlusView(), lp);

        mVgMinus.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT,
                stepMinusWeight));
        mVgMinus.setBackgroundResource(stepMinusBackgroundSelector);

        mVgPlus.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT,
                stepPlusWeight));
        mVgPlus.setBackgroundResource(stepPlusBackgroundSelector);

        mEtInput.setTextSize(inputFontSize);

        if (!inputIsInput){
            mEtInput.setFocusable(false);
            mEtInput.setEnabled(false);
        }else {
            mEtInput.addTextChangedListener(tw);
        }

        dataHolder.cur = dataHolder.min;
        mEtInput.setText(String.valueOf(dataHolder.cur));

        mVgMinus.setOnClickListener(onClickListener);
        mVgPlus.setOnClickListener(onClickListener);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (R.id.vg_minus == v.getId()) {
                dataHolder.cur -= 1;
            }else if (R.id.vg_plus == v.getId()){
                dataHolder.cur += 1;
            }
            mEtInput.setText(String.valueOf(dataHolder.cur));
        }
    };

    private void flushStepViewStatus(boolean is){
        if (!is){
            enableView(mVgMinus, false);
            enableView(mVgPlus, false);
            return;
        }
        if (dataHolder.cur > dataHolder.min && dataHolder.cur < dataHolder.max){
            if (!mVgMinus.isEnabled()){
                enableView(mVgMinus, true);
                mVgMinus.setOnClickListener(onClickListener);
            }
            if (!mVgPlus.isEnabled()){
                enableView(mVgPlus, true);
                mVgPlus.setOnClickListener(onClickListener);
            }
        }else if (dataHolder.cur == dataHolder.min){
            enableView(mVgPlus, true);
            enableView(mVgMinus, false);
        }else if (dataHolder.cur == dataHolder.max){
            enableView(mVgMinus, true);
            enableView(mVgPlus, false);
        }else {
            enableView(mVgMinus, false);
            enableView(mVgPlus, false);
        }
    }

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s.toString()) && TextUtils.isDigitsOnly(s.toString())){
                dataHolder.cur = Integer.valueOf(s.toString());
                if (null != onStepChangeListener){
                    onStepChangeListener.onStepChanged(dataHolder.cur, dataHolder.min, dataHolder.max);
                }
            }else {
                dataHolder.cur = 0;
                if (null != onStepChangeListener){
                    onStepChangeListener.onError();
                }
                flushStepViewStatus(false);
                return;
            }
            mEtInput.setSelection(s.toString().length());
            flushStepViewStatus(true);
        }
    };

    /**
     *
     * @return
     */
    public abstract View setStepMinusView();
    public abstract View setStepPlusView();

    private void enableView(ViewGroup parent, boolean enable){
        if (null == parent){
            return;
        }
        parent.setEnabled(enable);
        enableAllChildViews(parent, enable);
    }

    private void enableAllChildViews(ViewGroup parent, boolean enable){
        if (null == parent){
            return;
        }
        int cc = parent.getChildCount();
        if (0 == cc){
            return;
        }
        for (int i = 0; i < cc; i++){
            View v = parent.getChildAt(i);
            v.setEnabled(enable);
        }
    }

    private boolean isStepReport = false;
    private IOnStepChangeListener onStepChangeListener;
    public void setOnStepChangeListener(IOnStepChangeListener onStepChangeListener){
        this.onStepChangeListener = onStepChangeListener;
        if (null != onStepChangeListener && !isStepReport){
            isStepReport = true;
            if (dataHolder.cur >= dataHolder.min && dataHolder.cur <= dataHolder.max){
                onStepChangeListener.onStepChanged(dataHolder.cur, dataHolder.min, dataHolder.max);
            }else {
                onStepChangeListener.onError();
            }
        }
    }

    /**
     *
     */
    public interface IOnStepChangeListener{
        /**
         * @param step
         * @param min
         * @param max
         */
        void onStepChanged(int step, int min, int max);

        void onError();
    }

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
