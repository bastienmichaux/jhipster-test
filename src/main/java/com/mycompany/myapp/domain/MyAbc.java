package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MyAbc.
 */
@Entity
@Table(name = "MyAbcCamelCase")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MyAbc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prefix_my_book_suffix")
    private String Prefix_MyBook_Suffix;

    @Column(name = "prefix_my_field_123_my_suffix")
    private String Prefix__MyField123_MySuffix;

    @ManyToOne
    private Author author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix_MyBook_Suffix() {
        return Prefix_MyBook_Suffix;
    }

    public MyAbc Prefix_MyBook_Suffix(String Prefix_MyBook_Suffix) {
        this.Prefix_MyBook_Suffix = Prefix_MyBook_Suffix;
        return this;
    }

    public void setPrefix_MyBook_Suffix(String Prefix_MyBook_Suffix) {
        this.Prefix_MyBook_Suffix = Prefix_MyBook_Suffix;
    }

    public String getPrefix__MyField123_MySuffix() {
        return Prefix__MyField123_MySuffix;
    }

    public MyAbc Prefix__MyField123_MySuffix(String Prefix__MyField123_MySuffix) {
        this.Prefix__MyField123_MySuffix = Prefix__MyField123_MySuffix;
        return this;
    }

    public void setPrefix__MyField123_MySuffix(String Prefix__MyField123_MySuffix) {
        this.Prefix__MyField123_MySuffix = Prefix__MyField123_MySuffix;
    }

    public Author getAuthor() {
        return author;
    }

    public MyAbc author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyAbc myAbc = (MyAbc) o;
        if (myAbc.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, myAbc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyAbc{" +
            "id=" + id +
            ", Prefix_MyBook_Suffix='" + Prefix_MyBook_Suffix + "'" +
            ", Prefix__MyField123_MySuffix='" + Prefix__MyField123_MySuffix + "'" +
            '}';
    }
}
