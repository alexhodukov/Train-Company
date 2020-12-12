package com.train.db.repository.entities;

public class OrderDetailsEntity {
	private int id;
	
	private int orderId;
	
	private int productId;
	
	private int quantityOrdered;
	
	private double sumPrice;
	
	private int sumBonus;
	
	private ProductEntity product;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getQuantityOrdered() {
		return quantityOrdered;
	}
	
	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}
	
	public double getSumPrice() {
		return sumPrice;
	}
	
	public void setSumPrice(double sumPrice) {
		this.sumPrice = sumPrice;
	}
	
    public int getSumBonus() {
		return sumBonus;
	}

	public void setSumBonus(int sumBonus) {
		this.sumBonus = sumBonus;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + orderId;
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
		OrderDetailsEntity other = (OrderDetailsEntity) obj;
		if (id != other.id)
			return false;
		if (orderId != other.orderId)
			return false;
		return true;
	}

	@Override
    public String toString() {
        String text = "OrderDetailEntity [id=%s, orderId=%s, productId=%s, quantityOrdered=%s, sumPrice=%s, sumBonus=%s]";
        return String.format(
                text,
                id,
                orderId,
                productId,
                quantityOrdered,
                sumPrice,
                sumBonus
        );
    }
	
}
