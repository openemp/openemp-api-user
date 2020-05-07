package org.openemp.api.user.service;

import org.openemp.api.user.model.User;
import org.openemp.api.user.repository.UserRepository;
import org.openemp.api.user.exception.UserNotFoundException;
import org.openemp.api.user.model.UserAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashSet;
import java.util.Set;

/**
 * The type User service.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

	public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	/**
	 * Gets user by username.
	 *
	 * @param username the username
	 * @return the user
	 */
	public User getUserByUsername(String username) {
        User user = userRepository.getByUsernameAndRetiredFalse(username);
        if (user != null) return user;
        throw new UserNotFoundException(username);
    }

	/**
	 * Save user user.
	 *
	 * @param user the user
	 * @return the saved user
	 * @throws InstanceAlreadyExistsException the instance already exists exception
	 */
	public User saveUser(User user) throws InstanceAlreadyExistsException {

        User userExist = userRepository.getByUsernameAndRetiredFalse(user.getUsername());
        if (userExist != null) throw new InstanceAlreadyExistsException();
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Set<UserAttribute> attributes = new HashSet<>();

        for (UserAttribute attribute : user.getAttributes()) {
            UserAttribute userAttribute = new UserAttribute();
            userAttribute.setUser(user);
            userAttribute.setValue(attribute.getValue());
            attributes.add(userAttribute);
        }
        user.setAttributes(attributes);
        return userRepository.save(user);
    }

	/**
	 * Delete user user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User deleteUser(User user) {
        user.setRetired(true);
        return userRepository.save(user);
    }
}
