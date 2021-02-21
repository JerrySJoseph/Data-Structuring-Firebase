package com.example.data_structuring_firebase.Data.Models;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Board {
    public enum Status{
        STATUS_NOT_STARTED,
        STATUS_PENDING,
        STATUS_COMPLETE
    }
    String id;
    String name;
    long createdAt,timeLimit;
    Status status=Status.STATUS_NOT_STARTED;
    String desc;
    ArrayList<TaskModel> tasks= new ArrayList<>();

    public ArrayList<TaskModel> getTasks() {
        return tasks;
    }

    public Board() {
    }

    public Board(String id) {
        this.id = id;
    }

    public void addTask(TaskModel task)
    {
        this.tasks.add(task);
    }
    public void removeTask(TaskModel task)
    {
        this.tasks.remove(task);
    }
    public void updatetask(TaskModel task)
    {
        this.tasks.set(this.tasks.indexOf(task),task);
    }
    public void clearAllTasks()
    {
        this.tasks.clear();
    }
    public void setTasks(ArrayList<TaskModel> tasks) {
        this.tasks = tasks;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getId().equals(((Board)obj).getId());
    }
    public String toJSON()
    {
        return new Gson().toJson(this);
    }
}
