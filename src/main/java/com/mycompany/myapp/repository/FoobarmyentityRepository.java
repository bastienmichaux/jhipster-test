package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Foobarmyentity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Foobarmyentity entity.
 */
@SuppressWarnings("unused")
public interface FoobarmyentityRepository extends JpaRepository<Foobarmyentity,Long> {

}
