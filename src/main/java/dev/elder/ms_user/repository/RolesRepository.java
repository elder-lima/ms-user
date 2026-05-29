package dev.elder.ms_user.repository;

import dev.elder.ms_user.domain.roles.Roles;
import dev.elder.ms_user.domain.roles.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {

    Optional<Roles> findByNome(RoleName nome);

}
