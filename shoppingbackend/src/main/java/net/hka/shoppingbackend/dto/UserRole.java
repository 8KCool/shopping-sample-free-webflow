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

	// return a label formated string by capitalize the first character and make
	// rest of the role word as lower case
	public String getValueAsLabel() {
		return Character.toUpperCase(this.value.charAt(0)) + this.value.toLowerCase().substring(1);
	}
}
