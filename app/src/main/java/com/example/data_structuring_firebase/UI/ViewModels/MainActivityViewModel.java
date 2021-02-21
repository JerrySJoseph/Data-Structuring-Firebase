package com.example.data_structuring_firebase.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.Data.Models.TaskModel;
import com.example.data_structuring_firebase.Data.Repository.DataRepository;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {

    private LiveData<ArrayList<Board>> boardList;
    private MutableLiveData<ArrayList<TaskModel>> importantTasksList=new MutableLiveData<>();
    private MutableLiveData<String> name=new MutableLiveData<>();
    private MutableLiveData<String> caption=new MutableLiveData<>();
    private MutableLiveData<String> imageUrl=new MutableLiveData<>();

    DataRepository repository;


    public MainActivityViewModel() {
        super();
        repository=DataRepository.getInstance();
        boardList=repository.getAllBoards();
    }

    public LiveData<ArrayList<Board>> getBoardList() {
        return boardList;
    }


    public LiveData<ArrayList<TaskModel>> getImportantTasksList() {
        return importantTasksList;
    }

    public void setImportantTasksList(ArrayList<TaskModel> importantTasksList) {
        this.importantTasksList.setValue(importantTasksList);
    }

    public LiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public LiveData<String> getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption.postValue(caption);
    }

    public LiveData<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.postValue(imageUrl);
    }
    public void AddBoard(Board board)
    {
        repository.insertBoard(board);
    }

}
