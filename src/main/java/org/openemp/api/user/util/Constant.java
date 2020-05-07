package org.openemp.api.user.util;

/**
 * User Service Constants.
 */
public class Constant {

    // JWT Constants
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // REST API Constants

    public static final String API_VERSION = "/v1";

    public static final String USER_DOMAIN = "/users";
    public static final String PROFILE_DOMAIN = "/profiles";
    public static final String USER_ATTRIBUTE_DOMAIN = "/attributes";
    public static final String ATTRIBUTE_TYPE_DOMAIN = "/attributetypes";

    public static final String USER_ENDPOINT = API_VERSION + USER_DOMAIN;
    public static final String PROFILE_ENDPOINT = API_VERSION + PROFILE_DOMAIN;
    public static final String ATTRIBUTE_TYPE_ENDPOINT = API_VERSION + ATTRIBUTE_TYPE_DOMAIN;

    public static final String AUTH_LOGIN_URL = USER_ENDPOINT + "/authenticate";

    // Roles and Authorities

    public static final String ADMIN_ROLE = "adminRole";
    public static final String USER_ROLE = "userRole";
    public static final String READ_USERS = "readUsers";






}
