package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.RegionType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RegionType entity.
 */
public interface RegionTypeSearchRepository extends ElasticsearchRepository<RegionType, Long> {
}
