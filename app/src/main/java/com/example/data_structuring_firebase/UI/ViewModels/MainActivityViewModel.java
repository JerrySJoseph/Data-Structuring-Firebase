package com.example.data_structuring_firebase.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.Data.Models.TaskModel;
import com.example.data_structuring_firebase.Data.Repository.DataRepository;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {

    DataRepository repository;


    public MainActivityViewModel() {
        super();
        repository=DataRepository.getInstance();
    }

    public LiveData<ArrayList<Board>> getBoardList() {
        return repository.getAllBoards();
    }
    public void deleteBoard(Board board){
        repository.deleteBoard(board.getId());
    }

    public void updateBoard(Board board)
    {
        repository.updateBoard(board);
    }
    public void addBoard(Board board)
    {
        repository.insertBoard(board);
    }

}
