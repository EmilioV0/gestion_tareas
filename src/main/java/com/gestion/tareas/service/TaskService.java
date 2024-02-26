package com.gestion.tareas.service;


import com.gestion.tareas.dto.task.TaskByDate;
import com.gestion.tareas.dto.task.CreateTask;
import com.gestion.tareas.dto.task.GetTask;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    GetTask save(CreateTask task);
    void update(long id, CreateTask createTask);
    GetTask findById(Long id);
    void deleteById(Long id);
    void isFinished(Long id);
    void updateExpired();
    List<TaskByDate> getTasksByDate(String searchDate, Pageable pageable);
    List<TaskByDate> getAllTaskFinished(Pageable pageable);
    List<TaskByDate> getAllTaskExpired(Pageable pageable);


}
