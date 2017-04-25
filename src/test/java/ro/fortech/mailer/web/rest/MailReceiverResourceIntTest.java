package ro.fortech.mailer.web.rest;

import ro.fortech.mailer.FortechMailerApp;

import ro.fortech.mailer.domain.MailReceiver;
import ro.fortech.mailer.repository.MailReceiverRepository;
import ro.fortech.mailer.service.MailReceiverService;
import ro.fortech.mailer.service.dto.MailReceiverDTO;
import ro.fortech.mailer.service.mapper.MailReceiverMapper;
import ro.fortech.mailer.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MailReceiverResource REST controller.
 *
 * @see MailReceiverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FortechMailerApp.class)
public class MailReceiverResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private MailReceiverRepository mailReceiverRepository;

    @Autowired
    private MailReceiverMapper mailReceiverMapper;

    @Autowired
    private MailReceiverService mailReceiverService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMailReceiverMockMvc;

    private MailReceiver mailReceiver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MailReceiverResource mailReceiverResource = new MailReceiverResource(mailReceiverService);
        this.restMailReceiverMockMvc = MockMvcBuilders.standaloneSetup(mailReceiverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MailReceiver createEntity(EntityManager em) {
        MailReceiver mailReceiver = new MailReceiver()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .emailAddress(DEFAULT_EMAIL_ADDRESS);
        return mailReceiver;
    }

    @Before
    public void initTest() {
        mailReceiver = createEntity(em);
    }

    @Test
    @Transactional
    public void createMailReceiver() throws Exception {
        int databaseSizeBeforeCreate = mailReceiverRepository.findAll().size();

        // Create the MailReceiver
        MailReceiverDTO mailReceiverDTO = mailReceiverMapper.mailReceiverToMailReceiverDTO(mailReceiver);
        restMailReceiverMockMvc.perform(post("/api/mail-receivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mailReceiverDTO)))
            .andExpect(status().isCreated());

        // Validate the MailReceiver in the database
        List<MailReceiver> mailReceiverList = mailReceiverRepository.findAll();
        assertThat(mailReceiverList).hasSize(databaseSizeBeforeCreate + 1);
        MailReceiver testMailReceiver = mailReceiverList.get(mailReceiverList.size() - 1);
        assertThat(testMailReceiver.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMailReceiver.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMailReceiver.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void createMailReceiverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mailReceiverRepository.findAll().size();

        // Create the MailReceiver with an existing ID
        mailReceiver.setId(1L);
        MailReceiverDTO mailReceiverDTO = mailReceiverMapper.mailReceiverToMailReceiverDTO(mailReceiver);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMailReceiverMockMvc.perform(post("/api/mail-receivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mailReceiverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MailReceiver> mailReceiverList = mailReceiverRepository.findAll();
        assertThat(mailReceiverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mailReceiverRepository.findAll().size();
        // set the field null
        mailReceiver.setName(null);

        // Create the MailReceiver, which fails.
        MailReceiverDTO mailReceiverDTO = mailReceiverMapper.mailReceiverToMailReceiverDTO(mailReceiver);

        restMailReceiverMockMvc.perform(post("/api/mail-receivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mailReceiverDTO)))
            .andExpect(status().isBadRequest());

        List<MailReceiver> mailReceiverList = mailReceiverRepository.findAll();
        assertThat(mailReceiverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = mailReceiverRepository.findAll().size();
        // set the field null
        mailReceiver.setEmailAddress(null);

        // Create the MailReceiver, which fails.
        MailReceiverDTO mailReceiverDTO = mailReceiverMapper.mailReceiverToMailReceiverDTO(mailReceiver);

        restMailReceiverMockMvc.perform(post("/api/mail-receivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mailReceiverDTO)))
            .andExpect(status().isBadRequest());

        List<MailReceiver> mailReceiverList = mailReceiverRepository.findAll();
        assertThat(mailReceiverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMailReceivers() throws Exception {
        // Initialize the database
        mailReceiverRepository.saveAndFlush(mailReceiver);

        // Get all the mailReceiverList
        restMailReceiverMockMvc.perform(get("/api/mail-receivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mailReceiver.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getMailReceiver() throws Exception {
        // Initialize the database
        mailReceiverRepository.saveAndFlush(mailReceiver);

        // Get the mailReceiver
        restMailReceiverMockMvc.perform(get("/api/mail-receivers/{id}", mailReceiver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mailReceiver.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMailReceiver() throws Exception {
        // Get the mailReceiver
        restMailReceiverMockMvc.perform(get("/api/mail-receivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMailReceiver() throws Exception {
        // Initialize the database
        mailReceiverRepository.saveAndFlush(mailReceiver);
        int databaseSizeBeforeUpdate = mailReceiverRepository.findAll().size();

        // Update the mailReceiver
        MailReceiver updatedMailReceiver = mailReceiverRepository.findOne(mailReceiver.getId());
        updatedMailReceiver
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .emailAddress(UPDATED_EMAIL_ADDRESS);
        MailReceiverDTO mailReceiverDTO = mailReceiverMapper.mailReceiverToMailReceiverDTO(updatedMailReceiver);

        restMailReceiverMockMvc.perform(put("/api/mail-receivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mailReceiverDTO)))
            .andExpect(status().isOk());

        // Validate the MailReceiver in the database
        List<MailReceiver> mailReceiverList = mailReceiverRepository.findAll();
        assertThat(mailReceiverList).hasSize(databaseSizeBeforeUpdate);
        MailReceiver testMailReceiver = mailReceiverList.get(mailReceiverList.size() - 1);
        assertThat(testMailReceiver.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMailReceiver.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMailReceiver.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingMailReceiver() throws Exception {
        int databaseSizeBeforeUpdate = mailReceiverRepository.findAll().size();

        // Create the MailReceiver
        MailReceiverDTO mailReceiverDTO = mailReceiverMapper.mailReceiverToMailReceiverDTO(mailReceiver);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMailReceiverMockMvc.perform(put("/api/mail-receivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mailReceiverDTO)))
            .andExpect(status().isCreated());

        // Validate the MailReceiver in the database
        List<MailReceiver> mailReceiverList = mailReceiverRepository.findAll();
        assertThat(mailReceiverList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMailReceiver() throws Exception {
        // Initialize the database
        mailReceiverRepository.saveAndFlush(mailReceiver);
        int databaseSizeBeforeDelete = mailReceiverRepository.findAll().size();

        // Get the mailReceiver
        restMailReceiverMockMvc.perform(delete("/api/mail-receivers/{id}", mailReceiver.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MailReceiver> mailReceiverList = mailReceiverRepository.findAll();
        assertThat(mailReceiverList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MailReceiver.class);
    }
}
