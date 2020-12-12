package com.train.db.repository.entities;

public class StaffEntity extends UserEntity {	
	@Override
    public String toString() {
        String text = "StaffEntity [contractId=%s, firstName=%s, secondName=%s, email=%s, role=%s,"
        		+ " age=%s, registrationDate=%s]";
        return String.format(
                text,
                getContractId(),
                getFirstName(),
                getSecondName(),
                getEmail(),
                getRole(),
                getAge(),
                getRegistrationDate()
        );
    }
}
