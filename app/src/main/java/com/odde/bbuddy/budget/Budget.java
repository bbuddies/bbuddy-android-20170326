package com.odde.bbuddy.budget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.joda.time.LocalDate;
import org.joda.time.ReadablePartial;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Budget {
    private final Period period;
    private String month;
    private int amount;
    private long id;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Budget(String month, int amount){
        this.period = getPeriod();
    }

    private LocalDate getStartDate() {
        return LocalDate.parse(month +"-01");
    }

    private LocalDate getEndDate() {
        return getStartDate().dayOfMonth().withMaximumValue();
    }

    private Period getPeriod() {
        return new Period(getStartDate(), getEndDate());
    }

    public float getAmountOfOverlappingDays(Period period) {
        return (float) period.getOverlappingDayCount(this.period)
                / (float) this.period.getDayCount() * getAmount();
    }
}
