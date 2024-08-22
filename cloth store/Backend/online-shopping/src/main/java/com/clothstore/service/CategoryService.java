package com.clothstore.service;

import java.util.List;

import com.clothstore.model.Category;

public interface CategoryService {
	
	public List<Category> getAllCategories();
	
	public Category addCategory(Category category) ;
	
	public void deleteCategoryById(int categoryId);

}
