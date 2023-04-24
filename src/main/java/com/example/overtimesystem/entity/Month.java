package com.example.overtimesystem.entity;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Month {
    January("January", 1), February("February", 2), March("March", 3), April("April", 4), May("May", 5),
    June("June", 6), July("July", 7), August("August", 8), September("September", 9), October("October", 10),
    November("November", 11), December("December", 12);

    private static final Map<String, Month> BY_MONTH = new HashMap<>();
    private static final Map<Integer, Month> BY_MONTH_NUMBER = new HashMap<>();

    static {
        for (Month e : values()) {
            BY_MONTH.put(e.month, e);
            BY_MONTH_NUMBER.put(e.monthNumber, e);
        }
    }

    public final String month;
    public final int monthNumber;


    private Month(String month, int monthNumber) {
        this.month = month;
        this.monthNumber = monthNumber;
    }

    public static Month valueOfMonth(String month) {
        return BY_MONTH.get(month);
    }

    public static Month valueOfMonthNumber(int monthNumber) {
        return BY_MONTH_NUMBER.get(monthNumber);
    }

    public static void main(String[] args) {
        System.out.println(Month.valueOfMonthNumber(4));
    }
}
