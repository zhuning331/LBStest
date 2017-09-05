package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.Layer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Layer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LayerRepository extends JpaRepository<Layer, Long> {

}
