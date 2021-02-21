package com.example.data_structuring_firebase.UI.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data_structuring_firebase.Data.Models.TaskModel;
import com.example.data_structuring_firebase.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    ArrayList<TaskModel>models= new ArrayList<>();
    onItemClickListener onItemClickListener;

    public void setModels(ArrayList<TaskModel> models) {
        this.models = models;
    }

    public TaskAdapter(ArrayList<TaskModel> models) {
        this.models = models;
    }

    public TaskAdapter() {
        this.models= new ArrayList<>();
    }

    public void setOnItemClickListener(TaskAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.Bind(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    protected class TaskHolder extends RecyclerView.ViewHolder {

        TextView title,content,timestamp;
        ImageView isImportant;
        RelativeLayout layout;
        Context context;
        View indicator;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            content=itemView.findViewById(R.id.content);
            timestamp=itemView.findViewById(R.id.timestamp);
            isImportant=itemView.findViewById(R.id.important);
            layout=itemView.findViewById(R.id.innerLayout);
            indicator=itemView.findViewById(R.id.indicator);

            context=itemView.getContext();
        }
        private void Bind(TaskModel task)
        {
            title.setText(task.getTitle());
            content.setText(task.getDescription());
            int bg_color=R.color.colorGreen;
            if(task.getPriority().equals("PRIORITY_HIGH"))
                bg_color=R.color.colorOrange;
            else if(task.getPriority().equals("PRIORITY_MEDIUM"))
                bg_color=R.color.colorBlue;
            indicator.setBackgroundColor(context.getResources().getColor(bg_color));
            Drawable star= task.isImportant()?context.getResources().getDrawable(R.drawable.ic_star_selected):
                    context.getResources().getDrawable(R.drawable.ic_star);
            isImportant.setImageDrawable(star);
            timestamp.setText(new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date(task.getTimelimit())));
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)
                        onItemClickListener.onItemClick(task);
                }
            });
            isImportant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)
                        onItemClickListener.onImportantClick(task,getAdapterPosition());
                }
            });

        }
    }
    public interface onItemClickListener{
        void onItemClick(TaskModel model);
        void onImportantClick(TaskModel model,int position);
    }
}
