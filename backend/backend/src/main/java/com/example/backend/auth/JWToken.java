package com.example.backend.auth;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * JWT token utility class for encoding and decoding JSON Web Tokens.
 */
@Component
public class JWToken {

    // Constants for JWT claims
    public static final String JWT_ATTRIBUTE_NAME = "attr";
    private static final String JWT_CALLNAME_CLAIM = "sub";
    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS = "ip";

    // Instance variables representing JWT claims
    private String callName;
    private long userId;
    private String role;
    private String ipAddress;

    // Constructors
    /**
     * Constructs a new JWToken instance with specified parameters.
     *
     * @param callName The call name.
     * @param userId   The user ID.
     * @param role     The user role.
     */
    public JWToken(String callName, Long userId, String role) {
        this.callName = callName;
        this.userId = userId;
        this.role = role;
    }

    /**
     * Default constructor for JWToken.
     */
    public JWToken() {
    }

    // Method to encode a JWT
    /**
     * Encodes a JWT with specified issuer, passphrase, and expiration time.
     *
     * @param issuer     The issuer of the JWT.
     * @param passphrase The passphrase for generating the key.
     * @param expiration The expiration time of the JWT in seconds.
     * @return The encoded JWT as a string.
     */
    public String encode(String issuer, String passphrase, int expiration) {
        // Use Keys class to generate a secure key for HS512
        Key key = getKey(passphrase);

        return Jwts.builder()
                .claim(JWT_CALLNAME_CLAIM, this.callName)
                .claim(JWT_USERID_CLAIM, this.userId)
                .claim(JWT_ROLE_CLAIM, this.role)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Method to decode a JWT
    /**
     * Decodes a JWT and returns a JWToken instance.
     *
     * @param token      The JWT to decode.
     * @param passphrase The passphrase for generating the key.
     * @return The decoded JWToken.
     * @throws ExpiredJwtException    If the JWT has expired.
     * @throws MalformedJwtException  If the JWT is malformed.
     */
    public static JWToken decode(String token, String passphrase) throws ExpiredJwtException, MalformedJwtException {
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);

        Claims claims = jws.getBody();

        JWToken jwToken = new JWToken(
                claims.get(JWT_CALLNAME_CLAIM).toString(),
                Long.valueOf(claims.get(JWT_USERID_CLAIM).toString()),
                claims.get(JWT_ROLE_CLAIM).toString()
        );

        jwToken.setIpAddress((String) claims.get(JWT_IPADDRESS));
        return jwToken;
    }

    // Private method to generate a key from a passphrase
    /**
     * Generates a key from a passphrase.
     *
     * @param passphrase The passphrase for generating the key.
     * @return The generated key.
     */
    private static Key getKey(String passphrase) {
        byte[] keyBytes = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    // Getters and setters
    /**
     * Sets the call name.
     *
     * @param callName The call name to set.
     */
    public void setCallName(String callName) {
        this.callName = callName;
    }

    /**
     * Sets the user ID.
     *
     * @param userId The user ID to set.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Sets the user role.
     *
     * @param role The user role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the call name.
     *
     * @return The call name.
     */
    public String getCallName() {
        return callName;
    }

    /**
     * Gets the user ID.
     *
     * @return The user ID.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Gets the user role.
     *
     * @return The user role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the IP address.
     *
     * @param ipAddress The IP address to set.
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
