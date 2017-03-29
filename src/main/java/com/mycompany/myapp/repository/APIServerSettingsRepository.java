package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.APIServerSettings;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the APIServerSettings entity.
 */
@SuppressWarnings("unused")
public interface APIServerSettingsRepository extends JpaRepository<APIServerSettings,Long> {

}
