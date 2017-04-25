package ro.fortech.mailer.repository;

import ro.fortech.mailer.domain.Campaign;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Campaign entity.
 */
@SuppressWarnings("unused")
public interface CampaignRepository extends JpaRepository<Campaign,Long> {

    @Query("select campaign from Campaign campaign where campaign.user.login = ?#{principal.username}")
    List<Campaign> findByUserIsCurrentUser();

    @Query("select distinct campaign from Campaign campaign left join fetch campaign.tags")
    List<Campaign> findAllWithEagerRelationships();

    @Query("select campaign from Campaign campaign left join fetch campaign.tags where campaign.id =:id")
    Campaign findOneWithEagerRelationships(@Param("id") Long id);

}
