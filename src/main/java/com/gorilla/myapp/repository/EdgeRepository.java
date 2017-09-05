package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.Edge;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Edge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {
    @Query("select distinct edge from Edge edge left join fetch edge.types")
    List<Edge> findAllWithEagerRelationships();

    @Query("select edge from Edge edge left join fetch edge.types where edge.id =:id")
    Edge findOneWithEagerRelationships(@Param("id") Long id);

}
