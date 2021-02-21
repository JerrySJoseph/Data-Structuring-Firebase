package com.example.data_structuring_firebase.UI.Views.Board;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.data_structuring_firebase.UI.Adapters.TaskAdapter;
import com.example.data_structuring_firebase.Data.Models.TaskModel;
import com.example.data_structuring_firebase.R;
import com.example.data_structuring_firebase.UI.ViewModels.MainActivityViewModel;
import com.example.data_structuring_firebase.UI.ViewModels.ShowBoardActivityViewModel;
import com.example.data_structuring_firebase.UI.Views.AddTask.AddTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowBoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TaskAdapter adapter;
    FloatingActionButton fab;
    String TAG="FIREBASE";
    TextView datetime,title;
    ShowBoardActivityViewModel mViewModel;
    final int REQUEST_CODE=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_board);
        registerViewItems();
        mViewModel= new ViewModelProvider(this).get(ShowBoardActivityViewModel.class);
        mViewModel.InitViewModel(getIntent().getStringExtra("boardId"));
        BindViewModelsToView();
    }
    public void registerViewItems()
    {
        datetime=findViewById(R.id.datetime);
        title=findViewById(R.id.title);
        recyclerView=findViewById(R.id.recycler);
        fab=findViewById(R.id.add_btn);
        adapter=new TaskAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void BindViewModelsToView()
    {
        mViewModel.getBoardName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                title.setText(s);
            }
        });
        mViewModel.getTasksList().observe(this, new Observer<ArrayList<TaskModel>>() {
            @Override
            public void onChanged(ArrayList<TaskModel> taskModels) {
                adapter.setModels(taskModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void AddTasks(View view) {
        startActivityForResult(new Intent(this, AddTask.class),REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                String jsonData=data.getStringExtra("task_data");
                TaskModel model=new Gson().fromJson(jsonData,TaskModel.class);
                mViewModel.addTask(model);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}