package com.gestion.tareas;

import com.gestion.tareas.entity.Category;
import com.gestion.tareas.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class AppTareasApplicationTests {
	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void verifyGetReportCategories() {
//		Optional<Category> category = categoryRepository.findById(1L);
//
//		if (category.isPresent()) {
//			System.out.println(category.get());
//		} else {
//			System.out.println("False");
//		}

		System.out.println(categoryRepository.findReportCategories());
	}

}
