package org.openemp.api.user.util;

/**
 * User Service Constants.
 */
public class Constant {

    private Constant() {
        throw new IllegalStateException("Constant class");
    }

    // JWT Constants
    public static final long JWT_TOKEN_VALIDITY = 15 * 60 * 1000L; // 15 min

    // REST API Constants

    public static final String API_VERSION = "/v1";
    public static final String API = "/api";

    public static final String API_ALL = "/api/**";

    public static final String USER_DOMAIN = "/users";
    public static final String PROFILE_DOMAIN = "/profiles";
    public static final String USER_ATTRIBUTE_DOMAIN = "/attributes";
    public static final String ATTRIBUTE_TYPE_DOMAIN = "/attributetypes";

    public static final String USER_ENDPOINT = API + API_VERSION + USER_DOMAIN;
    public static final String PROFILE_ENDPOINT = API + API_VERSION + PROFILE_DOMAIN;
    public static final String ATTRIBUTE_TYPE_ENDPOINT = API + API_VERSION + ATTRIBUTE_TYPE_DOMAIN;
    public static final String USER_ATTRIBUTE_ENDPOINT = API + API_VERSION + USER_ATTRIBUTE_DOMAIN;

    public static final String AUTH_LOGIN_URL = USER_ENDPOINT + "/authenticate";

    // Roles and Authorities

    public static final String ADMIN_ROLE = "ADMIN";

    // Demo Data

    public static final String ADMIN_USER_UUID = "ef84bd88-15bd-4b71-916c-689523f56fff";
    public static final String ADMIN_PROFILE_UUID = "790c486c-627e-4e16-a16f-28dfa82cc13b";

}
