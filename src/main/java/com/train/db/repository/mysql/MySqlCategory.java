package com.train.db.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.train.db.BasicConnectionPool;
import com.train.db.ConnectionPoolI;
import com.train.db.ResultSetConverter;
import com.train.db.repository.dao.CategoryDaoI;
import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.CategoryEntity;

public class MySqlCategory implements CategoryDaoI {	
	private ConnectionPoolI connectionPool = BasicConnectionPool.getPool();

	@Override
	public List<CategoryEntity> loadAllCategories() {
		String query = "SELECT category_id, category_title, category_description, status_id FROM categories";
		Connection connection = connectionPool.getConnection();
		List<CategoryEntity> listCategories = new ArrayList<CategoryEntity>();		
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query);
			ResultSet resultSet = pdStatement.executeQuery()) {
			
			while (resultSet.next()) {
				CategoryEntity category = ResultSetConverter.createCategoryEntity(resultSet);
				listCategories.add(category);
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		
		connectionPool.releaseConnection(connection);
		return listCategories;
	}
	
	@Override
	public CategoryEntity loadCategoryById(int categoryId) {
		String query = "SELECT category_id, category_title, category_description, status_id"
					+ " FROM categories WHERE category_id = ?";
		Connection connection = connectionPool.getConnection();
		CategoryEntity category = null;

		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, categoryId);
			
			try (ResultSet resultSet = pdStatement.executeQuery()) {
				if (resultSet.next()) {
					category = ResultSetConverter.createCategoryEntity(resultSet);
				}
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		connectionPool.releaseConnection(connection);		
		return category;
	}
	
	@Override
	public int saveCategory(CategoryEntity category) {
		String query = "INSERT INTO categories (category_title, category_description, status_id) VALUES (?, ?, ?)";
		Connection connection = connectionPool.getConnection();
		Integer id = null;

		try (PreparedStatement pdStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			pdStatement.setString(1, category.getTitle());
			pdStatement.setString(2, category.getDescription());
			pdStatement.setInt(3, category.getStatus().getId());
			pdStatement.executeUpdate();

			try (ResultSet resultSet = pdStatement.getGeneratedKeys()) {
				if (resultSet.next()) {
					id = resultSet.getInt(1);
				}
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		connectionPool.releaseConnection(connection);
		return id;
	}
	
	@Override
	public boolean updateCategory(CategoryEntity category) {
		String query = "UPDATE categories SET category_title = ?, category_description = ?, status_id = ?"
					+ " WHERE category_id = ?";
		Connection connection = connectionPool.getConnection();
		int result = 0;
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			pdStatement.setString(1, category.getTitle());
			pdStatement.setString(2, category.getDescription());
			pdStatement.setInt(3, category.getStatus().getId());			
			pdStatement.setInt(4, category.getId());
			result = pdStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		connectionPool.releaseConnection(connection);
		return result == 1;
	}
	
	@Override
	public boolean updateCategoryStatus(CategoryEntity category) {
		String query = "UPDATE categories SET status_id = ? WHERE category_id = ?";
		Connection connection = connectionPool.getConnection();
		int result = 0;
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, category.getStatus().getId());			
			pdStatement.setInt(2, category.getId());
			result = pdStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		connectionPool.releaseConnection(connection);
		return result == 1;
	}
	
	@Override
	public boolean removeCategory(int categoryId) {
		String query = "DELETE FROM categories WHERE category_id = ?";
		Connection connection = connectionPool.getConnection();
		int result = 0;

		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, categoryId);
			result = pdStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		connectionPool.releaseConnection(connection);
		return result == 1;
	}
}
