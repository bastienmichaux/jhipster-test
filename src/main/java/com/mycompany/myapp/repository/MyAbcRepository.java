package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyAbc;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyAbc entity.
 */
@SuppressWarnings("unused")
public interface MyAbcRepository extends JpaRepository<MyAbc,Long> {

}
