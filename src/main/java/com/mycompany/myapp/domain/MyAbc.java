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
@Table(name = "my_abc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MyAbc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prefix_my_book_suffix")
    private String prefix_myBook_Suffix;

    @Column(name = "prefix_my_field_123_my_suffix")
    private String prefix__myField_123_MySuffix;

    @ManyToOne
    private Author author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix_myBook_Suffix() {
        return prefix_myBook_Suffix;
    }

    public MyAbc prefix_myBook_Suffix(String prefix_myBook_Suffix) {
        this.prefix_myBook_Suffix = prefix_myBook_Suffix;
        return this;
    }

    public void setPrefix_myBook_Suffix(String prefix_myBook_Suffix) {
        this.prefix_myBook_Suffix = prefix_myBook_Suffix;
    }

    public String getPrefix__myField_123_MySuffix() {
        return prefix__myField_123_MySuffix;
    }

    public MyAbc prefix__myField_123_MySuffix(String prefix__myField_123_MySuffix) {
        this.prefix__myField_123_MySuffix = prefix__myField_123_MySuffix;
        return this;
    }

    public void setPrefix__myField_123_MySuffix(String prefix__myField_123_MySuffix) {
        this.prefix__myField_123_MySuffix = prefix__myField_123_MySuffix;
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
            ", prefix_myBook_Suffix='" + prefix_myBook_Suffix + "'" +
            ", prefix__myField_123_MySuffix='" + prefix__myField_123_MySuffix + "'" +
            '}';
    }
}
