package org.openemp.api.user.repository;

import org.openemp.api.user.model.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {

}
