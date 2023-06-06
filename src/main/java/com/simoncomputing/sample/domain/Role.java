package com.simoncomputing.sample.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.NaturalId;

@Entity
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    // Note that we are specifically making this mutable because
    // we want to allow the user to change the name of a role.
    @NaturalId(mutable = true)
    private String name;
    
    private String description;
    
    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName="id"))
    private List<Permission> permissions = new ArrayList<>();
    
    public Role() {}
    
    public Role(String name, String description) {
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

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}