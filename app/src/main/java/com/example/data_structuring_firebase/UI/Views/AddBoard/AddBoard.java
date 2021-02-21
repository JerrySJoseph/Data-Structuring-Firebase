package com.example.data_structuring_firebase.UI.Views.AddBoard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddBoard extends AppCompatActivity {
    Calendar c = Calendar.getInstance();
    TextView tv_date,tv_time;
    EditText et_title,et_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);
        getSupportActionBar().setTitle("Add New Board");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_date=findViewById(R.id.tv_date);
        tv_time=findViewById(R.id.tv_time);
        et_title=findViewById(R.id.et_title);
        et_desc=findViewById(R.id.et_desc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        if(item.getItemId()==R.id.save_btn)
            onSaveClicked();
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onPickDateClick(View view) {
        // Get Current Date
        int mYear,mMonth,mDay;
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker= new DatePickerDialog(this,dateSetListener,mYear,mMonth,mDay);
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePicker.show();

    }
    public void onPickTimeClick(View view) {
        int mHour,mMin;
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMin = c.get(Calendar.MINUTE);
        TimePickerDialog timePicker= new TimePickerDialog(this, timeSetListener,mHour,mMin,false);
        timePicker.show();

    }

    DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            c.set(year,month,dayOfMonth);
            tv_date.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date(c.getTimeInMillis())));
        }
    };
    TimePickerDialog.OnTimeSetListener timeSetListener= new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
            c.set(Calendar.MINUTE,minute);
            tv_time.setText(new SimpleDateFormat("hh:mm a").format(new Date(c.getTimeInMillis())));
        }
    };


    public void onSaveClicked()
    {
        Intent intent = new Intent();
        intent.putExtra("board_data",prepareDataFromView().toJSON());
        setResult(RESULT_OK,intent);
        finish();
    }
    public Board prepareDataFromView()
    {
        Board model= new Board();
        model.setName(et_title.getText().toString().trim());
        model.setDesc(et_desc.getText().toString().trim());
        model.setTimeLimit(c.getTimeInMillis());
        return model;

    }
}