package com.simoncomputing.sample.dao;

import java.util.Optional;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.simoncomputing.sample.domain.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
	List<User> findByFirstnameOrderByLastnameIgnoreCase(String firstName);
	List<User> findByFirstnameAndLastnameIgnoreCase(String firstName, String lastName);
    List<User> findByLastnameOrderByFirstnameIgnoreCase(String lastName);
}
