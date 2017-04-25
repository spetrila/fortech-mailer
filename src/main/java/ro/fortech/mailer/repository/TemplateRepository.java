package ro.fortech.mailer.repository;

import ro.fortech.mailer.domain.Template;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Template entity.
 */
@SuppressWarnings("unused")
public interface TemplateRepository extends JpaRepository<Template,Long> {

    @Query("select template from Template template where template.user.login = ?#{principal.username}")
    List<Template> findByUserIsCurrentUser();

}
