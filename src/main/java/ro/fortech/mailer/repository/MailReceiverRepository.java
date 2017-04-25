package ro.fortech.mailer.repository;

import ro.fortech.mailer.domain.MailReceiver;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MailReceiver entity.
 */
@SuppressWarnings("unused")
public interface MailReceiverRepository extends JpaRepository<MailReceiver,Long> {

    @Query("select distinct mailReceiver from MailReceiver mailReceiver left join fetch mailReceiver.tags")
    List<MailReceiver> findAllWithEagerRelationships();

    @Query("select mailReceiver from MailReceiver mailReceiver left join fetch mailReceiver.tags where mailReceiver.id =:id")
    MailReceiver findOneWithEagerRelationships(@Param("id") Long id);

}
