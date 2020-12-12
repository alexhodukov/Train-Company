package com.train.db.repository.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.train.enumerations.Qualification;

public class DistributorEntity extends UserEntity {	
	private Qualification qualification;
	
	private int leaderContractId;
	
	private double money;
	
	private Map<Integer, DistributorEntity> mapFirstLineDistributors = new HashMap<>();
	
	public Map<Integer, DistributorEntity> getMapFirstLineDistributors() {
		return mapFirstLineDistributors;
	}

	public void setMapFirstLineDistributors(Map<Integer, DistributorEntity> mapFirstLineDistributors) {
		this.mapFirstLineDistributors = mapFirstLineDistributors;
	}
	
	public void addDistributor(DistributorEntity distributor) {
		mapFirstLineDistributors.put(distributor.getContractId(), distributor);
	}

	private List<OrderEntity> listOrders;
	
	private List<ReportEntity> listReports;
	
	public Qualification getQualification() {
		return qualification;
	}
	
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	
    public int getLeaderContractId() {
		return leaderContractId;
	}

	public void setLeaderContractId(int leaderContractId) {
		this.leaderContractId = leaderContractId;
	}
	
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}


	public List<OrderEntity> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<OrderEntity> listOrders) {
		this.listOrders = listOrders;
	}

	public List<ReportEntity> getListReports() {
		return listReports;
	}

	public void setListReports(List<ReportEntity> listReports) {
		this.listReports = listReports;
	}

	@Override
    public String toString() {
        String text = "DistributorEntity [contractId=%s, firstName=%s, secondName=%s, email=%s, role=%s,"
        		+ " age=%s, registrationDate=%s, qualification=%s, leaderContractId=%s, money=%s]";
        return String.format(
                text,
                getContractId(),
                getFirstName(),
                getSecondName(),
                getEmail(),
                getRole(),
                getAge(),
                getRegistrationDate(),
                qualification,
                leaderContractId,
                money
        );
    }
}
