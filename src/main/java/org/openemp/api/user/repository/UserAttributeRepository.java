package org.openemp.api.user.repository;

import org.openemp.api.user.model.User;
import org.openemp.api.user.model.UserAttribute;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * User attribute repository.
 */
@Repository
public interface UserAttributeRepository extends PagingAndSortingRepository<UserAttribute, Long> {

	/**
	 * Gets all by user.
	 *
	 * @param user the user
	 * @return a set of user attributes
	 */
	Set<UserAttribute> getAllByUser(User user);
}
