package com.train.db.repository.entities;

import java.time.LocalDate;

import com.train.enumerations.Role;

public class UserEntity {
	private int contractId;
	
	private String firstName;
	
	private String secondName;
	
	private String email;
	
	private String password;
	
	private Role role;
	
	private int age;
	
	private LocalDate registrationDate;

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}
	
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + contractId;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		UserEntity other = (UserEntity) obj;
		if (contractId != other.contractId)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
    public String toString() {
        String text = "DistributorEntity [contractId=%s, firstName=%s, secondName=%s, email=%s, "
        		+ "role=%s, age=%s, registrationDate=%s]";
        return String.format(
                text,
                contractId,
                firstName,
                secondName,
                email,
                role,
                age,
                registrationDate
        );
    }
}
