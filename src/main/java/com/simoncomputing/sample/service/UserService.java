package com.simoncomputing.sample.service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simoncomputing.sample.dao.RoleDao;
import com.simoncomputing.sample.dao.UserDao;

import com.simoncomputing.sample.dto.UserDto;
import com.simoncomputing.sample.dto.EditErrors;
import com.simoncomputing.sample.dto.PagingResponse;

import com.simoncomputing.sample.domain.User;
import com.simoncomputing.sample.domain.Role;
import com.simoncomputing.sample.domain.Permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.simoncomputing.sample.exception.NotFoundException;


@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public UserDto create(UserDto dto) {
        validate(dto, false);

        User user = new User();

        List<Role> roles = (List<Role>) roleDao.findAllById(dto.getRoleIDs());
        user.setRoles(roles);

        user = userDao.save(user);

        BeanUtils.copyProperties(dto, user);

        return dto;
    }

    public UserDto update(UserDto dto) {
        validate(dto, true);

        User user = new User();
        BeanUtils.copyProperties(dto, user);

        user = userDao.save(user);
        BeanUtils.copyProperties(user, dto);

        return dto;
    }

    public UserDto getById(Long id) {
        Optional<User> user = userDao.findById(id);
        if (user == null) {
            throw new NotFoundException("Permission not found");
        }
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public UserDto getByEmail(String email) {
        Optional<User> user = userDao.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new NotFoundException("Permission not found");
        }
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public void deleteById(Long id) {
        if (!userDao.existsById(id)) {
            throw new NotFoundException("Permission not found");
        }
        userDao.deleteById(id);
    }   

    /**
     * Returns a list of string permission names for the specified user.
     * Duplicates are removed.
     * 
     * @param id of user to return list of permissions for.
     * @return List of String permission names.
     */
    public List<String> getPermissions(Long id) {
    	Set<Permission> permissionCollection = new HashSet<Permission>();

    	Optional<User> userOptional = userDao.findById(id);
    	
    	if (!userOptional.isPresent()) {
    		throw new NotFoundException("Permission not found");
    	}
    	
    	User user = userOptional.get();
    	List<Role> userRoles = user.getRoles();
    	for (int k = 0; k < userRoles.size(); k++) {
    		Role temp = userRoles.get(k);
    		List<Permission> tempPermissions = temp.getPermissions();
    		permissionCollection.addAll(tempPermissions);
    	}
    	
    	List<String> permissionStringCollection = new ArrayList<String>();
    	for (Permission curr : permissionCollection) {
    		String currString = curr.toString();
    		permissionStringCollection.add(currString);
    	}
    	
    	return permissionStringCollection;
    }

    public List<UserDto> getByFirstName(String firstName) {
    	List<User> user = userDao.findByFirstNameOrderByLastNameIgnoreCase(firstName);
    	if (user == null) {
    		throw new NotFoundException("Permission not found");
    	}
    	List<UserDto> dto = new ArrayList<UserDto>();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public List<UserDto> getByLastName(String lastName) {
     	List<User> user = userDao.findByLastNameIgnoreCaseOrderByFirstName(lastName);
    	if (user == null) {
    		throw new NotFoundException("Permission not found");
    	}
    	List<UserDto> dto = new ArrayList<UserDto>();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public List<UserDto> getByFirstNameAndLastName(String firstName, String lastName) {
     	List<User> user = userDao.findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    	if (user == null) {
    		throw new NotFoundException("Permission not found");
    	}
    	List<UserDto> dto = new ArrayList<UserDto>();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    private void validate(UserDto dto, boolean update) {
        EditErrors errors = new EditErrors();
        
        if (update) {
            if (dto.getId() == null)
                throw new IllegalArgumentException("ID expected on update");

            if (!userDao.existsById(dto.getId()))
                throw new NotFoundException("ID not found");
        }
        else {
            if (dto.getId() != null) throw new IllegalArgumentException("ID not expected on create");
        }
        
        if (dto.getFirstName() == null || dto.getFirstName().isEmpty())
            errors.addError("firstName", "First name is required");
        else if (!userDao.findByFirstNameOrderByLastNameIgnoreCase(dto.getFirstName()).isEmpty())
            errors.addError("firstName", "First name already exists");
        
        if (dto.getLastName() == null || dto.getLastName().isEmpty())
            errors.addError("firstName", "Last name is required");
        else if (!userDao.findByLastNameIgnoreCaseOrderByFirstName(dto.getLastName()).isEmpty())
            errors.addError("lastName", "Last name already exists");
        
        if (dto.getDescription() == null || dto.getDescription().isEmpty())
            errors.addError("description", "Description is required");
        
        if (dto.getPassword() == null || dto.getPassword().isEmpty())
            errors.addError("password", "Password is required");
        
        if (dto.getRoleIDs() == null || dto.getRoleIDs().isEmpty()) {
        	errors.addError("role", "Role ID\'s are required");
        }
        
        errors.throwIfErrors();
    }

    public PagingResponse<UserDto> getUserPage(Integer page, Integer size, String sortFld ) {
        
        // Create a Pageable with out paging information
        Pageable paging = PageRequest.of(page, size, Sort.by(sortFld));

        // Pass it to the DAO and retrieve our results in a Page
        Page<User> pagedResult = userDao.findAll(paging);
        
        // Convert the Users in our page to UserDto
        List<UserDto> result = new ArrayList<>();
        if(pagedResult.hasContent()) {
            for (User user : pagedResult.getContent()) {
                result.add(toUserDto(user));
            }
        }
        
        // Create a PagingResponse DTO to send back to the client
        PagingResponse<UserDto> response = new PagingResponse<UserDto>();

        response.setList(result);
        response.setPage(pagedResult.getNumber());
        response.setTotal(pagedResult.getTotalElements());
        
        return response; 
    }
    

	private UserDto toUserDto(User user) {

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDto, user);
		return userDto;
	}
}
