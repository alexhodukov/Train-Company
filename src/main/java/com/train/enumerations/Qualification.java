package com.train.enumerations;

public enum Qualification {
	CLIENT, MANAGER, DIRECTOR, GOLD_DIRECTOR;
	
	public static Qualification parseInt(int i) {
		switch (i) {
			case 1 : return CLIENT;
			case 2 : return MANAGER;
			case 3 : return DIRECTOR;
			case 4 : return GOLD_DIRECTOR;
			default : return CLIENT;
		}
	}
}
