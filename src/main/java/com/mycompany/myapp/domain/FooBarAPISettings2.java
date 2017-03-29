package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FooBarAPISettings2.
 */
@Entity
@Table(name = "my_foo_barapisettings2")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FooBarAPISettings2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "foo_bar_api_settings_field_name")
    private String fooBarAPISettingsFieldName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFooBarAPISettingsFieldName() {
        return fooBarAPISettingsFieldName;
    }

    public FooBarAPISettings2 fooBarAPISettingsFieldName(String fooBarAPISettingsFieldName) {
        this.fooBarAPISettingsFieldName = fooBarAPISettingsFieldName;
        return this;
    }

    public void setFooBarAPISettingsFieldName(String fooBarAPISettingsFieldName) {
        this.fooBarAPISettingsFieldName = fooBarAPISettingsFieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FooBarAPISettings2 fooBarAPISettings2 = (FooBarAPISettings2) o;
        if (fooBarAPISettings2.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fooBarAPISettings2.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FooBarAPISettings2{" +
            "id=" + id +
            ", fooBarAPISettingsFieldName='" + fooBarAPISettingsFieldName + "'" +
            '}';
    }
}
