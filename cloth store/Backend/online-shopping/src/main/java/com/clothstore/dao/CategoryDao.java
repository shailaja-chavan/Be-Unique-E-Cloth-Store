package com.clothstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clothstore.model.Category;

public interface CategoryDao extends JpaRepository<Category, Integer> {
	

}
