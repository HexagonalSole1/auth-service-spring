package com.vallhallatech.authservice.persistance.repositories;

import com.vallhallatech.authservice.persistance.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
