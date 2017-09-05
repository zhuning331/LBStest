package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.WorkspaceConfig;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WorkspaceConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkspaceConfigRepository extends JpaRepository<WorkspaceConfig, Long> {

}
