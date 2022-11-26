package com.example.loto649;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private TextView numereView;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        numereView = findViewById(R.id.numere);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkDate(String line, int year, int month, int day){
       // System.out.println("Start to check date");
        String[] arrOfStr = line.split("\t", 2);
        Map<Integer, Month> months = new HashMap<>();
        months.put(1, Month.JANUARY);
        months.put(2, Month.FEBRUARY);
        months.put(3, Month.MARCH);
        months.put(4, Month.APRIL);
        months.put(5, Month.MAY);
        months.put(6, Month.JUNE);
        months.put(7, Month.JULY);
        months.put(8, Month.AUGUST);
        months.put(9, Month.SEPTEMBER);
        months.put(10, Month.OCTOBER);
        months.put(11, Month.NOVEMBER);
        months.put(12, Month.DECEMBER);
       // String dateInString = "Mon, 05 May 1980";
        String dateInString = arrOfStr[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate dateTime = LocalDate.parse(dateInString, formatter);
      //  System.out.println("y: "+dateTime.getYear()+" m: " + dateTime.getDayOfMonth()+ " d: "+dateTime.getMonth());
      //  System.out.println("y: "+year+" m: " + day+ " d: "+months.get(month));
        if(dateTime.getYear() == year && dateTime.getDayOfMonth() == day && dateTime.getMonth() == months.get(month)){
            System.out.println("FOUND");
            return true;
        }else{
            System.out.println("Keep searching");
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getNumbersFromDate(int day, int year, int month){
        System.out.println("Get numbers from date");
        InputStream is = getResources().openRawResource(R.raw.lotonumbers);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line;
        try {
            while ( (line = reader.readLine()) != null){
                if(checkDate(line,year,month,day)){
                    System.out.println("Found: "+line);
                    return line;
                }
            }
        } catch (IOException e){
            Log.println(Log.ASSERT,"asdasd","asd");
        }

        return "not found";
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day,month,year);
                System.out.println("onDateSet");
                String search =  getNumbersFromDate(day,year,month);
                if(search == "not found"){
                    numereView.setText("Nu s-a gasit");
                }else{
                    String[] numere = search.split("\t",7);
                    numereView.setText(numere[1] + "," + numere[2] + ","+ numere[3] + ","+ numere[4] + ","+ numere[5] + "," + numere[6]);
                    System.out.println(numere);
                }
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Ianuarie";
        if(month == 2)
            return "Februarie";
        if(month == 3)
            return "Martie";
        if(month == 4)
            return "Aprile";
        if(month == 5)
            return "Mai";
        if(month == 6)
            return "Iunie";
        if(month == 7)
            return "Iulie";
        if(month == 8)
            return "August";
        if(month == 9)
            return "Septembrie";
        if(month == 10)
            return "Octombrie";
        if(month == 11)
            return "Noiembrie";
        if(month == 12)
            return "Decembrie";

        return "NaN";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }
}