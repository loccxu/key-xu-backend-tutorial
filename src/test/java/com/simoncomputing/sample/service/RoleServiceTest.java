package com.simoncomputing.sample.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.simoncomputing.sample.Application;
import com.simoncomputing.sample.dto.PermissionDto;
import com.simoncomputing.sample.dto.RoleDto;

@Transactional
@RunWith(SpringRunner.class)  
@SpringBootTest(classes = Application.class)
public class RoleServiceTest {

    @Autowired
    RoleService roleService;
    
    @Autowired
    PermissionService permissionService;
    
    private PermissionDto loginPermission;
    private PermissionDto viewProfilePermission;
    
    @Before
    public void setup() {
        PermissionDto dto = new PermissionDto();
        dto.setName("LOGIN");
        dto.setDescription("Can login");
        loginPermission = permissionService.create(dto);
        
        dto = new PermissionDto();
        dto.setName("VIEW_PROFILE");
        dto.setDescription("Can view user profile");
        viewProfilePermission = permissionService.create(dto);
    }

    private RoleDto createRoleDto() {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("user");
        roleDto.setDescription("Standard user");
        roleDto.getPermissionIds().add(loginPermission.getId());
        roleDto.getPermissionIds().add(viewProfilePermission.getId());
        
        return roleDto;
    }

    private static void assertMatches(RoleDto expected, RoleDto actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPermissionIds(), actual.getPermissionIds());
    }
    
    // ...

    @Test
    public void update_OnSuccess_RecordMatches() {
        // Arrange
        RoleDto dto = createRoleDto();
        dto = roleService.create(dto);

        // Act
        dto.setName("login-role");
        dto.setDescription("User who can login");
        dto.getPermissionIds().remove(viewProfilePermission.getId());
        RoleDto result = roleService.update(dto);
        
        // Assert
        assertMatches(dto, result);
    }

    // ...
}