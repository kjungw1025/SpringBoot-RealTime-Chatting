package com.RealTime.Chatting.global.generator;

public class NextMonthGenerator {
    public static String generateNextMonth(String input) {
        String[] parts = input.split("/");

        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        int nextYear, nextMonth;

        if (month == 12) {
            // 만약 입력한 달이 12월이면, 다음 년도의 1월로 설정
            nextYear = year + 1;
            nextMonth = 1;
        } else {
            // 그 외의 경우에는 다음 달로 설정
            nextYear = year;
            nextMonth = month + 1;
        }
        String result = (nextYear + "/" + nextMonth);

        return result;
    }
}
