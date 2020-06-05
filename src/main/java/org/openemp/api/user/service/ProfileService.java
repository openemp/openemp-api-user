package org.openemp.api.user.service;

import org.openemp.api.user.exception.ResourceNotFoundException;
import org.openemp.api.user.model.Profile;
import org.openemp.api.user.repository.ProfileRepository;
import org.springframework.stereotype.Service;

/**
 * Profile service.
 */
@Service
public class ProfileService {

	private final ProfileRepository profileRepository;

	public ProfileService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	/**
	 * Gets profile.
	 *
	 * @param id the id
	 * @return the profile
	 */
	public Profile getProfile(Long id) {
		return profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}
	
	/**
	 * Save {@link Profile}.
	 * @param profile the profile to save {@link Profile}.
	 * @return the saved profile {@link Profile}.
	 */
	public Profile saveProfile(Profile profile) {
		return profileRepository.save(profile);
	}
	
	/**
	 * Delete a profile.
	 * @param profile the profile to delete.
	 */
	public void delete(Profile profile) {
		profileRepository.delete(profile);
	}
	
}
