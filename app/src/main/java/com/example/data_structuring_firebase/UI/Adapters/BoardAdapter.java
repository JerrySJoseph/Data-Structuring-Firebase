package com.example.data_structuring_firebase.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data_structuring_firebase.UI.CustomViews.ProgressBarView;
import com.example.data_structuring_firebase.Data.Models.Board;
import com.example.data_structuring_firebase.R;
import com.example.data_structuring_firebase.Utils.Functions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class BoardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Board> models;
    onItemClickListener onItemClickListener;
    private static final int VIEW_TYPE_EMPTY=-1;
    private static final int VIEW_TYPE_AD=-2;
    private static final int VIEW_TYPE_ITEM=-3;

    public BoardAdapter() {
        models=new ArrayList<>();
    }

    public void setOnItemClickListener(BoardAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BoardAdapter(ArrayList<Board> models) {
        this.models = models;
    }

    public void setModels(ArrayList<Board> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType)
        {
            case VIEW_TYPE_ITEM:holder=new BoardHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_boards,parent,false));break;
            default:holder=new EmptyBoardHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_boards_empty,parent,false));break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==VIEW_TYPE_ITEM)
            ((BoardHolder)holder).Bind(models.get(position));

    }

    @Override
    public int getItemCount() {
        return models.size()>1?models.size():1;
    }

    @Override
    public int getItemViewType(int position) {
        if(models.size()<1)
            return VIEW_TYPE_EMPTY;
        else
            return VIEW_TYPE_ITEM;
    }

    protected class BoardHolder extends RecyclerView.ViewHolder{
        CircleImageView icon;
        TextView title,date,status;
        ProgressBarView progressBarView;
        ImageView more;
        LinearLayout layout;
        public BoardHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.icon);
            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
            progressBarView=itemView.findViewById(R.id.pbar);
            more=itemView.findViewById(R.id.more);
            layout=itemView.findViewById(R.id.layout);
        }
        public void Bind(Board board)
        {
            title.setText(board.getName());
            date.setText(new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(board.getCreatedAt())));
            status.setText(Functions.ParseBoardStatus(board.getStatus()));
            progressBarView.setTitle("get a job");
            progressBarView.setValue(60);
            progressBarView.setLimit(100);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)
                        onItemClickListener.onItemClick(board);
                }
            });
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)
                        onItemClickListener.onMoreClick(board,more);
                }
            });
        }
    }
    protected class EmptyBoardHolder extends RecyclerView.ViewHolder{

        public EmptyBoardHolder(@NonNull View itemView) {
            super(itemView);

        }

    }
    public interface onItemClickListener{
        void onItemClick(Board board);
        void onMoreClick(Board board,View item);
    }
}
