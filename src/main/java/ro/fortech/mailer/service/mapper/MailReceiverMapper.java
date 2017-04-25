package ro.fortech.mailer.service.mapper;

import ro.fortech.mailer.domain.*;
import ro.fortech.mailer.service.dto.MailReceiverDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MailReceiver and its DTO MailReceiverDTO.
 */
@Mapper(componentModel = "spring", uses = {TagMapper.class, })
public interface MailReceiverMapper {

    MailReceiverDTO mailReceiverToMailReceiverDTO(MailReceiver mailReceiver);

    List<MailReceiverDTO> mailReceiversToMailReceiverDTOs(List<MailReceiver> mailReceivers);

    MailReceiver mailReceiverDTOToMailReceiver(MailReceiverDTO mailReceiverDTO);

    List<MailReceiver> mailReceiverDTOsToMailReceivers(List<MailReceiverDTO> mailReceiverDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default MailReceiver mailReceiverFromId(Long id) {
        if (id == null) {
            return null;
        }
        MailReceiver mailReceiver = new MailReceiver();
        mailReceiver.setId(id);
        return mailReceiver;
    }
    

}
