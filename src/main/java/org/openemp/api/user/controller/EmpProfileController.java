package org.openemp.api.user.controller;

import org.openemp.api.user.service.ProfileService;
import org.openemp.api.user.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.openemp.api.user.util.Constant.PROFILE_ENDPOINT;

@RestController
@CrossOrigin
@RequestMapping(PROFILE_ENDPOINT)
public class EmpProfileController {

	@Autowired
	private ProfileService profileService;

	@GetMapping("/{id}")
	public Profile getProfileByUuid(@PathVariable Long id) {
		return profileService.getProfile(id);
	}
}
