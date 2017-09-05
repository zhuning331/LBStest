package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.POIType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the POIType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface POITypeRepository extends JpaRepository<POIType, Long> {

}
