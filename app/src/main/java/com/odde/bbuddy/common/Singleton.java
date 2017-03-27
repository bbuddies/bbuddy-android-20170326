package com.odde.bbuddy.common;

import com.odde.bbuddy.budget.SumBudgets;

/**
 * Created by lizz on 2017/3/28.
 */

public class Singleton {
    public static Singleton singleton=new Singleton();
    private Singleton(){}
    private SumBudgets sumBudgets=null;

    public SumBudgets getSumBudgets(){
        if(sumBudgets==null){
            synchronized (Singleton.class){
                if(sumBudgets==null){
                    sumBudgets=new SumBudgets();
                }
            }
        }
        return sumBudgets;
    }

}
