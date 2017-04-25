package ro.fortech.mailer.service.impl;

import ro.fortech.mailer.service.MailReceiverService;
import ro.fortech.mailer.domain.MailReceiver;
import ro.fortech.mailer.repository.MailReceiverRepository;
import ro.fortech.mailer.service.dto.MailReceiverDTO;
import ro.fortech.mailer.service.mapper.MailReceiverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MailReceiver.
 */
@Service
@Transactional
public class MailReceiverServiceImpl implements MailReceiverService{

    private final Logger log = LoggerFactory.getLogger(MailReceiverServiceImpl.class);
    
    private final MailReceiverRepository mailReceiverRepository;

    private final MailReceiverMapper mailReceiverMapper;

    public MailReceiverServiceImpl(MailReceiverRepository mailReceiverRepository, MailReceiverMapper mailReceiverMapper) {
        this.mailReceiverRepository = mailReceiverRepository;
        this.mailReceiverMapper = mailReceiverMapper;
    }

    /**
     * Save a mailReceiver.
     *
     * @param mailReceiverDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MailReceiverDTO save(MailReceiverDTO mailReceiverDTO) {
        log.debug("Request to save MailReceiver : {}", mailReceiverDTO);
        MailReceiver mailReceiver = mailReceiverMapper.mailReceiverDTOToMailReceiver(mailReceiverDTO);
        mailReceiver = mailReceiverRepository.save(mailReceiver);
        MailReceiverDTO result = mailReceiverMapper.mailReceiverToMailReceiverDTO(mailReceiver);
        return result;
    }

    /**
     *  Get all the mailReceivers.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MailReceiverDTO> findAll() {
        log.debug("Request to get all MailReceivers");
        List<MailReceiverDTO> result = mailReceiverRepository.findAllWithEagerRelationships().stream()
            .map(mailReceiverMapper::mailReceiverToMailReceiverDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one mailReceiver by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MailReceiverDTO findOne(Long id) {
        log.debug("Request to get MailReceiver : {}", id);
        MailReceiver mailReceiver = mailReceiverRepository.findOneWithEagerRelationships(id);
        MailReceiverDTO mailReceiverDTO = mailReceiverMapper.mailReceiverToMailReceiverDTO(mailReceiver);
        return mailReceiverDTO;
    }

    /**
     *  Delete the  mailReceiver by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MailReceiver : {}", id);
        mailReceiverRepository.delete(id);
    }
}
