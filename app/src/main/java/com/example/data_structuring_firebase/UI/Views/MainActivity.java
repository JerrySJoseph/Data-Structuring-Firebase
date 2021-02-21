package com.example.data_structuring_firebase.UI.Views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.data_structuring_firebase.UI.Adapters.BoardAdapter;
import com.example.data_structuring_firebase.UI.Adapters.TaskAdapter;
import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.R;
import com.example.data_structuring_firebase.UI.Views.AddBoard.AddBoard;
import com.example.data_structuring_firebase.UI.Views.Board.ShowBoardActivity;
import com.example.data_structuring_firebase.UI.ViewModels.MainActivityViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel mViewModel;
    TextView name,caption;
    CircleImageView profile_image;
    RecyclerView recyclerView;
    ViewPager2 viewPager2;
    BoardAdapter boardAdapter;
    TaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerViews();
        mViewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);
        BindViewModeltoViews();
    }
    private void registerViews()
    {
        name=findViewById(R.id.name);
        caption=findViewById(R.id.caption);
        profile_image=findViewById(R.id.profile_image);
        recyclerView=findViewById(R.id.recycler);
        viewPager2=findViewById(R.id.boards_viewpager);
        boardAdapter= new BoardAdapter();
        boardAdapter.setOnItemClickListener(onItemClickListener);
        viewPager2.setAdapter(boardAdapter);
    }

    BoardAdapter.onItemClickListener onItemClickListener= new BoardAdapter.onItemClickListener() {
        @Override
        public void onItemClick(Board board) {
            Intent intent=new Intent(MainActivity.this, ShowBoardActivity.class);
            intent.putExtra("boardId",board.getId());
          startActivity(intent);
        }

        @Override
        public void onMoreClick(Board board) {

        }
    };
    private void BindViewModeltoViews()
    {
        //List of Boards
        mViewModel.getBoardList().observe(this, new Observer<ArrayList<Board>>() {
            @Override
            public void onChanged(ArrayList<Board> boards) {
                boardAdapter.setModels(boards);
                boardAdapter.notifyDataSetChanged();
                Log.e("MACTIVITY","Updated boards");
            }
        });

        mViewModel.getCaption().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                name.setText(s);
            }
        });
    }

    public void onAddBoardClick(View view) {
       //startAddBoardActivity
        startActivityForResult(new Intent(this, AddBoard.class),101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK)
        {
            String jsonData=data.getStringExtra("board_data");
            Board model=new Gson().fromJson(jsonData,Board.class);
            mViewModel.AddBoard(model);
        }
    }
}