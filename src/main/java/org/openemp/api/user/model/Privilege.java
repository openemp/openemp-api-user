package org.openemp.api.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
public class Privilege extends BaseEntity{

	private String name;

}
