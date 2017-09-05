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
 * A Region.
 */
@Entity
@Table(name = "region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "region")
public class Region implements Serializable {

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

    @OneToMany(mappedBy = "region")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PolygonRegion> polygonRegions = new HashSet<>();

    @OneToMany(mappedBy = "region")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RegularRegion> regularRegions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "region_types",
               joinColumns = @JoinColumn(name="regions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="types_id", referencedColumnName="id"))
    private Set<RegionType> types = new HashSet<>();

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

    public Region name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Region createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Region updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public Region deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Set<PolygonRegion> getPolygonRegions() {
        return polygonRegions;
    }

    public Region polygonRegions(Set<PolygonRegion> polygonRegions) {
        this.polygonRegions = polygonRegions;
        return this;
    }

    public Region addPolygonRegions(PolygonRegion polygonRegion) {
        this.polygonRegions.add(polygonRegion);
        polygonRegion.setRegion(this);
        return this;
    }

    public Region removePolygonRegions(PolygonRegion polygonRegion) {
        this.polygonRegions.remove(polygonRegion);
        polygonRegion.setRegion(null);
        return this;
    }

    public void setPolygonRegions(Set<PolygonRegion> polygonRegions) {
        this.polygonRegions = polygonRegions;
    }

    public Set<RegularRegion> getRegularRegions() {
        return regularRegions;
    }

    public Region regularRegions(Set<RegularRegion> regularRegions) {
        this.regularRegions = regularRegions;
        return this;
    }

    public Region addRegularRegions(RegularRegion regularRegion) {
        this.regularRegions.add(regularRegion);
        regularRegion.setRegion(this);
        return this;
    }

    public Region removeRegularRegions(RegularRegion regularRegion) {
        this.regularRegions.remove(regularRegion);
        regularRegion.setRegion(null);
        return this;
    }

    public void setRegularRegions(Set<RegularRegion> regularRegions) {
        this.regularRegions = regularRegions;
    }

    public Set<RegionType> getTypes() {
        return types;
    }

    public Region types(Set<RegionType> regionTypes) {
        this.types = regionTypes;
        return this;
    }

    public Region addTypes(RegionType regionType) {
        this.types.add(regionType);
        return this;
    }

    public Region removeTypes(RegionType regionType) {
        this.types.remove(regionType);
        return this;
    }

    public void setTypes(Set<RegionType> regionTypes) {
        this.types = regionTypes;
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
        Region region = (Region) o;
        if (region.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), region.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
