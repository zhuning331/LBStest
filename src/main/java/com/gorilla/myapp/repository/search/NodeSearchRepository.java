package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.Node;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Node entity.
 */
public interface NodeSearchRepository extends ElasticsearchRepository<Node, Long> {
}
