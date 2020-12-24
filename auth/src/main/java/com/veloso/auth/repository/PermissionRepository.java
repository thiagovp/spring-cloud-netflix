package com.veloso.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veloso.auth.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
	
	Permission findByDescription(String description);
	
}
