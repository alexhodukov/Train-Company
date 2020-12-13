package com.train.db.repository.dao;

import java.util.List;

import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.StaffEntity;

public interface StaffDaoI {
	List<StaffEntity> loadAllStaff() throws DaoException;
	
	StaffEntity loadStaffByContractId(int contractId) throws DaoException;
	
	boolean isExistEmail(String email) throws DaoException;
	
	int saveStaff(StaffEntity staff) throws DaoException;
	
	void updateStaffByContractId(StaffEntity staff) throws DaoException;
	
	boolean removeStaff(int contractId) throws DaoException;
}
