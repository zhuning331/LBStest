package com.gorilla.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Layer.
 */
@Entity
@Table(name = "layer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "layer")
public class Layer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "opacity")
    private Double opacity;

    @Column(name = "rotation")
    private Double rotation;

    @Column(name = "center_x")
    private Double centerX;

    @Column(name = "center_y")
    private Double centerY;

    @Column(name = "scale_x")
    private Double scaleX;

    @Column(name = "scale_y")
    private Double scaleY;

    @Column(name = "crop_x_min")
    private Double cropXMin;

    @Column(name = "crop_y_min")
    private Double cropYMin;

    @Column(name = "crop_x_max")
    private Double cropXMax;

    @Column(name = "center_longitude")
    private Double centerLongitude;

    @Column(name = "center_latitude")
    private Double centerLatitude;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "delete_time")
    private ZonedDateTime deleteTime;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public Layer priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public Layer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public Layer image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Layer imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Double getOpacity() {
        return opacity;
    }

    public Layer opacity(Double opacity) {
        this.opacity = opacity;
        return this;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public Double getRotation() {
        return rotation;
    }

    public Layer rotation(Double rotation) {
        this.rotation = rotation;
        return this;
    }

    public void setRotation(Double rotation) {
        this.rotation = rotation;
    }

    public Double getCenterX() {
        return centerX;
    }

    public Layer centerX(Double centerX) {
        this.centerX = centerX;
        return this;
    }

    public void setCenterX(Double centerX) {
        this.centerX = centerX;
    }

    public Double getCenterY() {
        return centerY;
    }

    public Layer centerY(Double centerY) {
        this.centerY = centerY;
        return this;
    }

    public void setCenterY(Double centerY) {
        this.centerY = centerY;
    }

    public Double getScaleX() {
        return scaleX;
    }

    public Layer scaleX(Double scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public void setScaleX(Double scaleX) {
        this.scaleX = scaleX;
    }

    public Double getScaleY() {
        return scaleY;
    }

    public Layer scaleY(Double scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public void setScaleY(Double scaleY) {
        this.scaleY = scaleY;
    }

    public Double getCropXMin() {
        return cropXMin;
    }

    public Layer cropXMin(Double cropXMin) {
        this.cropXMin = cropXMin;
        return this;
    }

    public void setCropXMin(Double cropXMin) {
        this.cropXMin = cropXMin;
    }

    public Double getCropYMin() {
        return cropYMin;
    }

    public Layer cropYMin(Double cropYMin) {
        this.cropYMin = cropYMin;
        return this;
    }

    public void setCropYMin(Double cropYMin) {
        this.cropYMin = cropYMin;
    }

    public Double getCropXMax() {
        return cropXMax;
    }

    public Layer cropXMax(Double cropXMax) {
        this.cropXMax = cropXMax;
        return this;
    }

    public void setCropXMax(Double cropXMax) {
        this.cropXMax = cropXMax;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }

    public Layer centerLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
        return this;
    }

    public void setCenterLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }

    public Layer centerLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
        return this;
    }

    public void setCenterLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Layer altitude(Double altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Layer createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Layer updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public Layer deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
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
        Layer layer = (Layer) o;
        if (layer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), layer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Layer{" +
            "id=" + getId() +
            ", priority='" + getPriority() + "'" +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", opacity='" + getOpacity() + "'" +
            ", rotation='" + getRotation() + "'" +
            ", centerX='" + getCenterX() + "'" +
            ", centerY='" + getCenterY() + "'" +
            ", scaleX='" + getScaleX() + "'" +
            ", scaleY='" + getScaleY() + "'" +
            ", cropXMin='" + getCropXMin() + "'" +
            ", cropYMin='" + getCropYMin() + "'" +
            ", cropXMax='" + getCropXMax() + "'" +
            ", centerLongitude='" + getCenterLongitude() + "'" +
            ", centerLatitude='" + getCenterLatitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
