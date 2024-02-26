package com.gestion.tareas.service;

import com.gestion.tareas.dto.category.CreateCategory;
import com.gestion.tareas.dto.category.GetCategory;
import com.gestion.tareas.dto.category.ReportCategory;
import com.gestion.tareas.dto.task.TaskByCategory;
import com.gestion.tareas.dto.task.TaskByDate;
import com.gestion.tareas.entity.Category;
import com.gestion.tareas.exception.ResourceNotFoundException;
import com.gestion.tareas.repository.CategoryRepository;
import com.gestion.tareas.search.TaskSearchByRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final static String entityName = CategoryServiceImpl.class.getName();
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public GetCategory save(CreateCategory category) {
        Category newCategory = new Category(category.name());
        return categoryRepository.save(newCategory);
    }

    @Override
    public ReportCategory findReportCategory(String categoryName) {
        existsCategory(categoryName);
        return categoryRepository.findReportCategory(categoryName);
    }

    @Override
    public List<ReportCategory> findReportCategories() {
        return categoryRepository.findReportCategories();
    }


    @Override
    public Integer deleteById(long id) {
        if ( categoryRepository.existsById(id) ) {
            throw new ResourceNotFoundException(entityName, id);
        }

        return categoryRepository.deleteById(id);
    }

    @Override
    public List<TaskByCategory> findByIdCategoryAndIdState(String categoryName,
                                                           String searchDate,
                                                           Pageable pageable) {
        existsCategory(categoryName);
        TaskSearchByRange range = TaskSearchByRange.getTaskSearchByRange(searchDate);
        System.out.println(range);
        return categoryRepository.findByIdCategoryAndIdState(
                categoryName, range, pageable).getContent();
    }

    @Override
    public List<TaskByCategory> getAllTaskFinishedByCategory(String category, Pageable pageable) {
        existsCategory(category);
        return categoryRepository.getAllTaskFinishedByCategory(category, pageable)
                .getContent();
    }

    @Override
    public List<TaskByCategory> getAllTaskExpiredByCategory(String category, Pageable pageable) {
        existsCategory(category);
        return categoryRepository.getAllTaskExpiredByCategory(category, pageable)
                .getContent();
    }

    private void existsCategory(String category) {
        if ( !categoryRepository.existsByName(category) ) {
            throw new ResourceNotFoundException(entityName, category);
        }
    }
}
