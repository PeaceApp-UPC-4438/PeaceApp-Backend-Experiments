package com.upc.pre.peaceapp.security.iam.domain.model.aggregates;

import com.upc.pre.peaceapp.security.iam.domain.model.entities.Role;
import com.upc.pre.peaceapp.shared.documentation.models.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "iam_users")
public class User extends AuditableAbstractAggregateRoot<User> {
    @Setter
    @NotBlank
    @Getter
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @Setter
    @Getter
    @NotBlank
    @Size(max = 120)
    private String password;


    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();

    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();

    }

    public User(String username, String password, List<Role> roles) {
        this(username, password);
        addRoles(roles);

    }

    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public User addRoles(List<Role> roles) {
        //var validatedRoles = Role.validateRoleSet(roles);
        this.roles.addAll(roles);
        return this;
    }
}
