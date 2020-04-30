package org.openemp.api.user.repository;

import org.openemp.api.user.model.AttributeType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AttributeTypeRepository extends CrudRepository<AttributeType, Long> {

	Optional<AttributeType> findByName(String name);
}
