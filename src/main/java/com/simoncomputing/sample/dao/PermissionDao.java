package com.simoncomputing.sample.dao;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.simoncomputing.sample.domain.Permission;

public interface PermissionDao extends PagingAndSortingRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
    Boolean existsByName(String name);
}