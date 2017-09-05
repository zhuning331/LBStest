package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.PolygonRegion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PolygonRegion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolygonRegionRepository extends JpaRepository<PolygonRegion, Long> {

}
