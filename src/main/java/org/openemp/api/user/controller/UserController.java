package org.openemp.api.user.controller;


import org.openemp.api.user.exception.WrongCredentialsException;
import org.openemp.api.user.model.User;
import org.openemp.api.user.service.EmpUserDetailsService;
import org.openemp.api.user.service.UserService;
import org.openemp.api.user.util.Constant;
import org.openemp.api.user.util.JwtTokenUtil;
import org.openemp.api.user.exception.UserDisabledException;
import org.openemp.api.user.security.model.JwtRequest;
import org.openemp.api.user.security.model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceAlreadyExistsException;

/**
 * User controller.
 */
@RestController
@CrossOrigin
@RequestMapping(Constant.USER_ENDPOINT)
public class UserController {

    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EmpUserDetailsService userDetailsService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets user by username.
     *
     * @param username the username
     * @return the user by username
     */
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Authenticate jwt response.
     *
     * @param authenticationRequest the authentication request
     * @return the jwt response
     */
    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }

    /**
     * Save user user.
     *
     * @param user the user
     * @return the user
     * @throws InstanceAlreadyExistsException the instance already exists exception
     */
    @PostMapping
    public User saveUser(@RequestBody User user) throws InstanceAlreadyExistsException {
        return userService.saveUser(user);
    }

    /**
     * Delete mapping user.
     *
     * @param user the user
     * @return the user
     */
    @DeleteMapping
    public User deleteMapping(@RequestBody User user) {
        return userService.deleteUser(user);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisabledException(username);
        } catch (BadCredentialsException e) {
            throw  new WrongCredentialsException();
        }
    }
}
