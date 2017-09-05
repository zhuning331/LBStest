package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.ServiceConfigService;
import com.gorilla.myapp.domain.ServiceConfig;
import com.gorilla.myapp.repository.ServiceConfigRepository;
import com.gorilla.myapp.repository.search.ServiceConfigSearchRepository;
import com.gorilla.myapp.service.dto.ServiceConfigDTO;
import com.gorilla.myapp.service.mapper.ServiceConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ServiceConfig.
 */
@Service
@Transactional
public class ServiceConfigServiceImpl implements ServiceConfigService{

    private final Logger log = LoggerFactory.getLogger(ServiceConfigServiceImpl.class);

    private final ServiceConfigRepository serviceConfigRepository;

    private final ServiceConfigMapper serviceConfigMapper;

    private final ServiceConfigSearchRepository serviceConfigSearchRepository;
    public ServiceConfigServiceImpl(ServiceConfigRepository serviceConfigRepository, ServiceConfigMapper serviceConfigMapper, ServiceConfigSearchRepository serviceConfigSearchRepository) {
        this.serviceConfigRepository = serviceConfigRepository;
        this.serviceConfigMapper = serviceConfigMapper;
        this.serviceConfigSearchRepository = serviceConfigSearchRepository;
    }

    /**
     * Save a serviceConfig.
     *
     * @param serviceConfigDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceConfigDTO save(ServiceConfigDTO serviceConfigDTO) {
        log.debug("Request to save ServiceConfig : {}", serviceConfigDTO);
        ServiceConfig serviceConfig = serviceConfigMapper.toEntity(serviceConfigDTO);
        serviceConfig = serviceConfigRepository.save(serviceConfig);
        ServiceConfigDTO result = serviceConfigMapper.toDto(serviceConfig);
        serviceConfigSearchRepository.save(serviceConfig);
        return result;
    }

    /**
     *  Get all the serviceConfigs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceConfigs");
        return serviceConfigRepository.findAll(pageable)
            .map(serviceConfigMapper::toDto);
    }

    /**
     *  Get one serviceConfig by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ServiceConfigDTO findOne(Long id) {
        log.debug("Request to get ServiceConfig : {}", id);
        ServiceConfig serviceConfig = serviceConfigRepository.findOne(id);
        return serviceConfigMapper.toDto(serviceConfig);
    }

    /**
     *  Delete the  serviceConfig by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceConfig : {}", id);
        serviceConfigRepository.delete(id);
        serviceConfigSearchRepository.delete(id);
    }

    /**
     * Search for the serviceConfig corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceConfigDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceConfigs for query {}", query);
        Page<ServiceConfig> result = serviceConfigSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(serviceConfigMapper::toDto);
    }
}
