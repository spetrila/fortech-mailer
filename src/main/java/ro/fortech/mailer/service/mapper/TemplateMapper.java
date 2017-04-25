package ro.fortech.mailer.service.mapper;

import ro.fortech.mailer.domain.*;
import ro.fortech.mailer.service.dto.TemplateDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Template and its DTO TemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface TemplateMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    TemplateDTO templateToTemplateDTO(Template template);

    List<TemplateDTO> templatesToTemplateDTOs(List<Template> templates);

    @Mapping(source = "userId", target = "user")
    Template templateDTOToTemplate(TemplateDTO templateDTO);

    List<Template> templateDTOsToTemplates(List<TemplateDTO> templateDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Template templateFromId(Long id) {
        if (id == null) {
            return null;
        }
        Template template = new Template();
        template.setId(id);
        return template;
    }
    

}
