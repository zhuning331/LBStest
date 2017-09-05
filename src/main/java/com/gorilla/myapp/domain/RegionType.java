package com.gorilla.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RegionType.
 */
@Entity
@Table(name = "region_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "regiontype")
public class RegionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "show_region")
    private Boolean showRegion;

    @Column(name = "border_color")
    private String borderColor;

    @Lob
    @Column(name = "background_image")
    private byte[] backgroundImage;

    @Column(name = "background_image_content_type")
    private String backgroundImageContentType;

    @Column(name = "background_image_scale")
    private Double backgroundImageScale;

    @Column(name = "background_image_color")
    private String backgroundImageColor;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "layer_id")
    private Long layerId;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "delete_time")
    private ZonedDateTime deleteTime;

    @ManyToOne
    private Workspace workspace;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public RegionType type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isShowRegion() {
        return showRegion;
    }

    public RegionType showRegion(Boolean showRegion) {
        this.showRegion = showRegion;
        return this;
    }

    public void setShowRegion(Boolean showRegion) {
        this.showRegion = showRegion;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public RegionType borderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public byte[] getBackgroundImage() {
        return backgroundImage;
    }

    public RegionType backgroundImage(byte[] backgroundImage) {
        this.backgroundImage = backgroundImage;
        return this;
    }

    public void setBackgroundImage(byte[] backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundImageContentType() {
        return backgroundImageContentType;
    }

    public RegionType backgroundImageContentType(String backgroundImageContentType) {
        this.backgroundImageContentType = backgroundImageContentType;
        return this;
    }

    public void setBackgroundImageContentType(String backgroundImageContentType) {
        this.backgroundImageContentType = backgroundImageContentType;
    }

    public Double getBackgroundImageScale() {
        return backgroundImageScale;
    }

    public RegionType backgroundImageScale(Double backgroundImageScale) {
        this.backgroundImageScale = backgroundImageScale;
        return this;
    }

    public void setBackgroundImageScale(Double backgroundImageScale) {
        this.backgroundImageScale = backgroundImageScale;
    }

    public String getBackgroundImageColor() {
        return backgroundImageColor;
    }

    public RegionType backgroundImageColor(String backgroundImageColor) {
        this.backgroundImageColor = backgroundImageColor;
        return this;
    }

    public void setBackgroundImageColor(String backgroundImageColor) {
        this.backgroundImageColor = backgroundImageColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public RegionType backgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Long getLayerId() {
        return layerId;
    }

    public RegionType layerId(Long layerId) {
        this.layerId = layerId;
        return this;
    }

    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }

    public Integer getPriority() {
        return priority;
    }

    public RegionType priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public RegionType createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public RegionType updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public RegionType deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public RegionType workspace(Workspace workspace) {
        this.workspace = workspace;
        return this;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
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
        RegionType regionType = (RegionType) o;
        if (regionType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regionType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegionType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", showRegion='" + isShowRegion() + "'" +
            ", borderColor='" + getBorderColor() + "'" +
            ", backgroundImage='" + getBackgroundImage() + "'" +
            ", backgroundImageContentType='" + backgroundImageContentType + "'" +
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
