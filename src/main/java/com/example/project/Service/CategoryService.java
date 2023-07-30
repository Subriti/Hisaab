package com.example.project.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.ActivityLog;
import com.example.project.Model.Category;
import com.example.project.Model.UserSession;
import com.example.project.Repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository CategoryRepository;

    private final UserSession userSession;
    private final ActivityLogService activityLogService;

	@Autowired
	public CategoryService(CategoryRepository CategoryRepository, UserSession userSession, ActivityLogService activityLogService) {
		this.CategoryRepository = CategoryRepository;
        this.userSession = userSession;
        this.activityLogService = activityLogService;
	}

	public List<Category> getCategories() {
		return CategoryRepository.findAll();
	}
	
	//find specific group
    public Category findCategoryByID(int categoryId) {
        Category category = CategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalStateException("Category with ID " + categoryId + " does not exist"));
        return category;
    }
	
	public void addNewCategory(Category Category) {
	    CategoryRepository.save(Category);
	    
	  //add activity log
        ActivityLog log= new ActivityLog();
        System.out.println("Current user: "+userSession.getUserDetails().getName());
        
        log.setUserId(userSession.getUserDetails());
        log.setMessage("You added a new category: '"+ Category.getCategoryName()+"'");
        log.setCreatedAt(new Date(System.currentTimeMillis()));
        activityLogService.addActivityLog(log);
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
