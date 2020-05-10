package org.openemp.api.user.service;

import org.openemp.api.user.model.AttributeType;
import org.openemp.api.user.repository.AttributeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Attribute type service.
 */
@Service
public class AttributeTypeService {

	@Autowired
	private AttributeTypeRepository attributeTypeRepository;

	/**
	 * Save attribute type.
	 *
	 * @param attributeType the attribute type
	 * @return the attribute type
	 */
	public AttributeType saveAttributeType(AttributeType attributeType) {
		return attributeTypeRepository.save(attributeType);
	}

	public Set<AttributeType> getAll() {
		return (Set<AttributeType>) attributeTypeRepository.findAll();
	}

	/**
	 * Delete attribute type.
	 *
	 * @param attributeType the attribute type
	 */
	public void deleteAttributeType(AttributeType attributeType) {
		attributeTypeRepository.delete(attributeType);
	}

	/**
	 * Get attribute type.
	 *
	 * @param attributeTypeName the attribute type name
	 * @return AttributeType if exist otherwise null
	 */
	public AttributeType getAttributeType(String attributeTypeName) {
		return attributeTypeRepository.findByName(attributeTypeName).orElse(null);
	}

}
