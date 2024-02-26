package com.gestion.tareas.controller;

import com.gestion.tareas.dto.task.TaskByDate;
import com.gestion.tareas.dto.task.CreateTask;
import com.gestion.tareas.dto.task.GetTask;
import com.gestion.tareas.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(TaskController.TASKS_PATH)
public class TaskController {
    private final TaskService taskService;
    public static final String TASKS_PATH = "/api/v1/tasks";
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<GetTask> createTask(@RequestBody CreateTask task) throws URISyntaxException {
        GetTask newTask = taskService.save(task);
        return ResponseEntity
                .created( new URI( TASKS_PATH + "/" + newTask.id() ) )
                .body(newTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable long id,
                                           @RequestBody CreateTask createTask) {
        taskService.update(id, createTask);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTask> getTaskById( @PathVariable("id") long taskId ) {
        GetTask task  = taskService.findById(taskId);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskById( @PathVariable("id") long taskId ) {
        taskService.deleteById(taskId);
    }


    @PatchMapping("/{id}/finished")
    public ResponseEntity<Void> updateTaskByFinished(@PathVariable long id) {
        taskService.isFinished(id);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TaskByDate> getTasksByDate(@PageableDefault Pageable pageable,
                                           @RequestParam(value = "searchDate",
                                                   defaultValue = "today") String searchDate) {
        return taskService.getTasksByDate(searchDate, pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/finished")
    public List<TaskByDate> getAllTaskFinished(@PageableDefault Pageable pageable) {
        return taskService.getAllTaskFinished(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/expired")
    public List<TaskByDate> getAllTaskExpired(@PageableDefault Pageable pageable) {
        return taskService.getAllTaskExpired(pageable);
    }


}
