package com.gorilla.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Workspace.
 */
@Entity
@Table(name = "workspace")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "workspace")
public class Workspace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "token")
    private String token;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "delete_time")
    private ZonedDateTime deleteTime;

    @OneToMany(mappedBy = "workspace")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkspaceConfig> configs = new HashSet<>();

    @OneToMany(mappedBy = "workspace")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Map> maps = new HashSet<>();

    @ManyToOne
    private Map initMap;

    @ManyToOne
    private User owner;

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

    public Workspace name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public Workspace token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Workspace createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Workspace updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public Workspace deleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public void setDeleteTime(ZonedDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Set<WorkspaceConfig> getConfigs() {
        return configs;
    }

    public Workspace configs(Set<WorkspaceConfig> workspaceConfigs) {
        this.configs = workspaceConfigs;
        return this;
    }

    public Workspace addConfigs(WorkspaceConfig workspaceConfig) {
        this.configs.add(workspaceConfig);
        workspaceConfig.setWorkspace(this);
        return this;
    }

    public Workspace removeConfigs(WorkspaceConfig workspaceConfig) {
        this.configs.remove(workspaceConfig);
        workspaceConfig.setWorkspace(null);
        return this;
    }

    public void setConfigs(Set<WorkspaceConfig> workspaceConfigs) {
        this.configs = workspaceConfigs;
    }

    public Set<Map> getMaps() {
        return maps;
    }

    public Workspace maps(Set<Map> maps) {
        this.maps = maps;
        return this;
    }

    public Workspace addMaps(Map map) {
        this.maps.add(map);
        map.setWorkspace(this);
        return this;
    }

    public Workspace removeMaps(Map map) {
        this.maps.remove(map);
        map.setWorkspace(null);
        return this;
    }

    public void setMaps(Set<Map> maps) {
        this.maps = maps;
    }

    public Map getInitMap() {
        return initMap;
    }

    public Workspace initMap(Map map) {
        this.initMap = map;
        return this;
    }

    public void setInitMap(Map map) {
        this.initMap = map;
    }

    public User getOwner() {
        return owner;
    }

    public Workspace owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
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
        Workspace workspace = (Workspace) o;
        if (workspace.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workspace.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Workspace{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", token='" + getToken() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", deleteTime='" + getDeleteTime() + "'" +
            "}";
    }
}
