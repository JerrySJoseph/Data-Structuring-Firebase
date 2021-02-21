package com.example.data_structuring_firebase.UI.Views.AddTask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.data_structuring_firebase.Data.Models.TaskModel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.data_structuring_firebase.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTask extends AppCompatActivity {
    Calendar c = Calendar.getInstance();
    TextView tv_date,tv_time;
    EditText et_title,et_desc;
    String Selected_Priority="PRIORITY_LOW";
    Switch sw_important;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setTitle("Add New Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_date=findViewById(R.id.tv_date);
        tv_time=findViewById(R.id.tv_time);
        et_title=findViewById(R.id.et_title);
        et_desc=findViewById(R.id.et_desc);
        sw_important=findViewById(R.id.sw_isImportant);
        setSelection(R.id.rb_low);
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
        intent.putExtra("task_data",prepareDataFromView().toJSON());
        setResult(RESULT_OK,intent);
        finish();
    }
    public TaskModel prepareDataFromView()
    {
        TaskModel model= new TaskModel();
        model.setTitle(et_title.getText().toString().trim());
        model.setDescription(et_desc.getText().toString().trim());
        model.setCreatedAt(System.currentTimeMillis());
        model.setPriority(Selected_Priority);
        model.setImportant(sw_important.isChecked());
        model.setTimelimit(c.getTimeInMillis());
        return model;

    }
    public void onPriorityChanged(View view) {
        switch (view.getId())
        {
            case R.id.high:
            case R.id.rb_high:setSelection(R.id.rb_high);
                                Selected_Priority="PRIORITY_HIGH";break;
            case R.id.medium:
            case R.id.rb_medium:setSelection(R.id.rb_medium);
                            Selected_Priority="PRIORITY_MEDIUM";break;

            default:setSelection(R.id.rb_low); Selected_Priority="PRIORITY_LOW";
        }
        Toast.makeText(this,Selected_Priority,Toast.LENGTH_SHORT).show();
    }

    public void setSelection(int id)
    {
        int[] rb_ids={R.id.rb_high,R.id.rb_low,R.id.rb_medium};
        for(int i:rb_ids)
        {
            RadioButton button= findViewById(i);
            button.setChecked(false);
        }
        RadioButton button= findViewById(id);
        button.setChecked(true);
    }

}