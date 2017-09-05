package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.Region;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Region entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query("select distinct region from Region region left join fetch region.types")
    List<Region> findAllWithEagerRelationships();

    @Query("select region from Region region left join fetch region.types where region.id =:id")
    Region findOneWithEagerRelationships(@Param("id") Long id);

}
