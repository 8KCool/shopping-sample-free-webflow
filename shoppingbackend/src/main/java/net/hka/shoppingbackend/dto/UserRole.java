package net.hka.shoppingbackend.dto;

public enum UserRole {
	USER("USER"), SUPPLIER("SUPPLIER"), ADMIN("ADMIN");
	
	private String value;	
	
	private UserRole(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

	public String getValueAsLabel() {
		return Character.toUpperCase(this.value.charAt(0)) + this.value.toLowerCase().substring(1);
	}
}
