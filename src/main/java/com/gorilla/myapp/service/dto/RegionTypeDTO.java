package com.gorilla.myapp.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the RegionType entity.
 */
public class RegionTypeDTO implements Serializable {

    private Long id;

    private String type;

    private Boolean showRegion;

    private String borderColor;

    @Lob
    private byte[] backgroundImage;
    private String backgroundImageContentType;

    private Double backgroundImageScale;

    private String backgroundImageColor;

    private String backgroundColor;

    private Long layerId;

    private Integer priority;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

    private ZonedDateTime deleteTime;

    private Long workspaceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isShowRegion() {
        return showRegion;
    }

    public void setShowRegion(Boolean showRegion) {
        this.showRegion = showRegion;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public byte[] getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(byte[] backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundImageContentType() {
        return backgroundImageContentType;
    }

    public void setBackgroundImageContentType(String backgroundImageContentType) {
        this.backgroundImageContentType = backgroundImageContentType;
    }

    public Double getBackgroundImageScale() {
        return backgroundImageScale;
    }

    public void setBackgroundImageScale(Double backgroundImageScale) {
        this.backgroundImageScale = backgroundImageScale;
    }

    public String getBackgroundImageColor() {
        return backgroundImageColor;
    }

    public void setBackgroundImageColor(String backgroundImageColor) {
        this.backgroundImageColor = backgroundImageColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Long getLayerId() {
        return layerId;
    }

    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegionTypeDTO regionTypeDTO = (RegionTypeDTO) o;
        if(regionTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regionTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegionTypeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", showRegion='" + isShowRegion() + "'" +
            ", borderColor='" + getBorderColor() + "'" +
            ", backgroundImage='" + getBackgroundImage() + "'" +
            ", backgroundImageScale='" + getBackgroundImageScale() + "'" +
            ", backgroundImageColor='" + getBackgroundImageColor() + "'" +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", layerId='" + getLayerId() + "'" +
            ", priority='" + getPriority() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
