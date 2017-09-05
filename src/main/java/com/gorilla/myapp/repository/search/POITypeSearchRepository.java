package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.POIType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the POIType entity.
 */
public interface POITypeSearchRepository extends ElasticsearchRepository<POIType, Long> {
}
