package org.openemp.api.user.service;

import org.openemp.api.user.model.Profile;
import org.openemp.api.user.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
     * @param uuid the id
     * @return the profile
     */
    public Profile getProfile(UUID uuid) {
        return profileRepository.getByUuidAndDeletedFalse(uuid);
    }

    /**
     * Save {@link Profile}.
     *
     * @param profile the profile to save {@link Profile}.
     * @return the saved profile {@link Profile}.
     */
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    /**
     * Delete a profile.
     *
     * @param profile the profile to delete.
     */
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

}
