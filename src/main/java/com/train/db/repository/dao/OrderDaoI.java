package com.train.db.repository.dao;

import java.util.List;

import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.OrderEntity;

public interface OrderDaoI {
	List<OrderEntity> loadAllOrders() throws DaoException;
	
	List<OrderEntity> loadOrdersByContractId(int contractId) throws DaoException;
	
	int saveOrder(OrderEntity order) throws DaoException;

	void removeOrder(int orderId) throws DaoException;
}
