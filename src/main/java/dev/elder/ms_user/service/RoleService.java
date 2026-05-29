package dev.elder.ms_user.service;

import dev.elder.ms_user.domain.roles.Roles;
import dev.elder.ms_user.domain.roles.enums.RoleName;
import dev.elder.ms_user.domain.roles.exceptions.RoleExceptionNotFound;
import dev.elder.ms_user.repository.RolesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RolesRepository rolesRepository;

    public RoleService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Transactional(readOnly = true)
    public Roles findByNome(RoleName roleName) {
        Roles role = rolesRepository.findByNome(roleName).orElseThrow(() -> new RoleExceptionNotFound("Role not found: "+ roleName));
        return role;
    }

}
