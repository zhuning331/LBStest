package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.POI;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the POI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface POIRepository extends JpaRepository<POI, Long> {
    @Query("select distinct poi from POI poi left join fetch poi.types")
    List<POI> findAllWithEagerRelationships();

    @Query("select poi from POI poi left join fetch poi.types where poi.id =:id")
    POI findOneWithEagerRelationships(@Param("id") Long id);

}
