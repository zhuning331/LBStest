package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.RegularRegion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RegularRegion entity.
 */
public interface RegularRegionSearchRepository extends ElasticsearchRepository<RegularRegion, Long> {
}
