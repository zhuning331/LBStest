package com.gorilla.myapp.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PolygonRegion entity.
 */
public class PolygonRegionDTO implements Serializable {

    private Long id;

    private String name;

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

        PolygonRegionDTO polygonRegionDTO = (PolygonRegionDTO) o;
        if(polygonRegionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), polygonRegionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PolygonRegionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
