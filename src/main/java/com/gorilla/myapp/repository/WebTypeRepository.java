package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.WebType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WebType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WebTypeRepository extends JpaRepository<WebType, Long> {

}
