package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.ServiceConfig;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ServiceConfig entity.
 */
public interface ServiceConfigSearchRepository extends ElasticsearchRepository<ServiceConfig, Long> {
}
