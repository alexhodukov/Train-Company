package com.train.db.repository.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderEntity {
	private int id;
	
	private LocalDate orderDate;
	
	private int contractId;
	
	private double totalPrice;
	
	private int totalBonus;
	
	private List<OrderDetailsEntity> listDetails;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	
	public int getContractId() {
		return contractId;
	}
	
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	
    public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalBonus() {
		return totalBonus;
	}

	public void setTotalBonus(int totalBonus) {
		this.totalBonus = totalBonus;
	}

	public List<OrderDetailsEntity> getListDetails() {
		return listDetails;
	}

	public void setListDetails(List<OrderDetailsEntity> listDetails) {
		this.listDetails = listDetails;
	}
	
	public void addDetailList(OrderDetailsEntity detail) {
		if (listDetails == null) {
			listDetails = new ArrayList<>();
		}
		listDetails.add(detail);
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
		OrderEntity other = (OrderEntity) obj;
		if (contractId != other.contractId)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
    public String toString() {
        String text = "OrderEntity [id=%s, orderDate=%s, contractId=%s, totalPrice=%s, totalBonus=%s]";
        return String.format(text, id, orderDate, contractId, totalPrice, totalBonus);
    }
	
}
