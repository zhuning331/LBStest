package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.LocationUpdate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LocationUpdate entity.
 */
public interface LocationUpdateSearchRepository extends ElasticsearchRepository<LocationUpdate, Long> {
}
