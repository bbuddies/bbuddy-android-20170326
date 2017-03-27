package com.odde.bbuddy.budget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.odde.bbuddy.R;

/**
 * Created by Stephen Yu on 27/03/2017.
 */

public class TotalBudgetsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String totalBudgets = getIntent().getExtras().getString("totalBudgets");
        setContentView(R.layout.activity_totalbudgets);
        ((TextView)findViewById(R.id.totalBudgetsText)).setText(totalBudgets);
    }
}
