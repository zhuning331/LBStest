package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.WebType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WebType entity.
 */
public interface WebTypeSearchRepository extends ElasticsearchRepository<WebType, Long> {
}
