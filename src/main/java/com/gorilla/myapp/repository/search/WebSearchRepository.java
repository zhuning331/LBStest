package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.Web;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Web entity.
 */
public interface WebSearchRepository extends ElasticsearchRepository<Web, Long> {
}
