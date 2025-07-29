package com.konekt.backend.Middlewares;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import static com.konekt.backend.Security.Filter.TokenJwtConfig.*;

public class DecodedJWTValidation {
    public static String decodedJWT(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        JwtParser parser = Jwts.parser().verifyWith(SECRET_KEY).build();
        Claims claims = parser.parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
}
