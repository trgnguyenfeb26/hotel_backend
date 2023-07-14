package cn.mafangui.hotel.utils;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MyDateTimeFormat {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    Calendar calendar = Calendar.getInstance();
    String[] weekDays = { "Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy" };

    /**
     * Lấy năm
     * @param date
     * @return
     */
    public int getYear(Date date) {
        simpleDateFormat.applyPattern("yyyy");
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    /**
     * Lấy tháng
     * @param date
     * @return
     */
    public int getMonth(Date date) {
        simpleDateFormat.applyPattern("MM");
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    /**
     * Lấy ngày
     * @param date
     * @return
     */
    public int getDay(Date date){
        simpleDateFormat.applyPattern("dd");
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    /**
     * Trả về thứ của một ngày
     * @param date
     * @return
     */
    public String getWeek(Date date){
        simpleDateFormat.applyPattern("yyyy-MM-dd");
        calendar.setTime(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) w = 0;
        return weekDays[w];
    }

    /**
     * Định dạng thành năm
     * @param date
     * @return
     */
    public String formatYear(Date date){
        simpleDateFormat.applyPattern("yyyy");
        return simpleDateFormat.format(date);
    }

    /**
     * Định dạng thành năm-tháng
     * @param date
     * @return
     */
    public String formatMonth(Date date){
        simpleDateFormat.applyPattern("yyyy-MM");
        return simpleDateFormat.format(date);
    }

    /**
     * Định dạng thành năm-tháng-ngày
     * @param date
     * @return
     */
    public String formatDay(Date date){
        simpleDateFormat.applyPattern("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * Định dạng thành năm-tháng-ngày-giờ-phút
     * @param date
     * @return
     */
    public String formatMinute(Date date){
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * Định dạng thành năm-tháng-ngày-giờ-phút-giây
     * @param date
     * @return
     */
    public String formatSecond(Date date){
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
