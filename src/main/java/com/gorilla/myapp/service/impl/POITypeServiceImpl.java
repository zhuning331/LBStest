package com.gorilla.myapp.service.impl;

import com.gorilla.myapp.service.POITypeService;
import com.gorilla.myapp.domain.POIType;
import com.gorilla.myapp.repository.POITypeRepository;
import com.gorilla.myapp.repository.search.POITypeSearchRepository;
import com.gorilla.myapp.service.dto.POITypeDTO;
import com.gorilla.myapp.service.mapper.POITypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing POIType.
 */
@Service
@Transactional
public class POITypeServiceImpl implements POITypeService{

    private final Logger log = LoggerFactory.getLogger(POITypeServiceImpl.class);

    private final POITypeRepository pOITypeRepository;

    private final POITypeMapper pOITypeMapper;

    private final POITypeSearchRepository pOITypeSearchRepository;
    public POITypeServiceImpl(POITypeRepository pOITypeRepository, POITypeMapper pOITypeMapper, POITypeSearchRepository pOITypeSearchRepository) {
        this.pOITypeRepository = pOITypeRepository;
        this.pOITypeMapper = pOITypeMapper;
        this.pOITypeSearchRepository = pOITypeSearchRepository;
    }

    /**
     * Save a pOIType.
     *
     * @param pOITypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public POITypeDTO save(POITypeDTO pOITypeDTO) {
        log.debug("Request to save POIType : {}", pOITypeDTO);
        POIType pOIType = pOITypeMapper.toEntity(pOITypeDTO);
        pOIType = pOITypeRepository.save(pOIType);
        POITypeDTO result = pOITypeMapper.toDto(pOIType);
        pOITypeSearchRepository.save(pOIType);
        return result;
    }

    /**
     *  Get all the pOITypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<POITypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all POITypes");
        return pOITypeRepository.findAll(pageable)
            .map(pOITypeMapper::toDto);
    }

    /**
     *  Get one pOIType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public POITypeDTO findOne(Long id) {
        log.debug("Request to get POIType : {}", id);
        POIType pOIType = pOITypeRepository.findOne(id);
        return pOITypeMapper.toDto(pOIType);
    }

    /**
     *  Delete the  pOIType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete POIType : {}", id);
        pOITypeRepository.delete(id);
        pOITypeSearchRepository.delete(id);
    }

    /**
     * Search for the pOIType corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<POITypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of POITypes for query {}", query);
        Page<POIType> result = pOITypeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(pOITypeMapper::toDto);
    }
}
