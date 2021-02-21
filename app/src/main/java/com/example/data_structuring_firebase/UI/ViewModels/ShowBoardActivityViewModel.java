package com.example.data_structuring_firebase.UI.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data_structuring_firebase.Data.Models.TaskModel;
import com.example.data_structuring_firebase.Data.Repository.DataRepository;
import com.example.data_structuring_firebase.UI.Views.Board.ShowBoardActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowBoardActivityViewModel extends ViewModel {

    DataRepository repository;
    private String date;
    String boardID;

    public void InitViewModel(String boardId) {
        repository= DataRepository.getInstance();
        this.boardID=boardId;

        date=new SimpleDateFormat("EEEE, dd MMM yyyy").format(new Date(System.currentTimeMillis()));

    }

    public LiveData<ArrayList<TaskModel>> getTasksList() {
        return repository.getTasksFor(boardID);
    }

    public LiveData<String> getBoardName() {
        return repository.getBoardNameFor(boardID);
    }

    public String getDate() {
        return date;
    }

    public void addTask(TaskModel task)
    {
        repository.inserttask(boardID,task);
    }

}
