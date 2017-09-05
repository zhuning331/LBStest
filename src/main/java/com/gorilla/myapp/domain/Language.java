package com.gorilla.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Language.
 */
@Entity
@Table(name = "language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "language")
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "workspace_token")
    private String workspaceToken;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "jhi_identity")
    private String identity;

    @Column(name = "jhi_value")
    private String value;

    @Column(name = "target")
    private String target;

    @Column(name = "code")
    private String code;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkspaceToken() {
        return workspaceToken;
    }

    public Language workspaceToken(String workspaceToken) {
        this.workspaceToken = workspaceToken;
        return this;
    }

    public void setWorkspaceToken(String workspaceToken) {
        this.workspaceToken = workspaceToken;
    }

    public String getType() {
        return type;
    }

    public Language type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentity() {
        return identity;
    }

    public Language identity(String identity) {
        this.identity = identity;
        return this;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getValue() {
        return value;
    }

    public Language value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTarget() {
        return target;
    }

    public Language target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCode() {
        return code;
    }

    public Language code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Language language = (Language) o;
        if (language.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), language.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Language{" +
            "id=" + getId() +
            ", workspaceToken='" + getWorkspaceToken() + "'" +
            ", type='" + getType() + "'" +
            ", identity='" + getIdentity() + "'" +
            ", value='" + getValue() + "'" +
            ", target='" + getTarget() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
