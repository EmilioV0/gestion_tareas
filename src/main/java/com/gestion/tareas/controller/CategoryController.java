package com.gestion.tareas.controller;

import com.gestion.tareas.dto.category.CreateCategory;
import com.gestion.tareas.dto.category.GetCategory;
import com.gestion.tareas.dto.category.ReportCategory;
import com.gestion.tareas.dto.task.TaskByCategory;
import com.gestion.tareas.service.CategoryService;
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
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/{name}/tasks")
    public ResponseEntity<List<TaskByCategory>> getTasksByCategory(
            @PageableDefault Pageable pageable,
            @RequestParam( value = "searchDate", defaultValue = "today" ) String searchDate,
            @PathVariable("name") String categoryName)
    {
        List<TaskByCategory> tasksByCategory = categoryService.
                findByIdCategoryAndIdState(categoryName, searchDate, pageable);

        return ResponseEntity.ok(tasksByCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable long id) {
        categoryService.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<GetCategory> save(@RequestBody CreateCategory createCategory) throws URISyntaxException {
        GetCategory category = categoryService.save(createCategory);
        return ResponseEntity.created( new URI("tasks/" + category.id())).body(category);
    }

    @GetMapping("/{categoryName}/report")
    public ResponseEntity<ReportCategory> getReportCategory(@PathVariable String categoryName) {
        ReportCategory reportCategory = categoryService.findReportCategory(categoryName);
        return ResponseEntity.ok(reportCategory);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/report")
    public List<ReportCategory> getReportCategories(@PathVariable String categoryName) {
        return categoryService.findReportCategories();
    }

    @GetMapping("/{categoryName}/tasks/finished")
    public ResponseEntity<List<TaskByCategory>> getAllTaskFinishedByCategory(@PathVariable String categoryName,
                                                                             @PageableDefault Pageable pageable) {
        List<TaskByCategory> result = categoryService.getAllTaskFinishedByCategory(categoryName, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{categoryName}/tasks/expired")
    public ResponseEntity<List<TaskByCategory>> getAllTaskExpiredByCategory(@PathVariable String categoryName,
                                                                             @PageableDefault Pageable pageable) {
        List<TaskByCategory> result = categoryService.getAllTaskExpiredByCategory(categoryName, pageable);
        return ResponseEntity.ok(result);
    }

}
