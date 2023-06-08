package com.simoncomputing.sample.dto;

import java.util.Objects;

public class PermissionDto {
    
    @Override
	public String toString() {
		return "PermissionDto [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

	private Long id;
    private String name;
    private String description;
    
    public PermissionDto() {}
    
    public PermissionDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermissionDto other = (PermissionDto) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    // Generate getters, setters, equals(), hashCode(), toString() here
}