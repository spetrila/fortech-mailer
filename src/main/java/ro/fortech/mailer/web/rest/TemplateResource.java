package ro.fortech.mailer.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fortech.mailer.security.SecurityUtils;
import ro.fortech.mailer.service.TemplateService;
import ro.fortech.mailer.service.UserService;
import ro.fortech.mailer.service.dto.TemplateDTO;
import ro.fortech.mailer.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Template.
 */
@RestController
@RequestMapping("/api")
public class TemplateResource {

    private final Logger log = LoggerFactory.getLogger(TemplateResource.class);

    private static final String ENTITY_NAME = "template";

    private final TemplateService templateService;

    private final UserService userService;

    public TemplateResource(TemplateService templateService, UserService userService) {
        this.templateService = templateService;
        this.userService = userService;
    }

    /**
     * POST  /templates : Create a new template.
     *
     * @param templateDTO the templateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new templateDTO, or with status 400 (Bad Request) if the template has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/templates")
    @Timed
    public ResponseEntity<TemplateDTO> createTemplate(@Valid @RequestBody TemplateDTO templateDTO) throws URISyntaxException {
        log.debug("REST request to save Template : {}", templateDTO);
        if (templateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new template cannot already have an ID")).body(null);
        }

        String userLogin = SecurityUtils.getCurrentUserLogin();
        templateDTO.setUserLogin(userLogin);
        templateDTO.setUserId(userService.getUserWithAuthoritiesByLogin(userLogin).get().getId());

        TemplateDTO result = templateService.save(templateDTO);
        return ResponseEntity.created(new URI("/api/templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /templates : Updates an existing template.
     *
     * @param templateDTO the templateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated templateDTO,
     * or with status 400 (Bad Request) if the templateDTO is not valid,
     * or with status 500 (Internal Server Error) if the templateDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/templates")
    @Timed
    public ResponseEntity<TemplateDTO> updateTemplate(@Valid @RequestBody TemplateDTO templateDTO) throws URISyntaxException {
        log.debug("REST request to update Template : {}", templateDTO);
        if (templateDTO.getId() == null) {
            return createTemplate(templateDTO);
        }
        TemplateDTO result = templateService.save(templateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, templateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /templates : get all the templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of templates in body
     */
    @GetMapping("/templates")
    @Timed
    public List<TemplateDTO> getAllTemplates() {
        log.debug("REST request to get all Templates");
        return templateService.findByUserIsCurrentUser();
    }

    /**
     * GET  /templates/:id : get the "id" template.
     *
     * @param id the id of the templateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the templateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/templates/{id}")
    @Timed
    public ResponseEntity<TemplateDTO> getTemplate(@PathVariable Long id) {
        log.debug("REST request to get Template : {}", id);
        TemplateDTO templateDTO = templateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(templateDTO));
    }

    /**
     * DELETE  /templates/:id : delete the "id" template.
     *
     * @param id the id of the templateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        log.debug("REST request to delete Template : {}", id);
        templateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
