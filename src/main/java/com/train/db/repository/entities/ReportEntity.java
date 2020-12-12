package com.train.db.repository.entities;

import java.time.LocalDate;

public class ReportEntity {
	private int id;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private double bonus;
	
	private int personalPoints;
	
	private int personalGroupPoints;
	
	private int groupPoints;
	
	private int contractId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public int getPersonalPoints() {
		return personalPoints;
	}

	public void setPersonalPoints(int personalPoints) {
		this.personalPoints = personalPoints;
	}

	public int getPersonalGroupPoints() {
		return personalGroupPoints;
	}

	public void setPersonalGroupPoints(int personalGroupPoints) {
		this.personalGroupPoints = personalGroupPoints;
	}

	public int getGroupPoints() {
		return groupPoints;
	}

	public void setGroupPoints(int groupPoints) {
		this.groupPoints = groupPoints;
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + contractId;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportEntity other = (ReportEntity) obj;
		if (contractId != other.contractId)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
    public String toString() {
        String text = "ReportEntity [id=%s, startDate=%s, endDate=%s, bonus=%s, personalPoints=%s,"
        		+ " personalGroupPoints=%s, groupPoints=%s, contractId=%s]";
        return String.format(
                text,
                id,
                startDate,
                endDate,
                bonus,
                personalPoints,
                personalGroupPoints,
                groupPoints,
                contractId
        );
    }
	
}
