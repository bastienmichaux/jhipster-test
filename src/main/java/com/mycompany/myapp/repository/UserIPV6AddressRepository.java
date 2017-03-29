package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserIPV6Address;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserIPV6Address entity.
 */
@SuppressWarnings("unused")
public interface UserIPV6AddressRepository extends JpaRepository<UserIPV6Address,Long> {

}
