package com.example.data_structuring_firebase.Data.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.data_structuring_firebase.Data.Api.NetworkApi;
import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.Data.Models.TaskModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataRepository {

    String selectedBoardId=null;
    static DataRepository mInstance;
    static ArrayList<Board> allBoardsList= new ArrayList<>();

    MutableLiveData<ArrayList<Board>> allBoards=new MutableLiveData<>();
    MutableLiveData<Board> currentBoard= new MutableLiveData<>();

    public static synchronized DataRepository getInstance() {
        if (mInstance== null)
            mInstance= new DataRepository();
        return mInstance;
    }

    public DataRepository() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        fetchAndRegisterforUpdates();
    }

    //LiveData returns
    public LiveData<ArrayList<Board>> getAllBoards()
    {
        return allBoards;
    }

    public LiveData<Board>getBoardFor(String boardID)
    {
        selectedBoardId=boardID;
        filterBoardsFor(boardID);
        return currentBoard;
    }

    //Filter board for a BoardID
    private void filterBoardsFor(String boardID) {
        if(boardID!=null)
        {
            int index=allBoardsList.indexOf(new Board(boardID));
            if(index>=0)
                currentBoard.setValue(allBoardsList.get(index));
            else
                currentBoard.setValue(new Board());
        }
        else
             currentBoard.setValue(new Board());
    }

    //CRUD Operations
    public void insertBoard(Board board) {
        NetworkApi.insertBoard(board);
    }
    public void inserttask(String boardID,TaskModel task) {
        int index=allBoardsList.indexOf(new Board(boardID));
        Board model=allBoardsList.get(index);
        model.addTask(task);
        NetworkApi.updateBoard(model);
    }
    public void deleteBoard(String boardID){
        NetworkApi.deleteBoard(boardID);
    }
    public void updateBoard(Board board)
    {
        NetworkApi.updateBoard(board);
    }

    //Register for changes in Network Database
    public void fetchAndRegisterforUpdates() {
        NetworkApi.getallBoards(networkApiResponseListener);
    }
    
    private NetworkApi.NetworkApiResponse networkApiResponseListener=new NetworkApi.NetworkApiResponse() {
        @Override
        public void OnResponse(Object response) {
            allBoardsList.clear();
            allBoardsList.addAll((ArrayList<Board>)response);
            filterBoardsFor(selectedBoardId);
            allBoards.postValue(allBoardsList);
        }

        @Override
        public void OnFailure(String error) {

        }
    };


}
