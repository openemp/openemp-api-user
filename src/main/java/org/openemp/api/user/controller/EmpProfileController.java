package org.openemp.api.user.controller;

import org.openemp.api.user.model.Profile;
import org.openemp.api.user.security.model.JwtResponse;
import org.openemp.api.user.service.EmpUserDetailsService;
import org.openemp.api.user.service.ProfileService;
import org.openemp.api.user.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtException;

import java.util.UUID;

import static org.openemp.api.user.util.Constant.PROFILE_ENDPOINT;

/**
 * Profile controller.
 */
@RestController
@CrossOrigin
@RequestMapping(PROFILE_ENDPOINT)
public class EmpProfileController {

    private final EmpUserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtils;

    

    @Autowired
    private ProfileService profileService;

    public EmpProfileController(JwtTokenUtil jwtTokenUtil, EmpUserDetailsService userDetailsService) {
        this.jwtTokenUtils = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Gets profile by uuid.
     *
     * @param uuid the uuid
     * @return the profile by uuid
     */
    @GetMapping("/{id}")
    public Profile getProfileByUuid(@PathVariable UUID uuid) {
        return profileService.getProfile(uuid);
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "uuid") String uuid) {

        String username = jwtTokenUtils.getSubjectProperty("username", token.split(" ")[1]);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String profileToken = jwtTokenUtils.generateProfileToken(userDetails, uuid);

        if (profileToken.isEmpty()) throw new JwtException("Unauthorized");
        return new JwtResponse(profileToken);

    } 


}
