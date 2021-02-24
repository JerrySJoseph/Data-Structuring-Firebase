package com.example.data_structuring_firebase.UI.Views.ShowBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.UI.Adapters.TaskAdapter;
import com.example.data_structuring_firebase.Data.Models.TaskModel;
import com.example.data_structuring_firebase.R;
import com.example.data_structuring_firebase.UI.ViewModels.ShowBoardActivityViewModel;
import com.example.data_structuring_firebase.UI.Views.AddBoard.AddBoard;
import com.example.data_structuring_firebase.UI.Views.AddTask.AddTask;
import com.example.data_structuring_firebase.UI.Views.MainActivity;
import com.example.data_structuring_firebase.Utils.AppBarStateChangeListener;
import com.example.data_structuring_firebase.Utils.Constants;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.data_structuring_firebase.Utils.Constants.RequestCodes.REQUEST_CODE_ADD_TASK;
import static com.example.data_structuring_firebase.Utils.Constants.RequestCodes.REQUEST_CODE_EDIT_BOARD;

public class ShowBoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TaskAdapter adapter;
    FloatingActionButton fab;
    String TAG="FIREBASE";
    TextView datetime,description;
    ShowBoardActivityViewModel mViewModel;
    Menu menu;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    NestedScrollView nestedScrollView;
    Board thisBoard=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_board);
        registerViewItems();

        appBarLayout.addOnOffsetChangedListener(onAppbarStateChanged);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewModel= new ViewModelProvider(this).get(ShowBoardActivityViewModel.class);
        mViewModel.InitViewModel(getIntent().getStringExtra("boardId"));

        BindViewModelsToView();
    }
    public void registerViewItems()
    {
        datetime=findViewById(R.id.datetime);
        recyclerView=findViewById(R.id.recycler);
        fab=findViewById(R.id.add_btn);
        nestedScrollView=findViewById(R.id.content);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarLayout=findViewById(R.id.appbar);
        description=findViewById(R.id.desc);
        adapter=new TaskAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        nestedScrollView.getParent().requestChildFocus(nestedScrollView,nestedScrollView);

    }

    private void BindViewModelsToView()
    {
        mViewModel.getBoard().observe(this, new Observer<Board>() {
            @Override
            public void onChanged(Board board) {
                updateView(board);
            }
        });
    }

    private void updateView(Board board)
    {
        thisBoard=board;
        getSupportActionBar().setTitle(board.getName());
        adapter.setModels(board.getTasks());
        description.setText(board.getDesc());
        adapter.notifyDataSetChanged();
        datetime.setText(mViewModel.getDate());

    }
    public void AddTasks(View view) {
        startActivityForResult(new Intent(this, AddTask.class),REQUEST_CODE_ADD_TASK);
    }
    private AppBarStateChangeListener onAppbarStateChanged= new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, State state) {
            Log.e("STATE",state.name());
            setSearchVisible(state==State.COLLAPSED);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_ADD_TASK && resultCode==RESULT_OK)
        {
                String jsonData=data.getStringExtra("task_data");
                TaskModel model=new Gson().fromJson(jsonData,TaskModel.class);
                mViewModel.addTask(model);
        }
        if(requestCode==REQUEST_CODE_EDIT_BOARD && resultCode==RESULT_OK)
        {
            String jsonData=data.getStringExtra("board_data");
            Board model=new Gson().fromJson(jsonData,Board.class);
            mViewModel.updateBoard(model);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;

        getMenuInflater().inflate(R.menu.show_tasks_menu,menu);
        SearchView searchView =(SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint("Search for tasks");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO: Method to filter recyclerview
                Toast.makeText(ShowBoardActivity.this,newText,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        setSearchVisible(false);
        return true;
    }

    private void setSearchVisible(boolean isVisible)
    {   if(menu!=null)
            menu.findItem(R.id.menu_search).setVisible(isVisible);
    }

    public void onEditBoard(Board board)
    {
        Intent intent=new Intent(this, AddBoard.class);
        intent.setAction(Constants.ActionCodes.ACTION_EDIT_BOARD);
        intent.putExtra("boarddata",board.toJSON());
        startActivityForResult(intent,REQUEST_CODE_EDIT_BOARD);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        if(item.getItemId()==R.id.menu_edit)
            onEditBoard(thisBoard);
        return super.onOptionsItemSelected(item);
    }
}