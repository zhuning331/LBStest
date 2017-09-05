package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.PolygonRegion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PolygonRegion entity.
 */
public interface PolygonRegionSearchRepository extends ElasticsearchRepository<PolygonRegion, Long> {
}
