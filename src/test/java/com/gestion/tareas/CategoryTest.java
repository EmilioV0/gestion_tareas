package com.gestion.tareas;

import com.gestion.tareas.repository.CategoryRepository;
import com.gestion.tareas.search.TaskSearchByRange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class CategoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void existsByName() {
        System.out.println( categoryRepository.existsByName("Shopping") );
    }

    @Test
    void search() {
        final int PAGE_NUMBER = 0;
        final int PAGE_SIZE = 10;
        Pageable pageable = PageRequest.of(
                PAGE_NUMBER,
                PAGE_SIZE,
                Sort.by("dateLimit")
        );
        TaskSearchByRange range = TaskSearchByRange.getTaskSearchByRange("tomorrow");

        categoryRepository.findByIdCategoryAndIdState(
                "Shopping", range, pageable).getContent().forEach(System.out::println);
    }


}
