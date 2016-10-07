package ru.anit.anitfresh.general.general;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 79900 on 14.07.2016.
 */
public class DataUtils {


    public static String getDate(long date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);

    };


    public static String getTime(long time) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");
        return timeFormat.format(time);

    };

    public static String getFullDate(long date){

        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        return fullDateFormat.format(date);
    }

    public static Long dataJsonToLong(String strData) {

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date docDate= null;

        try {
            docDate = format.parse(strData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return docDate.getTime();
    }

}
