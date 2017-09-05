package com.gorilla.myapp.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Edge entity.
 */
public class EdgeDTO implements Serializable {

    private Long id;

    private Integer order;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

    private ZonedDateTime deleteTime;

    private Long fromId;

    private Long toId;

    private Set<EdgeTypeDTO> types = new HashSet<>();

    private Long webId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long nodeId) {
        this.fromId = nodeId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long nodeId) {
        this.toId = nodeId;
    }

    public Set<EdgeTypeDTO> getTypes() {
        return types;
    }

    public void setTypes(Set<EdgeTypeDTO> edgeTypes) {
        this.types = edgeTypes;
    }

    public Long getWebId() {
        return webId;
    }

    public void setWebId(Long webId) {
        this.webId = webId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EdgeDTO edgeDTO = (EdgeDTO) o;
        if(edgeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), edgeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EdgeDTO{" +
            "id=" + getId() +
            ", order='" + getOrder() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
