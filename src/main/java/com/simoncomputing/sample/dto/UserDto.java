package com.simoncomputing.sample.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto {
    
    private Long id;

    private String firstName;
    private String lastName;
    private String description;
    private String password;
    
    private List<Long> roleIDs = new ArrayList<>();
    
    public UserDto() {}
    
    public UserDto(String firstName, String lastName, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", description="
				+ description + ", password=" + password + ", roleIDs=" + roleIDs + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, firstName, id, lastName, password, roleIDs);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(description, other.description) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(roleIDs, other.roleIDs);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Long> getRoleIDs() {
		return roleIDs;
	}

	public void setRoleIDs(List<Long> roleIDs) {
		this.roleIDs = roleIDs;
	}

    // Generate getters, setters, equals(), hashCode(), toString() here

}
