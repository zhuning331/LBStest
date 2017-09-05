package com.gorilla.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.gorilla.myapp.domain.enumeration.LayerDisplayMode;

/**
 * A Map.
 */
@Entity
@Table(name = "map")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "map")
public class Map implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "layer_display_mode")
    private LayerDisplayMode layerDisplayMode;

    @Column(name = "tile_url")
    private String tileURL;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "zoom_level")
    private Integer zoomLevel;

    @Column(name = "rotation")
    private Double rotation;

    @Column(name = "fix_rotation")
    private Boolean fixRotation;

    @Column(name = "show_map")
    private Boolean showMap;

    @Column(name = "show_layer")
    private Boolean showLayer;

    @Column(name = "show_center_as_poi")
    private Boolean showCenterAsPOI;

    @Lob
    @Column(name = "icon")
    private byte[] icon;

    @Column(name = "icon_content_type")
    private String iconContentType;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "delete_time")
    private ZonedDateTime deleteTime;

    @ManyToOne
    private Workspace workspace;

    @ManyToOne
    private Layer initLayer;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "map_layers",
               joinColumns = @JoinColumn(name="maps_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="layers_id", referencedColumnName="id"))
    private Set<Layer> layers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "map_webs",
               joinColumns = @JoinColumn(name="maps_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="webs_id", referencedColumnName="id"))
    private Set<Web> webs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "map_regions",
               joinColumns = @JoinColumn(name="maps_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="regions_id", referencedColumnName="id"))
    private Set<Region> regions = new HashSet<>();

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

    public Map name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LayerDisplayMode getLayerDisplayMode() {
        return layerDisplayMode;
    }

    public Map layerDisplayMode(LayerDisplayMode layerDisplayMode) {
        this.layerDisplayMode = layerDisplayMode;
        return this;
    }

    public void setLayerDisplayMode(LayerDisplayMode layerDisplayMode) {
        this.layerDisplayMode = layerDisplayMode;
    }

    public String getTileURL() {
        return tileURL;
    }

    public Map tileURL(String tileURL) {
        this.tileURL = tileURL;
        return this;
    }

    public void setTileURL(String tileURL) {
        this.tileURL = tileURL;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Map longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Map latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Map altitude(Double altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Integer getZoomLevel() {
        return zoomLevel;
    }

    public Map zoomLevel(Integer zoomLevel) {
        this.zoomLevel = zoomLevel;
        return this;
    }

    public void setZoomLevel(Integer zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public Double getRotation() {
        return rotation;
    }

    public Map rotation(Double rotation) {
        this.rotation = rotation;
        return this;
    }

    public void setRotation(Double rotation) {
        this.rotation = rotation;
    }

    public Boolean isFixRotation() {
        return fixRotation;
    }

    public Map fixRotation(Boolean fixRotation) {
        this.fixRotation = fixRotation;
        return this;
    }

    public void setFixRotation(Boolean fixRotation) {
        this.fixRotation = fixRotation;
    }

    public Boolean isShowMap() {
        return showMap;
    }

    public Map showMap(Boolean showMap) {
        this.showMap = showMap;
        return this;
    }

    public void setShowMap(Boolean showMap) {
        this.showMap = showMap;
    }

    public Boolean isShowLayer() {
        return showLayer;
    }

    public Map showLayer(Boolean showLayer) {
        this.showLayer = showLayer;
        return this;
    }

    public void setShowLayer(Boolean showLayer) {
        this.showLayer = showLayer;
    }

    public Boolean isShowCenterAsPOI() {
        return showCenterAsPOI;
    }

    public Map showCenterAsPOI(Boolean showCenterAsPOI) {
        this.showCenterAsPOI = showCenterAsPOI;
        return this;
    }

    public void setShowCenterAsPOI(Boolean showCenterAsPOI) {
        this.showCenterAsPOI = showCenterAsPOI;
    }

    public byte[] getIcon() {
        return icon;
    }

    public Map icon(byte[] icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public Map iconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
        return this;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Map createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Map updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public Map deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public Map workspace(Workspace workspace) {
        this.workspace = workspace;
        return this;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Layer getInitLayer() {
        return initLayer;
    }

    public Map initLayer(Layer layer) {
        this.initLayer = layer;
        return this;
    }

    public void setInitLayer(Layer layer) {
        this.initLayer = layer;
    }

    public Set<Layer> getLayers() {
        return layers;
    }

    public Map layers(Set<Layer> layers) {
        this.layers = layers;
        return this;
    }

    public Map addLayers(Layer layer) {
        this.layers.add(layer);
        return this;
    }

    public Map removeLayers(Layer layer) {
        this.layers.remove(layer);
        return this;
    }

    public void setLayers(Set<Layer> layers) {
        this.layers = layers;
    }

    public Set<Web> getWebs() {
        return webs;
    }

    public Map webs(Set<Web> webs) {
        this.webs = webs;
        return this;
    }

    public Map addWebs(Web web) {
        this.webs.add(web);
        return this;
    }

    public Map removeWebs(Web web) {
        this.webs.remove(web);
        return this;
    }

    public void setWebs(Set<Web> webs) {
        this.webs = webs;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public Map regions(Set<Region> regions) {
        this.regions = regions;
        return this;
    }

    public Map addRegions(Region region) {
        this.regions.add(region);
        return this;
    }

    public Map removeRegions(Region region) {
        this.regions.remove(region);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
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
        Map map = (Map) o;
        if (map.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), map.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Map{" +
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
            ", iconContentType='" + iconContentType + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
