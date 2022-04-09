package org.openemp.api.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;

import org.openemp.api.user.model.Privilege;
import org.openemp.api.user.model.Profile;
import org.openemp.api.user.model.Role;
import org.openemp.api.user.model.User;
import org.openemp.api.user.service.ProfileService;
import org.openemp.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Jwt token util.
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secret}")
    private String JWT_KEY;

    private static final String AUTHORITIES_KEY = "auth";

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    /**
     * Gets username from token.
     *
     * @param token the token
     * @return the username from token
     */
    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @SneakyThrows
    public String getSubjectProperty(String property, String token) {
        Claims claims = getAllClaimsFromToken(token);
        Map <String, String> subject = (Map<String, String>) claims.get("sub");

        String value = subject.get(property);


        return value;
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

    // for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_KEY).parseClaimsJws(token).getBody();
    }

    // check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token for user.
     *
     * @param userDetails the user details
     * @return the string
     */
    public String generateToken(UserDetails userDetails) {
        String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_KEY, authorities);

        Date expiration = new Date(new Date().getTime() + Constant.JWT_TOKEN_VALIDITY);

        return doGenerateToken(claims, userDetails.getUsername(), expiration);
    }


    /**
     * Generate token for profile.
     *
     * @param userDetails the profile details
     * @return the jwt string
     */
    public String generateProfileToken(UserDetails userDetails, String uuid) {
        String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Profile profile = profileService.getProfile(UUID.fromString(uuid));
        
        if (userDetails.getUsername() != profile.getUser().getUsername()) {
            return "";
        } 

        String privs = "";
        for (Role role : profile.getRoles()) {
            for (Privilege privilege : role.getPrivileges()) {
                privs = String.join(",", privilege.getName(), privs);
            }
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_KEY, String.join(",", authorities, privs));

        Date expiration = new Date(new Date().getTime() + Constant.JWT_TOKEN_VALIDITY);

        return doGenerateToken(claims, userDetails.getUsername(), expiration);
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject, Date expiration) {

        byte[] keyBytes = Decoders.BASE64.decode(this.JWT_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        JSONObject payload = new JSONObject();

        User user = userService.getUserByUsername(subject);

        String uuid = user.getUuid().toString();

        Map<String, Object> sub = new HashMap<>();
        sub.put("uuid", uuid);
        sub.put("username", subject);
        sub.put("type", user.getType());
        payload.put("sub", sub);

        payload.put("iat", new Date().getTime() / 1000L);
        payload.put("exp", expiration.getTime() / 1000L);
        payload.putAll(claims);

        return Jwts.builder()
                .setPayload(payload.toJSONString())
                .signWith(key, SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    /**
     * Validate token.
     *
     * @param token       the token
     * @param userDetails the user details
     * @return the boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getSubjectProperty("username", token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
