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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return userService.getUserByUuid(uuid, Boolean.FALSE);
    }

    /**
     * Gets all users.
     *
     * @return the user by uuid
     */
    @GetMapping
    public List<User> getUsers(
            @RequestParam(name = "page", defaultValue = "1") String page,
            @RequestParam(name = "size", defaultValue = "15") String size,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "sort", defaultValue = "asc") String sort,
            @RequestParam(name = "deleted", defaultValue = "0") Integer deleted
    ) {
        List<User> posts = userService.getUsersList(Integer.parseInt(page), Integer.parseInt(size), sortDir, sort, deleted);
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

        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisabledException(username);
        } catch (BadCredentialsException e) {
            throw new WrongCredentialsException();
        }

//        authenticate(, );

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
    public User saveUser(@RequestBody User user, @RequestHeader(value = "Authorization") String token) throws InstanceAlreadyExistsException {
        String userType = jwtTokenUtil.getSubjectProperty("type", token.split(" ")[1]);

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

}
