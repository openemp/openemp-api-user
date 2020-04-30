package org.openemp.api.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class AttributeType extends BaseEntity {

	private String name;
}
