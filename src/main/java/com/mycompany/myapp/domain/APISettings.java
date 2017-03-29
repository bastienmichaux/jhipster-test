package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A APISettings.
 */
@Entity
@Table(name = "microserviceapisettings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class APISettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preferred_api")
    private String preferredAPI;

    @Column(name = "port_8080_api_listener")
    private String port8080APIListener;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreferredAPI() {
        return preferredAPI;
    }

    public APISettings preferredAPI(String preferredAPI) {
        this.preferredAPI = preferredAPI;
        return this;
    }

    public void setPreferredAPI(String preferredAPI) {
        this.preferredAPI = preferredAPI;
    }

    public String getPort8080APIListener() {
        return port8080APIListener;
    }

    public APISettings port8080APIListener(String port8080APIListener) {
        this.port8080APIListener = port8080APIListener;
        return this;
    }

    public void setPort8080APIListener(String port8080APIListener) {
        this.port8080APIListener = port8080APIListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        APISettings aPISettings = (APISettings) o;
        if (aPISettings.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, aPISettings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "APISettings{" +
            "id=" + id +
            ", preferredAPI='" + preferredAPI + "'" +
            ", port8080APIListener='" + port8080APIListener + "'" +
            '}';
    }
}
