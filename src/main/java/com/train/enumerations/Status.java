package com.train.enumerations;

public enum Status {
	INACTIVE, ACTIVE;
	
	public int getId() {
		switch (this) {
			case INACTIVE : return 1;
			case ACTIVE : return 2;
			default : return 0;
		}
	}
	
	public static Status parseInt(int i) { 
		switch (i) {
			case 1 : return INACTIVE;
			case 2 : return ACTIVE;
			default : return INACTIVE;
		}
	}
}
