package org.openemp.api.user.service;

import org.openemp.api.user.exception.UserNotFoundException;
import org.openemp.api.user.model.User;
import org.openemp.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * User details service.
 */
@Service
public class EmpUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {

        Set<GrantedAuthority> authorities = new HashSet<>();

        User user = userRepository.getByUsernameAndRetiredFalse(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        authorities.add(new SimpleGrantedAuthority(user.getType()));

        user.getProfiles().stream().forEach(p -> {
            p.getRoles().stream().forEach(r -> {
                r.getPrivileges().stream().map(pr -> new SimpleGrantedAuthority(pr.getName()))
                        .forEach(authorities::add);
            });
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

}
