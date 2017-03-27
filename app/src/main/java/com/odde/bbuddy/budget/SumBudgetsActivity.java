package com.odde.bbuddy.budget;

//import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.odde.bbuddy.R;
import com.odde.bbuddy.common.Singleton;
//import com.odde.bbuddy.databinding.ActivitySumbudgetBinding;


/**
 * Created by lizz on 2017/3/27.
 */

public class SumBudgetsActivity extends Fragment {

    private TextView tv;
//    ActivitySumbudgetBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        if(binding==null) {
//            binding = DataBindingUtil.inflate(inflater, R.layout.activity_sumbudget, container, false);
//            binding.setSumBudgets(Singleton.singleton.getSumBudgets());
//        }
        return inflater.inflate(R.layout.activity_sumbudget,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) view.findViewById(R.id.amount);
    }


    @Override
    public void onResume() {
        super.onResume();
        tv.setText(Singleton.singleton.getSumBudgets().getSumAmount());

//        binding.notifyChange();
    }
}
