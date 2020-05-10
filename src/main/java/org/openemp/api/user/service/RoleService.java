package org.openemp.api.user.service;

import org.openemp.api.user.model.Role;
import org.openemp.api.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Role service.
 */
@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}
}
