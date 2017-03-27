package com.odde.bbuddy.budget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.odde.bbuddy.R;
import com.odde.bbuddy.common.Consumer;

import org.robobinding.ViewBinder;

import javax.inject.Inject;

import android.app.DatePickerDialog;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.odde.bbuddy.di.component.ActivityComponentFactory.createActivityComponentBy;

public class BudgetsActivity extends Fragment {

    @Inject
    PresentableBudgets presentableBudgets;

    @Inject
    ViewBinder viewBinder;

    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

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
                    Date startDate = dateFormat.parse(startTimeEditText.getText().toString());
                    Date endDate = dateFormat.parse(endTimeEditText.getText().toString());
                    presentableBudgets.searchTotalBudgets(startDate, endDate, new Consumer<List<Budget>>() {
                        @Override
                        public void accept(List<Budget> budgets) {
                            Intent intent = new Intent(BudgetsActivity.this.getContext(), TotalBudgetsActivity.class);
                            intent.putExtra("totalBudgets", "12345");
                            startActivity(intent);
                            //allBudgets = budgets;
                            //presentationModelChangeSupport.refreshPresentationModel();
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
