package com.simoncomputing.sample.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simoncomputing.sample.dao.PermissionDao;
import com.simoncomputing.sample.domain.Permission;
import com.simoncomputing.sample.dto.PermissionDto;
import com.simoncomputing.sample.dto.EditErrors;
import com.simoncomputing.sample.exception.NotFoundException;

@Service
@Transactional
public class PermissionService {
    
    @Autowired
    PermissionDao dao;
    
    public PermissionDto create(PermissionDto dto) {
    	validate(dto, false);
        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);
        
        permission = dao.save(permission);
        BeanUtils.copyProperties(permission, dto);
        
        return dto;
    }
    
    public PermissionDto update(PermissionDto dto) {
        validate(dto, true);
        
        Permission permission = new Permission();
        BeanUtils.copyProperties(dto, permission);
        
        permission = dao.save(permission);
        BeanUtils.copyProperties(permission, dto);
        
        return dto;
    }
    
    public void validate(PermissionDto dto, boolean update) {
        EditErrors errors = new EditErrors();
        
        if (update) {
            if (dto.getId() == null)
                throw new IllegalArgumentException("ID expected on update");

            if (!dao.existsById(dto.getId()))
                throw new NotFoundException("ID not found");
        }
        else {
            if (dto.getId() != null)
                throw new IllegalArgumentException("ID not expected on create");
        }
        
        if (dto.getName() == null || dto.getName().isEmpty())
            errors.addError("name", "Name is required");
        else if (dao.findByName(dto.getName()).isPresent())
            errors.addError("name", "Name already exists");
        
        if (dto.getDescription() == null || dto.getDescription().isEmpty())
            errors.addError("description", "Description is required");
        
        errors.throwIfErrors();
    }
    
    public PermissionDto getById(Long id) {
    	Optional<Permission> permission = dao.findById(id);
    	if (permission.isEmpty()) {
    		throw new NotFoundException("Permission not found");
    	}
    	PermissionDto dto = new PermissionDto();
        BeanUtils.copyProperties(permission, dto);
        return dto;
    }
    
    public PermissionDto getByName(String name) {
    	Optional<Permission> permission = dao.findByName(name);
    	if (permission.isEmpty()) {
    		throw new NotFoundException("Permission not found");
    	}
    	PermissionDto dto = new PermissionDto();
        BeanUtils.copyProperties(permission, dto);
        return dto;
    }
        
    public void deleteById(Long id) {
    	if (!dao.existsById(id)) {
    		throw new NotFoundException("Permission not found");
    	}
    	dao.deleteById(id);
    }
        
    public void deleteByName(String name) {
    	Optional<Permission> permission = dao.findByName(name);
    	if (permission.isEmpty()) {
    		throw new NotFoundException("Permission not found");
    	}
    	dao.delete(permission.get());
    }
}
