package org.openemp.api.user.repository;

import org.openemp.api.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    @Override
    Page<User> findAll(Pageable pageable);



    /**
     * Gets by username.
     *
     * @param deleted if the user is deleted
     * @return Users
     */
    List<User> getByDeleted(Boolean deleted);

    /**
     * Gets by username.
     *
     * @param username the username
     * @return User (if not retired)
     */
    User getByUsernameAndDeletedFalse(String username);

    /**
     * Gets by uuid.
     *
     * @param uuid the user uuid
     * @return User (if not retired)
     */
    User getByUuidAndDeletedFalse(UUID uuid);

    /**
     * Gets by uuid.
     *
     * @param uuid the user uuid
     * @return User
     */
    User getByUuid(UUID uuid);

}
