package com.gorilla.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RegularRegion.
 */
@Entity
@Table(name = "regular_region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "regularregion")
public class RegularRegion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sides")
    private Integer sides;

    @Column(name = "center_longitude")
    private Double centerLongitude;

    @Column(name = "center_latitude")
    private Double centerLatitude;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "corner_longitude")
    private Double cornerLongitude;

    @Column(name = "corner_latitude")
    private Double cornerLatitude;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "delete_time")
    private ZonedDateTime deleteTime;

    @ManyToOne
    private Region region;

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

    public RegularRegion name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSides() {
        return sides;
    }

    public RegularRegion sides(Integer sides) {
        this.sides = sides;
        return this;
    }

    public void setSides(Integer sides) {
        this.sides = sides;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }

    public RegularRegion centerLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
        return this;
    }

    public void setCenterLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }

    public RegularRegion centerLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
        return this;
    }

    public void setCenterLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public RegularRegion altitude(Double altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getCornerLongitude() {
        return cornerLongitude;
    }

    public RegularRegion cornerLongitude(Double cornerLongitude) {
        this.cornerLongitude = cornerLongitude;
        return this;
    }

    public void setCornerLongitude(Double cornerLongitude) {
        this.cornerLongitude = cornerLongitude;
    }

    public Double getCornerLatitude() {
        return cornerLatitude;
    }

    public RegularRegion cornerLatitude(Double cornerLatitude) {
        this.cornerLatitude = cornerLatitude;
        return this;
    }

    public void setCornerLatitude(Double cornerLatitude) {
        this.cornerLatitude = cornerLatitude;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public RegularRegion createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public RegularRegion updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public RegularRegion deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Region getRegion() {
        return region;
    }

    public RegularRegion region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
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
        RegularRegion regularRegion = (RegularRegion) o;
        if (regularRegion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regularRegion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegularRegion{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sides='" + getSides() + "'" +
            ", centerLongitude='" + getCenterLongitude() + "'" +
            ", centerLatitude='" + getCenterLatitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", cornerLongitude='" + getCornerLongitude() + "'" +
            ", cornerLatitude='" + getCornerLatitude() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
