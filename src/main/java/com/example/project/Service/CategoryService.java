package com.example.project.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Category;
import com.example.project.Repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository CategoryRepository;

	@Autowired
	public CategoryService(CategoryRepository CategoryRepository) {
		this.CategoryRepository = CategoryRepository;
	}

	public List<Category> getCategories() {
		return CategoryRepository.findAll();
	}

	public void addNewCategory(Category Category) {
	    CategoryRepository.save(Category);
	}

	public void deleteCategory(int CategoryId) {
		boolean exists = CategoryRepository.existsById(CategoryId);
		if (!exists) {
			throw new IllegalStateException("status with id " + CategoryId + " does not exist");
		}
		CategoryRepository.deleteById(CategoryId);
	}

	@Transactional
	public void updateCategory(int CategoryId, String CategoryName) {
		Category Category = CategoryRepository.findById(CategoryId)
				.orElseThrow(() -> new IllegalStateException("status with id " + CategoryId + " does not exist"));

		if (CategoryName != null && CategoryName.length() > 0 && !Objects.equals(Category.getCategoryName(), CategoryName)) {
		    Category.setCategoryName(CategoryName);
		}
	}

}
