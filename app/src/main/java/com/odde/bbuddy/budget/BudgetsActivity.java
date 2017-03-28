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
                            intent.putExtra("totalBudgets", String.valueOf((int)getTotalBudget(budgets, startDate, endDate)));
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


    public static double getTotalBudget(List<Budget> budgets, Date startDate, Date endDate){
        double totalAmount = 0;
        if (budgets == null){
            return 0;
        }
        for(Budget budeget : budgets) {
            String month = budeget.getMonth();
            double amount = budeget.getAmount();
            long start = startDate.getTime();
            long end = endDate.getTime();
            long first = getFirstDate(month);
            long last = getLastDate(month);
            int days = getTotalDays(month);
            if (start > last){
                continue;
            } else if ( end < first){
                continue;
            } else if (first <= start){

                if(end < last){
                    totalAmount += (amount/days)*getDaysBy2Date(start,end);
                } else {
                    totalAmount += (amount / days) * getDaysBy2Date(start, last);
                }
            } else if (last < end) {

                totalAmount += (amount/days)*getDaysBy2Date(first,last);
            } else if (first < end) {

                totalAmount += (amount/days)*getDaysBy2Date(first,end);
            } else {
                totalAmount += (amount/days)*getDaysBy2Date(start,end);
            }

        }
        return totalAmount;
    }

    private static long getDaysBy2Date (long date1, long date2){
        return (date2 - date1)/1000/60/60/24 + 1;
    }

    private static int getTotalDays(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7))-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private static long getFirstDate(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7))-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

    private static long getLastDate(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7)));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(calendar.DATE,-1);
        return calendar.getTimeInMillis();
    }

    private static long getDate(String date){
        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(date.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5,7))-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(8,10)));
        return calendar.getTimeInMillis();
    }

    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



    @Override
    public void onResume() {
        super.onResume();
        presentableBudgets.refresh();
    }
}
