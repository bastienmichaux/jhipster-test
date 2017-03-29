package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FooBarAPISettings;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FooBarAPISettings entity.
 */
@SuppressWarnings("unused")
public interface FooBarAPISettingsRepository extends JpaRepository<FooBarAPISettings,Long> {

}
