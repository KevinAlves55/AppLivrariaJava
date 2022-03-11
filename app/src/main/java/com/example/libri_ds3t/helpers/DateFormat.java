package com.example.libri_ds3t.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormat {

    private String dateFormat = "dd-MM-yyyy HH:mm:ss";
    private String locale = "pt-BR";
    SimpleDateFormat sdf = null;

    public DateFormat(){

        this.sdf = new SimpleDateFormat(this.dateFormat,
                                        new Locale(this.locale));

    }

    public String getDate(){
        return sdf.format(new Date());
    }


}
