package ro.fortech.mailer.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fortech.mailer.security.SecurityUtils;
import ro.fortech.mailer.service.*;
import ro.fortech.mailer.service.dto.CampaignDTO;
import ro.fortech.mailer.service.util.RandomUtil;
import ro.fortech.mailer.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Campaign.
 */
@RestController
@RequestMapping("/api")
public class CampaignResource {

    private final Logger log = LoggerFactory.getLogger(CampaignResource.class);

    private static final String ENTITY_NAME = "campaign";

    private final CampaignService campaignService;

    private final TemplateService templateService;

    private final MailService mailService;

    private final MailReceiverService receiverService;

    private final UserService userService;

    public CampaignResource(CampaignService campaignService, TemplateService templateService,
                            MailReceiverService receiverService, MailService mailService, UserService userService) {
        this.campaignService = campaignService;
        this.mailService = mailService;
        this.receiverService = receiverService;
        this.templateService = templateService;
        this.userService = userService;
    }

    /**
     * POST  /campaigns : Create a new campaign.
     *
     * @param campaignDTO the campaignDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campaignDTO, or with status 400 (Bad Request) if the campaign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/campaigns")
    @Timed
    public ResponseEntity<CampaignDTO> createCampaign(@Valid @RequestBody CampaignDTO campaignDTO) throws URISyntaxException {
        log.debug("REST request to save Campaign : {}", campaignDTO);
        if (campaignDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new campaign cannot already have an ID")).body(null);
        }

        String userLogin = SecurityUtils.getCurrentUserLogin();
        campaignDTO.setUserLogin(userLogin);
        campaignDTO.setUserId(userService.getUserWithAuthoritiesByLogin(userLogin).get().getId());

        CampaignDTO result = campaignService.save(campaignDTO);
        return ResponseEntity.created(new URI("/api/campaigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/campaigns/send")
    @Timed
    public ResponseEntity<CampaignDTO> sendCampaign(@Valid @RequestBody CampaignDTO campaignDTO) throws URISyntaxException {
        log.debug("REST request to update Campaign : {}", campaignDTO);
        receiverService.findAll().stream()
            .filter(receiver -> campaignDTO.getTags().stream().anyMatch(tag -> receiver.getTags().contains(tag)))
            .forEach(receiver -> mailService.sendEmail(receiver.getEmailAddress(), campaignDTO.getSubject(), RandomUtil.parseMailContent(templateService.findOne(campaignDTO.getTemplateId()).getContent(), receiver), true, true));

        return ResponseEntity.ok()
            .headers(HeaderUtil.createCampaignSentAlert(ENTITY_NAME, campaignDTO.getId().toString()))
            .body(campaignDTO);
    }

    /**
     * PUT  /campaigns : Updates an existing campaign.
     *
     * @param campaignDTO the campaignDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campaignDTO,
     * or with status 400 (Bad Request) if the campaignDTO is not valid,
     * or with status 500 (Internal Server Error) if the campaignDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/campaigns")
    @Timed
    public ResponseEntity<CampaignDTO> updateCampaign(@Valid @RequestBody CampaignDTO campaignDTO) throws URISyntaxException {
        log.debug("REST request to update Campaign : {}", campaignDTO);
        System.out.println("TEST update");
        if (campaignDTO.getId() == null) {
            return createCampaign(campaignDTO);
        }
        CampaignDTO result = campaignService.save(campaignDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, campaignDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /campaigns : get all the campaigns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of campaigns in body
     */
    @GetMapping("/campaigns")
    @Timed
    public List<CampaignDTO> getAllCampaigns() {
        log.debug("REST request to get all Campaigns for currently logged user");
        return campaignService.findByUserIsCurrentUser();
    }



    /**
     * GET  /campaigns/:id : get the "id" campaign.
     *
     * @param id the id of the campaignDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campaignDTO, or with status 404 (Not Found)
     */
    @GetMapping("/campaigns/{id}")
    @Timed
    public ResponseEntity<CampaignDTO> getCampaign(@PathVariable Long id) {
        log.debug("REST request to get Campaign : {}", id);
        CampaignDTO campaignDTO = campaignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(campaignDTO));
    }

    /**
     * DELETE  /campaigns/:id : delete the "id" campaign.
     *
     * @param id the id of the campaignDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/campaigns/{id}")
    @Timed
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        log.debug("REST request to delete Campaign : {}", id);
        campaignService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
