package com.gorilla.myapp.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RegularRegion entity.
 */
public class RegularRegionDTO implements Serializable {

    private Long id;

    private String name;

    private Integer sides;

    private Double centerLongitude;

    private Double centerLatitude;

    private Double altitude;

    private Double cornerLongitude;

    private Double cornerLatitude;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

    private ZonedDateTime deleteTime;

    private Long regionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSides() {
        return sides;
    }

    public void setSides(Integer sides) {
        this.sides = sides;
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

    public Double getCornerLongitude() {
        return cornerLongitude;
    }

    public void setCornerLongitude(Double cornerLongitude) {
        this.cornerLongitude = cornerLongitude;
    }

    public Double getCornerLatitude() {
        return cornerLatitude;
    }

    public void setCornerLatitude(Double cornerLatitude) {
        this.cornerLatitude = cornerLatitude;
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

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegularRegionDTO regularRegionDTO = (RegularRegionDTO) o;
        if(regularRegionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regularRegionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegularRegionDTO{" +
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
