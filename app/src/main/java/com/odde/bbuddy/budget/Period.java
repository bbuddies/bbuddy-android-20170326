package com.odde.bbuddy.budget;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getOverlappingDayCount(Period another) {
        LocalDate startOfOverlapping = startDate.isAfter(another.startDate) ? startDate : another.startDate;
        LocalDate endOfOverlapping = endDate.isBefore(another.endDate) ? endDate : another.endDate;
        return new Period(startOfOverlapping, endOfOverlapping).getDayCount();
    }

    public int getDayCount() {
        return Days.daysBetween(startDate, endDate).getDays() + 1;
    }
}
