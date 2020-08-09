package org.openemp.api.user.controller;

import org.openemp.api.user.model.Profile;
import org.openemp.api.user.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.openemp.api.user.util.Constant.PROFILE_ENDPOINT;

/**
 * Profile controller.
 */
@RestController
@CrossOrigin
@RequestMapping(PROFILE_ENDPOINT)
public class EmpProfileController {

    @Autowired
    private ProfileService profileService;

    /**
     * Gets profile by uuid.
     *
     * @param id the id
     * @return the profile by uuid
     */
    @GetMapping("/{id}")
    public Profile getProfileByUuid(@PathVariable Long id) {
        return profileService.getProfile(id);
    }
}
