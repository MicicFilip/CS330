package com.example.micic.cs330_v04;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Action Bar na Main strani
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.v05:
                Intent v05 = new Intent(this, SecondActivity.class);
                this.startActivity(v05);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void btnSaved_clicked(View view) {
        DisplayToast("Kliknuli ste na dugme Save");
    }

    static int progress;
    ProgressBar progressBar;
    int progressStatus = 0;
    Handler handler = new Handler();

    TimePicker timePicker;
    DatePicker datePicker;
    int hour, minute;
    int yr, month, day;
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = 0;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(200);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus = doSomeWork();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            private int doSomeWork() {
                try {
                    Thread.sleep(50);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ++progress;
            }


        }).start();


        // Ovo je pop-up sa TimePickerom i Kalendarom
//        timePicker = (TimePicker) findViewById(R.id.timePicker);
//        timePicker.setIs24HourView(true);
//
//        datePicker = (DatePicker) findViewById(R.id.datePicker);
//
//        Calendar today = Calendar.getInstance();
//        yr = today.get(Calendar.YEAR);
//        month = today.get(Calendar.MONTH);
//        day = today.get(Calendar.DAY_OF_MONTH);
//        showDialog(DATE_DIALOG_ID);


        Button btnOpen = (Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayToast("Kliknuli ste dugme open");
            }
        });


        CheckBox checkBox = (CheckBox) findViewById(R.id.chkAutosave);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    DisplayToast("Checkbox je cekiran");
                } else {
                    DisplayToast("Checkbox je odcekiran");
                }
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rdbGp1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = (RadioButton) findViewById(R.id.rdb1);
                if (rb1.isChecked()) {
                    DisplayToast("Option 1 je cekiran");
                } else {
                    DisplayToast("Option 2 je necekiran");
                }
            }
        });

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle1);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                    DisplayToast("Toggle je ukljucen");
                } else {
                    DisplayToast("Toggle je iskljucen");
                }
            }
        });


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeListener, hour, minute, false);
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateListener, yr, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yr = year;
            month = monthOfYear;
            day = dayOfMonth;
            Toast.makeText(getBaseContext(), "Izabrali ste : " + (month + 1) + "/" + day + "/" + year, Toast.LENGTH_SHORT).show();
        }


    };

    private TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
            hour = hourOfDay;
            minute = minuteOfHour;

            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
            Date date = new Date(0, 0, 0, hour, minute);
            String strDate = timeFormat.format(date);

            Toast.makeText(getBaseContext(), "Izabrali ste " + strDate, Toast.LENGTH_SHORT);
        }
    };

    public void onClick(View view) {
        Toast.makeText(getBaseContext(), "Izabrani datum: " + (datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear() + "\n" + "Izabrano vreme: " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute(), Toast.LENGTH_SHORT).show();
    }

    private void DisplayToast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }


}
