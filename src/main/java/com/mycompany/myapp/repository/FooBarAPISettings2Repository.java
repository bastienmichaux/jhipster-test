package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FooBarAPISettings2;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FooBarAPISettings2 entity.
 */
@SuppressWarnings("unused")
public interface FooBarAPISettings2Repository extends JpaRepository<FooBarAPISettings2,Long> {

}
