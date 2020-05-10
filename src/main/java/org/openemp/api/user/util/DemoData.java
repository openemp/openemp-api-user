package org.openemp.api.user.util;

import org.openemp.api.user.model.AttributeType;
import org.openemp.api.user.model.Role;
import org.openemp.api.user.model.User;
import org.openemp.api.user.model.UserAttribute;
import org.openemp.api.user.service.AttributeTypeService;
import org.openemp.api.user.service.ProfileService;
import org.openemp.api.user.service.RoleService;
import org.openemp.api.user.service.UserAttributeService;
import org.openemp.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

		userService.saveUser(admin);

	}
}
