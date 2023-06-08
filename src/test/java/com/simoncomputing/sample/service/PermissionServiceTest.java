package com.simoncomputing.sample.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.simoncomputing.sample.Application;
import com.simoncomputing.sample.dao.PermissionDao;
import com.simoncomputing.sample.dto.PermissionDto;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = Application.class)
@Transactional
public class PermissionServiceTest {
    
    @Autowired
    PermissionService service;

    @Autowired
    PermissionDao dao;
    
    private static PermissionDto createPermissionDto() {
        PermissionDto dto = new PermissionDto();
        dto.setName("LOGIN");
        dto.setDescription("User can login");
        return dto;
    }
    
    private static void assertMatches(PermissionDto expected, PermissionDto actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }
    
    @Test
    public void create_OnSuccess_DtoMatches() { // Arrange
        PermissionDto dto = createPermissionDto();
        
        // Act
        PermissionDto response = service.create(dto);
        
        // Assert
        assertMatches(dto, response);
    }

    @Test (expected = IllegalArgumentException.class)
    public void create_OnIdNotNull_IllegalArgumentException() {
        // Arrange
        PermissionDto dto = createPermissionDto();
        
        // Act
        dto.setId(1L);
        service.create(dto);
        
        // Assert
        // IllegalArgumentException expected
    }
}