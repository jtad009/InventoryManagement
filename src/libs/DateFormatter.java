/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Epic
 */
public class DateFormatter {

    /**
     * Date format = YYYY-MM-DD
     *
     * @param fromDate
     * @param toDate
     * @return int No of days between dates
     */
    public int numberOfDays(String fromDate, String toDate) {
        java.util.Calendar cal1 = new java.util.GregorianCalendar();
        java.util.Calendar cal2 = new java.util.GregorianCalendar();
        
        //split year, month and days from the date using StringBuffer.
       // fromDate = this.parseDate(fromDate);
        
        String[] date = fromDate.split("/");
        String yearFrom = date[0];
        String monFrom = date[1];
        String ddFrom = date[2];
       

        // set the fromDate in java.util.Calendar
        cal1.set(Integer.parseInt(yearFrom), Integer.parseInt(monFrom), Integer.parseInt(ddFrom));
        
        //split year, month and days from the date using StringBuffer.
        //toDate =  this.parseDate(toDate);
        String[] date_2 = toDate.split("/");
        String yearTo = date_2[0];
        String monTo = date_2[1];
        String ddTo = date_2[2];

        // set the toDate in java.util.Calendar
        cal2.set(Integer.parseInt(yearTo), Integer.parseInt(monTo), Integer.parseInt(ddTo));

        //call method daysBetween to get the number of days between two dates
        return daysBetween(cal1.getTime(), cal2.getTime());

    }

    private int daysBetween(Date d1, Date d2) {
        //switch dates to get +ive days
        return (int) (( d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
    }
    private String parseDate(String date){
        long dateTOChange = Long.parseLong(date);
        Date d = new Date(dateTOChange);
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            d = dateFormat.parse(d.toString());
        } catch (ParseException ex) {
            Logger.getLogger(DateFormatter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d != null ? dateFormat.format(d) : "";
    }
    public String todaysDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
       return dateFormat.format(c.getTime());
    }
}
