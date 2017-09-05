package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.Edge;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Edge entity.
 */
public interface EdgeSearchRepository extends ElasticsearchRepository<Edge, Long> {
}
