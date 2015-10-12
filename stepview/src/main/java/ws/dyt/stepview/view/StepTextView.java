package ws.dyt.stepview.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import ws.dyt.stepview.R;

/**
 * Created by yangxiaowei on 15/10/9.
 */
public class StepTextView extends BaseStepView {
    protected TextView mTvMinus;
    protected TextView mTvPlus;

    private float stepFontSize;
    private ColorStateList stepMinusFontSelector, stepPlusFontSelector;
    private String stepMinusText, stepPlusText;

    public StepTextView(Context context) {
        super(context);
        this.init(context, null, 0);
    }

    public StepTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, 0);
    }

    public StepTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle){
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.StepTextView, defStyle, 0);
        stepFontSize = px2sp(a.getDimension(R.styleable.StepTextView_step_font_size, defaultFontSize));
        stepMinusFontSelector = a.getColorStateList(R.styleable.StepTextView_step_minus_font_selector);
        stepPlusFontSelector = a.getColorStateList(R.styleable.StepTextView_step_plus_font_selector);

        stepMinusText = a.getString(R.styleable.StepTextView_step_minus_text);
        stepPlusText = a.getString(R.styleable.StepTextView_step_plus_text);

        a.recycle();

        this.initView();
    }

    private void initView(){
        mTvMinus.setTextSize(stepFontSize);
        mTvPlus.setTextSize(stepFontSize);

        mTvMinus.setText(stepMinusText);
        mTvPlus.setText(stepPlusText);

        mTvPlus.setTextColor(stepPlusFontSelector);
        mTvMinus.setTextColor(stepMinusFontSelector);
    }

    @Override
    public View setStepMinusView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        mTvMinus = (TextView) layoutInflater.inflate(R.layout.step_textview, null, false);
        return mTvMinus;
    }

    @Override
    public View setStepPlusView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        mTvPlus = (TextView) layoutInflater.inflate(R.layout.step_textview, null, false);
        return mTvPlus;
    }
}