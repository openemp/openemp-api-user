package org.openemp.api.user.controller;


import org.openemp.api.user.model.UserAttribute;
import org.openemp.api.user.service.UserAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.openemp.api.user.util.Constant.USER_ATTRIBUTE_ENDPOINT;

/**
 * User attribute controller.
 */
@RestController
@CrossOrigin
@RequestMapping(USER_ATTRIBUTE_ENDPOINT)
public class UserAttributeController {

    @Autowired
    private UserAttributeService userAttributeService;

    /**
     * Gets attributes by username.
     *
     * @param uuid the UUID
     * @return the attributes by User's UUID
     */
    @GetMapping("/{uuid}")
    public Set<UserAttribute> getAttributesByUuid(@PathVariable String uuid) {

        return userAttributeService.getUserAttributes(uuid);
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
