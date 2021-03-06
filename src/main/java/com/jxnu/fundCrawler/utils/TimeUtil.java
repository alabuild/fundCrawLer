package com.jxnu.fundCrawler.utils;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yaphyao
 * @version 2018/11/8
 * @see com.jxnu.fundCrawler.utils
 */
public class TimeUtil {
    private final static Logger logger = LoggerFactory.getLogger(TimeUtil.class);
    private static Set<String> timeSet = new HashSet<String>();
    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static {
        timeSet.add("2018-01-01");
        timeSet.add("2018-02-15");
        timeSet.add("2018-02-16");
        timeSet.add("2018-02-17");
        timeSet.add("2018-02-18");
        timeSet.add("2018-02-19");
        timeSet.add("2018-02-20");
        timeSet.add("2018-02-21");
        timeSet.add("2018-04-05");
        timeSet.add("2018-04-06");
        timeSet.add("2018-04-07");
        timeSet.add("2018-04-29");
        timeSet.add("2018-04-30");
        timeSet.add("2018-05-01");
        timeSet.add("2018-06-16");
        timeSet.add("2018-06-17");
        timeSet.add("2018-06-18");
        timeSet.add("2018-09-22");
        timeSet.add("2018-09-23");
        timeSet.add("2018-09-24");
        timeSet.add("2018-10-01");
        timeSet.add("2018-10-02");
        timeSet.add("2018-10-03");
        timeSet.add("2018-10-04");
        timeSet.add("2018-10-05");
        timeSet.add("2018-10-06");
        timeSet.add("2018-10-07");
    }

    /**
     * 获取两个时间之间时间(除了节假日和周末)
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static Set<String> intervalTime(String startTime, String endTime) {
        Set<String> sets = new HashSet<String>();
        try {
            Integer num = intervalDayNum(startTime, endTime);
            if (num == 0) return sets;
            for (int index = 0; index < num; index++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateFormat.parse(startTime));
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + index);
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                        calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) continue;
                String time = dateFormat.format(calendar.getTime());
                if (timeSet.contains(time)) continue;
                sets.add(time);
            }
            return sets;
        } catch (ParseException e) {
            return sets;
        }
    }


    /**
     * 两个时间相差天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Integer intervalDayNum(String startTime, String endTime) {
        Integer num = 0;
        try {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(dateFormat.parse(startTime));
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(dateFormat.parse(endTime));
            Long endTimeLong = endCalendar.getTime().getTime();
            Long startTimeLong = startCalendar.getTime().getTime();

            return (int) ((endTimeLong - startTimeLong)/86400000);
        } catch (Exception e) {
            logger.error("intervalTime error:()", ExceptionUtils.getStackTrace(e));
        }
        return num;
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(intervalDayNum("2017-11-07", "2018-11-08"));
        System.out.println(intervalTime("2017-10-09", "2017-11-08"));
    }
}
