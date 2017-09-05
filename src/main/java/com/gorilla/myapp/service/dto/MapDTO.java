package com.gorilla.myapp.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.gorilla.myapp.domain.enumeration.LayerDisplayMode;

/**
 * A DTO for the Map entity.
 */
public class MapDTO implements Serializable {

    private Long id;

    private String name;

    private LayerDisplayMode layerDisplayMode;

    private String tileURL;

    private Double longitude;

    private Double latitude;

    private Double altitude;

    private Integer zoomLevel;

    private Double rotation;

    private Boolean fixRotation;

    private Boolean showMap;

    private Boolean showLayer;

    private Boolean showCenterAsPOI;

    @Lob
    private byte[] icon;
    private String iconContentType;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

    private ZonedDateTime deleteTime;

    private Long workspaceId;

    private Long initLayerId;

    private Set<LayerDTO> layers = new HashSet<>();

    private Set<WebDTO> webs = new HashSet<>();

    private Set<RegionDTO> regions = new HashSet<>();

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

    public LayerDisplayMode getLayerDisplayMode() {
        return layerDisplayMode;
    }

    public void setLayerDisplayMode(LayerDisplayMode layerDisplayMode) {
        this.layerDisplayMode = layerDisplayMode;
    }

    public String getTileURL() {
        return tileURL;
    }

    public void setTileURL(String tileURL) {
        this.tileURL = tileURL;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Integer getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(Integer zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public Double getRotation() {
        return rotation;
    }

    public void setRotation(Double rotation) {
        this.rotation = rotation;
    }

    public Boolean isFixRotation() {
        return fixRotation;
    }

    public void setFixRotation(Boolean fixRotation) {
        this.fixRotation = fixRotation;
    }

    public Boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(Boolean showMap) {
        this.showMap = showMap;
    }

    public Boolean isShowLayer() {
        return showLayer;
    }

    public void setShowLayer(Boolean showLayer) {
        this.showLayer = showLayer;
    }

    public Boolean isShowCenterAsPOI() {
        return showCenterAsPOI;
    }

    public void setShowCenterAsPOI(Boolean showCenterAsPOI) {
        this.showCenterAsPOI = showCenterAsPOI;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
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

    public Long getInitLayerId() {
        return initLayerId;
    }

    public void setInitLayerId(Long layerId) {
        this.initLayerId = layerId;
    }

    public Set<LayerDTO> getLayers() {
        return layers;
    }

    public void setLayers(Set<LayerDTO> layers) {
        this.layers = layers;
    }

    public Set<WebDTO> getWebs() {
        return webs;
    }

    public void setWebs(Set<WebDTO> webs) {
        this.webs = webs;
    }

    public Set<RegionDTO> getRegions() {
        return regions;
    }

    public void setRegions(Set<RegionDTO> regions) {
        this.regions = regions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MapDTO mapDTO = (MapDTO) o;
        if(mapDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mapDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MapDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", layerDisplayMode='" + getLayerDisplayMode() + "'" +
            ", tileURL='" + getTileURL() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", zoomLevel='" + getZoomLevel() + "'" +
            ", rotation='" + getRotation() + "'" +
            ", fixRotation='" + isFixRotation() + "'" +
            ", showMap='" + isShowMap() + "'" +
            ", showLayer='" + isShowLayer() + "'" +
            ", showCenterAsPOI='" + isShowCenterAsPOI() + "'" +
            ", icon='" + getIcon() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
