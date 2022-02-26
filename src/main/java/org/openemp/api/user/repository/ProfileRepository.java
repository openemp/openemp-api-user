package org.openemp.api.user.repository;

import org.openemp.api.user.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Profile repository.
 */
@Repository
public interface ProfileRepository extends PagingAndSortingRepository<Profile, String> {

    @Override
    Page<Profile> findAll(Pageable pageable);

    Profile getByUuidAndDeletedFalse(UUID uuid);

}
