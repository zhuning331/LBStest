package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.Map;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Map entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MapRepository extends JpaRepository<Map, Long> {
    @Query("select distinct map from Map map left join fetch map.layers left join fetch map.webs left join fetch map.regions")
    List<Map> findAllWithEagerRelationships();

    @Query("select map from Map map left join fetch map.layers left join fetch map.webs left join fetch map.regions where map.id =:id")
    Map findOneWithEagerRelationships(@Param("id") Long id);

}
