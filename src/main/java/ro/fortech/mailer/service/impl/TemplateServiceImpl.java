package ro.fortech.mailer.service.impl;

import ro.fortech.mailer.service.TemplateService;
import ro.fortech.mailer.domain.Template;
import ro.fortech.mailer.repository.TemplateRepository;
import ro.fortech.mailer.service.dto.TemplateDTO;
import ro.fortech.mailer.service.mapper.TemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Template.
 */
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService{

    private final Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);

    private final TemplateRepository templateRepository;

    private final TemplateMapper templateMapper;

    public TemplateServiceImpl(TemplateRepository templateRepository, TemplateMapper templateMapper) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    /**
     * Save a template.
     *
     * @param templateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TemplateDTO save(TemplateDTO templateDTO) {
        log.debug("Request to save Template : {}", templateDTO);
        Template template = templateMapper.templateDTOToTemplate(templateDTO);
        template = templateRepository.save(template);
        TemplateDTO result = templateMapper.templateToTemplateDTO(template);
        return result;
    }

    /**
     *  Get all the templates.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TemplateDTO> findAll() {
        log.debug("Request to get all Templates");
        List<TemplateDTO> result = templateRepository.findAll().stream()
            .map(templateMapper::templateToTemplateDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateDTO> findByUserIsCurrentUser() {
        log.debug("Request to get all Templates for current logged user");
        List<TemplateDTO> result = templateRepository.findByUserIsCurrentUser().stream()
            .map(templateMapper::templateToTemplateDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one template by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TemplateDTO findOne(Long id) {
        log.debug("Request to get Template : {}", id);
        Template template = templateRepository.findOne(id);
        TemplateDTO templateDTO = templateMapper.templateToTemplateDTO(template);
        return templateDTO;
    }

    /**
     *  Delete the  template by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Template : {}", id);
        templateRepository.delete(id);
    }
}
