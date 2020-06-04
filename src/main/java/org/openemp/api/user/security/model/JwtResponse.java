package org.openemp.api.user.security.model;

import java.io.Serializable;

/**
 * Jwt response.
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    
    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }

	@Override
	public String toString() {
		return "{\"token\":\"" + jwtToken + "\"}";
	}
    
    
}
