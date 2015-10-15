package ws.dyt.quantityview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ws.dyt.stepview.view.BaseStepView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView tvShow = (TextView) findViewById(R.id.tv_show);
        BaseStepView.IOnStepChangeListener ll = new BaseStepView.IOnStepChangeListener() {
            @Override
            public void onStepChanged(int step, int min, int max) {
                tvShow.setText("" + step);
            }

            @Override
            public void onError() {
                tvShow.setText("");
            }
        };
        ((BaseStepView) findViewById(R.id.stepTextView)).setOnStepChangeListener(ll);
        ((BaseStepView) findViewById(R.id.stepTextViewCus1)).setOnStepChangeListener(ll);
        ((BaseStepView) findViewById(R.id.stepImageView)).setOnStepChangeListener(ll);
        ((BaseStepView) findViewById(R.id.stepImageViewCus1)).setOnStepChangeListener(ll);

    }

}
