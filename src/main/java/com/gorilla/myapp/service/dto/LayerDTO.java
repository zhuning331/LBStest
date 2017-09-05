package com.gorilla.myapp.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Layer entity.
 */
public class LayerDTO implements Serializable {

    private Long id;

    private Integer priority;

    private String name;

    @Lob
    private byte[] image;
    private String imageContentType;

    private Double opacity;

    private Double rotation;

    private Double centerX;

    private Double centerY;

    private Double scaleX;

    private Double scaleY;

    private Double cropXMin;

    private Double cropYMin;

    private Double cropXMax;

    private Double centerLongitude;

    private Double centerLatitude;

    private Double altitude;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

    private ZonedDateTime deleteTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }

    public Double getRotation() {
        return rotation;
    }

    public void setRotation(Double rotation) {
        this.rotation = rotation;
    }

    public Double getCenterX() {
        return centerX;
    }

    public void setCenterX(Double centerX) {
        this.centerX = centerX;
    }

    public Double getCenterY() {
        return centerY;
    }

    public void setCenterY(Double centerY) {
        this.centerY = centerY;
    }

    public Double getScaleX() {
        return scaleX;
    }

    public void setScaleX(Double scaleX) {
        this.scaleX = scaleX;
    }

    public Double getScaleY() {
        return scaleY;
    }

    public void setScaleY(Double scaleY) {
        this.scaleY = scaleY;
    }

    public Double getCropXMin() {
        return cropXMin;
    }

    public void setCropXMin(Double cropXMin) {
        this.cropXMin = cropXMin;
    }

    public Double getCropYMin() {
        return cropYMin;
    }

    public void setCropYMin(Double cropYMin) {
        this.cropYMin = cropYMin;
    }

    public Double getCropXMax() {
        return cropXMax;
    }

    public void setCropXMax(Double cropXMax) {
        this.cropXMax = cropXMax;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LayerDTO layerDTO = (LayerDTO) o;
        if(layerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), layerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LayerDTO{" +
            "id=" + getId() +
            ", priority='" + getPriority() + "'" +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
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
