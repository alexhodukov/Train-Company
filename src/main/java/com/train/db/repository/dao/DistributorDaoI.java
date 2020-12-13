package com.train.db.repository.dao;

import java.time.LocalDate;
import java.util.List;

import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.DistributorEntity;

public interface DistributorDaoI {
	List<DistributorEntity> loadAllDistributors() throws DaoException;
	
	DistributorEntity loadDistributorByContractId(int contractId) throws DaoException;
	
	DistributorEntity loadDistributorStructureWithReportsByContractIdAndDate(int contractId, LocalDate date) throws DaoException;
	
	boolean isExistEmail(String email) throws DaoException;
	
	int saveDistributor(DistributorEntity distributor) throws DaoException;
	
	void updateDistributorByContractId(DistributorEntity distributor) throws DaoException;
	
	boolean removeDistributor(int contractId) throws DaoException;
}
