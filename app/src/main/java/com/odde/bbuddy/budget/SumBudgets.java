package com.odde.bbuddy.budget;

//import android.databinding.BaseObservable;
//import android.databinding.Bindable;
//
//import com.odde.bbuddy.BR;


/**
 * Created by lizz on 2017/3/27.
 */

public class SumBudgets{//} extends BaseObservable{



    private String sumAmount;
    public SumBudgets(){}

   // @Bindable
    public String getSumAmount(){
        return sumAmount;
    }

    public void setSumAmount(String sumAmount){
        this.sumAmount=sumAmount;
    }

   // public void notifyChanged(){
//        notifyPropertyChanged(BR.sumAmount);
//    }
}
