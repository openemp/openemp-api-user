package org.openemp.api.user.security.model;

import java.io.Serializable;

/**
 * The type Jwt request.
 */
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;
	private boolean rememberMe = false;

	// Need default constructor for JSON Parsing
	public JwtRequest() {

	}

	/**
	 * Instantiates a new Jwt request.
	 *
	 * @param username the username
	 * @param password the password
	 */
	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	/**
     * Instantiates a new Jwt request.
     *
     * @param username the username
     * @param password the password
     * @param rememberMe the rememberMe
     */
    public JwtRequest(String username, String password, boolean rememberMe) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRememberMe(rememberMe);
    }

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

}
