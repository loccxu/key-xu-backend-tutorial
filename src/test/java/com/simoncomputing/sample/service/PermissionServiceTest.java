package com.simoncomputing.sample.service;

import com.simoncomputing.sample.Application;
import com.simoncomputing.sample.dao.PermissionDao;
import com.simoncomputing.sample.domain.Permission;
import com.simoncomputing.sample.dto.PermissionDto;
import com.simoncomputing.sample.exception.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class PermissionServiceTest {

    @Autowired
    PermissionService service;

    @Autowired
    PermissionDao dao;

    //Test Field
    PermissionDto dto;

    private static PermissionDto createPermissionDto() {
        PermissionDto dto = new PermissionDto();
        dto.setName("LOGIN");
        dto.setDescription("User can login");
        return dto;
    }

    @Before
    public void setup() {
        dto = createPermissionDto();
    }

    private static void assertMatches(PermissionDto expected, PermissionDto actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    //Test Create
    @Test
    public void create_OnSuccess_DtoMatches() { // Arrange
        PermissionDto response = service.create(dto);
        assertMatches(dto, response);
    }
    @Test (expected = IllegalArgumentException.class)
    public void create_OnIdNotNull_IllegalArgumentException() {
        dto.setId(1L);
        service.create(dto);
        // Assert
        // IllegalArgumentException expected
    }

    //Test Update
    @Test
    public void testUpdate() {
        //TODO VALIDATION CHECK
        PermissionDto createdDto = service.create(dto);

        dto.setName("new name");
        dto.setDescription("new description");

        PermissionDto updatedDto = service.update(dto);
        assertMatches(updatedDto, dto);
    }

    //Test getById
    @Test
    public void testGetById() {
        PermissionDto createdDto = service.create(dto);
        PermissionDto retrievedDto = service.getById(createdDto.getId());

        try {
            service.getById(createdDto.getId() + 1);
            fail("Expected NotFoundException not thrown");
        }
        catch (NotFoundException error) {
            assertEquals("Permission not found", error.getMessage());
        }

        assertMatches(dto, retrievedDto);
    }

    //Test getByName
    @Test
    public void testGetByName() {
        PermissionDto createdDto = service.create(dto);
        PermissionDto retrievedDto = service.getByName(createdDto.getName());

        try {
            service.getByName(createdDto.getName() + "1");
            fail("Expected NotFoundException not thrown");
        }
        catch (NotFoundException error) {
            assertEquals("Permission not found", error.getMessage());
        }

        assertMatches(dto, retrievedDto);
    }

    @Test
    public void testDeleteById() {
        PermissionDto createdDto = service.create(dto);
        Long currId = createdDto.getId();

        try {
            service.deleteById(createdDto.getId() + 1);
        }
        catch (NotFoundException error) {
            assertEquals("Permission not found", error.getMessage());
        }

        service.deleteById(currId);
        assertFalse(dao.existsById(currId));
    }

    @Test
    public void testDeleteByName() {
        PermissionDto createdDto = service.create(dto);
        String currName = createdDto.getName();

        try {
            service.deleteByName(currName + "1");
        }
        catch (NotFoundException error) {
            assertEquals("Permission not found", error.getMessage());
        }
        service.deleteByName(currName);
        assertFalse(dao.existsByName(createdDto.getName()));
    }
}