package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.Map;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Map entity.
 */
public interface MapSearchRepository extends ElasticsearchRepository<Map, Long> {
}
