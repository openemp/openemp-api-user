package org.openemp.api.user.service;

import org.openemp.api.user.model.User;
import org.openemp.api.user.model.UserAttribute;
import org.openemp.api.user.repository.UserAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * User attribute service.
 */
@Service
public class UserAttributeService {


    private final UserAttributeRepository userAttributeRepository;

    @Autowired
    private UserService userService;

    public UserAttributeService(UserAttributeRepository userAttributeRepository) {
        this.userAttributeRepository = userAttributeRepository;
    }

    /**
     * Gets user attributes.
     *
     * @param uuid the user UUID
     * @return the user attributes
     */
    public Set<UserAttribute> getUserAttributes(String uuid) {

        User user = userService.getUserByUuid(uuid, Boolean.FALSE);

        return userAttributeRepository.getAllByUser(user);
    }

    /**
     * Save user attribute.
     *
     * @param userAttribute the user attribute
     * @return the saved user attribute
     */
    public UserAttribute save(UserAttribute userAttribute) {
        return userAttributeRepository.save(userAttribute);
    }
}
