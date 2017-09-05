package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.POIHistoricalLocation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the POIHistoricalLocation entity.
 */
public interface POIHistoricalLocationSearchRepository extends ElasticsearchRepository<POIHistoricalLocation, Long> {
}
