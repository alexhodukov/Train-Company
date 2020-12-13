package com.train.db.repository.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.train.db.BasicConnectionPool;
import com.train.db.ConnectionPoolI;
import com.train.db.ResultSetConverter;
import com.train.db.repository.dao.OrderDaoI;
import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.OrderDetailsEntity;
import com.train.db.repository.entities.OrderEntity;

public class MySqlOrder implements OrderDaoI {
	private ConnectionPoolI connectionPool = BasicConnectionPool.getPool();
	
	@Override
	public List<OrderEntity> loadAllOrders() throws DaoException {
		throw new UnsupportedOperationException("load all orders does not support yet");	
	}

	@Override
	public List<OrderEntity> loadOrdersByContractId(int contractId) throws DaoException {
		String query = "SELECT o.order_id, o.order_date, o.contract_id, o.total_price, o.total_bonus,"
					+ " od.details_id, od.order_id, od.product_id, od.quantity_ordered, od.sum_price, od.sum_bonus,"
					+ " p.product_id"
					+ " FROM orders AS o LEFT JOIN order_details AS od ON o.order_id = od.order_id"
					+ " LEFT JOIN distributors AS d ON d.contract_id = o.contract_id"
					+ " LEFT JOIN products AS p ON p.product_id = od.product_id"
					+ " WHERE d.contract_id = ?"
					+ " ORDER BY o.order_id, od.details_id";
		Connection connection = connectionPool.getConnection();
		List<OrderEntity> listOrders = new ArrayList<>();
	
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, contractId);
			
			try (ResultSet resultSet = pdStatement.executeQuery()) {
				listOrders = ResultSetConverter.createListOrderEntities(resultSet);
			} 
	
		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return listOrders;
	}

	@Override
	public int saveOrder(OrderEntity order) throws DaoException {
		String insertOrder = "INSERT INTO orders (order_date, total_price, total_bonus, contract_id)"
					+ "	VALUES (?, ?, ?, ?)";
		String insertOrderDetails = "INSERT INTO order_details (order_id, product_id, quantity_ordered, sum_price, "
					+ " sum_bonus) VALUES (?, ?, ?, ?, ?)";
		String updateReport = "UPDATE reports SET personal_points = personal_points + ? WHERE contract_id = ?";
		Connection connection = connectionPool.getConnection();
		Integer idOrder = null;
		
		try (PreparedStatement pdStatementOrder = connection.prepareStatement(insertOrder, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement pdStatementDetails = connection.prepareStatement(insertOrderDetails, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement pdStatementReport = connection.prepareStatement(updateReport)) {
			
			pdStatementOrder.setDate(1, Date.valueOf(order.getOrderDate()));
			pdStatementOrder.setDouble(2, order.getTotalPrice());
			pdStatementOrder.setDouble(3, order.getTotalBonus());
			pdStatementOrder.setInt(4, order.getContractId());
			pdStatementOrder.executeUpdate();
			
			try (ResultSet resultSet = pdStatementOrder.getGeneratedKeys()) {
				if (resultSet.next()) {
					idOrder = resultSet.getInt(1);
				}
			}
			
			for (OrderDetailsEntity detail : order.getListDetails()) {
				System.out.println(detail);
				pdStatementDetails.clearParameters();
				pdStatementDetails.setInt(1, idOrder);
				pdStatementDetails.setInt(2, detail.getProductId());
				pdStatementDetails.setInt(3, detail.getQuantityOrdered());
				pdStatementDetails.setDouble(4, detail.getSumPrice());
				pdStatementDetails.setDouble(5, detail.getSumBonus());
				pdStatementDetails.addBatch();
			}
			pdStatementDetails.executeBatch();
			
			pdStatementReport.setInt(1, order.getTotalBonus());
			pdStatementReport.setInt(2, order.getContractId());
			pdStatementReport.executeUpdate();
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} 

		connectionPool.releaseConnection(connection);
		return idOrder;
	}

	@Override
	public void removeOrder(int orderId) throws DaoException {
		throw new UnsupportedOperationException("Delete order does not support yet");			
	}
	
}
