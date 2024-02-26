package com.gestion.tareas.service;

import com.gestion.tareas.dto.task.TaskByDate;
import com.gestion.tareas.dto.task.CreateTask;
import com.gestion.tareas.dto.task.GetTask;
import com.gestion.tareas.entity.Task;
import com.gestion.tareas.exception.ResourceNotFoundException;
import com.gestion.tareas.mapper.TaskMapper;
import com.gestion.tareas.repository.TaskRepository;
import com.gestion.tareas.search.TaskSearchByRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public GetTask save(CreateTask createTask) {
        Task task = taskRepository.save( taskMapper.toTask(createTask) );
        return taskMapper.toGetTask( task );
    }

    @Transactional
    @Override
    public void update(long id, CreateTask createTask) {
        if ( !existsById(id) ) {
            throw new ResourceNotFoundException("Task", id);
        }

        Task task = taskMapper.toTask(createTask);
        task.setId(id);
        taskRepository.save(task);
    }

    @Override
    public GetTask findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
        return taskMapper.toGetTask( task );
    }

    @Override
    public List<TaskByDate> getTasksByDate(String searchDate, Pageable pageable) {
        TaskSearchByRange range = TaskSearchByRange.getTaskSearchByRange(searchDate);
        return taskRepository.getTasksByDate(range, pageable).getContent();
    }

    @Override
    public List<TaskByDate> getAllTaskFinished(Pageable pageable) {
        return taskRepository.getAllTaskFinished(pageable).getContent();
    }

    @Override
    public List<TaskByDate> getAllTaskExpired(Pageable pageable) {
        return taskRepository.getAllTaskExpired(pageable).getContent();
    }

    @Override
    public void deleteById(Long id) {
        if ( !existsById(id) ) {
            throw new ResourceNotFoundException("Task", id);
        }

        taskRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void isFinished(Long id) {
        int result = taskRepository.isFinished(id);
        if ( result == 0) {
            throw new ResourceNotFoundException("Task", id);
        }
    }

    @Override
    public void updateExpired() {
        taskRepository.updateExpired();
    }

    private boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }
}
