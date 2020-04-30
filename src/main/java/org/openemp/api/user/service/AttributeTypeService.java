package org.openemp.api.user.service;

import org.openemp.api.user.model.AttributeType;
import org.openemp.api.user.repository.AttributeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AttributeTypeService {

	@Autowired
	private AttributeTypeRepository attributeTypeRepository;

	public AttributeType saveAttributeType(AttributeType attributeType) {
		return attributeTypeRepository.save(attributeType);
	}

	public Set<AttributeType> getAll() {
		return (Set<AttributeType>) attributeTypeRepository.findAll();
	}

	public void deleteAttributeType(AttributeType attributeType) {
		attributeTypeRepository.delete(attributeType);
	}

	public AttributeType getAttributeType(String attributeTypeName) {
		return attributeTypeRepository.findByName(attributeTypeName).orElse(null);
	}

}
