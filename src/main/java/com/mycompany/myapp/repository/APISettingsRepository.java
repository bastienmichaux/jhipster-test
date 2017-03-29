package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.APISettings;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the APISettings entity.
 */
@SuppressWarnings("unused")
public interface APISettingsRepository extends JpaRepository<APISettings,Long> {

}
