package org.openemp.api.user.repository;

import org.openemp.api.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

	/**
	 * Gets by username.
	 *
	 * @param username the username
	 * @return User (if not retired)
	 */
	User getByUsernameAndRetiredFalse(String username);
}
