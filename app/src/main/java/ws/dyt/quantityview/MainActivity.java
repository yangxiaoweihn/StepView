package ws.dyt.quantityview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import ws.dyt.stepview.view.BaseStepView;
import ws.dyt.stepview.view.StepTextView;

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
        ((BaseStepView) findViewById(R.id.aa)).setOnStepChangeListener(ll);
        ((BaseStepView) findViewById(R.id.stepImageView)).setOnStepChangeListener(ll);
        ((BaseStepView) findViewById(R.id.stepImageViewCus1)).setOnStepChangeListener(ll);

//        final StepTextView stepTextView = ((StepTextView) findViewById(R.id.stepTextView3));
//        stepTextView.setStepPlusText("加");
//        stepTextView.setStepMinusText("减");
//        stepTextView.setOnStepChangeListener(new BaseStepView.IOnStepChangeListener() {
//            @Override
//            public void onStepChanged(int step, int min, int max) {
//                int c = new Random().nextInt(255);
//                stepTextView.setInputColor(Color.rgb(Color.red(c), Color.green(c), Color.blue(c)));
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });


    }

}
