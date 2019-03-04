package com.why.modul_net.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static final String DETAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_MINUTE_SECOND = "HH:mm";

    public static long getCurrentTime() {
        return LocalDateTime.now().toDateTime().getMillis();
    }

    public static String getFormatedTime() {
        return getFormatedTime(PATTERN_MINUTE_SECOND);
    }

    public static String getFormatedTime(String pattern) {
        if (pattern == null) {
            pattern = DETAULT_PATTERN;
        }
        return LocalDateTime.now().toDateTime().toString(pattern);
    }

    public static String formatTime(String time) {
        return formatTime(time, DETAULT_PATTERN);
    }

    public static String formatTime(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            Date date = sdf.parse(time);
            long ms = date.getTime();
            return formatTime(ms);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int minutesSinceLastBack(long before) {
        DateTime nowTime = LocalDateTime.now().toDateTime();
        DateTime beforeTime = new LocalDateTime(before).toDateTime();
        return Minutes.minutesBetween(beforeTime, nowTime).getMinutes();
    }

    public static String formatTime2(long ms){
        DateTime now = LocalDateTime.now().toDateTime();
        DateTime before = new LocalDateTime(ms).toDateTime();
        return before.toString("yyyy-MM-dd");

    }

    public static String formatTime(long ms) {
        DateTime now = LocalDateTime.now().toDateTime();
        DateTime before = new LocalDateTime(ms).toDateTime();
        if (before.getMillis() > now.getMillis()) {
            return before.toString("yyyy-MM-dd HH:mm:ss");
        }

        if (before.getMillis() / 1000 == now.getMillis() / 1000) {
            return "刚刚";
        }
        StringBuilder builder = new StringBuilder();
        int seconds = Seconds.secondsBetween(before, now).getSeconds();
        int minutes = Minutes.minutesBetween(before, now).getMinutes();
        int hours = Hours.hoursBetween(before, now).getHours();
        int days = Days.daysBetween(before, now).getDays();

        if (seconds < 60) {
            builder.append(seconds);
            builder.append("秒前");
            return builder.toString();
        }

        if (minutes < 60) {
            builder.append(minutes);
            builder.append("分钟前");
            return builder.toString();
        }

        if (hours < 24) {
            builder.append(hours);
            builder.append("小时前");
            return builder.toString();
        }

        if (days <= 10) {
            builder.append(days);
            builder.append("天前");
            return builder.toString();
        }

        return before.toString("yyyy-MM-dd HH:mm:ss");
    }


    public static String chatTime(long ms) {
        DateTime now = LocalDateTime.now().toDateTime();
        DateTime before = new LocalDateTime(ms).toDateTime();
        if (before.getMillis() > now.getMillis()) {
            return before.toString("yyyy-MM-dd HH:mm:ss");
        }

        if (before.getMillis() / 1000 == now.getMillis() / 1000) {
            return "刚刚";
        }
        StringBuilder builder = new StringBuilder();
        int seconds = Seconds.secondsBetween(before, now).getSeconds();
        int minutes = Minutes.minutesBetween(before, now).getMinutes();
        int hours = Hours.hoursBetween(before, now).getHours();
        int days = Days.daysBetween(before, now).getDays();

        if (seconds < 60) {
            builder.append(seconds);
            builder.append("秒前");
            return builder.toString();
        }

        if (minutes < 60) {
            builder.append(minutes);
            builder.append("分钟前");
            return builder.toString();
        }

        if (hours < 24) {
            builder.append(hours);
            builder.append("小时前");
            return builder.toString();
        }

        if (days <= 10) {
            builder.append(days);
            builder.append("天前");
            return builder.toString();
        }

        return before.toString("yyyy-MM-dd");
    }

    public static String convertSeconds(int seconds) {
        if (seconds <= 0) {
            return "00:00";
        } else {
            int i = seconds / 60;
            String m = i + "";
            if (i < 10) {
                m = "0" + i;
            }

            int j = seconds % 60;
            String s = j + "";
            if (j < 10) {
                s = "0" + j;
            }
            return m + ":" + s;
        }
    }

    //根据秒数转化为时分秒   00:00:00
    public static String getTime(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour +":"+ minute + ":0" + second;
            }
            return "0" + hour +":"+ minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour +":"+ minute + ":0" + second;
        }
        return hour+":" + minute + ":" + second;
    }

}
