package ro.fortech.mailer.service;

import ro.fortech.mailer.service.dto.CampaignDTO;
import java.util.List;

/**
 * Service Interface for managing Campaign.
 */
public interface CampaignService {

    /**
     * Save a campaign.
     *
     * @param campaignDTO the entity to save
     * @return the persisted entity
     */
    CampaignDTO save(CampaignDTO campaignDTO);

    /**
     *  Get all the campaigns.
     *
     *  @return the list of entities
     */
    List<CampaignDTO> findAll();

    List<CampaignDTO> findByUserIsCurrentUser();

    /**
     *  Get the "id" campaign.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CampaignDTO findOne(Long id);

    /**
     *  Delete the "id" campaign.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
