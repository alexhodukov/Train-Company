package com.train.db.repository.dao;

import java.util.List;

import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.ReportEntity;

public interface ReportDaoI {
	List<ReportEntity> loadAllReports() throws DaoException;
	
	List<ReportEntity> loadReportsByContractId(int reportId) throws DaoException;
	
	int saveReport(ReportEntity report) throws DaoException;
	
	void updateReportById(ReportEntity report) throws DaoException;

	void removeReport(int reportId) throws DaoException;
}
