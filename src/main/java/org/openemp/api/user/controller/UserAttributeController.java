package org.openemp.api.user.controller;


import org.openemp.api.user.model.UserAttribute;
import org.openemp.api.user.service.UserAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.openemp.api.user.util.Constant.USER_ATTRIBUTE_DOMAIN;

/**
 * User attribute controller.
 */
@RestController
@CrossOrigin
@RequestMapping(USER_ATTRIBUTE_DOMAIN)
public class UserAttributeController {

    @Autowired
    private UserAttributeService userAttributeService;

    /**
     * Gets attributes by username.
     *
     * @param username the username
     * @return the attributes by username
     */
    @GetMapping("/{username}")
    public Set<UserAttribute> getAttributesByUsername(@PathVariable String username) {

        return userAttributeService.getUserAttributes(username);
    }

    /**
     * Save user attribute.
     *
     * @param userAttribute the user attribute
     * @return the user attribute
     */
    @PostMapping
    public UserAttribute save(@RequestBody UserAttribute userAttribute) {
        return userAttributeService.save(userAttribute);
    }


}
