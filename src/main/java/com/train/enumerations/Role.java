package com.train.enumerations;

public enum Role {
	SUPER_ADMIN, ADMIN, USER;
	
	public int getId() {
		switch (this) {
			case SUPER_ADMIN : return 1;
			case ADMIN : return 2;
			case USER : return 3;
			default : return 0;
		}
	}
	
	public static Role parseInt(int i) { 
		switch (i) {
			case 1 : return SUPER_ADMIN;
			case 2 : return ADMIN;
			case 3 : return USER;
			default : return USER;
		}
	}
}
