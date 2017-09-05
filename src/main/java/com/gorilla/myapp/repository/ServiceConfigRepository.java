package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.ServiceConfig;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ServiceConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceConfigRepository extends JpaRepository<ServiceConfig, Long> {

}
