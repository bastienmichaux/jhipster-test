package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserIPV6Address.
 */
@Entity
@Table(name = "ipv6address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserIPV6Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_ipv_6")
    private String addressIPV6;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressIPV6() {
        return addressIPV6;
    }

    public UserIPV6Address addressIPV6(String addressIPV6) {
        this.addressIPV6 = addressIPV6;
        return this;
    }

    public void setAddressIPV6(String addressIPV6) {
        this.addressIPV6 = addressIPV6;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserIPV6Address userIPV6Address = (UserIPV6Address) o;
        if (userIPV6Address.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userIPV6Address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserIPV6Address{" +
            "id=" + id +
            ", addressIPV6='" + addressIPV6 + "'" +
            '}';
    }
}
