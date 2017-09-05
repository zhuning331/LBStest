package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.BindingPOI;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BindingPOI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BindingPOIRepository extends JpaRepository<BindingPOI, Long> {

}
