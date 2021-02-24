package com.example.data_structuring_firebase.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.Data.Models.TaskModel;
import com.example.data_structuring_firebase.Data.Repository.DataRepository;

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

    public LiveData<Board> getBoard()
    {
        return repository.getBoardFor(boardID);
    }
    public void updateBoard(Board board)
    {
        repository.updateBoard(board);
    }
    public String getDate() {
        return date;
    }

    public void addTask(TaskModel task)
    {
        repository.inserttask(boardID,task);
    }

}
