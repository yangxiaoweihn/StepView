package ws.dyt.stepview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private View rootView;
    protected RelativeLayout mVgMinus;
    protected RelativeLayout mVgPlus;
    protected EditText mEtInput;

    private float stepMinusWeight = 1.f, stepPlusWeight = 1.f;
    private int stepMinusBackgroundSelector, stepPlusBackgroundSelector;

    private float inputFontSize;
    private boolean inputIsInput = true;
    private int inputColor;

    class DataHolder{
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
        rootView = LayoutInflater.from(context).inflate(R.layout.base_step_view, null, false);
        this.addView(rootView, lp);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseStepView, defStyle, 0);

        stepMinusWeight = a.getFloat(R.styleable.BaseStepView_step_minus_weight, 1.f);
        stepPlusWeight = a.getFloat(R.styleable.BaseStepView_step_plus_weight, 1.f);
        stepMinusBackgroundSelector = a.getResourceId(R.styleable.BaseStepView_step_minus_background_selector, R.drawable.step_background_selector);
        stepPlusBackgroundSelector = a.getResourceId(R.styleable.BaseStepView_step_plus_background_selector, R.drawable.step_background_selector);

        inputIsInput = a.getBoolean(R.styleable.BaseStepView_input_is_input, true);
        inputFontSize = px2sp(a.getDimension(R.styleable.BaseStepView_input_font_size, defaultFontSize));
        inputColor = a.getColor(R.styleable.BaseStepView_input_font_color, Color.argb(80, 0, 0, 0));

        dataHolder.min = a.getInt(R.styleable.BaseStepView_input_min, 0);
        dataHolder.max = a.getInt(R.styleable.BaseStepView_input_max, 0);
        dataHolder.cur = a.getInt(R.styleable.BaseStepView_input_cur, 0);


        a.recycle();

        this.initView();
    }

    private void initView(){
        mVgMinus = (RelativeLayout) rootView.findViewById(R.id.vg_minus);
        mVgPlus = (RelativeLayout) rootView.findViewById(R.id.vg_plus);
        mEtInput = (EditText) rootView.findViewById(R.id.et_input);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
                );
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        mVgMinus.addView(setStepMinusView(), lp);
        mVgPlus.addView(setStepPlusView(), lp);


        mEtInput.removeTextChangedListener(tw);
        mEtInput.addTextChangedListener(tw);
        mVgMinus.setOnClickListener(onClickListener);
        mVgPlus.setOnClickListener(onClickListener);
        this.flushViewParams();
    }

    private void flushViewParams(){
//        mVgMinus.setLayoutParams(new LinearLayout.LayoutParams(
//                LayoutParams.WRAP_CONTENT,
//                LayoutParams.MATCH_PARENT,
//                stepMinusWeight));
//        mVgMinus.setBackgroundResource(stepMinusBackgroundSelector);
//
//        mVgPlus.setLayoutParams(new LinearLayout.LayoutParams(
//                LayoutParams.WRAP_CONTENT,
//                LayoutParams.MATCH_PARENT,
//                stepPlusWeight));
//        mVgPlus.setBackgroundResource(stepPlusBackgroundSelector);
//
//        mEtInput.setTextSize(inputFontSize);
//        mEtInput.setTextColor(inputColor);
//
//        mEtInput.setFocusable(inputIsInput);
//        mEtInput.setEnabled(inputIsInput);

        this.setStepMinusWeight(stepMinusWeight);
        this.setStepMinusBackgroundSelector(stepMinusBackgroundSelector);
        this.setStepPlusWeight(stepPlusWeight);
        this.setStepPlusBackgroundSelector(stepPlusBackgroundSelector);
        this.setInputFontSize(inputFontSize);
        this.setInputColor(inputColor);
        this.setInputIsInput(inputIsInput);
        mEtInput.setText(String.valueOf(dataHolder.cur));
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

    private void flushStepViewStatus(boolean isStepEnable){
        if (!isStepEnable){
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
                int e = Integer.valueOf(s.toString());
                dataHolder.cur = e;
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
//        mEtInput.removeTextChangedListener(tw);
//        mEtInput.addTextChangedListener(tw);
//        mEtInput.setText(String.valueOf(dataHolder.cur));
//        if (null != onStepChangeListener && !isStepReport){
//            isStepReport = true;
//            if (dataHolder.cur >= dataHolder.min && dataHolder.cur <= dataHolder.max){
//                Log.e("DEBUG", "istener-cur: " + dataHolder.cur + ", " + this.hashCode()+" , "+mEtInput.hashCode());
//                onStepChangeListener.onStepChanged(dataHolder.cur, dataHolder.min, dataHolder.max);
//            }else {
//                onStepChangeListener.onError();
//            }
//        }
    }

    public void setStepMinusWeight(float stepMinusWeight) {
        this.stepMinusWeight = stepMinusWeight;
        mVgMinus.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT,
                stepMinusWeight));
    }

    public void setStepPlusWeight(float stepPlusWeight) {
        this.stepPlusWeight = stepPlusWeight;
        mVgPlus.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT,
                stepPlusWeight));
    }

    public void setStepMinusBackgroundSelector(int stepMinusBackgroundSelector) {
        this.stepMinusBackgroundSelector = stepMinusBackgroundSelector;
        mVgMinus.setBackgroundResource(stepMinusBackgroundSelector);
    }

    public void setStepPlusBackgroundSelector(int stepPlusBackgroundSelector) {
        this.stepPlusBackgroundSelector = stepPlusBackgroundSelector;
        mVgPlus.setBackgroundResource(stepPlusBackgroundSelector);
    }

    public void setInputFontSize(float inputFontSize) {
        this.inputFontSize = inputFontSize;
        mEtInput.setTextSize(inputFontSize);
    }

    public void setInputIsInput(boolean inputIsInput) {
        this.inputIsInput = inputIsInput;
        mEtInput.setFocusable(inputIsInput);
        mEtInput.setEnabled(inputIsInput);
    }

    public void setInputColor(int inputColor) {
        this.inputColor = inputColor;
        mEtInput.setTextColor(inputColor);
    }

    public void setInputMin(int min){
        this.dataHolder.min = min;
        this.flushStepViewStatus(true);
    }

    public void setInputMax(int max){
        this.dataHolder.max = max;
        this.flushStepViewStatus(true);
    }

    public void setInputCurrentValue(int currentValue){
        this.dataHolder.cur = currentValue;
        this.flushStepViewStatus(true);
    }

    /**
     *数据变动监听器
     */
    public interface IOnStepChangeListener{
        /**
         * @param step  变动数据
         * @param min   设置的最小值
         * @param max   设置的最大值
         */
        void onStepChanged(int step, int min, int max);

        /**
         *数据变动异常时回调
         */
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
