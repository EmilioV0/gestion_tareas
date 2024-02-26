package com.gestion.tareas.repository;

import com.gestion.tareas.dto.task.TaskByDate;
import com.gestion.tareas.dto.task.TaskByCategory;
import com.gestion.tareas.entity.Task;
import com.gestion.tareas.search.TaskSearchByRange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends Repository<Task, Long> {
    Page<TaskByCategory> findByIdCategory(Long id, Pageable pageable);
    Task save(Task task);
    Optional<Task> findById(long id);
    @Query(value = """
            SELECT new com.gestion.tareas.dto.task.TaskByDate(
                t.id,
                t.issue,
                t.dateLimit,
                c.name as categoryName,
                t.expired
            )
            FROM Task t JOIN t.category c
            WHERE t.dateLimit BETWEEN :#{#range.start} AND :#{#range.end}
            AND t.finished = false
            """)
    Page<TaskByDate> getTasksByDate(TaskSearchByRange range, Pageable pageable);
    @Query("""
            SELECT new com.gestion.tareas.dto.task.TaskByDate(
                t.id,
                t.issue,
                t.dateLimit,
                c.name as categoryName,
                t.expired
            )
            FROM Task t JOIN t.category c
            WHERE t.finished = true""")
    Page<TaskByDate> getAllTaskFinished(Pageable pageable);
    @Query("""
            SELECT new com.gestion.tareas.dto.task.TaskByDate(
                t.id,
                t.issue,
                t.dateLimit,
                c.name as categoryName,
                t.expired
            )
            FROM Task t JOIN t.category c
            WHERE t.expired = true and t.finished = false""")
    Page<TaskByDate> getAllTaskExpired(Pageable pageable);
    void deleteById(Long id);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE Task t
            SET t.finished = not t.finished
            WHERE t.id = :id
            """)
    int isFinished(Long id);
    boolean existsById(Long id);
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
            UPDATE Task t
            SET t.expired = (t.dateLimit < now())
            WHERE t.finished = false""")
    void updateExpired();
    @Query("""
            UPDATE Task t
            SET t.expired = (t.dateLimit > now())
            WHERE t.id = :id
            """)
    void updateExpiredTaskById(long id);
}













    /*
    @Procedure(procedureName = "update_state_all_task")
    void updateStateAllTask();
    @Procedure(procedureName = "update_state_task")
    void updateStateTask(@Param("id_task") Long id);
    */


