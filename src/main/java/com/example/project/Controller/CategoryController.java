package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Category;
import com.example.project.Service.CategoryService;

@RestController
@RequestMapping(path = "api/category")
public class CategoryController {

	private final CategoryService CategoryService;

	@Autowired
	public CategoryController(CategoryService CategoryService) {
        this.CategoryService= CategoryService;
    }
	
	@GetMapping("/getCategories")
	public List<Category> getAll(){
	    return CategoryService.getCategories();
	}

    @PostMapping("/addCategory")
    public void addNewCategory(@RequestBody Category Category) {
    	CategoryService.addNewCategory(Category);
	}

    @DeleteMapping(path= "/deleteCategory/{CategoryId}")
    public void deleteCategory(@PathVariable("CategoryId") int CategoryId) {
    	CategoryService.deleteCategory(CategoryId);
    }

    @PutMapping(path = "/updateCategory/{CategoryId}")
    public void updateCategory(@PathVariable("CategoryId") int CategoryId, String CategoryName) {
    	CategoryService.updateCategory(CategoryId,CategoryName);
    }
}
