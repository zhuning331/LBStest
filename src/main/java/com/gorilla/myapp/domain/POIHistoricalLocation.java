package com.gorilla.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A POIHistoricalLocation.
 */
@Entity
@Table(name = "poi_historical_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "poihistoricallocation")
public class POIHistoricalLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "bearing")
    private Double bearing;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @ManyToOne
    private POIType type;

    @ManyToOne
    private POI poi;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public POIHistoricalLocation longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public POIHistoricalLocation latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public POIHistoricalLocation altitude(Double altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getBearing() {
        return bearing;
    }

    public POIHistoricalLocation bearing(Double bearing) {
        this.bearing = bearing;
        return this;
    }

    public void setBearing(Double bearing) {
        this.bearing = bearing;
    }

    public Double getSpeed() {
        return speed;
    }

    public POIHistoricalLocation speed(Double speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public POIHistoricalLocation time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public POIType getType() {
        return type;
    }

    public POIHistoricalLocation type(POIType pOIType) {
        this.type = pOIType;
        return this;
    }

    public void setType(POIType pOIType) {
        this.type = pOIType;
    }

    public POI getPoi() {
        return poi;
    }

    public POIHistoricalLocation poi(POI pOI) {
        this.poi = pOI;
        return this;
    }

    public void setPoi(POI pOI) {
        this.poi = pOI;
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
        POIHistoricalLocation pOIHistoricalLocation = (POIHistoricalLocation) o;
        if (pOIHistoricalLocation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pOIHistoricalLocation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "POIHistoricalLocation{" +
            "id=" + getId() +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", bearing='" + getBearing() + "'" +
            ", speed='" + getSpeed() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
