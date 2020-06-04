package org.openemp.api.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * Privilege model.
 */
@Entity
@Setter
@Getter
public class Privilege extends BaseEntity{
	
	private static final long serialVersionUID = -7020441741588863617L;
	
	private String name;
	
	@ManyToMany(mappedBy = "privileges")
    private Set<Role> roles = new HashSet<>();

}
