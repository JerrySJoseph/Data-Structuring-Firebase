package com.example.data_structuring_firebase.Data.Models;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;

public class TaskModel {

    String id;
    String title;
    String description;
    String priority;
    Status status;
    long createdAt;
    long timelimit;
    boolean isImportant=false;
    boolean isFinished=false;

    public TaskModel() {
    }

    public TaskModel(String title, String description, long createdAt, long timelimit) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.timelimit = timelimit;
    }

    public TaskModel(String title, String description, long createdAt, long timelimit, boolean isImportant) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.timelimit = timelimit;
        this.isImportant = isImportant;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }


    @NonNull
    public String getId() {
        return id;
    }
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getTimelimit() {
        return timelimit;
    }

    public void setTimelimit(long timelimit) {
        this.timelimit = timelimit;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getId().equals(((TaskModel)obj).getId());
    }
    public String toJSON()
    {
        return new Gson().toJson(this);
    }


}
