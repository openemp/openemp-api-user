package org.openemp.api.user.repository;

import org.openemp.api.user.model.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Profile repository.
 */
@Repository
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {


}
