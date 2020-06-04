package org.openemp.api.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openemp.api.user.controller.UserController;
import org.openemp.api.user.model.AttributeType;
import org.openemp.api.user.model.Privilege;
import org.openemp.api.user.model.Role;
import org.openemp.api.user.model.User;
import org.openemp.api.user.model.UserAttribute;
import org.openemp.api.user.security.model.JwtRequest;
import org.openemp.api.user.security.model.JwtResponse;
import org.openemp.api.user.service.AttributeTypeService;
import org.openemp.api.user.service.EmpUserDetailsService;
import org.openemp.api.user.service.PrivilegeService;
import org.openemp.api.user.service.ProfileService;
import org.openemp.api.user.service.RoleService;
import org.openemp.api.user.service.UserAttributeService;
import org.openemp.api.user.service.UserService;
import org.openemp.api.user.util.Constant;
import org.openemp.api.user.util.JwtTokenUtil;
import org.openemp.api.util.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * The type Openemp user api application tests.
 */
@SpringBootTest(classes = OpenempUserApiApplication.class)
class OpenempUserApiApplicationTests {

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

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	EmpUserDetailsService userDetailsService;

	private static final String APPLICATION_JSON_VALUE = "application/json";

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		UserController userController = new UserController(userService, authenticationManager, jwtTokenUtil,
				userDetailsService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testAuthorize() throws Exception {

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
		profile = profileService.saveProfile(profile);

		// Generate a token with user name.
		JwtResponse tokenWithProfiles = new JwtResponse(
				jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(admin.getUsername())));

		// Request authentication.
		JwtRequest login = new JwtRequest();
		login.setUsername("admin");
		login.setPassword("P@$$w0rd");
		String loginToken = mockMvc
				.perform(post(Constant.AUTH_LOGIN_URL).contentType(APPLICATION_JSON_VALUE)
						.content(TestUtil.convertObjectToJsonBytes(login)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		// Token is not exists.
		assertThat(loginToken).isNotNull();
		assertThat(loginToken).contains("token");

		// Remove profiles.
		profileService.delete(profile);

		// Generate a token with user name.
		JwtResponse tokenWithoutProfiles = new JwtResponse(
				jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(admin.getUsername())));
		assertThat(tokenWithProfiles.toString()).isNotEqualTo(tokenWithoutProfiles.toString());
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
