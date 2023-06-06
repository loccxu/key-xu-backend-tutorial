package com.simoncomputing.sample.dao;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.simoncomputing.sample.domain.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	Optional<User> findByName(String name);
	Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
	Optional<User> findByFirstnameOrderByLastnameIgnoreCase(String firstName);
	Optional<User> findByFirstnameAndLastnameIgnoreCase(String firstName, String lastName);
    Optional<User> findByLastnameOrderByFirstnameIgnoreCase(String lastName);
}
