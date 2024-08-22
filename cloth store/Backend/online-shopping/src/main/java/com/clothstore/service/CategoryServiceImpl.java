package com.clothstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clothstore.dao.CategoryDao;
import com.clothstore.model.Category;


@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		 return categoryDao.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryDao.save(category);
	}
	
	@Override
    public void deleteCategoryById(int categoryId) {
        categoryDao.deleteById(categoryId);
    }

}
