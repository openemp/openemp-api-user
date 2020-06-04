package org.openemp.api.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * User model.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class  User extends BaseEntity {
	
	private static final long serialVersionUID = 9017827961351102792L;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Profile> profiles = new HashSet<>();

    private String type;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserAttribute> attributes = new HashSet<>();

}
