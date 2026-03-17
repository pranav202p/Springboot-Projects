package com.ttracker.tracker;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class TaskServiceImpl implements TaskService{

    private  static final AtomicInteger count=new AtomicInteger(0);
    List<Task> tasks=new ArrayList<>();

    @Override
    public int addTask(String desc) {
        Task newTask=new Task(count.incrementAndGet(),desc,"todo");
        tasks.add(newTask);
        return newTask.getId();
    }

    @Override
    public int updateTask(int taskId, String updesc) {

        tasks.stream().filter(id->id.getId()==taskId).findFirst().ifPresent(t->t.setDescription(updesc));
        return taskId;

    }


    @Override
    public boolean deleteTask(int taskid) {
         return tasks.removeIf(task->task.getId()==taskid);
    }

    @Override
    public void mark_in_progress(int taskId) {
        tasks.stream().filter(task->task.getId()==taskId).findFirst().ifPresent(task -> task.setStatus("in-progress"));
    }

    @Override
    public void mark_done(int taskId) {
        tasks.stream().filter(task->task.getId()==taskId).findFirst().ifPresent(task -> task.setStatus("done"));
    }

    @Override
    public List<Task> listAll() {
        return tasks;
    }

    @Override
    public List<Task> listDone() {
        return tasks.stream().filter(p-> p.getStatus().equals("done")).toList();
    }

    @Override
    public List<Task> listInProgress() {
        return tasks.stream().filter(p-> p.getStatus().equals("in-progress")).toList();
    }

    @Override
    public List<Task> listTodo() {
        return tasks.stream().filter(p-> p.getStatus().equals("todo")).toList();
    }
}
