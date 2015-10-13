package ws.dyt.stepview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import ws.dyt.stepview.R;

/**
 * Created by yangxiaowei on 15/10/12.
 */
public class StepImageView extends BaseStepView {
    protected ImageView mIvMinus;
    protected ImageView mIvPlus;

    private int stepMinusDrawableSelector, stepPlusDrawableSelector;

    public StepImageView(Context context) {
        super(context);
        this.init(context, null, 0);
    }

    public StepImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, 0);
    }

    public StepImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle){
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.StepImageView, defStyle, 0);
        stepMinusDrawableSelector = a.getResourceId(R.styleable.StepImageView_step_minus_drawable_selector, R.drawable.step_minus_image_selector);

        stepPlusDrawableSelector = a.getResourceId(R.styleable.StepImageView_step_plus_drawable_selector, R.drawable.step_plus_image_selector);

        a.recycle();

        this.initView();
    }

    private void initView(){
        this.setStepMinusDrawableSelector(stepMinusDrawableSelector);
        this.setStepPlusDrawableSelector(stepPlusDrawableSelector);
    }

    @Override
    public View setStepMinusView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        mIvMinus = (ImageView) layoutInflater.inflate(R.layout.step_imageview, null, false);
        return mIvMinus;
    }

    @Override
    public View setStepPlusView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        mIvPlus = (ImageView) layoutInflater.inflate(R.layout.step_imageview, null, false);
        return mIvPlus;
    }

    public void setStepMinusDrawableSelector(int stepMinusDrawableSelector) {
        this.stepMinusDrawableSelector = stepMinusDrawableSelector;
        mIvMinus.setImageResource(stepMinusDrawableSelector);
    }

    public void setStepPlusDrawableSelector(int stepPlusDrawableSelector) {
        this.stepPlusDrawableSelector = stepPlusDrawableSelector;
        mIvPlus.setImageResource(stepPlusDrawableSelector);
    }
}
