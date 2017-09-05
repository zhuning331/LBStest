package com.gorilla.myapp.repository;

import com.gorilla.myapp.domain.Web;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Web entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WebRepository extends JpaRepository<Web, Long> {
    @Query("select distinct web from Web web left join fetch web.types")
    List<Web> findAllWithEagerRelationships();

    @Query("select web from Web web left join fetch web.types where web.id =:id")
    Web findOneWithEagerRelationships(@Param("id") Long id);

}
