package com.example.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    // 1. एक सीक्रेट की (Secret Key) - यह कम से कम 256-bit लंबी होनी चाहिए (Hex Encoded)
    // आप अपनी पसंद की कोई भी लंबी स्ट्रिंग रख सकते हैं, लेकिन सुरक्षा के लिए यह सीक्रेट होनी चाहिए
    private static final String SECRET_KEY = "9a4f2c3e5d6b7a8e1f2c3d4e5f6a7b8c9a0b1c2d3e4f5a6b7c8d9e0f1a2b3c4d";

    // 2. टोकन से यूज़रनेम (Subject) निकालना
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. टोकन से कोई भी स्पेसिफिक जानकारी (Claim) निकालना
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 4. नया टोकन जेनरेट करना (सिर्फ यूज़रनेम के साथ)
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // 5. टोकन बनाने का असली लॉजिक (24 घंटे की वैलिडिटी के साथ)
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 Hours validity
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 6. टोकन को वैलिडेट करना (क्या यह इसी यूज़र का है और एक्सपायर तो नहीं हुआ?)
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 7. चेक करना कि टोकन एक्सपायर हुआ या नहीं
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 8. टोकन से एक्सपायरी डेट निकालना
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 9. टोकन को डिक्रिप्ट करके उसके अंदर का सारा डेटा (Claims) निकालना
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 10. SECRET_KEY को बाइट्स में बदल कर साइनिंग की (Signing Key) तैयार करना
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}