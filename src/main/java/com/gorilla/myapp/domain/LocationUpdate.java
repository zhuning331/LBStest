package com.gorilla.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LocationUpdate.
 */
@Entity
@Table(name = "location_update")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "locationupdate")
public class LocationUpdate implements Serializable {

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

    @Column(name = "layer_id")
    private Long layerId;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

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

    public LocationUpdate longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public LocationUpdate latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public LocationUpdate altitude(Double altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getBearing() {
        return bearing;
    }

    public LocationUpdate bearing(Double bearing) {
        this.bearing = bearing;
        return this;
    }

    public void setBearing(Double bearing) {
        this.bearing = bearing;
    }

    public Double getSpeed() {
        return speed;
    }

    public LocationUpdate speed(Double speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Long getLayerId() {
        return layerId;
    }

    public LocationUpdate layerId(Long layerId) {
        this.layerId = layerId;
        return this;
    }

    public void setLayerId(Long layerId) {
        this.layerId = layerId;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public LocationUpdate time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
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
        LocationUpdate locationUpdate = (LocationUpdate) o;
        if (locationUpdate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locationUpdate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocationUpdate{" +
            "id=" + getId() +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", bearing='" + getBearing() + "'" +
            ", speed='" + getSpeed() + "'" +
            ", layerId='" + getLayerId() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
