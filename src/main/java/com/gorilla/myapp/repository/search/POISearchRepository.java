package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.POI;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the POI entity.
 */
public interface POISearchRepository extends ElasticsearchRepository<POI, Long> {
}
