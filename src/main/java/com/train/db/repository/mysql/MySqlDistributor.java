package com.train.db.repository.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.train.db.BasicConnectionPool;
import com.train.db.ConnectionPoolI;
import com.train.db.ResultSetConverter;
import com.train.db.StructureDistributorBuilder;
import com.train.db.repository.dao.DistributorDaoI;
import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.DistributorEntity;

public class MySqlDistributor implements DistributorDaoI {
	private ConnectionPoolI connectionPool = BasicConnectionPool.getPool();
	
	@Override
	public List<DistributorEntity> loadAllDistributors() {
		String query = "SELECT contract_id, dist_first_name, dist_second_name, dist_password, dist_email, role_id,"
					+ " dist_age, dist_registration_date, qualification, leader_contract_id, dist_money"
					+ " FROM distributors";
		Connection connection = connectionPool.getConnection();
		List<DistributorEntity> listDistributor = new ArrayList<DistributorEntity>();
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query);
			ResultSet resultSet = pdStatement.executeQuery()) {
			
			while (resultSet.next()) {
				DistributorEntity distributor = ResultSetConverter.createDistributorEntity(resultSet);
				listDistributor.add(distributor);
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return listDistributor;
	}
	
	@Override
	public DistributorEntity loadDistributorStructureWithReportsByContractIdAndDate(int contractId, LocalDate endDate)
			throws DaoException {
		String query = "SELECT node.contract_id, node.dist_first_name, node.dist_second_name, node.dist_email,"
					+ " node.dist_age, node.qualification, node.leader_contract_id, r.report_id, r.start_date,"
					+ " r.end_date, r.bonus, r.personal_points, r.personal_group_points, r.group_points,"
					+ " (COUNT(parent.contract_id) - (sub_tree.depth + 1)) AS depth"
					+ " FROM distributors node, distributors parent, distributors sub_parent, reports r,"
					+ "     (SELECT node.contract_id, (COUNT(parent.contract_id) - 1) AS depth" 
					+ "		FROM distributors node, distributors parent"
					+ "		WHERE node.left_node_number BETWEEN parent.left_node_number AND parent.right_node_number" 
					+ "		AND node.contract_id = ?" 
					+ "		GROUP BY node.contract_id" 
					+ "		ORDER BY node.left_node_number" 
					+"      ) AS sub_tree"
					+ " WHERE node.left_node_number BETWEEN parent.left_node_number AND parent.right_node_number"
					+ " AND node.left_node_number BETWEEN sub_parent.left_node_number AND sub_parent.right_node_number" 
					+ " AND sub_parent.contract_id = sub_tree.contract_id" 
					+ " AND r.contract_id = node.contract_id AND r.end_date = ?" 
					+ " GROUP BY node.contract_id"
					+ " ORDER BY depth, node.left_node_number";
		
		Connection connection = connectionPool.getConnection();
		DistributorEntity distributor = null;
	
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, contractId);
			pdStatement.setDate(2, Date.valueOf(endDate), Calendar.getInstance());
			
			try (ResultSet resultSet = pdStatement.executeQuery()) {
				StructureDistributorBuilder builderTree = new StructureDistributorBuilder(resultSet);
				distributor = builderTree.createTree();
			} 
	
		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return distributor;
	}
	
	@Override
	public DistributorEntity loadDistributorByContractId(int contractId) {
		String query = "SELECT contract_id, dist_first_name, dist_second_name, dist_password, dist_email, role_id,"
					+ " dist_age, dist_registration_date, qualification, leader_contract_id, dist_money"
					+ " FROM distributors WHERE contract_id = ?";
		Connection connection = connectionPool.getConnection();
		DistributorEntity distributor = null;

		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, contractId);
			
			try (ResultSet resultSet = pdStatement.executeQuery()) {
				if (resultSet.next()) {
					distributor = ResultSetConverter.createDistributorEntity(resultSet);
				}	
			} 

		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return distributor;
	}
	
	@Override
	public boolean isExistEmail(String email) throws DaoException {
		String query = "SELECT contract_id FROM distributors WHERE dist_email = ?";
		Connection connection = connectionPool.getConnection();
		boolean isExist = false;
	
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setString(1, email);
			
			try (ResultSet resultSet = pdStatement.executeQuery()) {
				if (resultSet.next()) {
					isExist = true;
				}	
			} 
	
		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return isExist;
	}
	
	@Override
	public int saveDistributor(DistributorEntity distributor) {
		String call = "{CALL insert_distributor (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		Connection connection = connectionPool.getConnection();
		Integer id = null;

		try (CallableStatement callStatement = connection.prepareCall(call)) {

			callStatement.setInt(1, distributor.getLeaderContractId());
			callStatement.setString(2, distributor.getFirstName());
			callStatement.setString(3, distributor.getSecondName());
			callStatement.setString(4, distributor.getPassword());
			callStatement.setString(5, distributor.getEmail());
			callStatement.setInt(6, distributor.getRole().getId());
			callStatement.setInt(7, distributor.getAge());
			callStatement.setDate(8, Date.valueOf(distributor.getRegistrationDate()));
			callStatement.setString(9, distributor.getQualification().toString());
			callStatement.setDouble(10, distributor.getMoney());

			try (ResultSet resultSet = callStatement.executeQuery()) {
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
	public void updateDistributorByContractId(DistributorEntity distributor) {
		throw new UnsupportedOperationException("update distributor does not support yet");		
	}
	
	@Override
	public boolean removeDistributor(int contractId) {
		throw new UnsupportedOperationException("delete distributor does not support yet");	
	}
}
