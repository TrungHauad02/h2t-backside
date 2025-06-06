package com.englishweb.h2t_backside.security;

import com.englishweb.h2t_backside.exception.AuthenticateException;
import com.englishweb.h2t_backside.model.features.User;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    @Value("${jwt.signerkey}")
    private String signerKey;

    @Value("${security.jwt.expiration.access}")
    private long accessTokenExpiration;

    @Value("${security.jwt.expiration.refresh}")
    private long refreshTokenExpiration;

    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpiration, "access");
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpiration, "refresh");
    }

    private String generateToken(User user, long expirationTime, String tokenType) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .issuer("EnglishWebApplication.com")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plusMillis(expirationTime)))
                    .claim("token_type", tokenType) // Claim phân biệt loại token
                    .claim("id", user.getId());

            if ("access".equals(tokenType)) {
                claimsBuilder.claim("role", user.getRole()); // Chỉ access token mới có quyền
            } else if ("refresh".equals(tokenType)) {
                claimsBuilder.claim("is_refresh", true); // Thêm trường để xác định refresh token
            }

            JWTClaimsSet claimsSet = claimsBuilder.build();
            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(new MACSigner(signerKey.getBytes()));

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new AuthenticateException("Failed to generate token",  SeverityEnum.HIGH,
                    new HashMap<String, Object>() {{
                        put("user", user);
                        put("expirationTime", expirationTime);
                        put("tokenType", tokenType);
                    }});
        }
    }

    public boolean validateToken(String token, boolean isAccessToken) {
        try {
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            boolean isValid = signedJWT.verify(verifier) &&
                    signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date());

            String tokenType = signedJWT.getJWTClaimsSet().getStringClaim("token_type");

            if (isAccessToken && !"access".equals(tokenType)) {
                return false; // Nếu yêu cầu access token mà nhận được refresh token -> từ chối
            }

            return isValid;
        } catch (JOSEException | ParseException e) {
            return false;
        }
    }

    public Date getExpirationFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getExpirationTime();
        } catch (ParseException e) {
            throw new AuthenticateException("Failed to get expiration from token", SeverityEnum.MEDIUM,
                    new HashMap<String, Object>() {{
                        put("token", token);
                    }});
        }
    }

    public String getEmailFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new AuthenticateException("Failed to get email from token", SeverityEnum.MEDIUM,
                    new HashMap<String, Object>() {{
                        put("token", token);
                    }});
        }
    }

    public Long getUserIdFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getLongClaim("id");
        } catch (ParseException e) {
            throw new AuthenticateException("Failed to get id from token", SeverityEnum.MEDIUM,
                    new HashMap<String, Object>() {{
                put("token", token);
            }});
        }
    }
}
