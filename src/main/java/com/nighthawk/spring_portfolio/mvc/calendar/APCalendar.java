package com.nighthawk.spring_portfolio.mvc.calendar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

// Prototype Implementation

public class APCalendar {

    /**
     * Returns true if year is a leap year and false otherwise.
     * isLeapYear(2019) returns False
     * isLeapYear(2016) returns True
     */
    public static boolean isLeapYear(int year) {
        // implementation not shown
        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
            return true;
        else
            return false;
    }

    /**
     * Returns the value representing the day of the week
     * 0 denotes Sunday,
     * 1 denotes Monday, ...,
     * 6 denotes Saturday.
     * firstDayOfYear(2019) returns 2 for Tuesday.
     */
    public static int firstDayOfYear(int year) {
        // implementation not shown
        java.util.Date date = new java.util.GregorianCalendar(year, 0, 1).getTime();
        String day = date.toString();
        String firstDayOfYear = day.substring(0, 3);

        switch (firstDayOfYear) {
            case "Sun":
                return 0;
            case "Mon":
                return 1;
            case "Tue":
                return 2;
            case "Wed":
                return 3;
            case "Thu":
                return 4;
            case "Fri":
                return 5;
            case "Sat":
                return 6;
            default:
                return -1;
        }
    }

    /**
     * Returns n, where month, day, and year specify the nth day of the year.
     * This method accounts for whether year is a leap year.
     * dayOfYear(1, 1, 2019) return 1
     * dayOfYear(3, 1, 2017) returns 60, since 2017 is not a leap year
     * dayOfYear(3, 1, 2016) returns 61, since 2016 is a leap year.
     */
    public static int dayOfYear(int month, int day, int year) {
        // implementation not shown
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        int date = cal.get(Calendar.DAY_OF_YEAR);

        return date;
    }

    /**
     * Returns the number of leap years between year1 and year2, inclusive.
     * Precondition: 0 <= year1 <= year2
     */
    public static int numberOfLeapYears(int year1, int year2) {
        int leapYearCounter = 0;
        for (int year = year1; year <= year2; year++) {
            if (isLeapYear(year)) {
                leapYearCounter++;
            }
        }

        return leapYearCounter;
    }

    /**
     * Returns the value representing the day of the week for the given date
     * Precondition: The date represented by month, day, year is a valid date.
     */
    public static int dayOfWeek(int month, int day, int year) {
        return (dayOfYear(month, day, year) - 1 + firstDayOfYear(year)) % 7;
    }

    /** Tester method */
    public static void main(String[] args) {
        // Private access modifiers
        System.out.println("firstDayOfYear: " + APCalendar.firstDayOfYear(2025));
        System.out.println("dayOfYear: " + APCalendar.dayOfYear(3, 1, 2020));

        // Public access modifiers
        System.out.println("isLeapYear: " + APCalendar.isLeapYear(2022));
        System.out.println("numberOfLeapYears: " + APCalendar.numberOfLeapYears(2000, 2022));
        System.out.println("dayOfWeek: " + APCalendar.dayOfWeek(6, 21, 2023));
    }

}