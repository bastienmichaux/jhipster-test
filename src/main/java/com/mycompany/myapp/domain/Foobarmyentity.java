package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Foobarmyentity.
 */
@Entity
@Table(name = "myfoobarmyentity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Foobarmyentity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "m_y_foobarfield")
    private String mYFoobarfield;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getmYFoobarfield() {
        return mYFoobarfield;
    }

    public Foobarmyentity mYFoobarfield(String mYFoobarfield) {
        this.mYFoobarfield = mYFoobarfield;
        return this;
    }

    public void setmYFoobarfield(String mYFoobarfield) {
        this.mYFoobarfield = mYFoobarfield;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Foobarmyentity foobarmyentity = (Foobarmyentity) o;
        if (foobarmyentity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, foobarmyentity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Foobarmyentity{" +
            "id=" + id +
            ", mYFoobarfield='" + mYFoobarfield + "'" +
            '}';
    }
}
