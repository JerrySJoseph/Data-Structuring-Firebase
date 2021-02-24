package com.example.data_structuring_firebase.UI.Views;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.data_structuring_firebase.UI.Adapters.BoardAdapter;
import com.example.data_structuring_firebase.UI.Adapters.TaskAdapter;
import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.R;
import com.example.data_structuring_firebase.UI.Views.AddBoard.AddBoard;
import com.example.data_structuring_firebase.UI.Views.ShowBoard.ShowBoardActivity;
import com.example.data_structuring_firebase.UI.ViewModels.MainActivityViewModel;
import com.example.data_structuring_firebase.Utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.data_structuring_firebase.Utils.Constants.ActionCodes.ACTION_ADD_BOARD;
import static com.example.data_structuring_firebase.Utils.Constants.RequestCodes.REQUEST_CODE_ADD_BOARD;
import static com.example.data_structuring_firebase.Utils.Constants.RequestCodes.REQUEST_CODE_EDIT_BOARD;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel mViewModel;
    TextView name,caption;
    CircleImageView profile_image;
    RecyclerView task_recyclerView;
    ViewPager2 viewPager2;
    BoardAdapter boardAdapter;
    TaskAdapter taskAdapter;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerViews();
        alertDialogBuilder= new AlertDialog.Builder(this);
        mViewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);
        BindViewModeltoViews();
    }

    //Registering the view components
    private void registerViews()
    {
        name=findViewById(R.id.name);
        caption=findViewById(R.id.caption);
        profile_image=findViewById(R.id.profile_image);
        task_recyclerView=findViewById(R.id.tasks_recycler);
        viewPager2=findViewById(R.id.boards_viewpager);
        boardAdapter= new BoardAdapter();
        boardAdapter.setOnItemClickListener(onItemClickListener);
        viewPager2.setAdapter(boardAdapter);
    }

    // Item Click listener
    BoardAdapter.onItemClickListener onItemClickListener= new BoardAdapter.onItemClickListener() {
        @Override
        public void onItemClick(Board board) {
            Intent intent=new Intent(MainActivity.this, ShowBoardActivity.class);
            intent.putExtra("boardId",board.getId());
          startActivity(intent);
        }

        @Override
        public void onMoreClick(Board board,View item) {

            //Build a popup menu for More
            PopupMenu popupMenu = new PopupMenu(MainActivity.this,item);
            popupMenu.getMenuInflater().inflate(R.menu.boards_menu,popupMenu.getMenu());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true);
            }

            //On Menu Item Click
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId()==R.id.menu_delete)
                        generateAlertAndDelete(board);
                    else if(item.getItemId()==R.id.menu_edit)
                        onEditBoard(board);
                    return true;
                }
            });
            popupMenu.show();
        }
    };

    public void generateAlertAndDelete(Board board)
    {
        alertDialogBuilder.setTitle("Delete")
                .setMessage("Are you sure you want to delete this board?")
                .setNegativeButton("cancel",null)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.deleteBoard(board);
                    }
                }).create().show();
    }

    public void onEditBoard(Board board)
    {
        Intent intent=new Intent(MainActivity.this, AddBoard.class);
        intent.setAction(Constants.ActionCodes.ACTION_EDIT_BOARD);
        intent.putExtra("boarddata",board.toJSON());
        startActivityForResult(intent,REQUEST_CODE_EDIT_BOARD);
    }
    //Binding models to views
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
    }

    //On click handler for add board
    public void onAddBoardClick(View view) {
        Intent intent=new Intent(MainActivity.this, AddBoard.class);
        intent.setAction(ACTION_ADD_BOARD);
        startActivityForResult(intent,REQUEST_CODE_ADD_BOARD);
    }

    //On Activity result recieved
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ADD_BOARD && resultCode==RESULT_OK)
        {
            String jsonData=data.getStringExtra("board_data");
            Board model=new Gson().fromJson(jsonData,Board.class);
            mViewModel.addBoard(model);
        }
        else if(requestCode==REQUEST_CODE_EDIT_BOARD && resultCode==RESULT_OK)
        {
            String jsonData=data.getStringExtra("board_data");
            Board model=new Gson().fromJson(jsonData,Board.class);
            mViewModel.updateBoard(model);
        }
    }
}