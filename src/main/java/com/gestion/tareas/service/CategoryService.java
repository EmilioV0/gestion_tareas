package com.gestion.tareas.service;


import com.gestion.tareas.dto.category.CreateCategory;
import com.gestion.tareas.dto.category.GetCategory;
import com.gestion.tareas.dto.category.ReportCategory;
import com.gestion.tareas.dto.task.TaskByCategory;
import com.gestion.tareas.dto.task.TaskByDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    GetCategory save(CreateCategory category);
    ReportCategory findReportCategory(String categoryName);
    List<ReportCategory> findReportCategories();
    Integer deleteById(long id);
    List<TaskByCategory> findByIdCategoryAndIdState(String idCategory, String searchDate, Pageable pageable);
    List<TaskByCategory> getAllTaskFinishedByCategory(String category, Pageable pageable);
    List<TaskByCategory> getAllTaskExpiredByCategory(String category, Pageable pageable);
}
