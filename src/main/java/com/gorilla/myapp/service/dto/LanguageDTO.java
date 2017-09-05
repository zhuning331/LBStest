package com.gorilla.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Language entity.
 */
public class LanguageDTO implements Serializable {

    private Long id;

    private String workspaceToken;

    private String type;

    private String identity;

    private String value;

    private String target;

    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkspaceToken() {
        return workspaceToken;
    }

    public void setWorkspaceToken(String workspaceToken) {
        this.workspaceToken = workspaceToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LanguageDTO languageDTO = (LanguageDTO) o;
        if(languageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), languageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LanguageDTO{" +
            "id=" + getId() +
            ", workspaceToken='" + getWorkspaceToken() + "'" +
            ", type='" + getType() + "'" +
            ", identity='" + getIdentity() + "'" +
            ", value='" + getValue() + "'" +
            ", target='" + getTarget() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
