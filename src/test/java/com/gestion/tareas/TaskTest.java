package com.gestion.tareas;

import com.gestion.tareas.dto.task.CreateTask;
import com.gestion.tareas.dto.task.GetTask;
import com.gestion.tareas.entity.Task;
import com.gestion.tareas.repository.TaskRepository;
import com.gestion.tareas.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class TaskTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Test
    void findAll() {
        final int PAGE_NUMBER = 0;
        final int PAGE_SIZE = 5;
        Pageable pageable = PageRequest.of(
                PAGE_NUMBER,
                PAGE_SIZE,
                Sort.by("dateLimit")
        );
       // taskRepository.findAll().forEach(System.out::println);
    }

    @Test
    void findByIdCategory() {
        final int PAGE_NUMBER = 0;
        final int PAGE_SIZE = 1;
        Pageable pageable = PageRequest.of(
                PAGE_NUMBER,
                PAGE_SIZE,
                Sort.by("dateLimit")
        );

        taskRepository.findByIdCategory(4L, pageable).getContent().forEach(System.out::println);

        System.out.println();

        taskRepository.findByIdCategory(4L, pageable.next()).forEach(System.out::println);
    }

    @Test
    void today() {


    }

    @Test
    void updateStateTask() {
//        taskRepository.updateStateTask(2L);
    }
    @Test
    void updateStateAllTask() {
//        taskRepository.updateStateAllTask();
    }

    @Transactional
    @Rollback(value = false)
    @Test
    void isFinished() {
        // System.out.println(taskRepository.isFinished(12L));
//        System.out.println(taskRepository.isFinished(11L));
//        taskService.isFinished(11L);
        System.out.println(taskRepository.isFinished(11L));

    }

    @Transactional
    @Rollback(false)
    @Test
    void deleteById() {
        taskRepository.deleteById(6L);
    }

    @Rollback(false)
    @Test
    void update() {
        CreateTask task = new CreateTask("Read Head First Java", false,
                LocalDateTime.now(), 4L);
    taskService.update(12L, task);
    }

    @Transactional
    @Rollback(false)
    @Test
    void updateExpired() {
        taskRepository.updateExpired();
    }

    @Test
    void getAllTaskExpired() {
        final int PAGE_NUMBER = 0;
        final int PAGE_SIZE = 10;
        Pageable pageable = PageRequest.of(
                PAGE_NUMBER,
                PAGE_SIZE,
                Sort.by("dateLimit")
        );
        taskRepository.getAllTaskExpired(pageable).forEach(System.out::println);
    }

}
