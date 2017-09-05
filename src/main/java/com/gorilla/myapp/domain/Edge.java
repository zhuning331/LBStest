package com.gorilla.myapp.domain;

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
 * A Edge.
 */
@Entity
@Table(name = "edge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "edge")
public class Edge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_order")
    private Integer order;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "delete_time")
    private ZonedDateTime deleteTime;

    @ManyToOne
    private Node from;

    @ManyToOne
    private Node to;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "edge_types",
               joinColumns = @JoinColumn(name="edges_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="types_id", referencedColumnName="id"))
    private Set<EdgeType> types = new HashSet<>();

    @ManyToOne
    private Web web;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public Edge order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Edge createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Edge updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public Edge deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Node getFrom() {
        return from;
    }

    public Edge from(Node node) {
        this.from = node;
        return this;
    }

    public void setFrom(Node node) {
        this.from = node;
    }

    public Node getTo() {
        return to;
    }

    public Edge to(Node node) {
        this.to = node;
        return this;
    }

    public void setTo(Node node) {
        this.to = node;
    }

    public Set<EdgeType> getTypes() {
        return types;
    }

    public Edge types(Set<EdgeType> edgeTypes) {
        this.types = edgeTypes;
        return this;
    }

    public Edge addTypes(EdgeType edgeType) {
        this.types.add(edgeType);
        return this;
    }

    public Edge removeTypes(EdgeType edgeType) {
        this.types.remove(edgeType);
        return this;
    }

    public void setTypes(Set<EdgeType> edgeTypes) {
        this.types = edgeTypes;
    }

    public Web getWeb() {
        return web;
    }

    public Edge web(Web web) {
        this.web = web;
        return this;
    }

    public void setWeb(Web web) {
        this.web = web;
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
        Edge edge = (Edge) o;
        if (edge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), edge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Edge{" +
            "id=" + getId() +
            ", order='" + getOrder() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
