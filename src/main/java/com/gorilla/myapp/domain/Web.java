package com.gorilla.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Web.
 */
@Entity
@Table(name = "web")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "web")
public class Web implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "delete_time")
    private ZonedDateTime deleteTime;

    @OneToMany(mappedBy = "web")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Edge> edges = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "web_types",
               joinColumns = @JoinColumn(name="webs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="types_id", referencedColumnName="id"))
    private Set<WebType> types = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Web name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Web createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Web updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public Web deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Web edges(Set<Edge> edges) {
        this.edges = edges;
        return this;
    }

    public Web addEdges(Edge edge) {
        this.edges.add(edge);
        edge.setWeb(this);
        return this;
    }

    public Web removeEdges(Edge edge) {
        this.edges.remove(edge);
        edge.setWeb(null);
        return this;
    }

    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }

    public Set<WebType> getTypes() {
        return types;
    }

    public Web types(Set<WebType> webTypes) {
        this.types = webTypes;
        return this;
    }

    public Web addTypes(WebType webType) {
        this.types.add(webType);
        return this;
    }

    public Web removeTypes(WebType webType) {
        this.types.remove(webType);
        return this;
    }

    public void setTypes(Set<WebType> webTypes) {
        this.types = webTypes;
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
        Web web = (Web) o;
        if (web.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), web.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Web{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
