package org.openemp.api.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Privilege model.
 */
@Entity
@Setter
@Getter
public class Privilege extends BaseEntity {

    private static final long serialVersionUID = -7020441741588863617L;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    @JsonBackReference
    private Set<Role> roles = new HashSet<>();

}
