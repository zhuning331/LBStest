package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.RegularRegion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RegularRegion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegularRegionRepository extends JpaRepository<RegularRegion, Long> {

}
