package org.openemp.api.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

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
