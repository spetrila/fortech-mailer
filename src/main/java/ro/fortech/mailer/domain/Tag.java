package ro.fortech.mailer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MailReceiver> mailReceivers = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Campaign> campaigns = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Tag description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MailReceiver> getMailReceivers() {
        return mailReceivers;
    }

    public Tag mailReceivers(Set<MailReceiver> mailReceivers) {
        this.mailReceivers = mailReceivers;
        return this;
    }

    public Tag addMailReceivers(MailReceiver mailReceiver) {
        this.mailReceivers.add(mailReceiver);
        mailReceiver.getTags().add(this);
        return this;
    }

    public Tag removeMailReceivers(MailReceiver mailReceiver) {
        this.mailReceivers.remove(mailReceiver);
        mailReceiver.getTags().remove(this);
        return this;
    }

    public void setMailReceivers(Set<MailReceiver> mailReceivers) {
        this.mailReceivers = mailReceivers;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public Tag campaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
        return this;
    }

    public Tag addCampaigns(Campaign campaign) {
        this.campaigns.add(campaign);
        campaign.getTags().add(this);
        return this;
    }

    public Tag removeCampaigns(Campaign campaign) {
        this.campaigns.remove(campaign);
        campaign.getTags().remove(this);
        return this;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        if (tag.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
