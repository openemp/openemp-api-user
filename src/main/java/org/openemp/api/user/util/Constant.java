package org.openemp.api.user.util;

/**
 * User Service Constants.
 */
public class Constant {

	private Constant() {
		throw new IllegalStateException("Constant class");
	}

	// JWT Constants
	public static final long JWT_TOKEN_VALIDITY_WITHOUT_REMEMEBERME = 5 * 60 * 60 * 1000L;
	public static final long JWT_TOKEN_VALIDITY_WITH_REMEMBERME = 7 * 24 * 60 * 60 * 1000L; // 7 days

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

	public static final String AUTH_LOGIN_URL = USER_ENDPOINT + "/authenticate";

	// Roles and Authorities

	public static final String ADMIN_ROLE = "ADMIN";

}
