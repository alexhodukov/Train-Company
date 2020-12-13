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
import com.train.db.repository.dao.StaffDaoI;
import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.StaffEntity;

public class MySqlStaff implements StaffDaoI {
	private ConnectionPoolI connectionPool = BasicConnectionPool.getPool();

	@Override
	public List<StaffEntity> loadAllStaff() {
		String query = "SELECT contract_id, staff_first_name, staff_second_name, staff_password, staff_email,"
					+ " role_id, staff_age, staff_registration_date FROM staff";
		Connection connection = connectionPool.getConnection();
		List<StaffEntity> listStaff = new ArrayList<StaffEntity>();
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query);
			ResultSet resultSet = pdStatement.executeQuery()) {
			
			while (resultSet.next()) {
				StaffEntity staff = ResultSetConverter.createStaffEntity(resultSet);
				listStaff.add(staff);
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		
		connectionPool.releaseConnection(connection);
		return listStaff;
	}
	
	@Override
	public StaffEntity loadStaffByContractId(int contractId) {
		String query = "SELECT contract_id, staff_first_name, staff_second_name, staff_password, staff_email,"
					+ " role_id, staff_age, staff_registration_date FROM staff WHERE contract_id = ?";
		Connection connection = connectionPool.getConnection();
		StaffEntity staff = null;

		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, contractId);
			
			try (ResultSet resultSet = pdStatement.executeQuery()) {
				if (resultSet.next()) {
					staff = ResultSetConverter.createStaffEntity(resultSet);
				}
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return staff;
	}
	
	public boolean isExistEmail(String email) throws DaoException {
		String query = "SELECT contract_id FROM staff WHERE staff_email = ?";
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
	public int saveStaff(StaffEntity staff) {
		String query = "INSERT INTO staff (staff_first_name, staff_second_name, staff_password, staff_email,"
					+ " role_id, staff_age, staff_registration_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection connection = connectionPool.getConnection();
		Integer id = null;

		try (PreparedStatement pdStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			pdStatement.setString(1, staff.getFirstName());
			pdStatement.setString(2, staff.getSecondName());
			pdStatement.setString(3, staff.getPassword());
			pdStatement.setString(4, staff.getEmail());
			pdStatement.setInt(5, staff.getRole().getId());
			pdStatement.setInt(6, staff.getAge());
			pdStatement.setDate(7, Date.valueOf(staff.getRegistrationDate()));

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
	public boolean removeStaff(int contractId) {
		String query = "DELETE FROM staff WHERE contract_id = ?";
		Connection connection = connectionPool.getConnection();
		int result = 0;
		
		try (PreparedStatement pdStatement = connection.prepareStatement(query)) {
			
			pdStatement.setInt(1, contractId);
			result = pdStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} 
		
		connectionPool.releaseConnection(connection);
		return result == 1;
	}

	@Override
	public void updateStaffByContractId(StaffEntity staff) {
		throw new UnsupportedOperationException("update staff does not support yet");
	}
}
