package com.example.data_structuring_firebase.Data.Api;


import androidx.annotation.NonNull;

import com.example.data_structuring_firebase.Data.Models.Board;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NetworkApi {

    private static DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference("tasks_data");

    public static void getallBoards(NetworkApiResponse reposnseListener)
    {
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Board> boards= new ArrayList<>();
                for(DataSnapshot snap:snapshot.getChildren())
                    boards.add(snap.getValue(Board.class));
                if(reposnseListener!=null)
                    reposnseListener.OnResponse(boards);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if(reposnseListener!=null)
                    reposnseListener.OnResponse(error.getMessage());
            }
        });
    }

    public static void insertBoard(Board board)
    {
        DatabaseReference childRef=rootRef.push();
        board.setId(childRef.getKey());
        board.setCreatedAt(System.currentTimeMillis());
        childRef.setValue(board);
    }

    public static void updateBoard(Board board)
    {
        DatabaseReference childRef=rootRef.child(board.getId());
        childRef.setValue(board);
    }

    public interface NetworkApiResponse{
        void OnResponse(Object response);
        void OnFailure(String error);
    }


}
