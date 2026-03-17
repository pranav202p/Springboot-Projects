package com.ttracker.tracker;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface TaskService  {



    public  int addTask(String desc);
    public int updateTask(int taskId,String updesc);
    public boolean deleteTask(int id);
    public void mark_in_progress(int Taskid);
    public void mark_done(int id);
    public List<Task> listAll();
    public List<Task> listDone();
    public List<Task> listInProgress();
    public List<Task> listTodo();


}
