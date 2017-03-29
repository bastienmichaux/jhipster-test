package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PreferredServerAPISettings;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PreferredServerAPISettings entity.
 */
@SuppressWarnings("unused")
public interface PreferredServerAPISettingsRepository extends JpaRepository<PreferredServerAPISettings,Long> {

}
