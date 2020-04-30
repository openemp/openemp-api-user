package org.openemp.api.user.service;

import org.openemp.api.user.model.User;
import org.openemp.api.user.model.UserAttribute;
import org.openemp.api.user.repository.UserAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserAttributeService {


    private final UserAttributeRepository userAttributeRepository;

    @Autowired
    private UserService userService;

    public UserAttributeService(UserAttributeRepository userAttributeRepository) {
        this.userAttributeRepository = userAttributeRepository;
    }

    public Set<UserAttribute> getUserAttributes(String username) {

        User user = userService.getUserByUsername(username);

        return userAttributeRepository.getAllByUser(user);
    }

    public UserAttribute save(UserAttribute userAttribute) {
        return userAttributeRepository.save(userAttribute);
    }
}
