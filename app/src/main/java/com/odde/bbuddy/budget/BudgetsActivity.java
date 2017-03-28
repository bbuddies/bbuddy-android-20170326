package com.odde.bbuddy.budget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.odde.bbuddy.R;
import com.odde.bbuddy.common.Consumer;

import org.robobinding.ViewBinder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.odde.bbuddy.di.component.ActivityComponentFactory.createActivityComponentBy;

public class BudgetsActivity extends Fragment {

    @Inject
    PresentableBudgets presentableBudgets;

    @Inject
    ViewBinder viewBinder;

    @Inject
    CalTotalBudgetAmount calTotalBudgetAmount;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createActivityComponentBy(getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = viewBinder.inflateAndBindWithoutAttachingToRoot(R.layout.activity_budgets, presentableBudgets, container);

        View searchTotalBudgetsBtn = view.findViewById(R.id.searchTotalBudgetsBtn);
        final EditText startTimeEditText = (EditText) view.findViewById(R.id.startTimeDatePicker);
        final EditText endTimeEditText = (EditText) view.findViewById(R.id.endTimeDatePicker);
        searchTotalBudgetsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final Date startDate = dateFormat.parse(startTimeEditText.getText().toString());
                    final Date endDate = dateFormat.parse(endTimeEditText.getText().toString());

                    presentableBudgets.searchTotalBudgets(startDate, endDate, new Consumer<List<Budget>>() {
                        @Override
                        public void accept(List<Budget> budgets) {
                            Intent intent = new Intent(BudgetsActivity.this.getContext(), TotalBudgetsActivity.class);
                            intent.putExtra("totalBudgets", String.valueOf((int)calTotalBudgetAmount.getTotalBudget(budgets, startDate, endDate)));
                            startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }






    @Override
    public void onResume() {
        super.onResume();
        presentableBudgets.refresh();
    }
}
