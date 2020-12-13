package com.train.db.repository.mysql;

import java.util.List;

import com.train.db.BasicConnectionPool;
import com.train.db.ConnectionPoolI;
import com.train.db.repository.dao.ReportDaoI;
import com.train.db.repository.dao.exceptions.DaoException;
import com.train.db.repository.entities.ReportEntity;

public class MySqlReport implements ReportDaoI {
	private ConnectionPoolI connectionPool = BasicConnectionPool.getPool();
	
	@Override
	public List<ReportEntity> loadAllReports() throws DaoException {
		throw new UnsupportedOperationException("load all reports does not support yet");
	}
	
	@Override
	public List<ReportEntity> loadReportsByContractId(int reportId) throws DaoException {
		throw new UnsupportedOperationException("load report does not support yet");
	}
	
	@Override
	public int saveReport(ReportEntity report) throws DaoException {
		throw new UnsupportedOperationException("save report does not support yet");
	}
	
	@Override
	public void updateReportById(ReportEntity report) throws DaoException {
		throw new UnsupportedOperationException("update report does not support yet");
	}
	
	@Override
	public void removeReport(int reportId) throws DaoException {
		throw new UnsupportedOperationException("remove report does not support yet");
	}
}
