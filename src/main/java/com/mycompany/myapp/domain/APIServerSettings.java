package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A APIServerSettings.
 */
@Entity
@Table(name = "microserviceapiserver_settings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class APIServerSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "my_api_server_settings")
    private String myAPIServerSettings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyAPIServerSettings() {
        return myAPIServerSettings;
    }

    public APIServerSettings myAPIServerSettings(String myAPIServerSettings) {
        this.myAPIServerSettings = myAPIServerSettings;
        return this;
    }

    public void setMyAPIServerSettings(String myAPIServerSettings) {
        this.myAPIServerSettings = myAPIServerSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        APIServerSettings aPIServerSettings = (APIServerSettings) o;
        if (aPIServerSettings.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, aPIServerSettings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "APIServerSettings{" +
            "id=" + id +
            ", myAPIServerSettings='" + myAPIServerSettings + "'" +
            '}';
    }
}
