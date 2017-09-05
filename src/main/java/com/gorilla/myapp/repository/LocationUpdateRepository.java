package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.LocationUpdate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LocationUpdate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationUpdateRepository extends JpaRepository<LocationUpdate, Long> {

}
