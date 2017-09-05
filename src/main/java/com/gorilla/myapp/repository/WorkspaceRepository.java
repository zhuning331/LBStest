package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.Workspace;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Workspace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    @Query("select workspace from Workspace workspace where workspace.owner.login = ?#{principal.username}")
    List<Workspace> findByOwnerIsCurrentUser();

}
