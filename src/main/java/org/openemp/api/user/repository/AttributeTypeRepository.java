package org.openemp.api.user.repository;

import org.openemp.api.user.model.AttributeType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Attribute type repository.
 */
public interface AttributeTypeRepository extends CrudRepository<AttributeType, Long> {

    /**
     * Find by name (optional)
     *
     * @param name the attribute name
     * @return User (if found)
     */
    Optional<AttributeType> findByName(String name);
}
