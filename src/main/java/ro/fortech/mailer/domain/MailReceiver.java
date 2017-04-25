package ro.fortech.mailer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MailReceiver.
 */
@Entity
@Table(name = "mail_receiver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MailReceiver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "mail_receiver_tag",
               joinColumns = @JoinColumn(name="mail_receivers_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MailReceiver name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public MailReceiver description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public MailReceiver emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public MailReceiver tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public MailReceiver addTag(Tag tag) {
        this.tags.add(tag);
        tag.getMailReceivers().add(this);
        return this;
    }

    public MailReceiver removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getMailReceivers().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MailReceiver mailReceiver = (MailReceiver) o;
        if (mailReceiver.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mailReceiver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MailReceiver{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", emailAddress='" + emailAddress + "'" +
            '}';
    }
}
