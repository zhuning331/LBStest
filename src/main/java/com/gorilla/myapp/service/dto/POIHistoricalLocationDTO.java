package com.gorilla.myapp.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the POIHistoricalLocation entity.
 */
public class POIHistoricalLocationDTO implements Serializable {

    private Long id;

    private Double longitude;

    private Double latitude;

    private Double altitude;

    private Double bearing;

    private Double speed;

    private ZonedDateTime time;

    private Long typeId;

    private Long poiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getBearing() {
        return bearing;
    }

    public void setBearing(Double bearing) {
        this.bearing = bearing;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long pOITypeId) {
        this.typeId = pOITypeId;
    }

    public Long getPoiId() {
        return poiId;
    }

    public void setPoiId(Long pOIId) {
        this.poiId = pOIId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        POIHistoricalLocationDTO pOIHistoricalLocationDTO = (POIHistoricalLocationDTO) o;
        if(pOIHistoricalLocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pOIHistoricalLocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "POIHistoricalLocationDTO{" +
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
