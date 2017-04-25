package ro.fortech.mailer.service.mapper;

import ro.fortech.mailer.domain.*;
import ro.fortech.mailer.service.dto.CampaignDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Campaign and its DTO CampaignDTO.
 */
@Mapper(componentModel = "spring", uses = {TemplateMapper.class, UserMapper.class, TagMapper.class, })
public interface CampaignMapper {

    @Mapping(source = "template.id", target = "templateId")
    @Mapping(source = "template.name", target = "templateName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CampaignDTO campaignToCampaignDTO(Campaign campaign);

    List<CampaignDTO> campaignsToCampaignDTOs(List<Campaign> campaigns);

    @Mapping(source = "templateId", target = "template")
    @Mapping(source = "userId", target = "user")
    Campaign campaignDTOToCampaign(CampaignDTO campaignDTO);

    List<Campaign> campaignDTOsToCampaigns(List<CampaignDTO> campaignDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Campaign campaignFromId(Long id) {
        if (id == null) {
            return null;
        }
        Campaign campaign = new Campaign();
        campaign.setId(id);
        return campaign;
    }
    

}
