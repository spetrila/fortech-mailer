package ro.fortech.mailer.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.fortech.mailer.service.MailReceiverService;
import ro.fortech.mailer.web.rest.util.HeaderUtil;
import ro.fortech.mailer.service.dto.MailReceiverDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing MailReceiver.
 */
@RestController
@RequestMapping("/api")
public class MailReceiverResource {

    private final Logger log = LoggerFactory.getLogger(MailReceiverResource.class);

    private static final String ENTITY_NAME = "mailReceiver";
        
    private final MailReceiverService mailReceiverService;

    public MailReceiverResource(MailReceiverService mailReceiverService) {
        this.mailReceiverService = mailReceiverService;
    }

    /**
     * POST  /mail-receivers : Create a new mailReceiver.
     *
     * @param mailReceiverDTO the mailReceiverDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mailReceiverDTO, or with status 400 (Bad Request) if the mailReceiver has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mail-receivers")
    @Timed
    public ResponseEntity<MailReceiverDTO> createMailReceiver(@Valid @RequestBody MailReceiverDTO mailReceiverDTO) throws URISyntaxException {
        log.debug("REST request to save MailReceiver : {}", mailReceiverDTO);
        if (mailReceiverDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mailReceiver cannot already have an ID")).body(null);
        }
        MailReceiverDTO result = mailReceiverService.save(mailReceiverDTO);
        return ResponseEntity.created(new URI("/api/mail-receivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mail-receivers : Updates an existing mailReceiver.
     *
     * @param mailReceiverDTO the mailReceiverDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mailReceiverDTO,
     * or with status 400 (Bad Request) if the mailReceiverDTO is not valid,
     * or with status 500 (Internal Server Error) if the mailReceiverDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mail-receivers")
    @Timed
    public ResponseEntity<MailReceiverDTO> updateMailReceiver(@Valid @RequestBody MailReceiverDTO mailReceiverDTO) throws URISyntaxException {
        log.debug("REST request to update MailReceiver : {}", mailReceiverDTO);
        if (mailReceiverDTO.getId() == null) {
            return createMailReceiver(mailReceiverDTO);
        }
        MailReceiverDTO result = mailReceiverService.save(mailReceiverDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mailReceiverDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mail-receivers : get all the mailReceivers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mailReceivers in body
     */
    @GetMapping("/mail-receivers")
    @Timed
    public List<MailReceiverDTO> getAllMailReceivers() {
        log.debug("REST request to get all MailReceivers");
        return mailReceiverService.findAll();
    }

    /**
     * GET  /mail-receivers/:id : get the "id" mailReceiver.
     *
     * @param id the id of the mailReceiverDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mailReceiverDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mail-receivers/{id}")
    @Timed
    public ResponseEntity<MailReceiverDTO> getMailReceiver(@PathVariable Long id) {
        log.debug("REST request to get MailReceiver : {}", id);
        MailReceiverDTO mailReceiverDTO = mailReceiverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mailReceiverDTO));
    }

    /**
     * DELETE  /mail-receivers/:id : delete the "id" mailReceiver.
     *
     * @param id the id of the mailReceiverDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mail-receivers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMailReceiver(@PathVariable Long id) {
        log.debug("REST request to delete MailReceiver : {}", id);
        mailReceiverService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
