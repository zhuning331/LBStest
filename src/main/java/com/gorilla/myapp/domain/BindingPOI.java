package com.gorilla.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BindingPOI.
 */
@Entity
@Table(name = "binding_poi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bindingpoi")
public class BindingPOI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "binding_type")
    private String bindingType;

    @Column(name = "binding_key")
    private String bindingKey;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "delete_time")
    private ZonedDateTime deleteTime;

    @ManyToOne
    private POI poi;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBindingType() {
        return bindingType;
    }

    public BindingPOI bindingType(String bindingType) {
        this.bindingType = bindingType;
        return this;
    }

    public void setBindingType(String bindingType) {
        this.bindingType = bindingType;
    }

    public String getBindingKey() {
        return bindingKey;
    }

    public BindingPOI bindingKey(String bindingKey) {
        this.bindingKey = bindingKey;
        return this;
    }

    public void setBindingKey(String bindingKey) {
        this.bindingKey = bindingKey;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public BindingPOI createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public BindingPOI updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public BindingPOI deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public POI getPoi() {
        return poi;
    }

    public BindingPOI poi(POI pOI) {
        this.poi = pOI;
        return this;
    }

    public void setPoi(POI pOI) {
        this.poi = pOI;
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
        BindingPOI bindingPOI = (BindingPOI) o;
        if (bindingPOI.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bindingPOI.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BindingPOI{" +
            "id=" + getId() +
            ", bindingType='" + getBindingType() + "'" +
            ", bindingKey='" + getBindingKey() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
