package ws.dyt.quantityview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ws.dyt.test.BaseStepViewV2;
import ws.dyt.test.StepTextView;

public class MainActivity extends AppCompatActivity {
    StepTextView stepTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView tvShow = (TextView) findViewById(R.id.tv_show);
        stepTextView = (StepTextView) findViewById(R.id.stepTextView);
        stepTextView.setOnStepChangeListener(new BaseStepViewV2.IOnStepChangeListener() {
            @Override
            public void onStepChanged(int step, int min, int max) {
                tvShow.setText(""+step);
            }

            @Override
            public void onError() {
                tvShow.setText("");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepTextView.setStepPaddingRight(5);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

}
