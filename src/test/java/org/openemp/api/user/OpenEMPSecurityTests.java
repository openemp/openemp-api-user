package org.openemp.api.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import net.minidev.json.parser.ParseException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private static final String LOCALHOST_9000 = "http://localhost:9000";

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        UserController userController = new UserController(userService, authenticationManager, jwtTokenUtil,
                userDetailsService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void authenticateShouldReturnToken() throws Exception {


        // Request authentication.
        JwtRequest login = new JwtRequest();
        login.setUsername("admin");
        login.setPassword("P@$$w0rd");
        String loginToken = mockMvc
                .perform(post(Constant.AUTH_LOGIN_URL).contentType(APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(login)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Verify that token exists.
        assertThat(loginToken).isNotNull();
        assertThat(loginToken).contains("token");
    }

    @Test
    public void tokenShouldIncludePrivileges() throws ParseException {
        // Generate a token with username.
        JwtResponse token = new JwtResponse(
                jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername("admin"))
        );

        int i = token.getToken().lastIndexOf('.');

        String withoutSignature = token.getToken().substring(0, i+1);
        Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);

        String auth = String.valueOf(untrusted.getBody().get("auth"));

        assertThat(auth).contains("ADMIN");
        assertThat(auth).doesNotContain("ADMIN_ROLE_1");
    }

    @Test
    public void profileTokenShouldIncludeProfilePrivileges() throws ParseException {
        // Generate a token with username.
        JwtResponse token = new JwtResponse(
                jwtTokenUtil.generateProfileToken(userDetailsService.loadUserByUsername("admin"), Constant.ADMIN_PROFILE_UUID)
        );

        int i = token.getToken().lastIndexOf('.');

        String withoutSignature = token.getToken().substring(0, i+1);
        Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);

        String auth = String.valueOf(untrusted.getBody().get("auth"));

        assertThat(auth).contains("ADMIN_PRIVILEGE_1");
    }

    @Test
    public void testCorsConfig() throws Exception {

        // Request authentication.
        JwtRequest login = new JwtRequest();
        login.setUsername("admin");
        login.setPassword("P@$$w0rd");
        mockMvc.perform(options(LOCALHOST_9000 + Constant.AUTH_LOGIN_URL).contentType(APPLICATION_JSON_VALUE)
                .content(TestUtil.convertObjectToJsonBytes(login))).andExpect(status().isOk());
    }
}
