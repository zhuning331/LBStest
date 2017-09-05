package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.EdgeType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EdgeType entity.
 */
public interface EdgeTypeSearchRepository extends ElasticsearchRepository<EdgeType, Long> {
}
