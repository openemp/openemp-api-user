package org.openemp.api.user.util;

import static org.openemp.api.user.util.Constant.JWT_TOKEN_VALIDITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Jwt token util.
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;


    @Value("${jwt.secret}")
    private String JWT_KEY;
    
    private static final String AUTHORITIES_KEY = "auth";

	/**
	 * Gets username from token.
	 *
	 * @param token the token
	 * @return the username from token
	 */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

	/**
	 * Gets expiration date from jwt token.
	 *
	 * @param token the token
	 * @return the expiration date from token
	 */
	//
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

	/**
	 * Gets claim from token.
	 *
	 * @param <T>            the type parameter
	 * @param token          the token
	 * @param claimsResolver the claims resolver
	 * @return the claim from token
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_KEY).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

	/**
	 * Generate token string.
	 *
	 * @param userDetails the user details
	 * @return the string
	 */
	//generate token for user
    public String generateToken(UserDetails userDetails) {
    	String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_KEY, authorities);
        
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, JWT_KEY).compact();
    }

	/**
	 * Validate token.
	 *
	 * @param token       the token
	 * @param userDetails the user details
	 * @return the boolean
	 */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
