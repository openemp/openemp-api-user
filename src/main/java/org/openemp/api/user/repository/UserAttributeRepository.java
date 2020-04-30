package org.openemp.api.user.repository;

import org.openemp.api.user.model.User;
import org.openemp.api.user.model.UserAttribute;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserAttributeRepository extends PagingAndSortingRepository<UserAttribute, Long> {

    Set<UserAttribute> getAllByUser(User user);
}
