package org.openemp.api.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Role model.
 */
@Entity
@Getter
@Setter
public class Role extends BaseEntity {

    private static final long serialVersionUID = -6132338183111628177L;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Set<Profile> profiles = new HashSet<>();

}
