package org.openemp.api.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openemp.api.user.controller.UserController;
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

@SpringBootTest(classes = OpenempUserApiApplication.class)
public class OpenEMPSecurityTests {

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

		// Generate a token with user name.
		JwtResponse tokenWithProfiles = new JwtResponse(
				jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername("admin"), false));

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
		profileService.delete(profileService.getProfile(1L));

		// Generate a token with user name.
		JwtResponse tokenWithoutProfiles = new JwtResponse(
				jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername("admin"), false));
		assertThat(tokenWithProfiles.toString()).isNotEqualTo(tokenWithoutProfiles.toString());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAuthorizeWithRememberMe() throws Exception {
		// Generate a token with user name.
		JwtResponse tokenWithoutRemeberme = new JwtResponse(
				jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername("admin"), false));

		// Generate a token with user name.
		JwtResponse tokenWithRemeberme = new JwtResponse(
				jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername("admin"), true));
		
		// Tokens not null.
		assertThat(tokenWithoutRemeberme).isNotNull();
		assertThat(tokenWithRemeberme).isNotNull();
		
		// Getting expiration dates from tokens.
		Date expirationWithoutRememberme = jwtTokenUtil.getExpirationDateFromToken(tokenWithoutRemeberme.getToken());
		Date expirationWithRememberme = jwtTokenUtil.getExpirationDateFromToken(tokenWithRemeberme.getToken());
		
		assertThat(expirationWithoutRememberme).isNotNull();
		assertThat(expirationWithRememberme).isNotNull();
		assertThat(expirationWithoutRememberme).isBefore(expirationWithRememberme);
		assertThat(expirationWithRememberme).isAfter(expirationWithoutRememberme);
		
		// Check the 7 days for the token with the remember me option.
		Date dateToTest = new Date(System.currentTimeMillis() + Constant.JWT_TOKEN_VALIDITY_WITH_REMEMBERME);
		
		assertThat(dateToTest.getDay()).isEqualTo(expirationWithRememberme.getDay());
		assertThat(dateToTest.getMonth()).isEqualTo(expirationWithRememberme.getMonth());
		assertThat(dateToTest.getYear()).isEqualTo(expirationWithRememberme.getYear());
	}
}
