package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.BindingPOI;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BindingPOI entity.
 */
public interface BindingPOISearchRepository extends ElasticsearchRepository<BindingPOI, Long> {
}
