package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.RegionType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RegionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionTypeRepository extends JpaRepository<RegionType, Long> {

}
