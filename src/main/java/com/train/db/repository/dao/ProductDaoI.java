package com.train.db.repository.dao;

import java.util.List;

import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.ProductEntity;

public interface ProductDaoI {
	List<ProductEntity> loadAllProducts() throws DaoException;
	
	List<ProductEntity> loadAllProductsByCategory(int categoryId) throws DaoException;
	
	ProductEntity loadProductById(int productId) throws DaoException;
	
	int saveProduct(ProductEntity product) throws DaoException;
	
	boolean updateProduct(ProductEntity product) throws DaoException;
	
	boolean updateProductCategory(ProductEntity product) throws DaoException;
	
	boolean updateProductStatus(ProductEntity product) throws DaoException;
	
	boolean removeProduct(int productId) throws DaoException;
}
