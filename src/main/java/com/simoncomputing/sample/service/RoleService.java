package com.simoncomputing.sample.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simoncomputing.sample.dao.PermissionDao;
import com.simoncomputing.sample.domain.Permission;
import com.simoncomputing.sample.domain.Role;
import com.simoncomputing.sample.domain.User;
import com.simoncomputing.sample.dao.RoleDao;
import com.simoncomputing.sample.dto.EditErrors;
import com.simoncomputing.sample.dto.RoleDto;
import com.simoncomputing.sample.exception.NotFoundException;


@Service
@Transactional
public class RoleService {

    @Autowired
    RoleDao roleDao;

    // We need this to look up Permissions
    @Autowired
    PermissionDao permissionDao;

    public RoleDto create(RoleDto dto) {
        validate(dto, false);

        // We can still use copyProperties() to copy the fields that match
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);

        // Use the IDs to look up each Permission
        List<Permission> permissions = (List<Permission>) permissionDao.findAllById(dto.getPermissionIds());

        // Assign the list of Permissions to role
        role.setPermissions(permissions);

        role = roleDao.save(role);

        BeanUtils.copyProperties(role, dto);

        List<Long> permissionIds = role.getPermissions().stream()
            .map(Permission::getId)
            .collect(Collectors.toList());
        dto.setPermissionIds(permissionIds);

        return dto;
    }

    public RoleDto getById(Long id) {
        Optional<Role> roleDto = roleDao.findById(id);
        if (roleDto == null) {
            throw new NotFoundException("Role not found");
        }
        RoleDto dto = new RoleDto();
        BeanUtils.copyProperties(roleDto, dto);
        return dto;
    }

    public RoleDto getByName(String name) {
        Optional<Role> role = roleDao.findByName(name);
        if (role == null) {
            throw new NotFoundException("Permission not found");
        }
        RoleDto dto = new RoleDto();
        BeanUtils.copyProperties(role, dto);
        return dto;
    }

    public void deleteById(Long id) {
        if (!roleDao.existsById(id)) {
            throw new NotFoundException("Permission not found");
        }
        roleDao.deleteById(id);
    }

    public void deleteByName(String name) {
        Optional<Role> role = roleDao.findByName(name);
        if (!role.isPresent()) {
            throw new NotFoundException("Permission not found");
        }
        roleDao.delete(role.get());
    }

    private void validate(RoleDto dto, boolean update) {
        EditErrors errors = new EditErrors();
        
        if (update) {
            if (dto.getId() == null)
                throw new IllegalArgumentException("ID expected on update");

            if (!roleDao.existsById(dto.getId()))
                throw new NotFoundException("ID not found");
        }
        else {
            if (dto.getId() != null) throw new IllegalArgumentException("ID not expected on create");
        }
        
        if (dto.getName() == null || dto.getName().isEmpty())
            errors.addError("name", "Name is required");
        else if (roleDao.findByName(dto.getName()).isPresent())
            errors.addError("name", "Name already exists");
        
        if (dto.getDescription() == null || dto.getDescription().isEmpty())
            errors.addError("description", "Description is required");
        
        if (dto.getPermissionIds() == null || dto.getPermissionIds().isEmpty())
        	errors.addError("permission", "Permission ID\'s are required");
        
        errors.throwIfErrors();
    }

	public RoleDto update(RoleDto dto) {
        validate(dto, true);

        Role role = new Role();
        BeanUtils.copyProperties(dto, role);

        role = roleDao.save(role);
        BeanUtils.copyProperties(role, dto);

        return dto;
	}
}
