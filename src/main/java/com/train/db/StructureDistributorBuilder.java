package com.train.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.train.db.repository.entities.DistributorEntity;
import com.train.db.repository.entities.ReportEntity;
import com.train.enumerations.Qualification;

/**
 * Class which helps to build the tree structure of distributors from the table of data {@link java.sql.ResultSet}. 
 */
public class StructureDistributorBuilder {
	private ResultSet resultSet;
	private DistributorEntity distributor;
	
	public StructureDistributorBuilder(ResultSet resultSet) {
		this.resultSet = resultSet;
		this.distributor = new DistributorEntity();
	}
	
	/** 
     * This method runs building tree of distributors.
     * @return {@link DistributorEntity}. 
     */
	public DistributorEntity createTree() throws SQLException {
		if (resultSet.next()) {
			distributor = createDistributor();
		}
		
		while (resultSet.next()) {
			int depth = getDepth();
			exploreDistributor(distributor, depth - 1);
		}

		return distributor;
	}
	
	private void exploreDistributor(DistributorEntity dist, int depth) throws SQLException {
		if (resultSet == null) {
			return;
		}
		if (depth == 0) {
			if (dist.getContractId() == getSponsorId()) {
				dist.addDistributor(createDistributor());
			}
		} else {
			for (Map.Entry<Integer, DistributorEntity> entry : dist.getMapFirstLineDistributors().entrySet()) {
				exploreDistributor(entry.getValue(), depth - 1);
			}
		}
	}
	
	private DistributorEntity createDistributor() throws SQLException {
		DistributorEntity distributor = new DistributorEntity();
		
		distributor.setContractId(resultSet.getInt("node.contract_id"));
		distributor.setFirstName(resultSet.getString("node.dist_first_name"));
		distributor.setSecondName(resultSet.getString("node.dist_second_name"));
		distributor.setEmail(resultSet.getString("node.dist_email"));
		distributor.setAge(resultSet.getInt("node.dist_age"));
		distributor.setQualification(Qualification.valueOf(resultSet.getString("node.qualification")));
		distributor.setLeaderContractId(resultSet.getInt("node.leader_contract_id"));
		
		List<ReportEntity> reports = new ArrayList<>();
		ReportEntity report = new ReportEntity();
		report.setContractId(resultSet.getInt("node.contract_id"));
		report.setId(resultSet.getInt("r.report_id"));
		report.setStartDate(resultSet.getDate("r.start_date").toLocalDate());
		report.setEndDate(resultSet.getDate("r.end_date").toLocalDate());
		report.setBonus(resultSet.getInt("r.bonus"));
		report.setPersonalPoints(resultSet.getInt("r.personal_points"));
		report.setPersonalGroupPoints(resultSet.getInt("r.personal_group_points"));
		report.setGroupPoints(resultSet.getInt("r.group_points"));
		reports.add(report);
		distributor.setListReports(reports);

		return distributor;
	}
	
	private int getDepth() throws SQLException {
		return resultSet.getInt("depth");
	}
	
	private int getSponsorId() throws SQLException {
		return resultSet.getInt("node.leader_contract_id");
	}

}
