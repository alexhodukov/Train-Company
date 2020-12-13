package com.train.db.repository.dao;

import java.util.List;

import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.CategoryEntity;

public interface CategoryDaoI {
	List<CategoryEntity> loadAllCategories() throws DaoException;
	
	CategoryEntity loadCategoryById(int categoryId) throws DaoException;
	
	int saveCategory(CategoryEntity category) throws DaoException;
	
	boolean updateCategory(CategoryEntity category) throws DaoException;
	
	boolean updateCategoryStatus(CategoryEntity category) throws DaoException;
	
	boolean removeCategory(int categoryId) throws DaoException;
}
