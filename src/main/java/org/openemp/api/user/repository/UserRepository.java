package org.openemp.api.user.repository;

import org.openemp.api.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User getByUsernameAndRetiredFalse(String username);
}
