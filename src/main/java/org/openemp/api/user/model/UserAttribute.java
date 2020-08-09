package org.openemp.api.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * User attribute model.
 */
@Entity
@Table(name = "user_attributes")
@Getter
@Setter
public class UserAttribute extends BaseEntity {

    private static final long serialVersionUID = 954510045293626631L;

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "name")
    private AttributeType attributeType;

}
