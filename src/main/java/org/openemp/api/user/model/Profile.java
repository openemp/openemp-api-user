package org.openemp.api.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

/**
 * Profile model.
 */
@Entity
@Getter
@Setter
public class Profile extends BaseEntity {
	
	private static final long serialVersionUID = 6290907861094618128L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    private String profileType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "profiles_roles", 
        joinColumns = @JoinColumn(
          name = "profile_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
}
