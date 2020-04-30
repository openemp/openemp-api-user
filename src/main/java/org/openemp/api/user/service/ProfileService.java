package org.openemp.api.user.service;

import org.openemp.api.user.exception.ResourceNotFoundException;
import org.openemp.api.user.model.Profile;
import org.openemp.api.user.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

	private final ProfileRepository profileRepository;

	public ProfileService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	public Profile getProfile(Long id) {
		return profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}
}
