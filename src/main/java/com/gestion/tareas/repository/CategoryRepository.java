package com.gestion.tareas.repository;


import com.gestion.tareas.dto.category.GetCategory;
import com.gestion.tareas.dto.category.ReportCategory;
import com.gestion.tareas.dto.task.TaskByCategory;
import com.gestion.tareas.entity.Category;
import com.gestion.tareas.search.TaskSearchByRange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CategoryRepository extends Repository<Category, Long> {
    GetCategory save(Category category);
    @Query("""
            SELECT new com.gestion.tareas.dto.category.ReportCategory
            (
                c.name,
                count(*) as total,
                sum(CASE WHEN t.dateLimit < date(now()) THEN 1 ELSE 0 END) AS late
            )
            FROM Category c INNER JOIN Task t
            ON t.category.id = c.id
            WHERE t.finished = false
            GROUP BY t.category.id
            """)
    List<ReportCategory> findReportCategories();
    @Query("""
            SELECT new com.gestion.tareas.dto.category.ReportCategory
            (
                c.name,
                count(*) as total,
                sum(CASE WHEN t.dateLimit < date(now()) THEN 1 ELSE 0 END) AS late
            )
            FROM Category c INNER JOIN Task t
            ON t.category.id = c.id
            WHERE t.finished = false AND c.name = :categoryName
            GROUP BY t.category.id
            """)
    ReportCategory findReportCategory(String categoryName);
    @Query("""
            SELECT new com.gestion.tareas.dto.task.TaskByCategory(
                t.id,
                t.issue,
                t.dateLimit,
                t.expired
            )
            FROM Task t JOIN t.category c
            WHERE t.finished = true and c.name = :name""")
    Page<TaskByCategory> getAllTaskFinishedByCategory(String name, Pageable pageable);
    @Query("""
            SELECT new com.gestion.tareas.dto.task.TaskByCategory(
                t.id,
                t.issue,
                t.dateLimit,
                t.expired
            )
            FROM Task t JOIN t.category c
            WHERE t.expired = true AND c.name = :name
            AND t.finished = false""")
    Page<TaskByCategory> getAllTaskExpiredByCategory(String name, Pageable pageable);
    Integer deleteById(Long id);
    @Query("""
            SELECT
                new com.gestion.tareas.dto.task.TaskByCategory(
                    t.id,
                    t.issue,
                    t.dateLimit,
                    t.expired
                )
            FROM Task t JOIN t.category c
            WHERE c.name = :categoryName AND
            t.dateLimit >= :#{#range.start} and t.dateLimit <= :#{#range.end}
            AND t.finished = false
            """)
    Page<TaskByCategory> findByIdCategoryAndIdState(String categoryName, TaskSearchByRange range,
                                                    Pageable pageable);
    boolean existsByName(String name);
    boolean existsById(Long id);
}
