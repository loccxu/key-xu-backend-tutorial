package com.simoncomputing.sample.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleDto {
    
    private Long id;
    private String name;
    private String description;
    
    private List<Long> permissionIds = new ArrayList<>();
    
    public RoleDto() {}
    
    public RoleDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Long> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}

	@Override
	public String toString() {
		return "RoleDto [id=" + id + ", name=" + name + ", description=" + description + ", permissionIds="
				+ permissionIds + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, permissionIds);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleDto other = (RoleDto) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(permissionIds, other.permissionIds);
	}

    // Generate getters, setters, equals(), hashCode(), toString() here

}
