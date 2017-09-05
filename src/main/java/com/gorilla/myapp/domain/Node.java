package com.gorilla.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Node.
 */
@Entity
@Table(name = "node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "node")
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_order")
    private Integer order;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "poi_id")
    private Long poiId;

    @Column(name = "layer_id")
    private Long layerId;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "delete_time")
    private ZonedDateTime deleteTime;

    @ManyToOne
    private PolygonRegion polygonRegion;

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

    public Node order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Node longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Node latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Node altitude(Double altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Long getPoiId() {
        return poiId;
    }

    public Node poiId(Long poiId) {
        this.poiId = poiId;
        return this;
    }

    public void setPoiId(Long poiId) {
        this.poiId = poiId;
    }

    public Long getLayerId() {
        return layerId;
    }

    public Node layerId(Long layerId) {
        this.layerId = layerId;
        return this;
    }

    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Node createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Node updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public Node deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public PolygonRegion getPolygonRegion() {
        return polygonRegion;
    }

    public Node polygonRegion(PolygonRegion polygonRegion) {
        this.polygonRegion = polygonRegion;
        return this;
    }

    public void setPolygonRegion(PolygonRegion polygonRegion) {
        this.polygonRegion = polygonRegion;
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
        Node node = (Node) o;
        if (node.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), node.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Node{" +
            "id=" + getId() +
            ", order='" + getOrder() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", poiId='" + getPoiId() + "'" +
            ", layerId='" + getLayerId() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
