package com.marketmito.apporder.utils;

public enum Roles {
	
	ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");
	
	private String role;
	
	private Roles(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
	
}
