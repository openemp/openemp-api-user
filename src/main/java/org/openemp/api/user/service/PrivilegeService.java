package org.openemp.api.user.service;

import org.openemp.api.user.model.Privilege;
import org.openemp.api.user.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService {

	@Autowired
	private PrivilegeRepository privilegeRepository;

	public Privilege savePrivilege(Privilege privilege) {
		return privilegeRepository.save(privilege);
	}

}
