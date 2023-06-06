package com.simoncomputing.sample.dao;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.simoncomputing.sample.domain.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long> {
    Optional<Role> findByName(String name);
}