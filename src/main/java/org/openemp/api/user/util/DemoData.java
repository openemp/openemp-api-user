package org.openemp.api.user.util;

import org.openemp.api.user.model.*;
import org.openemp.api.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.openemp.api.user.util.Constant.ADMIN_PROFILE_UUID;
import static org.openemp.api.user.util.Constant.ADMIN_USER_UUID;

@Profile("dev")
@Component
public class DemoData implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserAttributeService userAttributeService;

    @Autowired
    AttributeTypeService attributeTypeService;

    @Autowired
    PrivilegeService privilegeService;

    UUID rootUuid = UUID.fromString(ADMIN_USER_UUID);

    @Override
    public void run(ApplicationArguments args) throws Exception {



        AttributeType phoneNumber = new AttributeType();
        phoneNumber.setName("Phone number");
        phoneNumber.setCreatedBy(rootUuid);
        phoneNumber = attributeTypeService.saveAttributeType(phoneNumber);

        UserAttribute adminPhoneNumber = new UserAttribute();
        adminPhoneNumber.setAttributeType(phoneNumber);
        adminPhoneNumber.setValue("0114-5100-1120");
        adminPhoneNumber.setCreatedBy(rootUuid);
        adminPhoneNumber = userAttributeService.save(adminPhoneNumber);

        User admin = new User();
        admin.setEmail("admin@mail.me");
        admin.setUsername("admin");
        admin.setActive(true);
        admin.setDeleted(false);
        admin.setPassword("P@$$w0rd");
        Set<UserAttribute> adminAttributes = new HashSet<>();
        adminAttributes.add(adminPhoneNumber);
        admin.setAttributes(adminAttributes);
        admin.setType("ADMIN");
        admin.setCreatedBy(rootUuid);

        admin = userService.saveUser(admin);

        org.openemp.api.user.model.Profile profile = new org.openemp.api.user.model.Profile();
        profile.setUser(admin);
        profile.setProfileType("ADMIN");
        profile.setUuid(UUID.fromString(ADMIN_PROFILE_UUID));
        profile.setCreatedBy(rootUuid);
        profile = profileService.saveProfile(profile);

        Role role = new Role();
        role.setName("ADMIN_ROLE_1");
        role.setPrivileges(createPrivileges());
        role.setCreatedBy(rootUuid);
        role = roleService.saveRole(role);

        profile.getRoles().add(role);
        profile.setCreatedBy(rootUuid);
        profileService.saveProfile(profile);

    }

    /**
     * Store privileges.
     *
     * @return {@link Set} of {@link Privilege}.
     */
    private Set<Privilege> createPrivileges() {
        Set<Privilege> privileges = new HashSet<>();

        Privilege privilege1 = new Privilege();
        privilege1.setName("ADMIN_PRIVILEGE_1");
        privilege1.setCreatedBy(rootUuid);
        privilege1 = privilegeService.savePrivilege(privilege1);
        privileges.add(privilege1);

        Privilege privilege2 = new Privilege();
        privilege2.setName("ADMIN_PRIVILEGE_2");
        privilege2.setCreatedBy(rootUuid);
        privilege2 = privilegeService.savePrivilege(privilege2);
        privileges.add(privilege2);

        Privilege privilege3 = new Privilege();
        privilege3.setName("ADMIN_PRIVILEGE_3");
        privilege3.setCreatedBy(rootUuid);
        privilege3 = privilegeService.savePrivilege(privilege3);
        privileges.add(privilege3);

        return privileges;
    }
}
