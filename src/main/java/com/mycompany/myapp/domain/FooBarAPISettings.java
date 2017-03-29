package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FooBarAPISettings.
 */
@Entity
@Table(name = "my_foo_barapisettings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FooBarAPISettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "foo_bar_api_settings_field")
    private String fooBarAPISettingsField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFooBarAPISettingsField() {
        return fooBarAPISettingsField;
    }

    public FooBarAPISettings fooBarAPISettingsField(String fooBarAPISettingsField) {
        this.fooBarAPISettingsField = fooBarAPISettingsField;
        return this;
    }

    public void setFooBarAPISettingsField(String fooBarAPISettingsField) {
        this.fooBarAPISettingsField = fooBarAPISettingsField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FooBarAPISettings fooBarAPISettings = (FooBarAPISettings) o;
        if (fooBarAPISettings.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fooBarAPISettings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FooBarAPISettings{" +
            "id=" + id +
            ", fooBarAPISettingsField='" + fooBarAPISettingsField + "'" +
            '}';
    }
}
