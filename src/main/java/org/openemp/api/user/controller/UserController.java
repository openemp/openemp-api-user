package org.openemp.api.user.controller;


import org.openemp.api.user.exception.UserDisabledException;
import org.openemp.api.user.exception.WrongCredentialsException;
import org.openemp.api.user.model.User;
import org.openemp.api.user.security.model.JwtRequest;
import org.openemp.api.user.security.model.JwtResponse;
import org.openemp.api.user.service.EmpUserDetailsService;
import org.openemp.api.user.service.UserService;
import org.openemp.api.user.util.Constant;
import org.openemp.api.user.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

/**
 * User controller.
 */
@RestController
@CrossOrigin
@RequestMapping(Constant.USER_ENDPOINT)
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final EmpUserDetailsService userDetailsService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, EmpUserDetailsService userDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Gets user by username.
     *
     * @param uuid the user's uuid
     * @return the user by uuid
     */
    @GetMapping("/{uuid}")
    public User getUserByUuid(@PathVariable String uuid) {
        return userService.getUserByUuid(uuid);
    }

    /**
     * Gets all users.
     *
     * @return the user by uuid
     */
    @GetMapping
    public List<User> getUsers(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortDir") String sortDir,
            @PathVariable("sort") String sort
    ) {
        List<User> posts = userService.getUsersList(page, size, sortDir, sort);
        return new ArrayList<>(posts);
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
            throw new WrongCredentialsException();
        }
    }
}
