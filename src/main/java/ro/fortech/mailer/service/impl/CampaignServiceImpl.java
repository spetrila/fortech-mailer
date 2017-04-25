package ro.fortech.mailer.service.impl;

import ro.fortech.mailer.service.CampaignService;
import ro.fortech.mailer.domain.Campaign;
import ro.fortech.mailer.repository.CampaignRepository;
import ro.fortech.mailer.service.dto.CampaignDTO;
import ro.fortech.mailer.service.mapper.CampaignMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Campaign.
 */
@Service
@Transactional
public class CampaignServiceImpl implements CampaignService{

    private final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

    private final CampaignRepository campaignRepository;

    private final CampaignMapper campaignMapper;

    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignMapper campaignMapper) {
        this.campaignRepository = campaignRepository;
        this.campaignMapper = campaignMapper;
    }

    /**
     * Save a campaign.
     *
     * @param campaignDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CampaignDTO save(CampaignDTO campaignDTO) {
        log.debug("Request to save Campaign : {}", campaignDTO);
        Campaign campaign = campaignMapper.campaignDTOToCampaign(campaignDTO);
        campaign = campaignRepository.save(campaign);
        CampaignDTO result = campaignMapper.campaignToCampaignDTO(campaign);
        return result;
    }

    /**
     *  Get all the campaigns.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CampaignDTO> findAll() {
        log.debug("Request to get all Campaigns");
        List<CampaignDTO> result = campaignRepository.findAllWithEagerRelationships().stream()
            .map(campaignMapper::campaignToCampaignDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignDTO> findByUserIsCurrentUser() {
        log.debug("Request to get all Campaigns");
        List<CampaignDTO> result = campaignRepository.findByUserIsCurrentUser().stream()
            .map(campaignMapper::campaignToCampaignDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one campaign by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CampaignDTO findOne(Long id) {
        log.debug("Request to get Campaign : {}", id);
        Campaign campaign = campaignRepository.findOneWithEagerRelationships(id);
        CampaignDTO campaignDTO = campaignMapper.campaignToCampaignDTO(campaign);
        return campaignDTO;
    }

    /**
     *  Delete the  campaign by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Campaign : {}", id);
        campaignRepository.delete(id);
    }
}
