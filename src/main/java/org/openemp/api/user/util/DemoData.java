package org.openemp.api.user.util;

import java.util.HashSet;
import java.util.Set;

import org.openemp.api.user.model.AttributeType;
import org.openemp.api.user.model.Privilege;
import org.openemp.api.user.model.Role;
import org.openemp.api.user.model.User;
import org.openemp.api.user.model.UserAttribute;
import org.openemp.api.user.service.AttributeTypeService;
import org.openemp.api.user.service.PrivilegeService;
import org.openemp.api.user.service.ProfileService;
import org.openemp.api.user.service.RoleService;
import org.openemp.api.user.service.UserAttributeService;
import org.openemp.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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

	@Override
	public void run(ApplicationArguments args) throws Exception {

		AttributeType phoneNumber = new AttributeType();
		phoneNumber.setName("Phone number");
		phoneNumber = attributeTypeService.saveAttributeType(phoneNumber);

		UserAttribute adminPhoneNumber = new UserAttribute();
		adminPhoneNumber.setAttributeType(phoneNumber);
		adminPhoneNumber.setValue("0114-5100-1120");
		adminPhoneNumber = userAttributeService.save(adminPhoneNumber);

		User admin = new User();
		admin.setEmail("admin@mail.me");
		admin.setUsername("admin");
		admin.setActive(true);
		admin.setRetired(false);
		admin.setPassword("P@$$w0rd");
		Set<UserAttribute> adminAttributes = new HashSet<>();
		adminAttributes.add(adminPhoneNumber);
		admin.setAttributes(adminAttributes);
		admin.setType("ADMIN");
		
		admin = userService.saveUser(admin);
		
		org.openemp.api.user.model.Profile profile = new org.openemp.api.user.model.Profile();
		profile.setUser(admin);
		profile.setProfileType("ADMIN");
		profile = profileService.saveProfile(profile);
		
		Role role = new Role();
		role.setName("ADMIN_ROLE_1");
		role.setPrivileges(createPrivileges());
		role = roleService.saveRole(role);

		profile.getRoles().add(role);
		profileService.saveProfile(profile);

	}
	
	/**
	 * Store privileges.
	 * @return {@link Set} of {@link Privilege}.
	 */
	private Set<Privilege> createPrivileges() {
		Set<Privilege> privileges = new HashSet<>();
		
		Privilege privilege1 = new Privilege();
		privilege1.setName("ADMIN_PRIVILEGE_1");
		privilege1 = privilegeService.savePrivilege(privilege1);
		privileges.add(privilege1);
		
		Privilege privilege2 = new Privilege();
		privilege2.setName("ADMIN_PRIVILEGE_2");
		privilege2 = privilegeService.savePrivilege(privilege2);
		privileges.add(privilege2);
		
		Privilege privilege3 = new Privilege();
		privilege3.setName("ADMIN_PRIVILEGE_3");
		privilege3 = privilegeService.savePrivilege(privilege3);
		privileges.add(privilege3);
		
		return privileges;
	}
}
