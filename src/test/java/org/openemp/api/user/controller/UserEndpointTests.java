package org.openemp.api.user.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openemp.api.user.OpenempUserApiApplication;
import org.openemp.api.user.model.User;
import org.openemp.api.user.service.*;
import org.openemp.api.user.util.Constant;
import org.openemp.api.user.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(classes = OpenempUserApiApplication.class)
public class UserEndpointTests {

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
    public void getUserShouldNotReturnPassword() throws Exception {

        String username = "admin";

        String token
                = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(username));
        User user = userService.getUserByUsername(username);
        ResultActions result = mockMvc.perform(get(LOCALHOST_9000 + Constant.USER_ENDPOINT + "/" + user.getUuid())
                .header("Authorization", token)
                .content(""));
        String returned = result.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(returned);
        Assert.isTrue(!jsonObject.has("password"), "Password should not be returned");
        Assert.isTrue(jsonObject.has("username"), "Should return username");

    }
}
