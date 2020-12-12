package com.train.db.repository.entities;

import java.util.List;

import com.train.enumerations.Status;

public class CategoryEntity {
	private int id;
	
	private String title;
	
	private String description;
	
	private Status status;
	
	private List<ProductEntity> listProducts;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<ProductEntity> getListProducts() {
		return listProducts;
	}

	public void setListProducts(List<ProductEntity> listProducts) {
		this.listProducts = listProducts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		CategoryEntity other = (CategoryEntity) obj;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
    public String toString() {
        String text = "CategoryEntity [id=%s, title=%s, description=%s, status=%s]";
        return String.format(text, id, title, description, status);
    }
	
}
