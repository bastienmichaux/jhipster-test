package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PreferredServerAPISettings.
 */
@Entity
@Table(name = "preferred_serverapisettings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PreferredServerAPISettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preferred_server_api_settings_http_port")
    private String preferredServerAPISettingsHTTPPort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreferredServerAPISettingsHTTPPort() {
        return preferredServerAPISettingsHTTPPort;
    }

    public PreferredServerAPISettings preferredServerAPISettingsHTTPPort(String preferredServerAPISettingsHTTPPort) {
        this.preferredServerAPISettingsHTTPPort = preferredServerAPISettingsHTTPPort;
        return this;
    }

    public void setPreferredServerAPISettingsHTTPPort(String preferredServerAPISettingsHTTPPort) {
        this.preferredServerAPISettingsHTTPPort = preferredServerAPISettingsHTTPPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreferredServerAPISettings preferredServerAPISettings = (PreferredServerAPISettings) o;
        if (preferredServerAPISettings.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, preferredServerAPISettings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PreferredServerAPISettings{" +
            "id=" + id +
            ", preferredServerAPISettingsHTTPPort='" + preferredServerAPISettingsHTTPPort + "'" +
            '}';
    }
}
