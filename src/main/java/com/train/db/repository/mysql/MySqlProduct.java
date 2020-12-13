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
import com.train.db.repository.dao.ProductDaoI;
import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.ProductEntity;

public class MySqlProduct implements ProductDaoI {
	private ConnectionPoolI connectionPool = BasicConnectionPool.getPool();
	
	@Override
	public List<ProductEntity> loadAllProducts() throws DaoException {
		String query = "SELECT product_id, product_title, product_description, product_price, product_quantity,"
					+ " product_bonus, category_id, status_id FROM products";
		Connection connection = connectionPool.getConnection();
		List<ProductEntity> listProducts = new ArrayList<ProductEntity>();
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query);
			ResultSet resultSet = pdStatement.executeQuery()) {
			
			while (resultSet.next()) {
				ProductEntity product = ResultSetConverter.createProductEntity(resultSet);
				listProducts.add(product);
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return listProducts;
	}
	
	@Override
	public List<ProductEntity> loadAllProductsByCategory(int categoryId) throws DaoException {
		String query = "SELECT product_id, product_title, product_description, product_price, product_quantity,"
					+ " product_bonus, category_id, status_id FROM products WHERE category_id = ?";
		Connection connection = connectionPool.getConnection();
		List<ProductEntity> listProducts = new ArrayList<ProductEntity>();
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, categoryId);
			
			try (ResultSet resultSet = pdStatement.executeQuery()) {
				while (resultSet.next()) {
					ProductEntity product = ResultSetConverter.createProductEntity(resultSet);
					listProducts.add(product);
				}
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return listProducts;
	}

	@Override
	public ProductEntity loadProductById(int productId) throws DaoException {
		String query = "SELECT product_id, product_title, product_description, product_price, product_quantity," 
					+ " product_bonus, category_id, status_id FROM products WHERE product_id = ?";
		Connection connection = connectionPool.getConnection();
		ProductEntity product = null;

		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, productId);
			
			try (ResultSet resultSet = pdStatement.executeQuery()) {
				if (resultSet.next()) {
					product = ResultSetConverter.createProductEntity(resultSet);
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return product;
	}

	@Override
	public int saveProduct(ProductEntity product) throws DaoException {
		String query = "INSERT INTO products (product_title, product_description, product_price, product_quantity,"
					+ " product_bonus, category_id, status_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection connection = connectionPool.getConnection();
		Integer id = null;

		try (PreparedStatement pdStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			pdStatement.setString(1, product.getTitle());
			pdStatement.setString(2, product.getDescription());
			pdStatement.setDouble(3, product.getPrice());
			pdStatement.setInt(4, product.getQuantity());
			pdStatement.setInt(5, product.getBonus());
			pdStatement.setInt(6, product.getCategoryId());
			pdStatement.setInt(7, product.getStatus().getId());

			try (ResultSet resultSet = pdStatement.getGeneratedKeys()) {
				if (resultSet.next()) {
					id = resultSet.getInt(1);
				}
			}	
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} 

		connectionPool.releaseConnection(connection);
		return id;
	}

	@Override
	public boolean updateProduct(ProductEntity product) throws DaoException {
		String query = "UPDATE products SET product_title = ?, product_description = ?, product_price = ?, "
					+ " product_quantity = ?, product_bonus = ?, category_id = ?, status_id = ? WHERE product_id = ?";
		Connection connection = connectionPool.getConnection();
		int result = 0;
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setString(1, product.getTitle());
			pdStatement.setString(2, product.getDescription());
			pdStatement.setDouble(3, product.getPrice());
			pdStatement.setInt(4, product.getQuantity());
			pdStatement.setInt(5, product.getBonus());
			pdStatement.setInt(6, product.getCategoryId());
			pdStatement.setInt(7, product.getStatus().getId());
			pdStatement.setInt(8, product.getId());
			result = pdStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return result == 1;
	}

	@Override
	public boolean updateProductStatus(ProductEntity product) throws DaoException {
		String query = "UPDATE products SET status_id = ? WHERE product_id = ?";
		Connection connection = connectionPool.getConnection();
		int result = 0;
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, product.getStatus().getId());			
			pdStatement.setInt(2, product.getId());
			result = pdStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return result == 1;
	}
	
	@Override
	public boolean updateProductCategory(ProductEntity product) throws DaoException {
		String query = "UPDATE products SET category_id = ? WHERE product_id = ?";
		Connection connection = connectionPool.getConnection();
		int result = 0;
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, product.getCategoryId());			
			pdStatement.setInt(2, product.getId());
			result = pdStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return result == 1;
	}

	@Override
	public boolean removeProduct(int productId) throws DaoException {
		String query = "DELETE FROM products WHERE product_id = ?";
		Connection connection = connectionPool.getConnection();
		int result = 0;

		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, productId);
			result = pdStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException(e);
		} 

		connectionPool.releaseConnection(connection);
		return result == 1;
	}
	
}
