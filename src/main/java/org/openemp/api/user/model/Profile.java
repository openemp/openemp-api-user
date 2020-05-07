package org.openemp.api.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

/**
 * Profile model.
 */
@Entity
@Getter
@Setter
public class Profile extends BaseEntity {


    @ManyToOne
    private User user;

    private String profileType;

    @ManyToMany
    private Set<Role> roles;
}
