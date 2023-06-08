package com.simoncomputing.sample.dao;

import java.util.Optional;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Query;
import com.simoncomputing.sample.domain.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	Optional<User> findByEmailIgnoreCase(String email);
	@Query("SELECT u FROM User u WHERE u.firstName = ?1 ORDER BY LOWER(u.lastName)")
	List<User> findByFirstNameOrderByLastNameIgnoreCase(String firstName);
	List<User> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
    List<User> findByLastNameIgnoreCaseOrderByFirstName(String lastName);
}