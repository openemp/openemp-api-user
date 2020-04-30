package org.openemp.api.user.controller;

import org.openemp.api.user.model.AttributeType;
import org.openemp.api.user.util.Constant;
import org.openemp.api.user.service.AttributeTypeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(Constant.ATTRIBUTE_TYPE_ENDPOINT)
public class AttributeTypeController {


	private AttributeTypeService attributeTypeService;

	@GetMapping("/{name}")
	public AttributeType getAttributeType(@PathVariable String name) {
		return attributeTypeService.getAttributeType(name);
	}

	@GetMapping()
	public Set<AttributeType> getAllAttributeTypes() {
		return attributeTypeService.getAll();
	}

	@PostMapping
	public AttributeType save(AttributeType attributeType) {
		return attributeTypeService.saveAttributeType(attributeType);
	}

	@DeleteMapping
	public void deleteAttributeType(AttributeType attributeType) {
		attributeTypeService.deleteAttributeType(attributeType);
	}



}
