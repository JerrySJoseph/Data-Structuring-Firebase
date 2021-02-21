package com.example.data_structuring_firebase.Data.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.data_structuring_firebase.Data.Api.NetworkApi;
import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.Data.Models.TaskModel;

import java.util.ArrayList;

public class DataRepository {

    static DataRepository mInstance;
    static ArrayList<Board> allBoardsList= new ArrayList<>();

    MutableLiveData<ArrayList<Board>> allBoards=new MutableLiveData<>();

    public static synchronized DataRepository getInstance() {
        if (mInstance != null)
            return mInstance;
        return new DataRepository();
    }

    public DataRepository() {
        registerForUpdates();
    }

    //LiveData returns
    public LiveData<ArrayList<Board>> getAllBoards()
    {
        return allBoards;
    }
    public LiveData<String> getBoardNameFor(String boardID) {
        MutableLiveData<String> name= new MutableLiveData<>();
        int index=allBoardsList.indexOf(new Board(boardID));
        if(index>-1)
            name.setValue(allBoardsList.get(index).getName());
        else
            name.setValue("Unknown");
        return name;
    }
    public LiveData<ArrayList<TaskModel>> getTasksFor(String boardID)
    {
        MutableLiveData<ArrayList<TaskModel>> tasks= new MutableLiveData<>();
        int index=allBoardsList.indexOf(new Board(boardID));
        tasks.setValue(allBoardsList.get(index).getTasks());
        return tasks;

    }

    //CRUD Operations
    public void insertBoard(Board board)
    {
        NetworkApi.insertBoard(board);
    }
    public void inserttask(String boardID,TaskModel task)
    {
        int index=allBoardsList.indexOf(new Board(boardID));
        Board model=allBoardsList.get(index);
        model.addTask(task);
        NetworkApi.updateBoard(model);
    }
    //Register for changes in Network Database
    public void registerForUpdates()
    {
        NetworkApi.getallBoards(new NetworkApi.NetworkApiResponse() {
            @Override
            public void OnResponse(Object response) {
                allBoardsList.clear();
                allBoardsList.addAll((ArrayList<Board>)response);
                allBoards.setValue(allBoardsList);
            }

            @Override
            public void OnFailure(String error) {

            }
        });
    }


}
