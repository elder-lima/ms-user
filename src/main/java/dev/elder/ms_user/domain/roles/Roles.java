package dev.elder.ms_user.domain.roles;

import dev.elder.ms_user.domain.roles.enums.RoleName;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TB_ROLES")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName nome;

    public Roles() {
    }

    public Roles(RoleName nome) {
        this.nome = nome;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public RoleName getNome() {
        return nome;
    }

    public void setNome(RoleName nome) {
        this.nome = nome;
    }
}
