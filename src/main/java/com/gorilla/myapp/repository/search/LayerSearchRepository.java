package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.Layer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Layer entity.
 */
public interface LayerSearchRepository extends ElasticsearchRepository<Layer, Long> {
}
