package com.gorilla.myapp.repository.search;

import com.gorilla.myapp.domain.WorkspaceConfig;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WorkspaceConfig entity.
 */
public interface WorkspaceConfigSearchRepository extends ElasticsearchRepository<WorkspaceConfig, Long> {
}
