package ro.fortech.mailer.service;

import ro.fortech.mailer.service.dto.MailReceiverDTO;
import java.util.List;

/**
 * Service Interface for managing MailReceiver.
 */
public interface MailReceiverService {

    /**
     * Save a mailReceiver.
     *
     * @param mailReceiverDTO the entity to save
     * @return the persisted entity
     */
    MailReceiverDTO save(MailReceiverDTO mailReceiverDTO);

    /**
     *  Get all the mailReceivers.
     *  
     *  @return the list of entities
     */
    List<MailReceiverDTO> findAll();

    /**
     *  Get the "id" mailReceiver.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MailReceiverDTO findOne(Long id);

    /**
     *  Delete the "id" mailReceiver.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
