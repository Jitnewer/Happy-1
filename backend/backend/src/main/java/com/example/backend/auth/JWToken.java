package com.example.backend.auth;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWToken {
    public static final String JWT_ATTRIBUTE_NAME = "attr";
    private static final String JWT_CALLNAME_CLAIM = "sub";
    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS = "ip";


    private String callName;
    private long userId;
    private String role;
    private String ipAddress;


    public JWToken(String callName, Long userId, String role) {
        this.callName = callName;
        this.userId = userId;
        this.role = role;
    }
    public JWToken() {

    }




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

    public static JWToken decode(String token, String passphrase) throws ExpiredJwtException, MalformedJwtException {
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parser().setSigningKey(key).build()
                .parseClaimsJws(token);

        Claims claims = jws.getBody();

        JWToken jwToken = new JWToken(
                claims.get(JWT_CALLNAME_CLAIM).toString(),
                Long.valueOf(claims.get(JWT_USERID_CLAIM).toString()),
                claims.get(JWT_ROLE_CLAIM).toString()
        );



        jwToken.setIpAddress((String) claims.get(JWT_IPADDRESS));
        return jwToken;
    }



    private static Key getKey(String passphrase) {
        byte[] keyBytes = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCallName() {
        return callName;
    }

    public long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
